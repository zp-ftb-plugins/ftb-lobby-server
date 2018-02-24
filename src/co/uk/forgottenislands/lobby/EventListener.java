package co.uk.forgottenislands.lobby;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.hanging.HangingBreakByEntityEvent;
import org.bukkit.event.hanging.HangingPlaceEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.weather.WeatherChangeEvent;

public class EventListener implements Listener {
	
	private Lobby plugin;
	
	public EventListener(Lobby plugin){
		this.plugin = plugin;
		Bukkit.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void playerInteract(PlayerInteractEvent event){
		
		Player player = event.getPlayer();
		
		if(event.getClickedBlock() == null) return;
		
		if((event.getClickedBlock().getType().equals(Material.SIGN_POST)) || event.getClickedBlock().getType().equals(Material.WALL_SIGN)){
			
			Sign sign = (Sign) event.getClickedBlock().getState();
			
			if(sign.getLine(1).contains("[CHECKPOINT]")){
					
				plugin.checkpoint.setCheckpoint(player);
				
				player.sendMessage(ChatColor.GREEN + "You will now spawn here when you hit a respawn sign!");
				return;
			} else if(sign.getLine(1).contains("[RESPAWN]") && sign.getLine(2).contains("[RESPAWN]")){
				
				if(plugin.checkpoint.tpCheckpoint(player)){
					
					player.sendMessage(ChatColor.GREEN + "You have been teleported to your last checkpoint!");
				} else {
					
					player.sendMessage(ChatColor.RED + "You have not set a checkpoint yet!");
				}
			} else if(sign.getLine(1).contains("[FINISH]") && sign.getLine(2).contains("[FINISH]")){
				
				plugin.checkpoint.completeChallenge(player);
				
				player.sendMessage(ChatColor.GREEN + "Congratulations on completing the challenge! Feel free to try again at any time!");
			}
		}
	}
	
	@EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
	public void onPlayerMove(PlayerMoveEvent event){
		
		Player player = event.getPlayer();
		
		Location visitor = new Location(player.getWorld(), 70, 86, 52, -45, 0);
		Location other = new Location(player.getWorld(), 0, 63, 0, -90, 0);
		
		if(player.getLocation().getBlockY() < 57){
			
			if(Lobby.perms.getPrimaryGroup(player).equals("Visitor")){
				player.teleport(visitor.getBlock().getRelative(0, 2, 0).getLocation());
			} else {
				player.teleport(other.getBlock().getRelative(0, 2, 0).getLocation());
			}
		}
	}
	
	@EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
	public void onPlayerJoin(PlayerJoinEvent event){
		
		Player player = event.getPlayer();
		
		player.setPlayerTime(6000, false);
	}
	
	@EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
	public void onWeatherChange(WeatherChangeEvent event){
		
		if(event.toWeatherState()){
			
			event.setCancelled(true);
		}
	}
	
	@EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
	public void onEntitySpawn(CreatureSpawnEvent event){
		
		event.setCancelled(true);
	}
	
	@EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
	public void onPlayerDamage(EntityDamageEvent event){
		
		event.setCancelled(true);
	}
	
	@EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
	public void onBlockPlace(BlockPlaceEvent event){
		
		Player player = event.getPlayer();
		if(!player.isOp()){
			event.setCancelled(true);
		}
	}
	
	@EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
	public void onBlockDestroy(BlockBreakEvent event){
		
		Player player = event.getPlayer();
		if(!player.isOp()){
			event.setCancelled(true);
		}
	}
	
	@EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
	public void onBlockInteract(PlayerInteractEvent event){
		
		Player player = event.getPlayer();
		Block block = event.getClickedBlock();
		if(!player.isOp()){
			if(!block.getType().equals(Material.SIGN) || !block.getType().equals(Material.WALL_SIGN) || !block.getType().equals(Material.SIGN_POST) || !block.getType().equals(Material.WOODEN_DOOR)){
				event.setCancelled(true);
			}
		}
	}
	
	@EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
	public void onFoodChange(FoodLevelChangeEvent event){
		
		event.setFoodLevel(20);
	}
	
	@EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
	public void onItemPickup(PlayerPickupItemEvent event){
		
		
	}
	@EventHandler(ignoreCancelled = true)
	public void onHangingBreakByEntity(HangingBreakByEntityEvent event){
		
		if(event.getRemover() instanceof Player){
			
			if(event.getEntity() instanceof ItemFrame){
				
				Player player = (Player) event.getRemover();
				
				if(!player.hasPermission("itemframe.destroy")){
					
					event.setCancelled(true);
					
				}
			}
		}
	}
	
	@EventHandler(ignoreCancelled = true)
	public void onHangingPlace(HangingPlaceEvent event){
		
		Player player = event.getPlayer();
		
		if(event.getEntity() instanceof ItemFrame){
		
			if(!player.hasPermission("itemframe.place")){
				
				event.setCancelled(true);
			}
		}
	}
	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onPlayerChat(AsyncPlayerChatEvent event){
		
		Player player = event.getPlayer();
		String message = event.getMessage().toLowerCase();
		
		if(Lobby.perms.getPrimaryGroup(player).equals("God") || Lobby.perms.getPrimaryGroup(player).equals("Co-Owner") || Lobby.perms.getPrimaryGroup(player).equals("Owner") || player.isOp()){
			
			event.setCancelled(false);
			event.getRecipients().clear();
			for(Player players : Bukkit.getOnlinePlayers()){
				
				event.getRecipients().add(players);
			}
		}
		
		if(Lobby.perms.getPrimaryGroup(player).equals("Visitor")){
			
			if(plugin.getSaveFile().getBoolean("rankup.blocked." + player.getName())){
				
				player.sendMessage(ChatColor.RED + "You are currently blocked from ranking up, an admin will review your case soon!");
				return;
			}
			
			if(message.equals("1") || message.equals("2")|| message.equals("3") || message.equals("4")){
				
				if(plugin.questionTracker.containsKey(player)){
					
					plugin.question.answerQuestion(player, plugin.questionTracker.get(player) + 1, message);
				} else {
					plugin.questionTracker.put(player, 0);
					plugin.question.askQuestion(player, 1);
				}

			} else {
				
				if(!plugin.questionTracker.containsKey(player)){
					
					plugin.questionTracker.put(player, 0);
					plugin.question.askQuestion(player, 1);
				} else {
					
					plugin.question.askQuestion(player, plugin.questionTracker.get(player) + 1);
				}
			}
		} else {
			
			/*for(Player recipient : event.getRecipients()){
				
				if(Lobby.perms.getPrimaryGroup(recipient).equals("Visitor")){
					
					event.getRecipients().remove(recipient);
				}
			}*/
		}
	}
}
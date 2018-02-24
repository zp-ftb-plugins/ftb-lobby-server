package co.uk.forgottenislands.lobby;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class Checkpoints {
	
	private Lobby plugin;
	
	public Checkpoints(Lobby plugin){
		
		this.plugin = plugin;
	}
	
	public void setCheckpoint(Player player){
		
		plugin.getSaveFile().set("checkpoint." + player.getName() + ".x", player.getLocation().getBlockX());
		plugin.getSaveFile().set("checkpoint." + player.getName() + ".y", player.getLocation().getBlockY());
		plugin.getSaveFile().set("checkpoint." + player.getName() + ".z", player.getLocation().getBlockZ());
		plugin.getSaveFile().set("checkpoint." + player.getName() + ".world", player.getLocation().getWorld().getName());
		
		plugin.saveSaveFile();
	}
	
	public Boolean tpCheckpoint(Player player){
		
		if(plugin.getSaveFile().get("checkpoint." + player.getName() + ".x") == null) return false;
		
		Integer x = plugin.getSaveFile().getInt("checkpoint." + player.getName() + ".x");
		Integer y = plugin.getSaveFile().getInt("checkpoint." + player.getName() + ".y");
		Integer z = plugin.getSaveFile().getInt("checkpoint." + player.getName() + ".z");
		String worldName = plugin.getSaveFile().getString("checkpoint." + player.getName() + ".world");
		
		player.teleport(new Location(Bukkit.getWorld(worldName), x, y, z));
		
		plugin.saveSaveFile();
		
		return true;
	}
	
	public void completeChallenge(Player player){
		
		plugin.getSaveFile().set("checkpoint." + player.getName() + ".x", null);
		plugin.getSaveFile().set("checkpoint." + player.getName() + ".y", null);
		plugin.getSaveFile().set("checkpoint." + player.getName() + ".z", null);
		plugin.getSaveFile().set("checkpoint." + player.getName() + ".world", null);
		
		this.giveReward(player);
		
		plugin.saveSaveFile();
	}
	
	public void giveReward(Player player){
		
		Lobby.econ.depositPlayer(player.getName(), 2000);
		player.teleport(player.getWorld().getSpawnLocation());
		player.sendMessage(ChatColor.GREEN + "Congratulations! You have won £2000 to spend in-game!");
	}
}

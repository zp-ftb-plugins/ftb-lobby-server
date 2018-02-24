package co.uk.forgottenislands.lobby;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ArenasExecutor implements CommandExecutor{
	
	private Lobby plugin;
	
	public ArenasExecutor(Lobby plugin){
		
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
		if(!(sender instanceof Player)){
			
			sender.sendMessage(ChatColor.RED + "You must be a player to perform these commands!");
			return true;
		}
		
		Player player = (Player) sender;
		
		if(args[0].equalsIgnoreCase("set")){
			
			Bukkit.getScheduler().runTaskTimer(plugin, new SetFlight(plugin, args[1], player), 1, 0);
			player.sendMessage(ChatColor.GREEN + "Recording Started");
		}
		
		if(args[0].equalsIgnoreCase("clear")){
			
			plugin.getConfig().set("nations." + args[1], null);
			plugin.saveConfig();
		}
		
		if(args[0].equalsIgnoreCase("goto")){
			
			plugin.activeTeleports.put(player, Bukkit.getScheduler().runTaskTimer(plugin, new GetFlight(plugin, args[1], player), 1, 0));
			player.sendMessage(ChatColor.GREEN + "You are on your way to " + args[1] + " nation!");
		}
		
		return true;
	}
}
package co.uk.forgottenislands.lobby;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class SetFlight implements Runnable {
	
	private Lobby plugin;
	private String name;
	private Player player;
	
	public SetFlight(Lobby plugin, String name, Player player){
		
		this.plugin = plugin;
		this.name = name;
		this.player = player;
	}

	@Override
	public void run() {
		
		if(player.isFlying()){
			
			if(plugin.getConfig().get("nations." + name + ".tppoints.amount") == null){
				plugin.getConfig().set("nations." + name + ".tppoints.amount", 0);
			}
			Integer spawnID = plugin.getConfig().getInt("nations." + name + ".tppoints.amount") + 1;
			Double x = player.getLocation().getX();
			Double y = player.getLocation().getY();
			Double z = player.getLocation().getZ();
			Float yaw = player.getLocation().getYaw();
			Float pitch = player.getLocation().getPitch();
				
			if(plugin.getConfig().getConfigurationSection("nations." + name + ".tppoints") != null){
				
				if(plugin.getSaveFile().getConfigurationSection("myarenas.arenas." + name + "spawnX-" + spawnID) == null){
						
					plugin.getConfig().set("nations." + name + ".tppoints.x-" + spawnID, x);
					plugin.getConfig().set("nations." + name + ".tppoints.y-" + spawnID, y);
					plugin.getConfig().set("nations." + name + ".tppoints.z-" + spawnID, z);
					plugin.getConfig().set("nations." + name + ".tppoints.yaw-" + spawnID, yaw);
					plugin.getConfig().set("nations." + name + ".tppoints.pitch-" + spawnID, pitch);
					plugin.getConfig().set("nations." + name + ".tppoints.amount", spawnID);
					
					plugin.saveConfig();
				}
			}
		} else {
			
			player.sendMessage(ChatColor.RED + "Recording Stopped!");
			return;
			
		}
	}
}

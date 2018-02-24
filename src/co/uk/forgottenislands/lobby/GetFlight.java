package co.uk.forgottenislands.lobby;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class GetFlight implements Runnable {
	
	private Lobby plugin;
	private String name;
	private Player player;
	private Integer tpID = 1;
	
	public GetFlight(Lobby plugin, String name, Player player){
		
		this.plugin = plugin;
		this.name = name;
		this.player = player;
	}
	
	@Override
	public void run() { 
			
		player.setAllowFlight(true);
		player.setFlying(true);
	
		if(plugin.getConfig().get("nations." + name + ".tppoints.x-" + tpID) != null){
				
			Double x = plugin.getConfig().getDouble("nations." + name + ".tppoints.x-" + tpID);
			Double y = plugin.getConfig().getDouble("nations." + name + ".tppoints.y-" + tpID);
			Double z = plugin.getConfig().getDouble("nations." + name + ".tppoints.z-" + tpID);
			Double yawRaw = plugin.getConfig().getDouble("nations." + name + ".tppoints.yaw-" + tpID);
			Float yaw = Float.parseFloat(yawRaw.toString());
			Double pitchRaw = plugin.getConfig().getDouble("nations." + name + ".tppoints.pitch-" + tpID);
			Float pitch = Float.parseFloat(pitchRaw.toString());
			
			player.teleport(new Location(player.getWorld(), x, y, z, yaw, pitch));
			
			tpID = tpID + 2;
		
		} else {
			
			player.setFlying(false);
			player.setAllowFlight(false);
			player.sendMessage(ChatColor.GREEN + "You have arrived!");
			plugin.activeTeleports.get(player).cancel();
			plugin.activeTeleports.remove(player);
		}
	}
}

package co.uk.forgottenislands.lobby;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Logger;

import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

public class Lobby extends JavaPlugin {
	
	private FileConfiguration saveConfig;
	private File saveConfigFile;
	public static Economy econ = null;
	public static Permission perms = null;
	private static final Logger log = Logger.getLogger("Minecraft");
	
	public Checkpoints checkpoint = new Checkpoints(this);
	public Questions question = new Questions(this);
	
	public HashMap<Player, BukkitTask> activeTeleports = new HashMap<Player, BukkitTask>();
	public HashMap<Player, Integer> questionTracker = new HashMap<Player, Integer>();
	public HashMap<Player, Integer> wrongAnswer = new HashMap<Player, Integer>();
	
	public void onEnable(){
		
		new EventListener(this);
		initializeSaveFile();
		initializeEconomy();
		setupPermissions();
		this.getCommand("lobby").setExecutor(new ArenasExecutor(this));
	}
	
	public void onDisable(){
		log.info("LobbyServer Disabled!!!!");
	}
	
	public void initializeEconomy() {
		
		if (!setupEconomy() ) {
            log.info(String.format("[%s] - Disabled due to no Vault dependency found!", getDescription().getName()));
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
	}
	
	private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }
	
    private boolean setupPermissions() {
        RegisteredServiceProvider<Permission> rsp = getServer().getServicesManager().getRegistration(Permission.class);
        perms = rsp.getProvider();
        return perms != null;
    }
	
	public void reloadSaveFile() {
		if (this.saveConfigFile == null) {
			this.saveConfigFile = new File(getDataFolder(), "savefile.yml");
		}
	
		this.saveConfig = YamlConfiguration.loadConfiguration(this.saveConfigFile);
	}
	
	public void saveSaveFile() {
		try {
			this.getSaveFile().save(saveConfigFile);
		} catch (IOException e) {
			log.severe("Could not save the save file!");
		}
	}
	
	public FileConfiguration getSaveFile() {
		if (this.saveConfig == null) {
			reloadSaveFile();
		}
		return this.saveConfig;
	}
	
	public void initializeSaveFile() {
		
		final FileConfiguration save = this.getSaveFile();
		save.options().header("Arena Save File!");
		this.saveSaveFile();
	}
}
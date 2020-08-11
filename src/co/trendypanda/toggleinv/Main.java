package co.trendypanda.toggleinv;

import org.bukkit.plugin.java.JavaPlugin;

import co.trendypanda.toggleinv.cmds.KeepInventoryCommand;
import co.trendypanda.toggleinv.cmds.KeepXPCommand;

public class Main extends JavaPlugin {
	private static Main instance;
	@Override
	public void onEnable() {
		getCommand("keepinv").setExecutor(new KeepInventoryCommand());
		getCommand("keepxp").setExecutor(new KeepXPCommand());
		this.getConfig().options().copyDefaults();
		saveDefaultConfig();
		instance = this;
	}
	public static Main getInstance() {
		return instance;
	}
}
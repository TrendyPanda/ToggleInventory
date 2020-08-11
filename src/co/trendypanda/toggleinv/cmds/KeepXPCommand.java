package co.trendypanda.toggleinv.cmds;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import co.trendypanda.toggleinv.Main;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.cacheddata.CachedPermissionData;
import net.luckperms.api.model.data.DataMutateResult;
import net.luckperms.api.model.user.User;
import net.luckperms.api.node.Node;

public class KeepXPCommand implements CommandExecutor {
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (sender instanceof Player) {
			String EssentialsPerms = Main.getInstance().getConfig().getString("KeepXPTogglePermission");
			String NoPerms = Main.getInstance().getConfig().getString("NoPermissionMessage");
			String KeepXPPerms = Main.getInstance().getConfig().getString("KeepXPPermission");
			String EnableMessage = Main.getInstance().getConfig().getString("KeepXPEnableMessage");
			String DisableMessage = Main.getInstance().getConfig().getString("KeepXPDisableMessage");
			String AlreadyOn = Main.getInstance().getConfig().getString("KeepXPAlreadyOn");
			String AlreadyOff = Main.getInstance().getConfig().getString("KeepXPAlreadyOff");
			String InvalidArgs = Main.getInstance().getConfig().getString("InvalidArgs");
			Player p = (Player) sender;
			LuckPerms api = LuckPermsProvider.get();
			User player = api.getUserManager().getUser(p.getUniqueId());
			CachedPermissionData permissionData = player.getCachedData().getPermissionData();
			DataMutateResult result;
			if (!p.hasPermission(KeepXPPerms)) {
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', NoPerms));
				return false;
			}
			if (args.length == 0) {
				if (permissionData.checkPermission(EssentialsPerms).asBoolean() == false) {
					result = player.data().add(Node.builder(EssentialsPerms).build());
					p.sendMessage(ChatColor.translateAlternateColorCodes('&', EnableMessage));
				} else {
					result = player.data().remove(Node.builder(EssentialsPerms).build());
					p.sendMessage(ChatColor.translateAlternateColorCodes('&', DisableMessage));
				}
				api.getUserManager().saveUser(player);
				return false;
			}
			if (args.length != 0) {

				if (args[0].equalsIgnoreCase("on")) {
					if (permissionData.checkPermission(EssentialsPerms).asBoolean() == false) {
						result = player.data().add(Node.builder(EssentialsPerms).build());
						p.sendMessage(ChatColor.translateAlternateColorCodes('&', EnableMessage));
					} else {
						p.sendMessage(ChatColor.translateAlternateColorCodes('&', AlreadyOn));
					}
					return false;
				}
				if (args[0].equalsIgnoreCase("off")) {
					if (permissionData.checkPermission(EssentialsPerms).asBoolean() == false) {
						p.sendMessage(ChatColor.translateAlternateColorCodes('&', AlreadyOff));
					} else {
						result = player.data().remove(Node.builder(EssentialsPerms).build());
						p.sendMessage(ChatColor.translateAlternateColorCodes('&', DisableMessage));
					}
					return false;
				}
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', InvalidArgs));
				return false;

			}

		}

		sender.sendMessage("Only players can execute this command!");
		return false;
	}
}
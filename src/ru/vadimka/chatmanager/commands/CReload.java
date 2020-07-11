package ru.vadimka.chatmanager.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import ru.vadimka.chatmanager.Config;

public class CReload implements CommandExecutor {
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String labale, String[] args) {
		if (sender.isPermissionSet("chatmanager.reload")) {
			Config.loadMainConfig();
			Config.loadMessages();
			Config.rooms.clear();
			sender.sendMessage(Config.PLUGIN_PREFIX+" Плагин перезагружен");
		} else {
			sender.sendMessage(Config.PLUGIN_PREFIX+" "+Config.NOT_PERMITED);
		}
		return true;
	}

}

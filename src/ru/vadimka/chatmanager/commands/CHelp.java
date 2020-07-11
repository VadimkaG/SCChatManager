package ru.vadimka.chatmanager.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import ru.vadimka.chatmanager.Config;

public class CHelp implements CommandExecutor {
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String labale, String[] args) {
		sender.sendMessage(Config.PLUGIN_PREFIX+" "+Config.HELP);
		if (sender.isPermissionSet("chatmanager.room.list")) {
			sender.sendMessage(Config.HELP_LIST);
		}
		if (sender.isPermissionSet("chatmanager.room.own")) {
			sender.sendMessage(Config.HELP_CREATE);
			sender.sendMessage(Config.HELP_ACCEPT);
		}
		if (sender.isPermissionSet("chatmanager.room.admin")) {
			sender.sendMessage(Config.HELP_RELOAD);
			sender.sendMessage(Config.HELP_REMOVE);
		}
		if (sender.isPermissionSet("chatmanager.room.join")) {
			sender.sendMessage(Config.HELP_JOIN);
			sender.sendMessage(Config.HELP_LEAVE);
			sender.sendMessage(Config.HELP_ROOMUSERS);
			sender.sendMessage(Config.HELP_TOGGLE_GLOBAL_CHAT);
		}
		return true;
	}
}

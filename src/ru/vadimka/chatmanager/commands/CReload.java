package ru.vadimka.chatmanager.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import ru.vadimka.chatmanager.ChatManager;
import ru.vadimka.chatmanager.Config;

public class CReload implements CommandExecutor {
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String labale, String[] args) {
		if (sender.isPermissionSet("chatmanager.reload")) {
			Config.loadMainConfig();
			ChatManager.getRoom("admin", ChatManager.getInstance()).setChatCommand(Config.CHATCOMMAND_ADMIN);
			Config.loadMessages();
			sender.sendMessage("Плагин перезагружен");
		} else {
			sender.sendMessage(Config.NOT_PERMITED);
		}
		return true;
	}

}

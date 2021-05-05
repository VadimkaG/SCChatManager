package ru.vadimka.chatmanager.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import ru.vadimka.chatmanager.Config;
import ru.vadimka.chatmanager.metas.GlobalChatEnabledMeta;

public class CToggleGlobalChat implements CommandExecutor {
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String labale, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			if (GlobalChatEnabledMeta.get(player)) {
				GlobalChatEnabledMeta.set(player, false);
				sender.sendMessage(Config.GLOBAL_CHAT_OFF);
			} else {
				GlobalChatEnabledMeta.set(player, true);
				sender.sendMessage(Config.GLOBAL_CHAT_ON);
			}
		} else
			sender.sendMessage("Эта команда только для игроков");
		return true;
	}
}

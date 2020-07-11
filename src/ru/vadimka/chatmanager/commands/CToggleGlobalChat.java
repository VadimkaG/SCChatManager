package ru.vadimka.chatmanager.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import ru.vadimka.chatmanager.Config;
import ru.vadimka.chatmanager.objects.PlayerStat;

public class CToggleGlobalChat implements CommandExecutor {
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String labale, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			PlayerStat ps = Config.playerStat.get(player.getName());
			if (!ps.getRoom().equalsIgnoreCase("")) {
				if (ps.changeGlobalChat())
					sender.sendMessage(Config.PLUGIN_PREFIX+" "+Config.GLOBAL_CHAT_ON);
				else 
					sender.sendMessage(Config.PLUGIN_PREFIX+" "+Config.GLOBAL_CHAT_OFF);
			} else {
				sender.sendMessage(Config.PLUGIN_PREFIX+" "+Config.ROOM_NOT_JOINED);
			}
		} else
			sender.sendMessage(Config.PLUGIN_PREFIX+" Эта команда только для игроков");
		return true;
	}
}

package ru.vadimka.chatmanager.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import ru.vadimka.chatmanager.Config;
import ru.vadimka.chatmanager.Utils;
import ru.vadimka.chatmanager.objects.Room;

public class CCreateRoom implements CommandExecutor {
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String labale, String[] args) {
		String message = "";
		if (sender instanceof Player) {
			Player player = (Player) sender;
			if (sender.isPermissionSet("chatmanager.room.own")) {
				if (Config.rooms.containsKey(player.getName())) {
					message = Config.ROOM_EXISTS;
					message = Utils.getColors(message.replace("%ROOM%", sender.getName()));
				} else {
					if (args.length == 0) {
						Config.rooms.put(sender.getName(), new Room(player));
						message = Config.ROOM_CREATED;
						message = Utils.getColors(message.replace("%ROOM%", sender.getName()));
					} else {
						message = Config.USAGE_ROOM_CREATE;
					}
				}
			} else {
				message = Config.NOT_PERMITED;
			}
		} else {
			message = "Эта команда только для игроков";
		}
		sender.sendMessage(Config.PLUGIN_PREFIX+" "+message);
		return true;
	}

}

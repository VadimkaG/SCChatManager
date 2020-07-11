package ru.vadimka.chatmanager.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import ru.vadimka.chatmanager.Config;
import ru.vadimka.chatmanager.Utils;

public class CRemoveRoom implements CommandExecutor {
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String labale, String[] args) {
		String message = "";
		if (sender instanceof Player) {
			Player player = (Player) sender;
			if (sender.isPermissionSet("chatmanager.room.admin")) {
				if (!Config.rooms.containsKey(player.getName())) {
					message = Config.ROOM_NOT_EXISTS;
					message = Utils.getColors(message.replace("%ROOM%", sender.getName()));
				} else {
					if (args.length == 1) {
						//Player owner = Config.loader.getServer().getPlayer(args[0]);
						if (Config.rooms.containsKey(args[0])) {
							Utils.deleteRoom(args[0]);
							message = Utils.getColors(Config.ROOM_REMOVED.replace("%ROOM%", args[0]));
						} else {
							sender.sendMessage(Config.ROOM_NOT_EXISTS.replace("%ROOM%", args[0]));
						}
					} else {
						message = Config.USAGE_ROOM_REMOVE;
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

package ru.vadimka.chatmanager.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import ru.vadimka.chatmanager.Config;
import ru.vadimka.chatmanager.Utils;

public class CJoinRoom implements CommandExecutor {
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String labale, String[] args) {
		String message = "";
		if (sender instanceof Player) {
			Player player = (Player) sender;
			if (sender.isPermissionSet("chatmanager.room.join")) {
				String playerRoom = Config.playerStat.get(player.getName()).getRoom();
				if (playerRoom.equalsIgnoreCase("")) {
					if (args.length == 1) {
						if (Config.rooms.containsKey(args[0])) {
							Config.rooms.get(args[0]).addWaitingPlayer(player);
							message = Utils.getColors(Config.REQUEST_SENDED);
						} else {
							message = Utils.getColors(Config.ROOM_NOT_EXISTS.replace("%ROOM%", args[0]));
						}
					} else {
						message = Utils.getColors(Config.USAGE_ROOM_JOIN);
					}
				} else {
					message = Config.ROOM_ALREADY_JOINED;
					message = Utils.getColors(message.replace("%ROOM%", playerRoom));
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

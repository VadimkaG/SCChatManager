package ru.vadimka.chatmanager.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import ru.vadimka.chatmanager.Config;
import ru.vadimka.chatmanager.Utils;
import ru.vadimka.chatmanager.objects.Room;

public class CListRoomUsers implements CommandExecutor {
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String labale, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			if (sender.isPermissionSet("chatmanager.room.join")) {
				String playerRoom = Config.playerStat.get(player.getName()).getRoom();
				Room room = Config.rooms.get(playerRoom);
				if (!playerRoom.equalsIgnoreCase("")) {
					player.sendMessage(Config.ROOMS_LIST_USERS);
					for (Player p : room.getPlayers()) {
						player.sendMessage(Utils.getColors(Config.ROOMS_LIST_USERS_ITEM.replace("%NAME%", p.getName())));
					}
				} else {
					sender.sendMessage(Config.PLUGIN_PREFIX+" "+Config.ROOM_NOT_JOINED);
				}
			} else {
				sender.sendMessage(Config.PLUGIN_PREFIX+" "+Config.NOT_PERMITED);
			}
		} else {
			sender.sendMessage("Эта команда только для игроков");
		}
		return true;
	}

}

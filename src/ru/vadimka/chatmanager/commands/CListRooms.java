package ru.vadimka.chatmanager.commands;

import java.util.Map;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import ru.vadimka.chatmanager.Config;
import ru.vadimka.chatmanager.Utils;
import ru.vadimka.chatmanager.objects.Room;

public class CListRooms implements CommandExecutor {
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String labale, String[] args) {
		if (sender.isPermissionSet("chatmanager.room.list")) {
			if (Config.rooms.isEmpty()) {
				sender.sendMessage(Config.PLUGIN_PREFIX+" "+Config.ROOMS_LIST_EMPTY);
			} else {
				sender.sendMessage(Config.PLUGIN_PREFIX+" "+Config.ROOMS_LIST);
				for (Map.Entry<String, Room> entry: Config.rooms.entrySet()) {
					String key = entry.getKey();
					sender.sendMessage(Config.PLUGIN_PREFIX+" "+Utils.getColors(Config.ROOMS_LIST_ITEM.replace("%ROOM%", key)));
				}
			}
		} else {
			sender.sendMessage(Config.PLUGIN_PREFIX+" "+Config.NOT_PERMITED);
		}
		return true;
	}

}

package ru.vadimka.chatmanager.commands;

import java.util.HashMap;
import java.util.Map.Entry;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import ru.vadimka.chatmanager.ChatManager;
import ru.vadimka.chatmanager.Config;
import ru.vadimka.chatmanager.Room;

public class CListRooms implements CommandExecutor {
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String labale, String[] args) {
		if (sender.isPermissionSet("chatmanager.room.list")) {
			HashMap<String, Room> rooms = ChatManager.listRooms();
			String message = Config.ROOMS_LIST_HEADER;
			for (Entry<String, Room> roomEntry : rooms.entrySet()) {
				message += "\n"+Config.ROOMS_LIST
						.replace("%ALIAS%", roomEntry.getValue().getPlugin().getName()+"::"+roomEntry.getValue().getAlias())
						.replace("%NAME%", roomEntry.getValue().getName());
				
			}
			sender.sendMessage(message);
		} else
			sender.sendMessage(Config.NOT_PERMITED);
		return true;
	}

}

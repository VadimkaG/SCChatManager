package ru.vadimka.chatmanager.commands;

import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import ru.vadimka.chatmanager.ChatManager;
import ru.vadimka.chatmanager.Config;
import ru.vadimka.chatmanager.Room;

public class CListRoomUsers implements CommandExecutor {
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String labale, String[] args) {
		if (args.length != 1)
			return false;
		Room room = ChatManager.getRoomGlobal(args[0]);
		
		if (room == null) {
			sender.sendMessage(Config.ROOM_NOT_EXISTS);
			return true;
		}
		
		if (sender instanceof Player) {
			Player player = (Player)sender;
			if (!player.isPermissionSet("chatmanager.room.roster")) {
				sender.sendMessage(Config.NOT_PERMITED);
				return true;
			}
			if (!player.isPermissionSet("chatmanager.room.roster.other") && !room.hasPlayer(player)) {
				sender.sendMessage(Config.NOT_PERMITED);
				return true;
			}
		}
		
		List<Player> players = room.getPlayers();
		
		String message = Config.ROOMS_ROSTER_HEADER;
		for (Player player : players) {
			message += "\n"+(Config.ROOMS_ROSTER.replaceAll("%NAME%", player.getName()));
		}
		sender.sendMessage(message);
		
		return true;
	}

}

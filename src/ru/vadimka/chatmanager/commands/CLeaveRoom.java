package ru.vadimka.chatmanager.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import ru.vadimka.chatmanager.Config;
import ru.vadimka.chatmanager.Utils;
import ru.vadimka.chatmanager.objects.Room;

public class CLeaveRoom implements CommandExecutor {
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String labale, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			if (sender.isPermissionSet("chatmanager.room.join")) {
				Room room = Config.rooms.get(Config.playerStat.get(player.getName()).getRoom());
				if (room.isOwner(player)) {
					Utils.deleteRoom(player.getName());
				} else {
					room.kickPlayer(player);
					player.sendMessage(Config.PLUGIN_PREFIX+" "+Config.YOU_LEAVE);
				}
			} else {
				sender.sendMessage(Config.PLUGIN_PREFIX+" "+Config.NOT_PERMITED);
			}
		}
		return true;
	}

}

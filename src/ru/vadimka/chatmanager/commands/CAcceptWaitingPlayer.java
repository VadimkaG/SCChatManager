package ru.vadimka.chatmanager.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import ru.vadimka.chatmanager.Config;
import ru.vadimka.chatmanager.Utils;

public class CAcceptWaitingPlayer implements CommandExecutor {
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String labale, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			if (sender.isPermissionSet("chatmanager.room.own")) {
				if (Config.rooms.containsKey(player.getName()) && Config.rooms.get(player.getName()).waitExist()) {
					//String message = Utils.getColors(Config.JOINED_ROOM.replace("%PLAYER%", Config.rooms.get(player.getName()).getWaitingPlayer().getName()));
					Config.rooms.get(player.getName()).acceptJoin();
					/*List<Player> roomPlayers = Config.rooms.get(player.getName()).getPlayers();
					for (int i=0; i < roomPlayers.size();i++) {
						roomPlayers.get(i).sendMessage(Utils.getColors(message.replace("%PLAYER%", player.getName())));
					}*/
				} else {
					sender.sendMessage(Config.PLUGIN_PREFIX+" "+Utils.getColors(Config.REQUEST_NOT_EXIST));
				}
			}
		} else {
			sender.sendMessage(Config.PLUGIN_PREFIX+" "+"Эта команда только для игроков");
		}
		return true;
	}

}

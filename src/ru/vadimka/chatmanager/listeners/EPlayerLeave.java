package ru.vadimka.chatmanager.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import ru.vadimka.chatmanager.Config;
import ru.vadimka.chatmanager.Utils;
import ru.vadimka.chatmanager.objects.PlayerStat;
import ru.vadimka.chatmanager.objects.Room;

public class EPlayerLeave implements Listener {
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent e) {
		Player player = e.getPlayer();
		PlayerStat playerStat = Config.playerStat.get(player.getName());
		if (playerStat == null) return;
		Room room = Config.rooms.get(Config.playerStat.get(player.getName()).getRoom());
		if (room != null && room.isOwner(player)) {
			Utils.deleteRoom(Config.playerStat.get(player.getName()).getRoom());
		} else if (room != null) {
			if (room != null) {
				room.kickPlayer(player);
			}
		}
		Config.playerStat.remove(player.getName());
	}
}

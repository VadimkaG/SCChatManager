package ru.vadimka.chatmanager.listeners;

import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import ru.vadimka.chatmanager.ChatManager;
import ru.vadimka.chatmanager.Room;
import ru.vadimka.chatmanager.metas.GlobalChatEnabledMeta;
import ru.vadimka.chatmanager.metas.LastMessageMeta;
import ru.vadimka.chatmanager.metas.RoomMeta;

public class PlayerListener implements Listener {
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent e) {
		Player player = e.getPlayer();
		List<String> rooms = RoomMeta.get(player).cloneList();
		for (String roomAlias : rooms) {
			Room room = ChatManager.getRoomGlobal(roomAlias);
			if (room != null)
				room.kickPlayer(player);
		}
	}
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
		Player player = e.getPlayer();
		LastMessageMeta.set(player, System.currentTimeMillis());
		RoomMeta.set(player, new RoomMeta());
		GlobalChatEnabledMeta.set(player, true);
		
		if (player.isPermissionSet("chatmanager.adminchat"))
			ChatManager.getRoom("admin", ChatManager.getInstance()).addPlayer(player);
	}
}

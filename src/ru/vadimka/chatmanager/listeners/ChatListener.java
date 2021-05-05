package ru.vadimka.chatmanager.listeners;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import ru.vadimka.chatmanager.ChatManager;
import ru.vadimka.chatmanager.Config;
import ru.vadimka.chatmanager.Room;
import ru.vadimka.chatmanager.chatreplacers.ChatTemplateReplacer;
import ru.vadimka.chatmanager.metas.GlobalChatEnabledMeta;
import ru.vadimka.chatmanager.metas.LastMessageMeta;
import ru.vadimka.chatmanager.metas.RoomMeta;

public class ChatListener implements Listener {
	
	@EventHandler
	public void onChat(AsyncPlayerChatEvent event) {
	// ====== Пред инициализация =======
		if (event.isCancelled()) return;
		Player player = event.getPlayer();
		if (player == null) return;
		String message = event.getMessage();
	// =================================

	// ==================== Частота отправки сообщения ============================
		if (Config.MESASGE_INTERVAL > 0) {
			Long Interval = System.currentTimeMillis() - LastMessageMeta.get(player);
			if (Interval <= Config.MESASGE_INTERVAL) {
				player.sendMessage(Config.MESSAGE_FAST
						.replaceAll("%TIME%", String.valueOf(Config.MESASGE_INTERVAL>999?Config.MESASGE_INTERVAL/1000.0:Config.MESASGE_INTERVAL))
						.replaceAll("%TYPE%", Config.MESASGE_INTERVAL>999?Config.MESSAGE_SECONDS:Config.MESSAGE_MILISECONDS)
					);
				event.setCancelled(true);
				return;
			}
		}
	// ============================================================================

	// ======= Проверка условия отправки сообщения в комнату =========
		List<String> playerRooms = RoomMeta.get(player).asList();
		
		Room sendToRoom = null;
		for (String roomAlias : playerRooms) {
			Room room = ChatManager.getRoomGlobal(roomAlias);
			if (room!= null && message.startsWith(room.getChatCommand())) {
				sendToRoom = room;
				break;
			}
		}
		
		// Если сообщение пишется в общий чат, но общий чат выключен
		if (sendToRoom == null && !GlobalChatEnabledMeta.get(player)) {
			if (playerRooms.size() > 0) {
				Room room = ChatManager.getRoomGlobal(playerRooms.get(0));
				if (room != null)
					sendToRoom = room;
			} else {
				event.setCancelled(true);
				return;
			}
		}
		
	// ===============================================================

	// ========= Поиск получателей  ===============
		
		event.getRecipients().clear();
		for (Player p: Bukkit.getOnlinePlayers()) {
			if (sendToRoom != null && sendToRoom.hasPlayer(p)) {
				event.getRecipients().add(p);
			} else if (sendToRoom == null && GlobalChatEnabledMeta.get(p))
				event.getRecipients().add(p);
		}

	// =============================================

	// ===== Обработка формата сообщения =====
		String messageFormat = Config.MESSAGE_FORMAT;
		
		if (sendToRoom != null) {
			messageFormat = Config.MESSAGE_ROOM_FORMAT;
			messageFormat = messageFormat.replace("%ROOM%", sendToRoom.getName());
		}
		
		messageFormat = messageFormat.replace("%PLAYER%", player.getName());
		messageFormat = messageFormat.replace("%DISPLAYNAME%", player.getDisplayName());
		messageFormat = messageFormat.replace("%WORLD%", player.getWorld().getName());
		
		for (ChatTemplateReplacer replacer: ChatManager.getChatReplacers()) {
			messageFormat = messageFormat.replace(replacer.template(), replacer.replace(player));
		}
		
		messageFormat = Config.replaceColorCodes(messageFormat);
		
		messageFormat = messageFormat.replace("%MESSAGE%", "%2$s");
		
	// ===========================================
		
	// Пост инициализация
		
		// Красим сообщение пользователя
		if (player.hasPermission("chatmanager.color")) {
			message = Config.replaceColorCodes(message);
			event.setMessage(message);
		
		// Если прав на цвет нет - очищаем цвета
		} else {
			message = Config.clearColorCodes(message);
			event.setMessage(message);
		}
		
		// Убираем управляющие символы комнаты
		if (sendToRoom != null) {
			message = message.replaceFirst(sendToRoom.getChatCommand(), "");
			event.setMessage(message);
		}
		// ============================
		
		LastMessageMeta.set(player, System.currentTimeMillis());
		event.setFormat(messageFormat);
		
	// ==================
	}
}

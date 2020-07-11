package ru.vadimka.chatmanager.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import ru.vadimka.chatmanager.Config;
import ru.vadimka.chatmanager.Utils;
import ru.vadimka.chatmanager.objects.PlayerStat;
import ru.vadimka.chatmanager.objects.Room;

public class EChat implements Listener {
	
	@EventHandler
	public void onChat(AsyncPlayerChatEvent event) {
		// Если эвент отменен, то нет смысла что то делать
		if (event.isCancelled()) return;
		
	// Пред инициализация
		Player player = event.getPlayer();
		PlayerStat stat = Config.playerStat.get(player.getName());
		if (Config.MESASGE_INTERVAL > 0) {
			Long Interval = System.currentTimeMillis() - stat.getTimeLastMessage();
			if (Interval <= Config.MESASGE_INTERVAL) {
				player.sendMessage(Config.PLUGIN_PREFIX+" "+Config.MESSAGE_FAST.replace("%TIME%", String.valueOf(Config.MESASGE_INTERVAL)));
				event.setCancelled(true);
				return;
			}
		}
		String message = event.getMessage();
		Boolean isGlobalChat = message.startsWith("!");
		Boolean isAdminChat;
		if (message.startsWith("@") && player.isPermissionSet("chatmanager.adminchat")) isAdminChat = true;
		else isAdminChat = false;
		Boolean inRoom = false;
	// ===================
		
	// ========= Обработка сообщения =======
		
		Room playerRoom = null;
		String playerRoomName = stat.getRoom();
		
		// Поиск группы отправителя
		if (!stat.getRoom().equalsIgnoreCase("")) {
			playerRoom = Config.rooms.get(stat.getRoom());
			inRoom = playerRoom.inRoom(player);
		}
		
		// Если отправителя нет в группе, то он использует глобальный чат
		if (!inRoom) isGlobalChat = true;
		
		PlayerStat pStat; // Статистика
		String pRoomName = ""; // Имя комнаты
		
		event.getRecipients().clear();
		for (Player p : Config.loader.getServer().getOnlinePlayers()) {
			pStat = Config.playerStat.get(p.getName());
			pRoomName = pStat.getRoom();
			
			// Если сообщение отправлено в админский чат и у пользователя есть права
			//if (isAdminChat) if (p.isPermissionSet("chatmanager.adminchat")) event.getRecipients().add(p);
			
			// Получатель с группой
			if (!pRoomName.equalsIgnoreCase("")) {
				
				// Если отправлено в глобальный чат и у получателя не выключен глобальный чат
				if (isGlobalChat && pStat.isGlobalChat()) event.getRecipients().add(p);
				
				// Если отправитель в группе и группы равны
				else if (inRoom && pRoomName.equalsIgnoreCase(playerRoomName)) event.getRecipients().add(p);
			
			// =======================
			
			// Получатель без группы
				
			} else {
				
				// Если отправлено в глобальный чат
				if (isGlobalChat) event.getRecipients().add(p);
				
			}
			
			// =======================
		}
		
	// =============================================
		
	// ===== Обработка формата сообщения =====
		String messageFormat = Config.MESSAGE_FORMAT;
		
		if (inRoom && !isGlobalChat && !isAdminChat) {
			messageFormat = Config.MESSAGE_ROOM_FORMAT;
			messageFormat = messageFormat.replace("%ROOM%", player.getName());
		}
		
		else if (isAdminChat) {
			messageFormat = Config.MESSAGE_ADMIN_FORMAT;
		}
		
		messageFormat = messageFormat.replace("%PLAYER%", player.getName());
		messageFormat = messageFormat.replace("%DISPLAYNAME%", player.getDisplayName());
		messageFormat = messageFormat.replace("%WORLD%", player.getWorld().getName());
		
		if (Config.getChat() != null) {
			messageFormat = messageFormat.replace("%PREFIX%", Config.getChat().getPlayerPrefix(player));
			messageFormat = messageFormat.replace("%SUFFIX%", Config.getChat().getPlayerSuffix(player));
		} else {
			messageFormat = messageFormat.replace("%PREFIX%", "");
			messageFormat = messageFormat.replace("%SUFFIX%", "");
		}
		
		messageFormat = Utils.getColors(messageFormat);
		
		messageFormat = messageFormat.replace("%MESSAGE%", "%2$s");
		
	// ===========================================
		
	// Пост инициализация
		
		// Красим сообщение пользователя
		if (player.hasPermission("chatmanager.color")) {
			message = Utils.getColors(message);
			event.setMessage(message);
		}
		
		// Убираем управляющие символы
		if (message.startsWith("!")) { // Глобальный чат
			message = message.replaceFirst("!", "");
			event.setMessage(message);
		}
		if (message.startsWith("@")) { // Админ-чат
			message = message.replaceFirst("@", "");
			event.setMessage(message);
		}
		// ============================
		
		stat.setTimeLastMessage(System.currentTimeMillis());
		event.setFormat(messageFormat);
		
	// ==================
	}
}

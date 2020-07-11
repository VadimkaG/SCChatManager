package ru.vadimka.chatmanager.objects;

import org.bukkit.entity.Player;

import ru.vadimka.chatmanager.Config;

public class PlayerStat {
	private Long TIME_LAST_MESSAGE;
	private Boolean ENABLE_GLOBAL_CHAT;
	private String CURENT_ROOM;
	private Player PLAYER;
	
	public PlayerStat(Player player) {
		TIME_LAST_MESSAGE = System.currentTimeMillis();
		ENABLE_GLOBAL_CHAT = true;
		CURENT_ROOM = "";
		PLAYER = player;
	}
	/**
	 * Установить комнату для пользователя
	 * @param room - имя комнаты
	 */
	public void setRoom(String room) {
		if (room.equalsIgnoreCase("")) ENABLE_GLOBAL_CHAT = true;
		CURENT_ROOM = room;
	}
	/**
	 * Получить комнату пользователя
	 * @return
	 */
	public String getRoom() {
		if (!Config.rooms.containsKey(CURENT_ROOM) || !Config.rooms.get(CURENT_ROOM).inRoom(PLAYER))
			return "";
		return CURENT_ROOM;
	}
	/**
	 * Утсановить дату последнего сообщения
	 * @param time - timestamp
	 */
	public void setTimeLastMessage(Long time) {
		TIME_LAST_MESSAGE = time;
	}
	/**
	 * Получить дату последнего отправленного сообщения
	 * ДЖата выводится в timestamp
	 * @return
	 */
	public Long getTimeLastMessage() {
		return TIME_LAST_MESSAGE;
	}
	/**
	 * Разрешить или запретить показ глобального чата
	 * @return Boolean
	 */
	public Boolean changeGlobalChat() {
		if (ENABLE_GLOBAL_CHAT)
			ENABLE_GLOBAL_CHAT = false;
		else
			ENABLE_GLOBAL_CHAT = true;
		return ENABLE_GLOBAL_CHAT;
	}
	/**
	 * Показывать ли глобальный чат
	 * @return Boolean
	 */
	public Boolean isGlobalChat() {
		return ENABLE_GLOBAL_CHAT;
	}
}

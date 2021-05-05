package ru.vadimka.chatmanager.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import ru.vadimka.chatmanager.Room;

public class PlayerLeaveChatRoomEvent extends Event {
	private static final HandlerList handlers = new HandlerList();
	protected Player player;
	protected Room room;
	public PlayerLeaveChatRoomEvent(Player player, Room room) {
		this.player = player;
		this.room = room;
	}
	/**
	 * Получить игрока
	 * @return
	 */
	public Player getPlayer() {
		return player;
	}
	/**
	 * Получить комнату, в которую он зашел
	 * @return
	 */
	public Room getRoom() {
		return room;
	}
	@Override
	public HandlerList getHandlers() {
		return handlers;
	}
	public static HandlerList getHandlerList() {
		return handlers;
	}
}

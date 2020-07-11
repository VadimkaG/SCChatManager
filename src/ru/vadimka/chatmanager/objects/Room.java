package ru.vadimka.chatmanager.objects;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;

import ru.vadimka.chatmanager.Config;
import ru.vadimka.chatmanager.Utils;

public class Room {
	private List<Player> users;
	private Player owner = null;
	private Player waitPlayer = null;
	
	public Room(Player RoomOwner) {
		users = new ArrayList<Player>();
		if (RoomOwner != null) {
			owner = RoomOwner;
			users.add(owner);
			Config.playerStat.get(owner.getName()).setRoom(RoomOwner.getName());
		} else
			owner = null;
	}
	/**
	 * Получить имя комнаты
	 * @return String
	 */
	private String getName() {
		if (ownerExists())
			return owner.getName();
		else return "";
	}
	/**
	 * Есть ли владелец у этой комнаты
	 * FIXME: На данный момент функционал комнат без владельцев
	 * не предусмотрен
	 * @return Boolean
	 */
	private Boolean ownerExists() {
		if (owner == null)
			return false;
		else
			return true;
	}
	/**
	 * Принять ожидающего игрока в комнату
	 */
	public void acceptJoin() {
		if (waitExist()) {
			addPlayer(waitPlayer);
			waitPlayer = null;
		}
	}
	/**
	 * Если ли игроки, ожидающие решения
	 * @return
	 */
	public Boolean waitExist() {
		if (waitPlayer == null)
			return false;
		else
			return true;
	}
	/**
	 * Добавить игрока, ожидающего решения
	 * о принятии его в комнату
	 * @param player
	 */
	public void addWaitingPlayer(Player player) {
		if (player.isOnline() && !inRoom(player)) {
			waitPlayer = player;
			if (ownerExists()) {
				owner.sendMessage(Config.PLUGIN_PREFIX+" "+Config.REQUEST_JOIN.replace("%PLAYER%", player.getName()));
			}
		}
	}
	/**
	 * Список игроков комнаты
	 * @return List<Player>
	 */
	public List<Player> getPlayers() {
		return users;
	}
	/**
	 * Находится ли пользователь в данной комнате
	 * @param player
	 * @return
	 */
	public Boolean inRoom(Player player) {
		//if (!player.isOnline()) return false;
		return users.contains(player);
	}
	/**
	 * Является ли пользователем владельцем данной комнаты
	 * @param player
	 * @return
	 */
	public Boolean isOwner(Player player) {
		if (ownerExists() && player.equals(owner)) return true;
		return false;
	}
	/**
	 * Выкинуть всех пользователей из комнаты
	 */
	public void removeAllPlayers() {
		for (Player player : users) {
			Config.playerStat.get(player.getName()).setRoom("");
		}
		users.clear();
	}
	/**
	 * Выкинуть пользователя из комнаты
	 * @param player - пользователь
	 * @return Boolean
	 */
	public Boolean kickPlayer(Player player) {
		if (!inRoom(player) || isOwner(player)) return false;
		users.remove(player);
		Config.playerStat.get(player.getName()).setRoom("");
		String message = Config.PLUGIN_PREFIX+" "+Utils.getColors(Config.PLAYER_LEAVE.replace("%PLAYER%", player.getName()));
		for (Player p : users) {
			p.sendMessage(message);
		}
		return true;
	}
	/**
	 * Добавить пользователя в комнату
	 * @param player - игрок
	 * @return Boolean
	 */
	public Boolean addPlayer(Player player) {
		if (users.contains(player)) return false;
		users.add(player);
		Config.playerStat.get(player.getName()).setRoom(getName());
		String message = Config.PLUGIN_PREFIX+" "+Utils.getColors(Config.JOINED_ROOM.replace("%PLAYER%", player.getName()));
		for (Player p : users) {
			p.sendMessage(message);
		}
		return true;
	}
}

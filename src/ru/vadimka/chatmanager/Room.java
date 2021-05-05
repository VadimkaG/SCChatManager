package ru.vadimka.chatmanager;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import ru.vadimka.chatmanager.events.PlayerJoinChatRoomEvent;
import ru.vadimka.chatmanager.events.PlayerLeaveChatRoomEvent;
import ru.vadimka.chatmanager.metas.RoomMeta;

public class Room {
	protected String alias;
	protected String name;
	protected String chatCommand;
	protected List<Player> users;
	protected Plugin plugin;
	protected List<Player> whiteList;
	
	/**
	 * Создать комнату
	 * @param alias - Идентификатор комнаты
	 * @param name  - Название комнаты
	 * @param plugin  - Плагин, для которго создается комната
	 */
	public Room(String alias,String name, String chatCommand,Plugin plugin) {
		users = new ArrayList<Player>();
		whiteList = new ArrayList<>();
		this.name = name;
		this.alias = alias;
		this.plugin = plugin;
		this.chatCommand = chatCommand;
	}
	/**
	 * Создать комнату с такимже alias, как и имя
	 * @param name - наименование комнаты
	 * @param plugin  - Плагин, для которго создается комната
	 */
	public Room(String name, String chatCommand,Plugin plugin) {
		this(name,name,chatCommand,plugin);
	}
	/**
	 * Получить alias группы
	 * @return
	 */
	public String getAlias() {
		return alias;
	}
	/**
	 * Изменить псевданим комнаты
	 * @param alias
	 */
	public void setAlias(String alias) {
		this.alias = alias;
	}
	/**
	 * Получить польный псевданим команды
	 * @return
	 */
	public String getAliasFull() {
		return plugin.getName()+"::"+alias;
	}
	/**
	 * Получить имя комнаты
	 * @return String
	 */
	public String getName() {
		return name;
	}
	/**
	 * Установить имя чата
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * Получить префикс-команду чата
	 * @return
	 */
	public String getChatCommand() {
		return chatCommand;
	}
	/**
	 * Установить команду чата
	 * @param chatCommand
	 */
	public void setChatCommand(String chatCommand) {
		this.chatCommand = chatCommand;
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
	public Boolean hasPlayer(Player player) {
		return users.contains(player);
	}
	/**
	 * Выкинуть пользователя из комнаты
	 * @param player - пользователь
	 * @return Boolean
	 */
	public Boolean kickPlayer(Player player) {
		if (!hasPlayer(player)) return false;
		users.remove(player);
		RoomMeta.get(player).remove(getAliasFull());
		Bukkit.getPluginManager().callEvent(new PlayerLeaveChatRoomEvent(player, this));
		return true;
	}
	/**
	 * Выкинуть всех пользователей
	 */
	public void kickAllPlayers() {
		if (users.size() < 1) return;
		for (Player player : users) {
			RoomMeta.get(player).remove(getAliasFull());
		}
		users.clear();
	}
	/**
	 * Добавить пользователя в комнату
	 * @param player - игрок
	 * @return Boolean
	 */
	public Boolean addPlayer(Player player) {
		if (users.contains(player)) return false;
		users.add(player);
		RoomMeta.get(player).asList().add(getAliasFull());
		Bukkit.getPluginManager().callEvent(new PlayerJoinChatRoomEvent(player, this));
		return true;
	}
	/**
	 * Пригласить игрока в комнату
	 * @param player
	 */
	public void invitePlayer(Player player) {
		whiteList.add(player);
	}
	/**
	 * Удалить игрока из белого списка
	 * @param player
	 */
	public void unInvitePlayer(Player player) {
		whiteList.remove(player);
	}
	/**
	 * Можно ли присоединиться пользователю
	 * @param player - Игрок, который хочет присоединиться
	 * @return
	 */
	public boolean allowJoin(Player player) {
		return whiteList.contains(player);
	}
	/**
	 * Получить причину, по которой игрока не пустило в комнату
	 * @param player - Игрок, который попытался войти в комнату
	 * @return
	 */
	public String deniedReason(Player player) {
		return Config.YOU_NOT_IN_WHITELIST;
	}
	/**
	 * Получество пользователей в комнате
	 * @return
	 */
	public int count() {
		return users.size();
	}
	/**
	 * Получить плагин, от которого наследуется комната
	 * @return
	 */
	public Plugin getPlugin() {
		return plugin;
	}
}

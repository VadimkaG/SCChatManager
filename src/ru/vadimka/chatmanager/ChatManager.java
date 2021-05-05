package ru.vadimka.chatmanager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import ru.vadimka.chatmanager.chatreplacers.ChatTemplateReplacer;
import ru.vadimka.chatmanager.chatreplacers.PermissionsEXPrefixReplacer;
import ru.vadimka.chatmanager.chatreplacers.PermissionsEXSuffixReplacer;
import ru.vadimka.chatmanager.chatreplacers.VaultChatPrefixReplacer;
import ru.vadimka.chatmanager.chatreplacers.VaultChatSuffixReplacer;
import ru.vadimka.chatmanager.commands.CPlayerRoom;
import ru.vadimka.chatmanager.commands.CReload;
import ru.vadimka.chatmanager.listeners.ChatListener;
import ru.vadimka.chatmanager.listeners.PlayerListener;
import ru.vadimka.chatmanager.tabcompliters.TCPlayerRoom;

public class ChatManager extends JavaPlugin {
	protected static List<ChatTemplateReplacer> CHAT_REPLACERS;
	protected static HashMap<String,Room> ROOMS;
	protected static ChatManager INSTANCE;
	
	public static ChatManager getInstance() {
		return INSTANCE;
	}
	
	public void onEnable() {
		CHAT_REPLACERS = new ArrayList<>();
		ROOMS = new HashMap<String,Room>();
		INSTANCE = this;
		
		if (VaultChatSuffixReplacer.dependsCheck()) {
			addChatReplacer(new VaultChatPrefixReplacer(getServer()));
			addChatReplacer(new VaultChatSuffixReplacer(getServer()));
		} else if (PermissionsEXPrefixReplacer.dependsCheck()) {
			addChatReplacer(new PermissionsEXPrefixReplacer());
			addChatReplacer(new PermissionsEXSuffixReplacer());
		}
		
		Config.loadMainConfig();
		Config.loadMessages();
		getServer().getPluginManager().registerEvents(new ChatListener(), this);
		getServer().getPluginManager().registerEvents(new PlayerListener(), this);
		getCommand("chmreload").setExecutor(new CReload());
		getCommand("chr").setExecutor(new CPlayerRoom());
		getCommand("chr").setTabCompleter(new TCPlayerRoom());
		
		addRoom(new Room("admin", Config.CHATCOMMAND_ADMIN, this));
	}
	/**
	 * Добавить новый чат провадер
	 * @param provider
	 */
	public static void addChatReplacer(ChatTemplateReplacer provider) {
		CHAT_REPLACERS.add(provider);
	}
	/**
	 * Получить список всех провайдеров
	 * @return
	 */
	public static List<ChatTemplateReplacer> getChatReplacers() {
		return CHAT_REPLACERS;
	}
	/**
	 * Получить список комнат
	 * @return
	 */
	public static HashMap<String,Room> listRooms() {
		return ROOMS;
	}
	/**
	 * Добавить новую комнату
	 * @param room - Комната
	 * @param plugin - Плагин, к которому привязана комната
	 */
	public static void addRoom(Room room) {
		ROOMS.put(
				room
					.getPlugin()
					.getName()
				+"::"+
				room.getAlias(),room);
	}
	/**
	 * Существует ли комната
	 * @param alias - Имя комнаты
	 * @param plugin - Плагин, к которому привязана комната
	 * @return
	 */
	public static boolean hasRoom(String alias,Plugin plugin) {
		return ROOMS.containsKey(plugin.getName()+"::"+alias);
	}
	/**
	 * Получить комнату
	 * @param alias - Имя комнаты
	 * @param plugin - Плагин, к которому привязана комната
	 * @return
	 */
	public static Room getRoom(String alias,Plugin plugin) {
		return getRoomGlobal(plugin.getName()+"::"+alias);
	}
	/**
	 * Получить комнату по иглобальному имени
	 * @param alias - Имя комнаты
	 * @param plugin - Плагин, к которому привязана комната
	 * @return
	 */
	public static Room getRoomGlobal(String alias) {
		return ROOMS.get(alias);
	}
	/**
	 * Удалить комнату
	 * @param alias - Имя комнаты
	 * @param plugin - Плагин, к которому привязана комната
	 */
	public static void delRoom(String alias,Plugin plugin) {
		delRoomGlobal(plugin.getName()+"::"+alias);
	}
	/**
	 * Удалить комнату
	 * @param alias - Имя комнаты
	 */
	public static void delRoomGlobal(String alias) {
		Room room = ROOMS.get(alias);
		if (room != null)
			room.kickAllPlayers();
		ROOMS.remove(alias);
	}
}

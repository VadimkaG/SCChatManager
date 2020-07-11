package ru.vadimka.chatmanager;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import ru.vadimka.chatmanager.chatproviders.ChatProvider;
import ru.vadimka.chatmanager.objects.PlayerStat;
import ru.vadimka.chatmanager.objects.Room;

public abstract class Config {
	
	public static String PLUGIN_PREFIX = "[ChatManager]";
	
	public static String HELP = "Помощь по плагину:";
	public static String HELP_RELOAD = "%COMMAND% - Перезагружает плагин";
	public static String HELP_LIST = "%COMMAND% - Список комнат";
	public static String HELP_CREATE = "%COMMAND% - Создать комнату";
	public static String HELP_ACCEPT = "%COMMAND% - Принять пользователя в комнату";
	public static String HELP_ROOMUSERS = "%COMMAND% - Список пользователей в комнате";
	public static String HELP_TOGGLE_GLOBAL_CHAT = "%COMMAND% - Переключить глобальный чат";
	public static String HELP_REMOVE = "%COMMAND% - Удалить комнату";
	public static String HELP_JOIN = "%COMMAND% - Войти в комнату";
	public static String HELP_LEAVE = "%COMMAND% - Покинуть комнату";
	public static String HELP_CHAT_GLOBAL = "%COMMAND% - Глобальный чат";
	public static String HELP_CHAT_ADMIN = "%COMMAND% - Админ-чат";
	
	public static String NOT_PERMITED = "Вам не разрешена данная операция";
	public static String PLAYER_OFFLINE = "Игрок %PLAYER% не в сети";
	public static String ROOM_EXISTS = "Комната %ROOM% уже существует";
	public static String ROOM_NOT_EXISTS = "Комната %ROOM% не существует";
	public static String ROOM_ALREADY_JOINED = "Вы уже состоите в комнате %ROOM%";
	public static String ROOM_NOT_JOINED = "Вы не состоите ни в одной комнате";
	public static String GLOBAL_CHAT_ON = "Глобальный чат включен";
	public static String GLOBAL_CHAT_OFF = "Глобальный чат выключен";

	public static String REQUEST_SENDED = "Запрос отправлен";
	public static String REQUEST_NOT_EXIST = "Нет активных запросов";
	public static String REQUEST_JOIN = "%PLAYER% хочет войти в вашу комнату";
	public static String JOINED_ROOM = "%PLAYER% вошел в комнату";
	public static String PLAYER_LEAVE = "%PLAYER% покинул комнату";
	public static String YOU_LEAVE = "Вы покинули комнату";
	public static String MESSAGE_FAST = "Сообщение можно отправлять раз в %TIME% милисекунд.";
	
	public static String ROOM_CREATED = "Комната %ROOM% создана";
	public static String ROOM_REMOVED = "Комната %ROOM% удалена";
	public static String PLAYER_ADDED = "Пользователь %PLAYER% добавлен в комнату %ROOM%";
	public static String PLAYER_REMOVED = "Пользователь %PLAYER% удален из команты %ROOM%";
	
	public static String ROOMS_LIST = "Список комнат: ";
	public static String ROOMS_LIST_ITEM = "%ROOM%";
	public static String ROOMS_LIST_EMPTY = "Список комнат пуст.";
	
	public static String ROOMS_LIST_USERS = "Список пользователей: ";
	public static String ROOMS_LIST_USERS_ITEM = "%NAME%";
	
	public static String USAGE_ROOM_CREATE = "Используйте: %COMMAND%";
	public static String USAGE_ROOM_REMOVE = "Используйте: %COMMAND% <ник>";
	public static String USAGE_ROOM_JOIN = "Используйте: %COMMAND% <комната>";
	
	public static String MESSAGE_FORMAT = "%PREFIX%%PLAYER%%SUFFIX%: %MESSAGE%";
	public static String MESSAGE_ROOM_FORMAT = "[%ROOM%] %PREFIX%%PLAYER%%SUFFIX%: %MESSAGE%";
	public static String MESSAGE_ADMIN_FORMAT = "[admin] %PREFIX%%PLAYER%%SUFFIX%: %MESSAGE%";
	public static Integer MESASGE_INTERVAL = 0;
	
	public static HashMap<String,Room> rooms = new HashMap<String,Room>();
	
	public static String configDir = "";
	public static ChatManager loader;
	//private static Chat chat = null;
	private static ChatProvider CHAT = null;
	public static HashMap<String,PlayerStat> playerStat;
	
	// Инициализация
	public static void init(ChatManager l) {
		loader = l;
		configDir = "plugins/"+l.getDescription().getName()+"/";
		playerStat = new HashMap<String,PlayerStat>();
		
		for (Player p : l.getServer().getOnlinePlayers()) {
			playerStat.put(p.getName(), new PlayerStat(p));
		}
		
		loadMainConfig();
		loadMessages();
	}
	
	// Главный конфиг
	public static void loadMainConfig() {
		File f = new File(configDir+"settings.yml");
		YamlConfiguration y = YamlConfiguration.loadConfiguration(f);
		if (!f.exists()) saveDefaultMainConfig(f,y);
		MESSAGE_FORMAT = y.getString("messageFormat");
		MESSAGE_ROOM_FORMAT = y.getString("messageRoomFormat");
		MESSAGE_ADMIN_FORMAT = y.getString("messageAdminFormat");
		MESASGE_INTERVAL = y.getInt("messageIntervalPerUserMilis");
	}
	
	public static void saveDefaultMainConfig(File f,YamlConfiguration y) {
		y.options().copyDefaults(true);
		y.addDefault("messageFormat", MESSAGE_FORMAT);
		y.addDefault("messageRoomFormat", MESSAGE_ROOM_FORMAT);
		y.addDefault("messageAdminFormat", MESSAGE_ADMIN_FORMAT);
		y.addDefault("messageIntervalPerUserMilis", MESASGE_INTERVAL);
		try {y.save(f);} catch (IOException e) {
			Utils.print("Ошибка сохранения config.yml.");
		}
	}
	
	// Локали
	public static void loadMessages() {
		File f = new File(configDir+"messages.yml");
		YamlConfiguration y = YamlConfiguration.loadConfiguration(f);
		if (!f.exists()) saveDefaultMessages(f,y);
		
		PLUGIN_PREFIX = y.getString("pluginPrefix");

		HELP = y.getString("help");
		HELP_RELOAD = y.getString("helpReload");
		HELP_LIST = y.getString("helpList");
		HELP_CREATE = y.getString("helpCreate");
		HELP_ACCEPT = y.getString("helpYes");
		HELP_ROOMUSERS = y.getString("helpRoomUsers");
		HELP_TOGGLE_GLOBAL_CHAT = y.getString("helpToggleGlobalChat");
		HELP_REMOVE = y.getString("helpremove");
		HELP_JOIN = y.getString("helpJoin");
		HELP_LEAVE = y.getString("helpLeave");
		
		NOT_PERMITED = y.getString("notPermited");
		PLAYER_OFFLINE = y.getString("PlayerOffline");
		ROOM_EXISTS = y.getString("RoomAlreadyExists");
		ROOM_NOT_EXISTS = y.getString("RoomNotExists");
		ROOM_ALREADY_JOINED = y.getString("YouAreAlrearyInRoom");
		ROOM_NOT_JOINED = y.getString("YouNotJoinedInRoom");
		
		ROOM_CREATED = y.getString("roomCreated");
		ROOM_REMOVED = y.getString("roomRemoved");
		PLAYER_ADDED = y.getString("PlayerAdded");
		PLAYER_REMOVED = y.getString("PlayerRemoved");
		
		REQUEST_SENDED = y.getString("RequestSended");
		REQUEST_NOT_EXIST = y.getString("RequestNotExist");
		REQUEST_JOIN = y.getString("PlayerRequiestToJoin");
		JOINED_ROOM = y.getString("JoinedInRoom");
		PLAYER_LEAVE = y.getString("PlayerLeave");
		YOU_LEAVE = y.getString("YouLeave");
		
		ROOMS_LIST = y.getString("RoomsList");
		ROOMS_LIST_ITEM = y.getString("RoomsListItem");
		ROOMS_LIST_EMPTY = y.getString("RoomsListEmpty");
		
		USAGE_ROOM_CREATE = y.getString("UsageRoomCreate");
		USAGE_ROOM_REMOVE = y.getString("UsageRoomRemove");
		USAGE_ROOM_JOIN = y.getString("UsageJoinToRoom");
		
		// Подставляем выражения
		HELP_RELOAD = Utils.getColors(HELP_RELOAD.replace("%COMMAND%", "/chmreload"));
		HELP_LIST = Utils.getColors(HELP_LIST.replace("%COMMAND%", "/chrlist"));
		HELP_CREATE = Utils.getColors(HELP_CREATE.replace("%COMMAND%", "/chrcreate"));
		HELP_ACCEPT = Utils.getColors(HELP_ACCEPT.replace("%COMMAND%", "/chryes"));
		HELP_ROOMUSERS = Utils.getColors(HELP_ROOMUSERS.replace("%COMMAND%", "/chroster"));
		HELP_TOGGLE_GLOBAL_CHAT = Utils.getColors(HELP_TOGGLE_GLOBAL_CHAT.replace("%COMMAND%", "/chrtoglobal"));
		HELP_REMOVE = Utils.getColors(HELP_REMOVE.replace("%COMMAND%", "/chrremove"));
		HELP_JOIN = Utils.getColors(HELP_JOIN.replace("%COMMAND%", "/chrjoin"));
		HELP_LEAVE = Utils.getColors(HELP_LEAVE.replace("%COMMAND%", "/chrleave"));
		USAGE_ROOM_JOIN = Utils.getColors(USAGE_ROOM_JOIN.replace("%COMMAND%", "/chrjoin"));
		USAGE_ROOM_CREATE = Utils.getColors(USAGE_ROOM_CREATE.replace("%COMMAND%", "/chrcreate"));
		USAGE_ROOM_REMOVE = Utils.getColors(USAGE_ROOM_REMOVE.replace("%COMMAND%", "/chrremove"));
		HELP_CHAT_GLOBAL = Utils.getColors(HELP_CHAT_GLOBAL.replace("%COMMAND%", "!"));
		HELP_CHAT_ADMIN = Utils.getColors(HELP_CHAT_ADMIN.replace("%COMMAND%", "@"));
		// ===================
		
		// Красим сообщения
		PLUGIN_PREFIX = Utils.getColors(PLUGIN_PREFIX);
		HELP = Utils.getColors(HELP);
		NOT_PERMITED = Utils.getColors(NOT_PERMITED);
		ROOMS_LIST = Utils.getColors(ROOMS_LIST);
		ROOMS_LIST_EMPTY = Utils.getColors(ROOMS_LIST_EMPTY);
		ROOMS_LIST_USERS = Utils.getColors(ROOMS_LIST);
		ROOMS_LIST_USERS_ITEM = Utils.getColors(ROOMS_LIST_USERS_ITEM);
		GLOBAL_CHAT_ON = Utils.getColors(GLOBAL_CHAT_ON);
		GLOBAL_CHAT_OFF = Utils.getColors(GLOBAL_CHAT_OFF);
		YOU_LEAVE = Utils.getColors(YOU_LEAVE);
		ROOM_REMOVED = Utils.getColors(ROOM_REMOVED);
		MESSAGE_FAST = Utils.getColors(MESSAGE_FAST);
		// ==================
	}
	
	public static void saveDefaultMessages(File f,YamlConfiguration y) {
		y.options().copyDefaults(true);
		
		y.addDefault("pluginPrefix", PLUGIN_PREFIX);

		y.addDefault("help", HELP);
		y.addDefault("helpReload", HELP_RELOAD);
		y.addDefault("helpList", HELP_LIST);
		y.addDefault("helpCreate", HELP_CREATE);
		y.addDefault("helpYes", HELP_ACCEPT);
		y.addDefault("helpRoomUsers", HELP_ROOMUSERS);
		y.addDefault("helpToggleGlobalChat", HELP_TOGGLE_GLOBAL_CHAT);
		y.addDefault("helpremove", HELP_REMOVE);
		y.addDefault("helpJoin", HELP_JOIN);
		y.addDefault("helpLeave", HELP_LEAVE);
		
		y.addDefault("notPermited", NOT_PERMITED);
		y.addDefault("PlayerOffline", PLAYER_OFFLINE);
		y.addDefault("RoomAlreadyExists", ROOM_EXISTS);
		y.addDefault("RoomNotExists", ROOM_NOT_EXISTS);
		y.addDefault("YouAreAlrearyInRoom", ROOM_ALREADY_JOINED);
		y.addDefault("YouNotJoinedInRoom", ROOM_NOT_JOINED);
		
		y.addDefault("roomCreated", ROOM_CREATED);
		y.addDefault("roomRemoved", ROOM_REMOVED);
		y.addDefault("PlayerAdded", PLAYER_ADDED);
		y.addDefault("PlayerRemoved", PLAYER_REMOVED);
		
		y.addDefault("RequestSended", REQUEST_SENDED);
		y.addDefault("RequestNotExist", REQUEST_NOT_EXIST);
		y.addDefault("PlayerRequiestToJoin", REQUEST_JOIN);
		y.addDefault("JoinedInRoom", JOINED_ROOM);
		y.addDefault("PlayerLeave", PLAYER_LEAVE);
		y.addDefault("YouLeave", YOU_LEAVE);
		
		y.addDefault("RoomsList", ROOMS_LIST);
		y.addDefault("RoomsListItem", ROOMS_LIST_ITEM);
		y.addDefault("RoomsListEmpty", ROOMS_LIST_EMPTY);
		
		y.addDefault("UsageRoomCreate", USAGE_ROOM_CREATE);
		y.addDefault("UsageRoomRemove", USAGE_ROOM_REMOVE);
		y.addDefault("UsageJoinToRoom", USAGE_ROOM_JOIN);
		try {y.save(f);} catch (IOException e) {
			Utils.print("Ошибка сохранения messages.yml.");
		}
	}
	
	public static void setChat(ChatProvider chat) {
		CHAT = chat;
	}
	
	public static ChatProvider getChat() {
		return CHAT;
	}
}

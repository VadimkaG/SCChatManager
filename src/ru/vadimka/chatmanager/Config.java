package ru.vadimka.chatmanager;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.YamlConfiguration;

public abstract class Config {

	public static String NOT_PERMITED = "Вам не разрешена данная операция";
	public static String ROOM_NOT_EXISTS = "Комната не существует";

	public static String GLOBAL_CHAT_ON = "Глобальный чат включен";
	public static String GLOBAL_CHAT_OFF = "Глобальный чат выключен";

	public static String INVERT_CHAT_ON = "Ивертированный чат включен. Теперь по умолчанию сообщение пишутся в комнату";
	public static String INVERT_CHAT_OFF = "Ивертированный чат выключен. Теперь по умолчанию сообщения пишутся в глобальный чат.";

	public static String JOINED_ROOM = "%PLAYER% вошел в комнату";
	public static String PLAYER_LEAVE = "%PLAYER% покинул комнату";
	public static String YOU_LEAVE = "Вы покинули комнату";
	public static String YOU_NOT_IN_WHITELIST = "Вы не приглашены в чат";
	public static String MESSAGE_FAST = "Сообщение можно отправлять раз в %TIME% %TYPE%.";
	public static String MESSAGE_MILISECONDS = "милисекунд";
	public static String MESSAGE_SECONDS = "секунд";
	
	public static String ROOMS_LIST_HEADER = "Список комнат: ";
	public static String ROOMS_LIST = "%ALIAS% %NAME%";
	
	public static String ROOMS_ROSTER_HEADER = "Список пользователей: ";
	public static String ROOMS_ROSTER = "%NAME%";
	
	public static String MESSAGE_FORMAT = "%PLAYER%: %MESSAGE%";
	public static String MESSAGE_ROOM_FORMAT = "[%ROOM%] %PLAYER%: %MESSAGE%";
	public static String CHATCOMMAND_ADMIN = "@";
	public static Integer MESASGE_INTERVAL = 0;
	
	/**
	 * Загрузить конфиг
	 */
	public static void loadMainConfig() {
		File f = new File("plugins/"+ChatManager.getInstance().getDescription().getName()+"/config.yml");
		YamlConfiguration y = YamlConfiguration.loadConfiguration(f);
		boolean needSetDefault = !f.exists();
		if (needSetDefault)
			y.options().copyDefaults(true);
		MESSAGE_FORMAT = procStringWithoutColors(y,needSetDefault,"format.global",MESSAGE_FORMAT);
		MESSAGE_ROOM_FORMAT = procStringWithoutColors(y,needSetDefault,"format.room",MESSAGE_ROOM_FORMAT);
		MESASGE_INTERVAL = procString(y,needSetDefault,"msgInterval",MESASGE_INTERVAL);
		CHATCOMMAND_ADMIN = procStringWithoutColors(y,needSetDefault,"chatcommand.admin",CHATCOMMAND_ADMIN);
		if (needSetDefault)
			try {y.save(f);} catch (IOException e) {
				ChatManager.getInstance().getLogger().info("Ошибка сохранения config.yml.");
			}
	}
	/**
	 * Загрузить сообщения
	 */
	public static void loadMessages() {
		File f = new File("plugins/"+ChatManager.getInstance().getDescription().getName()+"/messages.yml");
		YamlConfiguration y = YamlConfiguration.loadConfiguration(f);
		boolean needSetDefault = !f.exists();
		if (needSetDefault)
			y.options().copyDefaults(true);
		NOT_PERMITED = procString(y,needSetDefault,"notPermited",NOT_PERMITED);

		ROOM_NOT_EXISTS = procString(y,needSetDefault,"room.notExists",ROOM_NOT_EXISTS);

		GLOBAL_CHAT_ON = procString(y,needSetDefault,"room.globalchat.on",GLOBAL_CHAT_ON);
		GLOBAL_CHAT_OFF = procString(y,needSetDefault,"room.globalchat.off",GLOBAL_CHAT_OFF);
		
		INVERT_CHAT_ON = procString(y,needSetDefault,"room.invertchat.on",INVERT_CHAT_ON);
		INVERT_CHAT_OFF = procString(y,needSetDefault,"room.invertchat.off",INVERT_CHAT_OFF);

		JOINED_ROOM = procString(y,needSetDefault,"player.join",JOINED_ROOM);
		PLAYER_LEAVE = procString(y,needSetDefault,"player.leave",PLAYER_LEAVE);
		YOU_LEAVE = procString(y,needSetDefault,"you.leave",YOU_LEAVE);
		YOU_NOT_IN_WHITELIST = procString(y,needSetDefault,"you.notInWhiteList",YOU_NOT_IN_WHITELIST);
		MESSAGE_FAST = procString(y,needSetDefault,"you.sendMesasgeFast",MESSAGE_FAST);
		MESSAGE_SECONDS = procString(y,needSetDefault,"you.seconds",MESSAGE_SECONDS);
		MESSAGE_MILISECONDS = procString(y,needSetDefault,"you.miliseconds",MESSAGE_MILISECONDS);

		ROOMS_LIST_HEADER = procString(y,needSetDefault,"room.list.header",ROOMS_LIST_HEADER);
		ROOMS_LIST = procString(y,needSetDefault,"room.list.footer",ROOMS_LIST);

		ROOMS_ROSTER_HEADER = procString(y,needSetDefault,"players.list.header",ROOMS_ROSTER_HEADER);
		ROOMS_ROSTER = procString(y,needSetDefault,"players.list.item",ROOMS_ROSTER);
		if (needSetDefault)
			try {y.save(f);} catch (IOException e) {
				ChatManager.getInstance().getLogger().info("Ошибка сохранения messages.yml.");
			}
	}
	/**
	 * Заменить стандартный символ цвета на более удобный
	 * @param msg- Сообщение
	 * @return
	 */
	public static String replaceColorCodes(String msg) {
		if (msg == null) return "";
		return msg.replaceAll("&([a-z0-9])", "\u00A7$1");
	}
	/**
	 * Очистить символы цвета
	 * @param msg - Сообщение
	 * @return
	 */
	public static String clearColorCodes(String msg) {
		if (msg == null) return "";
		return msg.replaceAll("&([a-z0-9])", "");
	}
	/**
	 * Попытаться загрузить строку из конфига
	 * @param y - Конфиг
	 * @param fileNotExists - Если файл конфигурации не существует
	 * @param alias - Идентификатор конфигурации
	 * @param value - Значение по умолчанию
	 * @return
	 */
	private static String procString(YamlConfiguration y, boolean fileNotExists, String alias, String value) {
		y.addDefault(alias, value);
		if (fileNotExists) {
			return value;
		} else
			return replaceColorCodes(y.getString(alias));
	}
	/**
	 * Попытаться загрузить строку из конфига
	 * @param y - Конфиг
	 * @param fileNotExists - Если файл конфигурации не существует
	 * @param alias - Идентификатор конфигурации
	 * @param value - Значение по умолчанию
	 * @return
	 */
	private static String procStringWithoutColors(YamlConfiguration y, boolean fileNotExists, String alias, String value) {
		y.addDefault(alias, value);
		if (fileNotExists) {
			return value;
		} else
			return y.getString(alias);
	}
	/**
	 * Попытаться загрузить строку из конфига
	 * @param y - Конфиг
	 * @param fileNotExists - Если файл конфигурации не существует
	 * @param alias - Идентификатор конфигурации
	 * @param value - Значение по умолчанию
	 * @return
	 */
	private static Integer procString(YamlConfiguration y, boolean fileNotExists, String alias, Integer value) {
		y.addDefault(alias, value);
		if (fileNotExists) {
			return value;
		} else
			return y.getInt(alias);
	}
}

package ru.vadimka.chatmanager;

import org.bukkit.plugin.java.JavaPlugin;

import ru.vadimka.chatmanager.chatproviders.PermissionsEXChatProvider;
import ru.vadimka.chatmanager.chatproviders.VaultChatProvider;
import ru.vadimka.chatmanager.commands.CAcceptWaitingPlayer;
import ru.vadimka.chatmanager.commands.CCreateRoom;
import ru.vadimka.chatmanager.commands.CHelp;
import ru.vadimka.chatmanager.commands.CJoinRoom;
import ru.vadimka.chatmanager.commands.CLeaveRoom;
import ru.vadimka.chatmanager.commands.CListRoomUsers;
import ru.vadimka.chatmanager.commands.CListRooms;
import ru.vadimka.chatmanager.commands.CReload;
import ru.vadimka.chatmanager.commands.CRemoveRoom;
import ru.vadimka.chatmanager.commands.CToggleGlobalChat;
import ru.vadimka.chatmanager.listeners.EChat;
import ru.vadimka.chatmanager.listeners.EPlayerJoin;
import ru.vadimka.chatmanager.listeners.EPlayerLeave;

public class ChatManager extends JavaPlugin {
	public void onEnable() {
		
		if (VaultChatProvider.dependsCheck())
			Config.setChat(new VaultChatProvider(getServer()));
		else if (PermissionsEXChatProvider.dependsCheck())
			Config.setChat(new PermissionsEXChatProvider());
		
		Config.init(this);
		getServer().getPluginManager().registerEvents(new EChat(), this);
		getServer().getPluginManager().registerEvents(new EPlayerJoin(), this);
		getServer().getPluginManager().registerEvents(new EPlayerLeave(), this);
		getCommand("chmreload").setExecutor(new CReload());
		getCommand("chrcreate").setExecutor(new CCreateRoom());
		getCommand("chrremove").setExecutor(new CRemoveRoom());
		getCommand("chrlist").setExecutor(new CListRooms());
		getCommand("chrjoin").setExecutor(new CJoinRoom());
		getCommand("chrleave").setExecutor(new CLeaveRoom());
		getCommand("chroster").setExecutor(new CListRoomUsers());
		getCommand("chrtoglobal").setExecutor(new CToggleGlobalChat());
		getCommand("chryes").setExecutor(new CAcceptWaitingPlayer());
		getCommand("chr").setExecutor(new CHelp());
		Utils.print("Плагин запущен");
	}
	
	public void onDisable() {
		Utils.print("Плагин остановлен");
	}
}

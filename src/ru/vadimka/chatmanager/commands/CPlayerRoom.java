package ru.vadimka.chatmanager.commands;

import java.util.Arrays;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CPlayerRoom implements CommandExecutor {
	
	public static final String PREFIX_COMMAND_LIST          = "list";
	public static final String PREFIX_COMMAND_ROSTER        = "roster";
	public static final String PREFIX_COMMAND_TOGGLE_GLOBAL = "togglobal";
	
	public final CListRooms            COMMAND_LIST               = new CListRooms();
	public final CListRoomUsers        COMMAND_ROSTER             = new CListRoomUsers();
	public final CToggleGlobalChat     COMMAND_TOGGLE_GLOBAL      = new CToggleGlobalChat();

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (args.length >= 1) {
			String[] newArgs;
			if (args.length > 1) newArgs = Arrays.copyOfRange(args, 1, args.length);
			else newArgs = new String[0];
			boolean isSuccess = false;
			switch (args[0].trim()) {
			case PREFIX_COMMAND_LIST:
				isSuccess = COMMAND_LIST.onCommand(sender, cmd, label+" "+PREFIX_COMMAND_LIST, newArgs);
				break;
			case PREFIX_COMMAND_ROSTER:
				isSuccess = COMMAND_ROSTER.onCommand(sender, cmd, label+" "+PREFIX_COMMAND_ROSTER, newArgs);
				break;
			case PREFIX_COMMAND_TOGGLE_GLOBAL:
				isSuccess = COMMAND_TOGGLE_GLOBAL.onCommand(sender, cmd, label+" "+PREFIX_COMMAND_TOGGLE_GLOBAL, newArgs);
				break;
			default:
				isSuccess = false;
			}
			if (!isSuccess)
				printHelp(sender, label);
		} else
			printHelp(sender, label);
		return true;
	}

	private void printHelp(CommandSender sender, String label) {
		sender.sendMessage("/"+label+" "+PREFIX_COMMAND_LIST+" - Список комнат");
		sender.sendMessage("/"+label+" "+PREFIX_COMMAND_ROSTER+" <комната> - Список игроков в комнате");
		sender.sendMessage("/"+label+" "+PREFIX_COMMAND_TOGGLE_GLOBAL+" - Включить или выключить отображение глобального чата");
	}

}

package ru.vadimka.chatmanager.tabcompliters;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import ru.vadimka.chatmanager.commands.CPlayerRoom;

public class TCPlayerRoom implements TabCompleter {

	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
		List<String> data = new ArrayList<String>();
		if (args.length == 1) {
			tryadd(args, data, CPlayerRoom.PREFIX_COMMAND_LIST);
			tryadd(args, data, CPlayerRoom.PREFIX_COMMAND_ROSTER);
			tryadd(args, data, CPlayerRoom.PREFIX_COMMAND_TOGGLE_GLOBAL);
		}
		return data;
	}
	
	private void tryadd(String[] args, List<String> data,String text) {
		int argLen = args[0].length();
		if (
				args[0].equalsIgnoreCase("") || 
				args[0].equalsIgnoreCase(text) || 
				(
					text.length() >= argLen &&
					text.substring(0,argLen).equalsIgnoreCase(args[0])
				)
			)
			data.add(text);
	}

}

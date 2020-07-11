package ru.vadimka.chatmanager.chatproviders;

import org.bukkit.entity.Player;

import ru.tehkode.permissions.PermissionUser;
import ru.tehkode.permissions.bukkit.PermissionsEx;

public class PermissionsEXChatProvider implements ChatProvider {
	
	public static boolean dependsCheck() {
		try {
			Class.forName( "ru.tehkode.permissions.bukkit.PermissionsEx" );
			return true;
		} catch( ClassNotFoundException e ) {
			return false;
		}
	}

	@Override
	public String getPlayerPrefix(Player player) {
		PermissionUser user = PermissionsEx.getUser(player);
		return user.getPrefix();
	}

	@Override
	public String getPlayerSuffix(Player player) {
		PermissionUser user = PermissionsEx.getUser(player);
		return user.getSuffix();
	}

}

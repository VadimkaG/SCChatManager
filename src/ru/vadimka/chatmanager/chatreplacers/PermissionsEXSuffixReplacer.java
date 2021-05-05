package ru.vadimka.chatmanager.chatreplacers;

import org.bukkit.entity.Player;

import ru.tehkode.permissions.PermissionUser;
import ru.tehkode.permissions.bukkit.PermissionsEx;

public class PermissionsEXSuffixReplacer implements ChatTemplateReplacer {

	public static boolean dependsCheck() {
		try {
			Class.forName( "ru.tehkode.permissions.bukkit.PermissionsEx" );
			return true;
		} catch( ClassNotFoundException e ) {
			return false;
		}
	}

	@Override
	public String template() { return "%SUFFIX%"; }

	@Override
	public String replace(Player player) {
		PermissionUser user = PermissionsEx.getUser(player);
		return user.getSuffix();
	}

}

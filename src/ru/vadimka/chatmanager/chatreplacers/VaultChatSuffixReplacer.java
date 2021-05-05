package ru.vadimka.chatmanager.chatreplacers;

import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;

import net.milkbowl.vault.chat.Chat;

public class VaultChatSuffixReplacer implements ChatTemplateReplacer {
	
	private Chat CHAT;
	
	public VaultChatSuffixReplacer(Server server) {
		RegisteredServiceProvider<Chat> chatProvider = server.getServicesManager().getRegistration(Chat.class);
		
		if (chatProvider != null) CHAT = chatProvider.getProvider();
	}
	
	public static boolean dependsCheck() {
		try {
			Class.forName("net.milkbowl.vault.chat.Chat");
			return true;
		} catch(ClassNotFoundException e) {
			return false;
		}
	}

	@Override
	public String template() { return "%SUFFIX%"; }

	@Override
	public String replace(Player player) {
		return CHAT.getPlayerSuffix(player);
	}
}

package ru.vadimka.chatmanager.chatproviders;

import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;

import net.milkbowl.vault.chat.Chat;

public class VaultChatProvider implements ChatProvider {
	
	private Chat CHAT;
	
	public VaultChatProvider(Server server) {
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
	public String getPlayerPrefix(Player player) {
		return CHAT.getPlayerPrefix(player);
	}

	@Override
	public String getPlayerSuffix(Player player) {
		return CHAT.getPlayerSuffix(player);
	}
}

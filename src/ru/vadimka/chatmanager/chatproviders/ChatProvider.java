package ru.vadimka.chatmanager.chatproviders;

import org.bukkit.entity.Player;

public interface ChatProvider {
	String getPlayerPrefix(Player player);
	String getPlayerSuffix(Player player);
}

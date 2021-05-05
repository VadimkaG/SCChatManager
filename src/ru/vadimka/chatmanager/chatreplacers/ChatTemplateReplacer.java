package ru.vadimka.chatmanager.chatreplacers;

import org.bukkit.entity.Player;

public interface ChatTemplateReplacer {
	String template();
	String replace(Player player);
}

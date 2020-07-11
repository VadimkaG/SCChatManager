package ru.vadimka.chatmanager.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import ru.vadimka.chatmanager.Config;
import ru.vadimka.chatmanager.objects.PlayerStat;

public class EPlayerJoin implements Listener {
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
		Config.playerStat.put(e.getPlayer().getName(), new PlayerStat(e.getPlayer()));
	}
}

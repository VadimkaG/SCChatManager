package ru.vadimka.chatmanager;

import org.bukkit.entity.Player;

import ru.vadimka.chatmanager.objects.Room;

public abstract class Utils {
	
	/* Список игроков поблизости
	public static List<Player> getLocalPlayers(Player sender, double range) {
		Location playerLocation = sender.getLocation();
		List<Player> recipients = new LinkedList<Player>();
		double squaredDistance = Math.pow(range, 2);
		for (Player recipient : Config.loader.getServer().getOnlinePlayers()) {
			if (!recipient.getWorld().equals(sender.getWorld())) continue;
			if (playerLocation.distanceSquared(recipient.getLocation()) > squaredDistance) continue;
			recipients.add(recipient);
		}
		return recipients;
	}*/
	
	// Раскрасить сообщение
	public static String getColors(String msg) {
		if (msg == null) return "";
		return msg.replaceAll("&([a-z0-9])", "\u00A7$1");
	}
	
	// Вывод сообщения в консоль
	public static void print(String msg) {
		System.out.println(Config.PLUGIN_PREFIX+" "+msg);
	}
	
	// Удаление комнаты
	public static Boolean deleteRoom(String roomName) {
		if (!Config.rooms.containsKey(roomName)) return false;
		Room playerRoom = Config.rooms.get(roomName);
		for (Player p : playerRoom.getPlayers()) {
			p.sendMessage(Config.PLUGIN_PREFIX+" "+Config.ROOM_REMOVED.replace("%ROOM%", roomName));
		}
		playerRoom.removeAllPlayers();
		Config.rooms.remove(roomName);
		return true;
	}
}

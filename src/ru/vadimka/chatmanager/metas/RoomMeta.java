package ru.vadimka.chatmanager.metas;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;

import ru.vadimka.chatmanager.ChatManager;

public class RoomMeta implements MetadataValue {
	private List<String> ROOM;

	public RoomMeta() { ROOM = Collections.synchronizedList(new ArrayList<>()); }
	@Override
	public boolean asBoolean() { return ROOM.size()>0?true:false; }
	@Override
	public byte asByte() { return 0; }
	@Override
	public double asDouble() { return 0; }
	@Override
	public float asFloat() { return 0; }
	@Override
	public int asInt() { return 0; }
	@Override
	public long asLong() { return 0; }
	@Override
	public short asShort() { return 0; }
	@Override
	public String asString() { return ROOM.size()>0?ROOM.get(0):""; }
	@Override
	public Plugin getOwningPlugin() { return ChatManager.getInstance(); }
	@Override
	public void invalidate() { }
	@Override
	public Object value() { return ROOM; }
	public List<String> asList() { return ROOM; }
	public List<String> cloneList() { return new ArrayList<>(ROOM); }
	/**
	 * Удалить группу по алиасу
	 * @param alias
	 */
	public void remove(String alias) {
		for (int i = 0; i < ROOM.size(); i++) {
			if (ROOM.get(i).equalsIgnoreCase(alias))
				ROOM.remove(i);
		}
	}
	/**
	 * Получить мету
	 * @param player - Игрок, у которого будет получена мета
	 * @return
	 */
	public static RoomMeta get(Player player) {
		List<MetadataValue> metaList = player.getMetadata("SCChM::Room");
		for (MetadataValue metadataValue : metaList) {
			if (metadataValue instanceof RoomMeta) 
				return ((RoomMeta)metadataValue);
		}
		return null;
	}
	/**
	 * Установить мету
	 * @param player - Игрок, которому будет установлена мета
	 * @param value - Значение меты
	 */
	public static void set(Player player, RoomMeta value) {
		player.setMetadata("SCChM::Room", value);
	}

}

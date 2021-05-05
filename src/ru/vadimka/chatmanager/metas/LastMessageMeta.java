package ru.vadimka.chatmanager.metas;

import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;

import ru.vadimka.chatmanager.ChatManager;

public class LastMessageMeta implements MetadataValue {
	protected long TIME_LAST_MESSAGE;
	
	LastMessageMeta(long value) { TIME_LAST_MESSAGE = value; }
	@Override
	public boolean asBoolean() { return TIME_LAST_MESSAGE>0?true:false; }
	@Override
	public byte asByte() { return 0; }
	@Override
	public double asDouble() { return Double.valueOf(TIME_LAST_MESSAGE); }
	@Override
	public float asFloat() { return Float.valueOf(TIME_LAST_MESSAGE); }
	@Override
	public int asInt() { return (int) TIME_LAST_MESSAGE; }
	@Override
	public long asLong() { return TIME_LAST_MESSAGE; }
	@Override
	public short asShort() { return (short) TIME_LAST_MESSAGE; }
	@Override
	public String asString() { return String.valueOf(TIME_LAST_MESSAGE); }
	@Override
	public Plugin getOwningPlugin() { return ChatManager.getInstance(); }
	@Override
	public void invalidate() {}
	@Override
	public Object value() { return TIME_LAST_MESSAGE; }
	/**
	 * Получить мету
	 * @param player - Игрок, у которого будет получена мета
	 * @return
	 */
	public static long get(Player player) {
		List<MetadataValue> metaList = player.getMetadata("SCChM::LastMessage");
		for (MetadataValue metadataValue : metaList) {
			if (metadataValue instanceof LastMessageMeta) 
				return ((LastMessageMeta)metadataValue).asLong();
		}
		return 0;
	}
	/**
	 * Установить мету
	 * @param player - Игрок, которому будет установлена мета
	 * @param value - Значение меты
	 */
	public static void set(Player player, long value) {
		player.setMetadata("SCChM::LastMessage", new LastMessageMeta(value));
	}

}

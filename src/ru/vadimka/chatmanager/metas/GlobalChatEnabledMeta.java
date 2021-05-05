package ru.vadimka.chatmanager.metas;

import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;

import ru.vadimka.chatmanager.ChatManager;

public class GlobalChatEnabledMeta implements MetadataValue {
	protected boolean VALUE;
	
	public GlobalChatEnabledMeta(boolean value) { VALUE = value; }
	@Override
	public boolean asBoolean() { return VALUE; }
	@Override
	public byte asByte() { return (byte) (VALUE?1:0); }
	@Override
	public double asDouble() { return VALUE?1:0; }
	@Override
	public float asFloat() { return VALUE?1:0; }
	@Override
	public int asInt() { return VALUE?1:0; }
	@Override
	public long asLong() { return VALUE?1:0; }
	@Override
	public short asShort() { return (short) (VALUE?1:0); }
	@Override
	public String asString() { return String.valueOf(VALUE); }
	@Override
	public Plugin getOwningPlugin() { return ChatManager.getInstance(); }
	@Override
	public void invalidate() {}
	@Override
	public Object value() { return VALUE; }
	/**
	 * Получить мету
	 * @param player - Игрок, у которого будет получена мета
	 * @return
	 */
	public static boolean get(Player player) {
		List<MetadataValue> metaList = player.getMetadata("SCChM::GlobalChatEnabled");
		for (MetadataValue metadataValue : metaList) {
			if (metadataValue instanceof GlobalChatEnabledMeta) 
				return ((GlobalChatEnabledMeta)metadataValue).asBoolean();
		}
		return false;
	}
	/**
	 * Установить мету
	 * @param player - Игрок, которому будет установлена мета
	 * @param value - Значение меты
	 */
	public static void set(Player player, boolean value) {
		player.setMetadata("SCChM::GlobalChatEnabled", new GlobalChatEnabledMeta(value));
	}

}

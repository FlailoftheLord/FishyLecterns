package me.flail.fishylecterns;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Lectern;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import me.flail.fishylecterns.tools.Logger;

public class LecternItem extends Logger {

	private ItemStack lecternItem;
	private String uuid;

	protected LecternItem(ItemStack item) {
		lecternItem = item;
		uuid = plugin.lecternDataTag;

		lecternItem = addTag(lecternItem, uuid, "Fishy Lectern");
		ItemMeta meta = lecternItem.getItemMeta();
		meta.setDisplayName(chat(plugin.config.get("LecternItemName").toString()));

		lecternItem.setItemMeta(meta);
	}

	public static LecternItem newLectern() {
		ItemStack item = new ItemStack(Material.LECTERN);

		return new LecternItem(item);
	}

	public boolean giveToPlayer(Player player) {
		player.getInventory().addItem(lecternItem);
		return player.isOnline();
	}

	public ItemStack getItem() {
		return lecternItem;
	}

	public static Lectern setLecternData(Lectern lectern, String tag, String data) {
		PersistentDataContainer dataContainer = lectern.getPersistentDataContainer();
		NamespacedKey nKey = new NamespacedKey(plugin, plugin.nKey.getKey() + "-" + tag);

		dataContainer.set(nKey, PersistentDataType.STRING, data);

		lectern.update(true);
		return lectern;
	}

	public static String getLecternData(Lectern lectern, String tag) {
		PersistentDataContainer dataContainer = lectern.getPersistentDataContainer();
		NamespacedKey nKey = new NamespacedKey(plugin, plugin.nKey.getKey() + "-" + tag);

		return dataContainer.has(nKey, PersistentDataType.STRING) ? dataContainer.get(nKey, PersistentDataType.STRING)
				: null;
	}

}

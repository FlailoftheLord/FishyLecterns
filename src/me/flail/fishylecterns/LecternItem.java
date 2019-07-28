package me.flail.fishylecterns;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

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


}

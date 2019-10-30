package me.flail.fishylecterns;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Lectern;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerTakeLecternBookEvent;
import org.bukkit.inventory.ItemStack;

import me.flail.fishylecterns.tools.Logger;

public class LecternListener extends Logger implements Listener {

	@EventHandler(priority = EventPriority.LOWEST)
	public void onInteract(PlayerTakeLecternBookEvent event) {
		Player player = event.getPlayer();
		Lectern lectern = event.getLectern();


		LecternLocations locations = new LecternLocations(event.getLectern().getWorld());

		if (locations.hasLectern(lectern.getLocation())) {
			if (!player.hasPermission("fishylecterns.use")) {
				event.setCancelled(true);

				player.sendMessage(chat(plugin.config.getString("NoPermission")));
				player.closeInventory();
				return;
			}

			if (!plugin.config.getBoolean("CanHaveMultipleBooks", true) && !player.hasPermission("fishylecterns.bypass")) {

				for (ItemStack invItem : player.getInventory().getContents().clone()) {

					if (hasTag(invItem, "fishy-book") && getTag(invItem, "fishy-book").equals(lectern.getLocation().toString())) {
						event.setCancelled(true);

						player.sendMessage(chat(plugin.config.getString("AlreadyHaveBook")));
						player.closeInventory();
						return;
					}

				}

			}

			ItemStack book = event.getBook();
			book = addTag(book, "fishy-book", lectern.getLocation().toString());
			book.setAmount(1);

			final ItemStack bookClone = book.clone();
			plugin.server.getScheduler().scheduleSyncDelayedTask(plugin, () -> {
				bookClone.setAmount(1);

				lectern.getSnapshotInventory().addItem(bookClone);
				lectern.update();

				String sound = plugin.config.get("BookRegenerateSound").toString().toUpperCase();
				if (sound.isEmpty()) {
					return;
				}
				try {
					lectern.getWorld().playSound(lectern.getLocation(),
							Sound.valueOf(sound), 1, 0);

				} catch (Exception e) {
					console("&cInvalid sound enum for config value&8: &eBookRegenerateSound");
				}
			}, 6L);

		}

	}

	@EventHandler(priority = EventPriority.LOW)
	public void onPlace(BlockPlaceEvent event) {
		Player player = event.getPlayer();
		ItemStack item = event.getItemInHand();

		if (hasTag(item, plugin.lecternDataTag)) {

			if (!player.hasPermission("fishylecterns.useitem")) {

				player.sendMessage(chat(plugin.config.getString("NoPermission")));
				return;
			}

			LecternLocations locations = new LecternLocations(player.getWorld());
			locations.addLectern(event.getBlock().getLocation());

			player.sendMessage(chat(plugin.config.getString("LecternPlaced")));
			return;
		}

	}

	@EventHandler(priority = EventPriority.LOW)
	public void onBreak(BlockBreakEvent event) {
		Player player = event.getPlayer();
		if (event.getBlock().getType() == Material.LECTERN) {
			LecternLocations locations = new LecternLocations(event.getBlock().getWorld());

			if (locations.hasLectern(event.getBlock().getLocation())) {
				locations.removeLectern(event.getBlock().getLocation());

				if (player.hasPermission("fishylecterns.useitem")) {
					event.getBlock().setType(Material.AIR);
					LecternItem.newLectern().giveToPlayer(player);

					player.sendMessage(chat(plugin.config.getString("LecternRemoved")));
				}

			}

		}

	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onBookDrop(PlayerDropItemEvent event) {
		Player player = event.getPlayer();
		ItemStack item = event.getItemDrop().getItemStack();

		if (hasTag(item, "fishy-book")) {
			if (!plugin.config.getBoolean("CanDropLecternBook") && !player.hasPermission("fishylecterns.bypass")) {
				event.setCancelled(true);

				player.sendMessage(chat(plugin.config.getString("CannotDropBook")));
			}

		}

	}

}

package me.flail.fishylecterns;

import org.bukkit.Material;
import org.bukkit.block.Lectern;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerTakeLecternBookEvent;
import org.bukkit.inventory.ItemStack;

import me.flail.fishylecterns.tools.Logger;

public class LecternListener extends Logger implements Listener {

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onInteract(PlayerTakeLecternBookEvent event) {
		Player player = event.getPlayer();
		ItemStack book = event.getBook();
		Lectern lectern = event.getLectern();

		String lecternData = LecternItem.getLecternData(lectern, plugin.lecternDataTag);
		if (lecternData != null) {
			if (!player.hasPermission("fishylecterns.use")) {
				event.setCancelled(true);

				player.sendMessage(chat("&cYou can't use that Lectern!"));
				return;
			}

			plugin.server.getScheduler().scheduleSyncDelayedTask(plugin, () -> {

				lectern.getInventory().clear();
				lectern.getInventory().addItem(book);
				lectern.update();
			}, 6L);

		}

	}

	@EventHandler
	public void onPlace(BlockPlaceEvent event) {
		Player player = event.getPlayer();
		ItemStack item = event.getItemInHand();

		console(item.toString());

		console(event.getBlock().getState().toString());
		if ((event.getBlock() instanceof Lectern) && hasTag(item, plugin.lecternDataTag)) {

			if (player.hasPermission("fishylecterns.use")) {
				Lectern lectern = (Lectern) event.getBlock().getState();

				lectern.getInventory().addItem(new ItemStack(Material.DIAMOND_BLOCK));
				lectern.update();

				console("is a fishy one");

				lectern = LecternItem.setLecternData(lectern, plugin.lecternDataTag, "Fishy Lectern");
				lectern.update();
			}

		}

	}

	@EventHandler
	public void onBreak(BlockBreakEvent event) {
		Player player = event.getPlayer();
		if ((event.getBlock() instanceof Lectern) && player.hasPermission("fishylecterns.use")) {
			Lectern lectern = (Lectern) event.getBlock().getState();

			if (LecternItem.getLecternData(lectern, plugin.lecternDataTag) != null) {
				event.setDropItems(false);

				LecternItem.newLectern().giveToPlayer(player);
			}

		}

	}

}

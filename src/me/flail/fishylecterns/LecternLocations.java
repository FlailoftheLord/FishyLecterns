package me.flail.fishylecterns;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.Location;
import org.bukkit.World;

import me.flail.fishylecterns.tools.DataFile;
import me.flail.fishylecterns.tools.Logger;

/**
 * This handles how Lectern data is stored persistently.
 * Since you cannot save data on blocks over a server reboot, Lectern Data is stored in their
 * respective World folders.
 * 
 * @author FlailoftheLord
 */
public class LecternLocations extends Logger {

	private Set<Location> lecterns;
	private DataFile storage;
	private World world;

	/**
	 * Gets a set of all FishyLecterns for this world.
	 * 
	 * @param world
	 *                  the world to grab lecterns for.
	 */
	@SuppressWarnings("unchecked")
	public LecternLocations(World world) {
		storage = new DataFile(world.getWorldFolder() + "/fishylecterns.yml", true);
		lecterns = new HashSet<>();

		if (storage.hasValue("LecternLocations")) {
			lecterns.addAll((Set<Location>) storage.getObj("LecternLocations"));
		}

		saveData();
	}

	public World getWorld() {
		return world;
	}

	public void addLectern(Location location) {
		lecterns.add(location);

		saveData();
	}

	public boolean hasLectern(Location location) {
		return lecterns.contains(location);
	}

	public void removeLectern(Location location) {
		lecterns.remove(location);

		saveData();
	}

	protected void saveData() {
		storage.setValue("LecternLocations", lecterns);
	}


}

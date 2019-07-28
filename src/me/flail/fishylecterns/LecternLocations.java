package me.flail.fishylecterns;

import java.util.Set;

import org.bukkit.Location;
import org.bukkit.World;

import me.flail.fishylecterns.tools.Logger;

/**
 * This handles how Lectern data is stored persistently.
 * Since you cannot save data on blocks over a server reboot, Lectern Data is stored in their
 * respective World folders.
 * 
 * @author FlailoftheLord
 */
public class LecternLocations extends Logger {

	/**
	 * Gets a list of all FishyLecterns for this world.
	 * 
	 * @param world
	 *                  the world to grab lecterns for.
	 * @return the new Set of Locations for each Fishy Lectern
	 */
	public Set<Location> get(World world) {

	}

}

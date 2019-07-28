package me.flail.fishylecterns;

import org.bukkit.NamespacedKey;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import me.flail.fishylecterns.tools.Logger;

public class FishyLecterns extends JavaPlugin {

	public FileConfiguration config;
	public Server server;

	public NamespacedKey nKey = new NamespacedKey(this, "FishyLecterns");

	public String lecternDataTag;

	@Override
	public void onLoad() {

		server = getServer();

		saveDefaultConfig();
		config = getConfig();
	}

	@Override
	public void onEnable() {
		Logger logger = new Logger();

		lecternDataTag = config.get("LecternUUID").toString();

		server.getPluginManager().registerEvents(new LecternListener(), this);
		logger.console("registered Lectern Listener...");
		registerCommands();
		logger.nl();
		logger.console(" &3Fishy&cLecterns");
		logger.console("   &7v" + getDescription().getVersion() + " &2by FlailoftheLord.");
		logger.console("  &cYeehaaaa!");

	}

	@Override
	public void onDisable() {
		Logger logger = new Logger();

		logger.console("&3Bye&cBye!");
	}

	public void reload() {
		Logger logger = new Logger();

		logger.nl();
		logger.console("&areloading FishyLecterns...");

		onDisable();
		onLoad();
		onEnable();

		logger.nl();
		logger.console("&aplugin reloaded!");
	}

	private void registerCommands() {
		Logger logger = new Logger();

		for (String cmd : getDescription().getCommands().keySet()) {
			getCommand(cmd).setExecutor(this);
			logger.console("&7registered command&8: &e/" + cmd);
		}
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		Logger logger = new Logger();

		if (sender instanceof Player) {
			Player operator = (Player) sender;
			String defaultInfo = logger.chat("&3Fishy&cLecterns &7v" + getDescription().getVersion() + " &2by FlailoftheLord.");
			if (!operator.hasPermission("fishylecterns.command")) {

				operator.sendMessage(logger.chat(config.get("NoPermission").toString()));
				return true;
			}

			switch (args.length) {
			case 0:

				operator.sendMessage(defaultInfo);
				return true;
			}

			if (args.length > 0) {
				switch (args[0].toLowerCase()) {
				case "get":
					LecternItem.newLectern().giveToPlayer(operator);

					operator.sendMessage(logger.chat("&aYou just got a &3Fishy&cLectern."));
					break;
				case "reload":
					reload();

					operator.sendMessage(logger.chat("&aFishyLecterns reloaded!"));
					break;

				}

			}

			return true;
		}

		logger.console("&cYou must use this command in-game!");
		return true;
	}

}
package pw.kaboom.extras;

import java.util.Collections;

import org.bukkit.Bukkit;
import org.bukkit.block.BlockFace;
import org.bukkit.plugin.java.JavaPlugin;

import pw.kaboom.extras.commands.CommandConsole;
import pw.kaboom.extras.commands.CommandEnchantAll;
import pw.kaboom.extras.commands.CommandKaboom;
import pw.kaboom.extras.commands.CommandPrefix;
import pw.kaboom.extras.commands.CommandPumpkin;
import pw.kaboom.extras.commands.CommandTellraw;
import pw.kaboom.extras.modules.block.BlockCheck;
import pw.kaboom.extras.modules.block.BlockPhysics;
import pw.kaboom.extras.modules.entity.EntityExplosion;
import pw.kaboom.extras.modules.entity.EntityKnockback;
import pw.kaboom.extras.modules.entity.EntitySpawn;
import pw.kaboom.extras.modules.entity.EntityTeleport;
import pw.kaboom.extras.modules.player.PlayerChat;
import pw.kaboom.extras.modules.player.PlayerCommand;
import pw.kaboom.extras.modules.player.PlayerConnection;
import pw.kaboom.extras.modules.player.PlayerDamage;
import pw.kaboom.extras.modules.player.PlayerInteract;
import pw.kaboom.extras.modules.player.PlayerRecipe;
import pw.kaboom.extras.modules.player.PlayerTeleport;
import pw.kaboom.extras.modules.server.ServerCommand;
import pw.kaboom.extras.modules.server.ServerTick;

public final class Main extends JavaPlugin {
	@Override
	public void onLoad() {
		/* Fill lists */
		Collections.addAll(
			BlockPhysics.getBlockFaces(),
			BlockFace.NORTH,
			BlockFace.SOUTH,
			BlockFace.WEST,
			BlockFace.EAST,
			BlockFace.UP
		);

		/* Load missing config.yml defaults */
		getConfig().options().copyDefaults(true);
		saveConfig();
	}

	@Override
	public void onEnable() {
		/* Commands */
		this.getCommand("console").setExecutor(new CommandConsole());
		this.getCommand("enchantall").setExecutor(new CommandEnchantAll());
		this.getCommand("kaboom").setExecutor(new CommandKaboom());
		this.getCommand("prefix").setExecutor(new CommandPrefix());
		this.getCommand("pumpkin").setExecutor(new CommandPumpkin());
		this.getCommand("tellraw").setExecutor(new CommandTellraw());

		/* Block-related modules */
		this.getServer().getPluginManager().registerEvents(new BlockCheck(), this);
		this.getServer().getPluginManager().registerEvents(new BlockPhysics(), this);

		/* Entity-related modules */
		this.getServer().getPluginManager().registerEvents(new EntityExplosion(), this);
		this.getServer().getPluginManager().registerEvents(new EntityKnockback(), this);
		this.getServer().getPluginManager().registerEvents(new EntitySpawn(), this);
		this.getServer().getPluginManager().registerEvents(new EntityTeleport(), this);

		/* Player-related modules */
		this.getServer().getPluginManager().registerEvents(new PlayerChat(), this);
		this.getServer().getPluginManager().registerEvents(new PlayerCommand(), this);
		this.getServer().getPluginManager().registerEvents(new PlayerConnection(), this);
		this.getServer().getPluginManager().registerEvents(new PlayerDamage(), this);
		this.getServer().getPluginManager().registerEvents(new PlayerInteract(), this);
		this.getServer().getPluginManager().registerEvents(new PlayerRecipe(), this);
		this.getServer().getPluginManager().registerEvents(new PlayerTeleport(), this);

		/* Server-related modules */
		this.getServer().getPluginManager().registerEvents(new ServerCommand(), this);
		this.getServer().getPluginManager().registerEvents(new ServerTick(), this);
	}

	@Override
	public void onDisable() {
		if (Bukkit.isStopping()) {
			/* This should never be done in a critical environment, as it can lead to data corruption.
				We are not too concerned with data corruption, as the server resets daily. In our case, it's
				more important to ensure that the server never hangs. */
			Runtime.getRuntime().halt(0);
		}
	}
}

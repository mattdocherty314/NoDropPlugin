package me.mattdocherty.nodropplugin;

import me.mattdocherty.nodropplugin.events.OnListItems;
import org.bukkit.plugin.java.JavaPlugin;

import me.mattdocherty.nodropplugin.commands.BaseCommand;
import me.mattdocherty.nodropplugin.events.OnPlayerDropItem;
import me.mattdocherty.nodropplugin.events.OnPlayerJoin;
import me.mattdocherty.nodropplugin.files.PlayerOptionsConfig;

public final class NoDropPlugin extends JavaPlugin {
    public void onEnable() {
        // Register Events
        this.getServer().getPluginManager().registerEvents(new OnPlayerDropItem(), this);
        this.getServer().getPluginManager().registerEvents(new OnPlayerJoin(), this);
        this.getServer().getPluginManager().registerEvents(new OnListItems(), this);

        // Load config files
        this.getConfig().options().copyDefaults();
        this.saveDefaultConfig();
        PlayerOptionsConfig.setup();
        PlayerOptionsConfig.get().options().copyDefaults(true);
        PlayerOptionsConfig.save();

        // Register commands
        this.getCommand("nd").setExecutor(new BaseCommand());

        System.out.println("Started: Drop Check Plugin.");
    }

    public void onDisable() {
        System.out.println("Stopped: Drop Check Plugin.");
    }
}

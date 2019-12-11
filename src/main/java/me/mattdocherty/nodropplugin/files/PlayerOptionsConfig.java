package me.mattdocherty.nodropplugin.files;

import java.io.File;
import java.io.IOException;
import me.mattdocherty.nodropplugin.NoDropPlugin;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

// This is responsible for interfacing with the custom config file 'playerOptions.yml'
public class PlayerOptionsConfig {
    private static File file;
    private static FileConfiguration configFile;

    // Create a new file
    public static void setup() {
        File configDir = Bukkit.getServer().getPluginManager().getPlugin("NoDropPlugin").getDataFolder();
        file = new File(configDir, "playerOptions.yml");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException ex) {
                return;
            }
        }

        configFile = YamlConfiguration.loadConfiguration(file);
    }

    // Get reference for file
    public static FileConfiguration get() {
        return configFile;
    }

    // Save the config file
    public static void save() {
        try {
            configFile.save(file);
        } catch (IOException ex) {
            System.out.println("Unable to save 'playerOptions.yml'!");
        }

    }

    // Reload the file
    public static void reload() {
        configFile = YamlConfiguration.loadConfiguration(file);
    }

    // Create default user settings from the default config file
    public static void createUserSettings(String playerUUID) {
        Plugin pl = NoDropPlugin.getPlugin(NoDropPlugin.class);
        configFile.addDefault(playerUUID + ".enabled", pl.getConfig().getBoolean("enabled-by-default"));
        configFile.addDefault(playerUUID + ".items", pl.getConfig().getMapList("default-undroppable-items"));
        save();
    }
}

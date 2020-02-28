package me.mattdocherty.nodropplugin.commands;

import me.mattdocherty.nodropplugin.files.PlayerOptionsConfig;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Map;

public class ListItems {
    public static boolean listItems(Player p, String playerUUID) {
        List<Map<?, ?>> itemList = PlayerOptionsConfig.get().getMapList(playerUUID + ".items"); // Load list from config

        // Check to see if there is a list in the config
        if (itemList != null && itemList.size() != 0) {
        }
        // Give error for having nothing in the no drop list or no drop list initialised
        else {
            p.sendMessage(ChatColor.RED + "You do not have anything in your no drop list.");
        }

        return true;
    }
}

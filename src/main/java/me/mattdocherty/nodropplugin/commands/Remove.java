package me.mattdocherty.nodropplugin.commands;

import java.util.List;
import java.util.Map;
import me.mattdocherty.nodropplugin.convert.SerializeItem;
import me.mattdocherty.nodropplugin.files.PlayerOptionsConfig;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Remove {
    public static boolean removeItem(Player p, String playerUUID) {
        ItemStack itemToRemove = p.getInventory().getItemInMainHand(); // Get item from hand
        List<Map<?, ?>> itemList = PlayerOptionsConfig.get().getMapList(playerUUID + ".items"); // Load list from config

        // Give error for checking nothing
        if (itemToRemove.getType().equals(Material.AIR)) {
            p.sendMessage(ChatColor.RED + "You are not holding anything.");
        }
        // Check to see if there is a list in the config
        else if (itemList != null && itemList.size() != 0) {
            Map<String, Object> convertedItemRemove = SerializeItem.serializeItem(itemToRemove); // Get metadata to remove
            // Remove item from list and save to config
            itemList.remove(convertedItemRemove);
            PlayerOptionsConfig.get().set(playerUUID + ".items", itemList);
            PlayerOptionsConfig.save();
            p.sendMessage(ChatColor.GREEN + "You have removed your held item to your drop check items.");
        }
        // Give error for having nothing in the no drop list or no drop list initialised
        else {
            p.sendMessage(ChatColor.RED + "You do not have anything in your drop check items.");
        }
        return true;
    }

    public static boolean removeID(Player p, String playerUUID, String id) {
        return false;
    }
}

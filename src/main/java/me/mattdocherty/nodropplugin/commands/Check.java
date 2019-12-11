package me.mattdocherty.nodropplugin.commands;

import me.mattdocherty.nodropplugin.convert.SerializeItem;
import me.mattdocherty.nodropplugin.files.PlayerOptionsConfig;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Map;

public class Check {
    public static boolean checkItem(Player p, String playerUUID) {
        ItemStack itemToCheck = p.getInventory().getItemInMainHand(); // Get item from hand

        List<Map<?, ?>> itemList = PlayerOptionsConfig.get().getMapList(playerUUID + ".items"); // Load list from config

        // Give error for checking nothing
        if (itemToCheck.getType().equals(Material.AIR)) {
            p.sendMessage(ChatColor.RED + "You are not holding anything.");
        }
        // Check to see if there is a list in the config
        else if (itemList != null && itemList.size() != 0) {
            Map<String, Object> convertedItemRemove = SerializeItem.serializeItem(itemToCheck); // Get metadata to check
            // Give confirmation if in no drop list
            if (itemList.contains(convertedItemRemove)) {
                p.sendMessage(ChatColor.GREEN + "You have your held item in your no drop item list.");
            }
            // Give rejection if not in no drop list
            else {
                p.sendMessage(ChatColor.RED + "You do not have your held item in your no drop list.");
            }
        }
        // Give error for having nothing in the no drop list or no drop list initialised
        else {
            p.sendMessage(ChatColor.RED + "You do not have anything in your no drop list.");
        }

        return true;
    }
}

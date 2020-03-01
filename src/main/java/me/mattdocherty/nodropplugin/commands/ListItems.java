package me.mattdocherty.nodropplugin.commands;

import me.mattdocherty.nodropplugin.convert.SerializeEnchantments;
import me.mattdocherty.nodropplugin.convert.SerializeItem;
import me.mattdocherty.nodropplugin.files.PlayerOptionsConfig;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ListItems {
    public static boolean listItems(Player p, String playerUUID) {
        List<Map<?, ?>> itemList = PlayerOptionsConfig.get().getMapList(playerUUID + ".items"); // Load list from config

        // Check to see if there is a list in the config
        if (itemList != null && itemList.size() != 0) {
            p.sendMessage(ChatColor.GOLD + "Items on your No Drop list are:"); // Title of List

            int itemCount = 1; // To count the items
            // Go through all the items in the list
            for (Map<?, ?> item : itemList) {
                // Create variables once rather multiple times while checking
                String itemType = item.get("item-type").toString();
                String itemName = item.get("item-name").toString();
                ArrayList<String> itemLore = (ArrayList<String>)item.get("item-lore");
                Map<String, Integer> itemEnchants = ((Map<String, Integer>)item.get("item-enchants"));

                // Item Type
                p.sendMessage(ChatColor.GOLD+"("+itemCount+") "+ChatColor.YELLOW+itemType);

                // Only Present the extra information if it exists
                // Item Name
                if (!itemName.equals("")) {
                    p.sendMessage(ChatColor.GOLD+"  Name: '"+ChatColor.YELLOW+itemName+ChatColor.GOLD+"'");
                }
                // Item Lore
                if (itemLore != null) {
                    p.sendMessage(ChatColor.GOLD+"  Lore:");
                    for (int i = 0; i < itemLore.size(); i++) {
                        p.sendMessage("    "+ChatColor.YELLOW+"'"+itemLore.get(i)+ChatColor.YELLOW+"'");
                    }
                }
                // Item Enchantments
                if (itemEnchants.size() != 0) {
                    p.sendMessage(ChatColor.GOLD+"  Enchantments:");
                    for (Map.Entry<String, Integer> enchant : itemEnchants.entrySet()) {
                        // Store the name and level of the enchantment in a variable
                        String enchName = (enchant.getKey().split("minecraft:")[1]).split("'")[0]; // Remove the 'minecraft:'
                        String enchLvl = SerializeEnchantments.convertFromNumberToNumeral(enchant.getValue());
                        p.sendMessage("    "+ChatColor.YELLOW+"'"+enchName+" "+enchLvl+ChatColor.YELLOW+"'");
                    }
                }
                itemCount++;
            }
        }
        // Give error for having nothing in the no drop list or no drop list initialised
        else {
            p.sendMessage(ChatColor.RED + "You do not have anything in your no drop list.");
        }

        return true;
    }
}

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
            p.sendMessage(ChatColor.DARK_AQUA + "Items on your No Drop list are:");
            int itemCount = 1;
            for (Map<?, ?> item : itemList) {
                String itemType = item.get("item-type").toString();
                String itemName = item.get("item-name").toString();
                ArrayList<String> itemLore = (ArrayList<String>)item.get("item-lore");
                Map<String, Integer> itemEnchants = ((Map<String, Integer>)item.get("item-enchants"));

                p.sendMessage(ChatColor.DARK_AQUA+"("+itemCount+") "+ChatColor.AQUA+itemType);

                if (!itemName.equals("")) {
                    p.sendMessage(ChatColor.DARK_AQUA+"  Name: '"+ChatColor.AQUA+itemName+ChatColor.DARK_AQUA+"'");
                }
                if (itemLore != null) {
                    p.sendMessage(ChatColor.DARK_AQUA+"  Lore:");
                    for (int i = 0; i < itemLore.size(); i++) {
                        p.sendMessage("    "+ChatColor.AQUA+"'"+itemLore.get(i)+ChatColor.AQUA+"'");
                    }
                }
                if (itemEnchants.size() != 0) {
                    p.sendMessage(ChatColor.DARK_AQUA+"  Enchantments:");
                    for (Map.Entry<String, Integer> enchant : itemEnchants.entrySet()) {
                        String enchName = (enchant.getKey().split("minecraft:")[1]).split("'")[0];
                        String enchLvl = SerializeEnchantments.convertFromNumberToNumeral(enchant.getValue());
                        p.sendMessage("    "+ChatColor.AQUA+"'"+enchName+" "+enchLvl+ChatColor.AQUA+"'");
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

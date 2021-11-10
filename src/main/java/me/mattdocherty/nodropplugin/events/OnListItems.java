package me.mattdocherty.nodropplugin.events;

import me.mattdocherty.nodropplugin.NoDropPlugin;
import me.mattdocherty.nodropplugin.commands.Remove;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class OnListItems implements Listener {
    private static Inventory listInv;

    public static void onListItems(Player p, List<Map<?, ?>> itemList) {
        int closestMultNine = (int)Math.min(Math.ceil(itemList.size()/9.0)*9, 54);
        String title = "No Drop List";
        Plugin pl = NoDropPlugin.getPlugin(NoDropPlugin.class);
        if (pl.getConfig().getBoolean("click-list-to-remove")) {
            title += " (Click to Remove)";
        }

        listInv = Bukkit.createInventory(null, closestMultNine, title);

        int itemCount = 1; // To count the items
        // Go through all the items in the list
        for (Map<?, ?> item : itemList) {
            // Create variables once rather multiple times while checking
            String itemType = item.get("item-type").toString();
            String itemName = item.get("item-name").toString();
            ArrayList<String> itemLore = (ArrayList<String>)item.get("item-lore");
            Map<String, Integer> itemEnchants = ((Map<String, Integer>)item.get("item-enchants"));

            // Item Type
            ItemStack newItem = createInvItem(itemCount, itemType, itemName, itemLore, itemEnchants);
            listInv.addItem(newItem);
            itemCount++;
        }
        p.openInventory(listInv);
    }

    private static ItemStack createInvItem(int index, String type, String name, ArrayList<String> lore, Map<String, Integer> enchants) {
        Material itemMat = Material.valueOf(type);

        ItemStack item = new ItemStack(itemMat, index);
        ItemMeta itemMeta = item.getItemMeta();

        itemMeta.setDisplayName(name);
        itemMeta.setLore(lore);

        item.setItemMeta(itemMeta);

        enchants.forEach((ench, level) -> {
            NamespacedKey enchKey = NamespacedKey.minecraft(ench.split(":")[1].replace("'", ""));
            item.addEnchantment(Enchantment.getByKey(enchKey), level);
        });

        return item;
    }

    @EventHandler
    public void onListItemClick(InventoryClickEvent e) {
        if (e.getInventory() != listInv) {
            return;
        }
        e.setCancelled(true);

        Player p = (Player)e.getWhoClicked();
        String playerUUID = p.getUniqueId().toString();
        int id = e.getRawSlot()+1;

        Plugin pl = NoDropPlugin.getPlugin(NoDropPlugin.class);
        if (!pl.getConfig().getBoolean("click-list-to-remove")) {
            return;
        }
        if (!p.hasPermission("nodrop.remove")) {
            p.sendMessage(ChatColor.DARK_RED + "You do not have permission to run that command.");
            return;
        }
        Remove.removeID(p, playerUUID, id);
    }
}

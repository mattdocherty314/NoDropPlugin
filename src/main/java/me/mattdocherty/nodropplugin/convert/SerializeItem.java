package me.mattdocherty.nodropplugin.convert;

import java.util.HashMap;
import java.util.Map;
import org.bukkit.inventory.ItemStack;

// This class is required to save the specified metadata in a human-readable format
public class SerializeItem {

    public static Map<String, Object> serializeItem(ItemStack item) {
        Map<String, Object> serializedItem = new HashMap();

        serializedItem.put("item-type", item.getType().name());
        serializedItem.put("item-name", item.getItemMeta().getDisplayName());
        serializedItem.put("item-lore", item.getItemMeta().getLore());
        serializedItem.put("item-enchants", SerializeEnchantments.serializeEnchantments(item.getItemMeta().getEnchants()));

        return serializedItem;
    }
}
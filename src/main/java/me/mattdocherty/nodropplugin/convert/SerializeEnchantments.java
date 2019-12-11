package me.mattdocherty.nodropplugin.convert;

import java.util.HashMap;
import java.util.Map;
import org.bukkit.enchantments.Enchantment;

// This class is required to save the enchantments in a human-readable format
public class SerializeEnchantments {
    public static Map<String, Integer> serializeEnchantments(Map<Enchantment, Integer> enchantments) {
        Map<String, Integer> serializedEnchantment = new HashMap();

        // Go through all of the list of enchantments and serialize them
        enchantments.forEach((enchantment, level) -> {
            serializedEnchantment.put("'" + enchantment.getKey().toString() + "'", level);
        });

        return serializedEnchantment;
    }
}

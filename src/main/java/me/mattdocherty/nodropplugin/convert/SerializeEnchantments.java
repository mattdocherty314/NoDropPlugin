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
            serializedEnchantment.put("'" + enchantment.getKey() + "'", level);
        });

        return serializedEnchantment;
    }

    public static String convertFromNumberToNumeral(Integer level) {
        // Convert the number into the Roman Numeral (Will make a function, rather than hardcode)
        switch (level) {
            case 1:
                return "";
            case 2:
                return "II";
            case 3:
                return "III";
            case 4:
                return "IV";
            case 5:
                return "V";
            default:
                return level.toString();
        }
    }
}


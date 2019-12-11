package me.mattdocherty.nodropplugin.commands;

import me.mattdocherty.nodropplugin.convert.SerializeItem;
import me.mattdocherty.nodropplugin.files.PlayerOptionsConfig;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.permissions.PermissionAttachmentInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.apache.commons.lang.StringUtils.isNumeric;

// The /nd add sub command
public class Add {
    public static boolean addItem(Player p, String playerUUID) {
        ItemStack itemToAdd = p.getInventory().getItemInMainHand(); // Get item from hand

        // Give error for adding nothing
        if (itemToAdd.getType().equals(Material.AIR)) {
            p.sendMessage(ChatColor.RED + "You are not holding anything.");
            return true;
        }
        else {
            Map<String, Object> convertedItemAdd = SerializeItem.serializeItem(itemToAdd); // Get metadata to save
            // Get existing list from the config
            List<Map<?, ?>> itemList = PlayerOptionsConfig.get().getMapList(playerUUID + ".items");

            // Make sure that there is an entry
            if (itemList != null && !((List)itemList).isEmpty()) {
                // Let the player know if trying to add an item already added
                if (((List)itemList).contains(convertedItemAdd)) {
                    p.sendMessage(ChatColor.RED + "The item is already on your no drop list.");
                    return true;
                }
                // Check player limit set by permissions and give error, if applicable
                int playerAddLimit = getItemListLimit(p);
                if ((itemList.size() >= playerAddLimit) && (playerAddLimit != -1)) {
                    p.sendMessage(ChatColor.RED + "You have reached your limit of items on your no drop list.");
                    return true;
                }
            }
            // If there wasn't an entry in the config, create a new one
            else {
                itemList = new ArrayList();
            }

            // Add to the config file and save it.
            ((List)itemList).add(convertedItemAdd);
            PlayerOptionsConfig.get().set(playerUUID + ".items", itemList);
            PlayerOptionsConfig.save();
            p.sendMessage(ChatColor.GREEN + "You have added the held item to your no drop list.");

            return true;
        }
    }

    // Function that gets the permission for the no drop list limit.
    private static int getItemListLimit(Player p) {
        // Go through all the permissions
        for (PermissionAttachmentInfo permissionInfo : p.getEffectivePermissions()) {
            String permission = permissionInfo.getPermission();
            // Check to see if it is the specific permission
            if (permission.startsWith("nodrop.limit")) {
                // Separate the number from the permission
                String limitString = permission.substring(permission.lastIndexOf('.')+1, permission.length());
                if (isNumeric(limitString)) {
                    return Integer.parseInt(limitString);
                }
            }
        }
        // Return -1 if no permission
        return -1;
    }
}

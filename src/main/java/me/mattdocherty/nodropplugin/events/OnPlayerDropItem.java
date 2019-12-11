package me.mattdocherty.nodropplugin.events;

import java.util.List;
import java.util.Map;
import me.mattdocherty.nodropplugin.convert.SerializeItem;
import me.mattdocherty.nodropplugin.files.PlayerOptionsConfig;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;

// Runs when a player attempts to drop an item
public class OnPlayerDropItem implements Listener {
    @EventHandler
    public void onPlayerDropItem(PlayerDropItemEvent e) {
        Player p = e.getPlayer();
        String playerUUID = p.getUniqueId().toString(); // Get player's UUID for config access

        if (PlayerOptionsConfig.get().getBoolean(playerUUID + ".enabled")) {
            List<Map<?, ?>> itemList = PlayerOptionsConfig.get().getMapList(playerUUID + ".items");
            Map<String, Object> itemDropped = SerializeItem.serializeItem(e.getItemDrop().getItemStack());

            // If the player attempts to drop an item on their list prevent them
            if (itemList.contains(itemDropped)) {
                e.setCancelled(true);
                p.sendMessage(ChatColor.YELLOW + "You have disabled item dropping for that item.");
            }
        }

    }
}

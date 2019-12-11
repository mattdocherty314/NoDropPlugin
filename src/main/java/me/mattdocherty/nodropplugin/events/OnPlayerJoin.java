package me.mattdocherty.nodropplugin.events;

import me.mattdocherty.nodropplugin.files.PlayerOptionsConfig;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

// Runs when a player joins the game
public class OnPlayerJoin implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        // Checks to see if the player has an entry in the config file and if not, create one
        if (!this.hasConfigSettings(e.getPlayer())) {
            PlayerOptionsConfig.createUserSettings(e.getPlayer().getUniqueId().toString());
        }

    }

    private boolean hasConfigSettings(Player p) {
        return PlayerOptionsConfig.get().isSet(p.getUniqueId().toString());
    }
}

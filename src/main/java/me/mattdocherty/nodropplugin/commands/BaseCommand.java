package me.mattdocherty.nodropplugin.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BaseCommand implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        // Check if command was run by player
        if (sender instanceof Player) {
            Player p = (Player) sender;
            String playerUUID = p.getUniqueId().toString(); // Get player UUID for config access

            if (command.getName().equals("nd")) {
                // If no arguments are given, add the item held in the player's hand to the list of no drop items.
                if (args.length == 0) {
                    if (!p.hasPermission("nodrop.drop")) {
                        p.sendMessage(ChatColor.DARK_RED + "You do not have permission to run that command.");
                        return true;
                    }
                    return Add.addItem(p, playerUUID);
                }

                switch (args[0]) {
                    // If 'remove' argument is given, the item held in the player's hand is removed from the list of no drop items.
                    case "remove":
                        if (!p.hasPermission("nodrop.remove")) {
                            p.sendMessage(ChatColor.DARK_RED + "You do not have permission to run that command.");
                            return true;
                        }
                        if (args.length == 2) {
                            return Remove.removeItem(p, playerUUID);
                        }
                        return Remove.removeID(p, playerUUID, args[2]);
                    // If 'check' argument is given, check if the item held in the player's hand is in the list of no drop items.
                    case "check":
                        if (!p.hasPermission("nodrop.check")) {
                            p.sendMessage(ChatColor.DARK_RED + "You do not have permission to run that command.");
                            return true;
                        }
                        return Check.checkItem(p, playerUUID);
                    case "list":
                        if (!p.hasPermission("nodrop.list")) {
                            p.sendMessage(ChatColor.DARK_RED + "You do not have permission to run that command.");
                            return true;
                        }
                        return ListItems.listItems(p, playerUUID);
                    // Otherwise, say to user invalid command
                    default:
                        p.sendMessage(ChatColor.DARK_RED + "Invalid Command!");
                        return false;
                }
            }
        }

        System.out.println(ChatColor.DARK_RED + "That command is only available to players!");

        return false;
    }
}

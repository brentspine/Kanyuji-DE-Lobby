package de.kanyuji.lobby.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MessageLaterCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] strings) {
        if(!(sender instanceof Player)) {
            Player player = (Player) sender;
            if(!player.hasPermission("lobby.messagelater")) {

            }
        }
        return false;
    }

}

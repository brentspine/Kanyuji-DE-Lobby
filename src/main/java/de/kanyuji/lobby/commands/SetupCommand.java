package de.kanyuji.lobby.commands;

import de.kanyuji.lobby.Main;
import de.kanyuji.lobby.utils.LocationUtil;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetupCommand implements CommandExecutor {

    // /setup [spawn]

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)) {
            sender.sendMessage(Main.PREFIX + "Dieser Befehl ist nur für InGame");
            return true;
        }
        Player player = (Player) sender;
        if(args.length <= 0) {
            player.sendMessage(Main.PREFIX + "Verwendung: /" + label + " [spawn]");
            return true;
        }
        if (args[0].equalsIgnoreCase("spawn")) {
            player.sendMessage(Main.PREFIX + "Der Spawn wurde gesetzt!");
            new LocationUtil(Main.getInstance(), player.getLocation(), "spawn").saveLocation();
            return true;
        }
        return true;
    }

    //todo setup command

}

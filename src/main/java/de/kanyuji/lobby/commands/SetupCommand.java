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
        if(!player.hasPermission("lobby.setup")) {
            player.sendMessage(Main.NOPERM);
            return true;
        }
        if(args.length <= 0) {
            player.sendMessage(Main.PREFIX + "Verwendung: /" + label + " [spawn | <GameMode>]");
            return true;
        } else {
            new LocationUtil(Main.getInstance(),  player.getLocation(), "locs." + args[0].toUpperCase()).saveLocation();
            player.sendMessage(Main.PREFIX + "Du hast die Location für §c" + args[0] + "§7 gesetzt");
        }

        return true;
    }

    //todo setup command

}

package de.kanyuji.lobby.commands;

import de.kanyuji.lobby.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.ArrayList;
import java.util.UUID;

public class BuildCommand implements CommandExecutor {

    public static ArrayList<UUID> allowedToBuild = new ArrayList<>();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)) {
            sender.sendMessage(Main.PREFIX + "Dieser Command ist nur InGame verfügbar!");
            return false;
        }
        Player player = (Player) sender;
        if(allowedToBuild.contains(player.getUniqueId())) {
            allowedToBuild.remove(player.getUniqueId());
            player.sendMessage(Main.PREFIX + "§cBuild Modus §7wurde für dich §cdeaktiviert");
            return false;
        }
        allowedToBuild.add(player.getUniqueId());
        player.sendMessage(Main.PREFIX + "§cBuild Modus §7wurde für dich §caktiviert");
        return false;
    }

}

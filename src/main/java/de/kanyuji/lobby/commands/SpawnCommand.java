package de.kanyuji.lobby.commands;

import de.kanyuji.lobby.Main;
import de.kanyuji.lobby.utils.LocationUtil;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SpawnCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)) {
            sender.sendMessage(Main.PREFIX + "Dieser Befehl ist nur für InGame");
            return true;
        }
        Player player = (Player) sender;
        player.teleport(new LocationUtil(Main.getInstance(), "spawn").getLocation());
        player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1, 1);
        return false;
    }

}

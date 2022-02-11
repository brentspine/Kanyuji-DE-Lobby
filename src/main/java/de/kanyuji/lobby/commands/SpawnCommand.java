package de.kanyuji.lobby.commands;

import de.kanyuji.lobby.Main;
import de.kanyuji.lobby.Settings;
import de.kanyuji.lobby.utils.LocationUtil;
import org.bukkit.Sound;
import org.bukkit.block.data.type.EnderChest;
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
        player.playSound(player.getLocation(), Settings.TELEPORT_SOUND, 1, 1);
        //player.sendMessage(player.getLocation().subtract(0, -1, 0).getBlock().getType().name() + " type, " + player.getLocation().subtract(0, -1, 0).getBlock().getLocation().getYaw() + " Yaw, " + player.getLocation().subtract(0, -1, 0).getBlock().getLocation().getPitch() + " Pitch");
        //player.sendMessage( player.getLocation().subtract(0, 1, 0).getBlock().getType().name() + " type, " + ((EnderChest) player.getLocation().subtract(0, 1, 0).getBlock().getLocation().getBlock().getBlockData()).getFacing().name() + " facing, " + player.getLocation().subtract(0, 1, 0).getBlock().getLocation().getYaw() + " Yaw, " + player.getLocation().subtract(0, 1, 0).getBlock().getLocation().getPitch() + " Pitch");
        return false;
    }

}

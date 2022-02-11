package de.kanyuji.lobby.commands;

import de.brentspine.kanyujiapi.mysql.data.MySQLMessageLater;
import de.brentspine.kanyujiapi.util.DirectMessage;
import de.kanyuji.lobby.Main;
import de.kanyuji.lobby.utils.UUIDFetcher;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class MessageLaterCommand implements CommandExecutor, Listener {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)) {
            return true;
        }
        Player player = (Player) sender;
        if(!player.hasPermission("lobby.messagelater")) {
            player.sendMessage(Main.NOPERM);
            return true;
        }
        //messageLater <Player> <Message>
        if(args.length < 2) {
            sender.sendMessage(Main.PREFIX + "§cUsage: /messageLater <Player> <Message>");
            return true;
        }
        OfflinePlayer target = Bukkit.getOfflinePlayer(UUIDFetcher.getUUIDWithOfflinePlayer(args[0]));
        String message = "";
        Boolean confirm = false;
        for(String current : args) {
            if(current != args[0]) {
                message = message + current + " ";
            }
        }
        message = message.replaceAll("&", "§");
        try {
            target.getName();
        } catch (Exception e) {
            sender.sendMessage(Main.PREFIX + "§cDieser Spieler existiert nicht!");
            return true;
        }

        for(Player current : Bukkit.getOnlinePlayers()) {
            if(current.getUniqueId() == target.getUniqueId()) {
                Player t = (Player) target;
                t.sendMessage(Main.PREFIX + "§2§lYou got a message from §a§l" + player.getName());
                t.sendMessage(Main.PREFIX + message);
                player.sendMessage(Main.PREFIX + "Da hast §c" + target.getName() + "§7 eine Nachricht gesendet ");
                return true;
            }
        }


        MySQLMessageLater.createMessage(target.getUniqueId(), player.getUniqueId(), message);
        player.sendMessage(Main.PREFIX + "Da hast §c" + target.getName() + "§7 eine Nachricht gesendet ");

        return false;
    }


    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if(MySQLMessageLater.isTargetExisting(player.getUniqueId())) {
            ArrayList<DirectMessage> dms = MySQLMessageLater.getMessages(player.getUniqueId());
            for(DirectMessage current : dms) {
                player.sendMessage(Main.PREFIX + "§2§lYou got a message from §a§l" + UUIDFetcher.getNameWithOfflinePlayer(UUID.fromString(current.getSender())) + "§2§l:");
                player.sendMessage(Main.PREFIX + current.getMessage());
            }
            MySQLMessageLater.removeAllMessages(player.getUniqueId());
        }
    }


}

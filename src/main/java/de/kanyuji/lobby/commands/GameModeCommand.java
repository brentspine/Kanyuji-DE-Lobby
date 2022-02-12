package de.kanyuji.lobby.commands;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class GameModeCommand implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String Usage = ChatColor.RED + "Wrong Usage \n" +
                ChatColor.WHITE + ChatColor.BOLD +
                "Use: \n" +
                "0 -> survival \n" +
                "1 -> creative \n" +
                "2 -> adventure \n" +
                "3 -> spectator \n";
        if (!(sender instanceof Player player)){
            sender.sendMessage("You are not a Player");
            return false;
        }
        switch (args[0].toLowerCase(Locale.ROOT)) {
            case "0", "survival" -> {
                if (player.getGameMode() == GameMode.SURVIVAL) {
                    break;
                }
                player.setGameMode(GameMode.SURVIVAL);
                player.sendMessage("» " + ChatColor.GRAY + "Survival");
            }
            case "1", "creative" -> {
                if (player.getGameMode() == GameMode.CREATIVE) {
                    break;
                }
                player.setGameMode(GameMode.CREATIVE);
                player.sendMessage("» " + ChatColor.GRAY + "Creative");
            }
            case "2", "adventure" -> {
                if (player.getGameMode() == GameMode.ADVENTURE) {
                    break;
                }
                player.setGameMode(GameMode.ADVENTURE);
                player.sendMessage("» " + ChatColor.GRAY + "Adventure");
            }
            case "3", "spectator" -> {
                if (player.getGameMode() == GameMode.SPECTATOR) {
                    break;
                }
                player.setGameMode(GameMode.SPECTATOR);
                player.sendMessage("» " + ChatColor.GRAY + "Spectator");
            }
            default -> player.sendMessage(Usage);
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (!sender.hasPermission("lobby.gm")) return null;
        ArrayList<String> list = new ArrayList<>();
        if (args.length==0) return list;
        list.add("Survival");
        list.add("Creative");
        list.add("Adventure");
        list.add("Spectator");
        String a = args[args.length-1].toLowerCase();
        ArrayList<String> l2 = new ArrayList<>();
        for (String s : list) {
            String s1 = s.toLowerCase();
            if (s1.startsWith(a)){
                l2.add(s);
            }
        }
        return list;
    }
}
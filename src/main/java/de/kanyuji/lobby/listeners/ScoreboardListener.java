package de.kanyuji.lobby.listeners;

import de.brentspine.coinsystem.mysql.MySQLCoins;
import de.kanyuji.lobby.Main;
import de.kanyuji.lobby.fastboard.FastBoard;
import de.kanyuji.lobby.utils.MySQLUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;

import javax.swing.text.NumberFormatter;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ScoreboardListener implements Listener {

    public static final Map<UUID, FastBoard> boards = new HashMap<>();
    public static final Map<UUID, Integer> coins = new HashMap<>();
    public static final Map<UUID, String> playTime = new HashMap<>();

    @EventHandler
    public void handlePlayerJoinEvent(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        FastBoard board = new FastBoard(player);
        board.updateTitle(ChatColor.AQUA.toString() + ChatColor.BOLD + "Lobby");
        boards.put(player.getUniqueId(), board);
    }

    @EventHandler
    public void handlePlayerQuitEvent(PlayerQuitEvent e) {
        Player player = e.getPlayer();
        FastBoard board = boards.remove(player.getUniqueId());
        if (board != null) {
            board.delete();
        }
    }
    public static void updateBoard(FastBoard board) {
        board.updateLines(
                "",
                "§fRang",
                "§7Todo", "",
                "§6Coins",
                "§7" + "debug", "",
                "§eSpielzeit",
                "§7" + "debug",
                "             ");
    }


    public static void updateCoins(UUID uuid) {
        coins.remove(uuid);
        new NumberFormatter();
        coins.put(uuid, MySQLCoins.getPoints(uuid));
    }

    public static void updatePlayTime(UUID uuid) {
        playTime.remove(uuid);
        playTime.put(uuid, MySQLUtil.getFormattedTime(uuid));
    }


    //private static void executeEveryMinute() {
    //   new BukkitRunnable() {
    //        @Override
    //        public void run() {
    //            for(Player current : Bukkit.getOnlinePlayers()) {
    //                updatePlayTime(current.getUniqueId());
    //                updateCoins(current.getUniqueId());
    //            }
    //        }
    //    }.runTaskTimer(Main.getInstance(), 20*30, 20*30);
    //}

}

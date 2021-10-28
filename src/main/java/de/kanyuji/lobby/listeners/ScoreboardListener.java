package de.kanyuji.lobby.listeners;

import fr.mrmicky.fastboard.FastBoard;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ScoreboardListener implements Listener {

    public static final Map<UUID, FastBoard> boards = new HashMap<>();

    @EventHandler
    public void handlePlayerJoinEvent(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        FastBoard board = new FastBoard(player);
        board.updateTitle("§b§lLobby");
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
    //todo update method


}

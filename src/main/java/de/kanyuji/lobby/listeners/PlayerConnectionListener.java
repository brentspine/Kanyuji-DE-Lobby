package de.kanyuji.lobby.listeners;

import de.kanyuji.lobby.Main;
import de.kanyuji.lobby.utils.ItemBuilder;
import de.kanyuji.lobby.utils.LocationUtil;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerConnectionListener implements Listener {

    @EventHandler
    public void handlePlayerJoinEvent(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        player.getInventory().clear();
        try {
            player.teleport(new LocationUtil(Main.getInstance(), "locs.SPAWN").getLocation());
        } catch (Exception e) {
            //player.sendMessage(Main.PREFIX + "§cInterner Fehler (unbekannte Position)");
            Bukkit.getConsoleSender().sendMessage(Main.PREFIX + "§4Fehlender Spawn!");
        }
        player.teleport(new LocationUtil(Main.getInstance(), "locs.SPAWN").getLocation());
        player.setFoodLevel(20);
        player.setMaxHealth(6);
        player.setGameMode(GameMode.SURVIVAL);
        player.getInventory().setItem(0, new ItemBuilder(Material.FIREWORK_ROCKET).setDisplayName("§b§lTeleporter").build());
        player.getInventory().setItem(1, new ItemBuilder(Material.LIME_DYE).setDisplayName("§9Spieler §8» §7Angezeigt").build());
        player.getInventory().setItem(4, new ItemBuilder(Material.FIREWORK_STAR).setDisplayName("§7Gadget").build());
        player.getInventory().setItem(7, new ItemBuilder(Material.CHEST).setDisplayName("§6§lInventar").build());
        player.getInventory().setItem(8, new ItemBuilder(Material.PLAYER_HEAD).setSkullOwner(player.getUniqueId()).setDisplayName("§b§lProfil").build());
        if (player.isOp()){
            player.setAllowFlight(true);
            player.setFlying(true);
        }
        //nick tool wird vom nick plugin ins inventar gelegt || Command block zum starten lege ich später ins inventar!!!
        //todo join animation(lass mich das machen)

        //ScoreboardListener.updateCoins(player.getUniqueId());
        //ScoreboardListener.updatePlayTime(player.getUniqueId());

    }

    @EventHandler
    public void handlePlayerQuitEvent(PlayerQuitEvent event) {
        event.setQuitMessage(null);
        if(HideListener.cooldown.contains(event.getPlayer()))
            HideListener.cooldown.remove(event.getPlayer());
    }
}

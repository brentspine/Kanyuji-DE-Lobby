package de.kanyuji.lobby.listeners;

import de.kanyuji.lobby.Main;
import de.kanyuji.lobby.utils.ItemBuilder;
import de.kanyuji.lobby.utils.LocationUtil;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerConnectionListener implements Listener {

    @EventHandler
    public void handlePlayerJoinEvent(PlayerJoinEvent event) {
        event.setJoinMessage(null);
        Player player = event.getPlayer();
        player.getInventory().clear();
        player.teleport(new LocationUtil(Main.getInstance(), "spawn").getLocation());
        player.getInventory().setItem(0, new ItemBuilder(Material.FIREWORK_ROCKET).setDisplayName("§b§lTeleporter").build());
        player.getInventory().setItem(1, new ItemBuilder(Material.RED_DYE).setDisplayName("§c§lSpieler Versteckt").build());
        player.getInventory().setItem(4, new ItemBuilder(Material.FIREWORK_STAR).setDisplayName("§7Gadget").build());
        player.getInventory().setItem(7, new ItemBuilder(Material.CHEST).setDisplayName("§6§lInventar").build());
        player.getInventory().setItem(8, new ItemBuilder(Material.PLAYER_HEAD).setSkullOwner(player.getUniqueId()).setDisplayName("§6§lInventar").build());

    }
}

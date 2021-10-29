package de.kanyuji.lobby.listeners.cosmetics;

import de.kanyuji.lobby.Main;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.HashMap;
import java.util.UUID;

public class BlockTrails implements Listener {

    HashMap<UUID, Material> equippedTrail = new HashMap<>();

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        if(!equippedTrail.containsKey(player.getUniqueId())) {
            return;
        }
        Block block = player.getLocation().subtract(0, 1,0).getBlock();
        Material material = equippedTrail.get(player.getUniqueId());
        Material oldMaterial = block.getType();
        if(!oldMaterial.isSolid()) {
            return;
        }
        block.setType(material);
        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.getInstance(), () -> block.setType(oldMaterial), 40);
    }

}

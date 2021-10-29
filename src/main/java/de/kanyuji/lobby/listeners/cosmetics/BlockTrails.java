package de.kanyuji.lobby.listeners.cosmetics;

import de.kanyuji.lobby.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.HashMap;
import java.util.Locale;
import java.util.UUID;

public class BlockTrails implements Listener {

    private static HashMap<UUID, Material> equippedTrail = new HashMap<>();
    private static HashMap<Location, Material> oldBlocks = new HashMap<>();

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        if(!equippedTrail.containsKey(player.getUniqueId())) {
            return;
        }
        Block block = player.getLocation().subtract(0, 1,0).getBlock();
        if(oldBlocks.containsKey(block.getLocation())) {
            return;
        }
        Material oldMaterial = block.getType();
        Material materialAbove = player.getLocation().getBlock().getType();
        if(!oldMaterial.isSolid() || (!materialAbove.isSolid() && materialAbove != Material.AIR && materialAbove != Material.CAVE_AIR)) {
            return;
        }
        Material material = equippedTrail.get(player.getUniqueId());
        block.setType(material);
        oldBlocks.put(block.getLocation(), oldMaterial);

        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.getInstance(), () -> {
            block.getLocation().getBlock().setType(oldBlocks.get(block.getLocation()));
            oldBlocks.remove(block.getLocation());
        }, 15);


    }


    public static void setEquippedTrail(UUID uuid, Material material) {
        equippedTrail.remove(uuid);
        equippedTrail.put(uuid, material);
    }

}

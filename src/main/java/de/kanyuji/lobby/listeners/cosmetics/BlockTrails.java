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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.UUID;

public class BlockTrails implements Listener {

    private static HashMap<UUID, Material> equippedTrail = new HashMap<>();
    private static HashMap<Location, Material> oldBlocks = new HashMap<>();

    private static ArrayList<Material> blockedBlocks = new ArrayList<>();

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
        if(blockedBlocks.contains(oldMaterial) || !materialAbove.isAir() || oldMaterial.isAir()) { //|| blockedBlocks.contains(oldMaterial)
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

    public static void run() {
        blockedBlocks.add(Material.AIR);
        blockedBlocks.add(Material.WATER);
        blockedBlocks.add(Material.LAVA);
        blockedBlocks.add(Material.RED_BANNER);
        blockedBlocks.add(Material.CAVE_AIR);
        blockedBlocks.add(Material.VOID_AIR);
        blockedBlocks.add(Material.GRASS);
        blockedBlocks.add(Material.TALL_GRASS);
        blockedBlocks.add(Material.CHEST);
        blockedBlocks.add(Material.TRAPPED_CHEST);
        blockedBlocks.add(Material.HONEY_BLOCK);


        blockedBlocks.add(Material.ACACIA_STAIRS);
        blockedBlocks.add(Material.ANDESITE_STAIRS);
        blockedBlocks.add(Material.BIRCH_STAIRS);
        blockedBlocks.add(Material.BLACKSTONE_STAIRS);
        blockedBlocks.add(Material.BRICK_STAIRS);
        blockedBlocks.add(Material.BIRCH_STAIRS);
        blockedBlocks.add(Material.COBBLESTONE_STAIRS);
        blockedBlocks.add(Material.CRIMSON_STAIRS);
        blockedBlocks.add(Material.ACACIA_STAIRS);
        blockedBlocks.add(Material.END_STONE_BRICK_STAIRS);
        blockedBlocks.add(Material.GRANITE_STAIRS);
        blockedBlocks.add(Material.JUNGLE_STAIRS);
        blockedBlocks.add(Material.MOSSY_COBBLESTONE_STAIRS);
        blockedBlocks.add(Material.OAK_STAIRS);
        blockedBlocks.add(Material.QUARTZ_STAIRS);
        blockedBlocks.add(Material.SANDSTONE_STAIRS);
        blockedBlocks.add(Material.PRISMARINE_STAIRS);
        blockedBlocks.add(Material.STONE_BRICK_STAIRS);

        blockedBlocks.add(Material.ACACIA_SIGN);
        blockedBlocks.add(Material.ACACIA_WALL_SIGN);
        blockedBlocks.add(Material.BIRCH_SIGN);
        blockedBlocks.add(Material.BIRCH_WALL_SIGN);
        blockedBlocks.add(Material.CRIMSON_SIGN);
        blockedBlocks.add(Material.CRIMSON_WALL_SIGN);
        blockedBlocks.add(Material.DARK_OAK_SIGN);
        blockedBlocks.add(Material.DARK_OAK_WALL_SIGN);
        blockedBlocks.add(Material.JUNGLE_SIGN);
        blockedBlocks.add(Material.JUNGLE_WALL_SIGN);
        blockedBlocks.add(Material.OAK_SIGN);
        blockedBlocks.add(Material.OAK_WALL_SIGN);
        blockedBlocks.add(Material.SPRUCE_SIGN);
        blockedBlocks.add(Material.SPRUCE_WALL_SIGN);
        blockedBlocks.add(Material.WARPED_SIGN);
        blockedBlocks.add(Material.WARPED_WALL_SIGN);

        blockedBlocks.add(Material.POPPY);
        blockedBlocks.add(Material.POTTED_POPPY);
        blockedBlocks.add(Material.POTTED_WITHER_ROSE);
        blockedBlocks.add(Material.WITHER_ROSE);
        blockedBlocks.add(Material.POTTED_ACACIA_SAPLING);
        blockedBlocks.add(Material.ACACIA_SAPLING);
        blockedBlocks.add(Material.POTTED_ALLIUM);
        blockedBlocks.add(Material.ALLIUM);


    }

}

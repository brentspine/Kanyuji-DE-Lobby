package de.kanyuji.lobby.rewardchests.inventories;

import de.kanyuji.lobby.Main;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class CosmeticChestListener implements Listener {

    private Main plugin;

    public CosmeticChestListener(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onCosmeticChest(PlayerInteractEvent event) {
        if(event.getClickedBlock() == null) return;
        if(event.getClickedBlock().getType() != Material.TRAPPED_CHEST && event.getClickedBlock().getType() != Material.CHEST && event.getClickedBlock().getType() != Material.ENDER_CHEST) return;
        Block block = event.getClickedBlock();
        Player player = event.getPlayer();
        if(block.equals(plugin.getRewardLocationManager().getCosmeticChest())) {
            player.sendMessage(Main.PREFIX + "Cosmetic chest");
        }
    }

}

package de.kanyuji.lobby.listeners;

import de.kanyuji.lobby.Main;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;

public class BlockedListeners implements Listener {

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        if(!event.getPlayer().isOp()) {
            event.setCancelled(true);
            event.getPlayer().sendMessage(Main.PREFIX + "§cDu darfst hier keine Blöcke platzieren!");
        }
    }

    public void onBlockBreak(BlockBreakEvent event) {
        if(!event.getPlayer().isOp()) {
            event.setCancelled(true);
            event.getPlayer().sendMessage(Main.PREFIX + "§cDu darfst hier keine Blöcke abbauen!");
        }
    }

    @EventHandler
    public void onPlayerDamage(EntityDamageEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onFoodLevelChange(FoodLevelChangeEvent event) {
        event.setCancelled(true);
    }



}

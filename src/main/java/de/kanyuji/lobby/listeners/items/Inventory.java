package de.kanyuji.lobby.listeners.items;

import de.kanyuji.lobby.Main;
import de.kanyuji.lobby.utils.ItemBuilder;
import de.kanyuji.lobby.utils.LocationUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class Inventory implements Listener {

    private static org.bukkit.inventory.Inventory inventory = Bukkit.createInventory(null, 9*6, "§b§lInventar");

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if(event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if(event.getItem() == null) return;
            if(event.getItem().hasItemMeta() && event.getItem().getItemMeta() != null) {
                if(event.getItem().getType() == Material.CHEST) {
                    event.getPlayer().openInventory(inventory);
                }

            }
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if(event.getWhoClicked() instanceof Player) {
            Player player = (Player) event.getWhoClicked();
            if(event.getClickedInventory() == null) return;
            if(event.getCurrentItem() == null) return;
            if(!(event.getCurrentItem().hasItemMeta())) return;
            if(!event.getView().getTitle().equalsIgnoreCase("§b§lInventar")) return;
            event.setCancelled(true);
            switch (event.getCurrentItem().getType()) {
                case LIME_STAINED_GLASS:

                    break;
                case BEDROCK:
                    player.sendMessage(Main.PREFIX + "Placeholder");
                    break;
            }
        }
    }

    public static void setInventory() {
        inventory.setItem(3, new ItemBuilder(Material.BEDROCK).setDisplayName("§7Coming Soon").build());
        inventory.setItem(5, new ItemBuilder(Material.BEDROCK).setDisplayName("§7Coming Soon").build());
        inventory.setItem(10, new ItemBuilder(Material.BEDROCK).setDisplayName("§7Coming Soon").build());
        inventory.setItem(16, new ItemBuilder(Material.BEDROCK).setDisplayName("§7Coming Soon").build());
        inventory.setItem(22, new ItemBuilder(Material.FIREWORK_ROCKET).setDisplayName("§e§lEvent").build());
        inventory.setItem(31, new ItemBuilder(Material.FIRE_CHARGE).setDisplayName("§7Spawn").build());
        inventory.setItem(37, new ItemBuilder(Material.BEDROCK).setDisplayName("§9Coming Soon").build());
        inventory.setItem(43, new ItemBuilder(Material.BEDROCK).setDisplayName("§9Coming Soon").build());
        inventory.setItem(48, new ItemBuilder(Material.BEDROCK).setDisplayName("§9Coming Soon").build());
        inventory.setItem(50, new ItemBuilder(Material.BEDROCK).setDisplayName("§9Coming Soon").build());
    }

}
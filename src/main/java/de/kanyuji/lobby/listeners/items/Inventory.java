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
import org.bukkit.inventory.ItemStack;

public class Inventory implements Listener {

    private static org.bukkit.inventory.Inventory inventory = Bukkit.createInventory(null, 3*6, "§b§lInventar");

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if(event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if(event.getItem() == null) return;
            if(event.getItem().hasItemMeta() && event.getItem().getItemMeta() != null) {
                if(event.getItem().getType() == Material.CHEST) {
                    event.getPlayer().openInventory(inventory);
                    event.setCancelled(true);
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
            if(event.getView().getTitle().equalsIgnoreCase("§b§lInventar")) {
                event.setCancelled(true);
                handleMainInventoryClick(event.getCurrentItem().getType(), player);
            }
        }
    }

    public void handleMainInventoryClick(Material material, Player player) {
        switch (material) {
            case LIME_STAINED_GLASS:

                break;
            case BEDROCK:
                player.sendMessage(Main.PREFIX + "Placeholder");
                break;
        }
    }

    public static void setInventory() {
        ItemStack grayPane = new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE).setDisplayName("§c").build();
        //for (int i = 0; i < 10; i++) {
            //inventory.setItem(i, grayPane);
        //}
        inventory.setItem(10, new ItemBuilder(Material.LIME_STAINED_GLASS).setDisplayName("§6Block Trails").setLore("§7Wenn aktiviert erscheint", "§7beim bewegen hinter dir", "§7die ausgewählte Blockart").build());
        //inventory.setItem(11, grayPane);
        //inventory.setItem(12, grayPane);

        //inventory.setItem(14, grayPane);
        //inventory.setItem(15, grayPane);

        //inventory.setItem(17, grayPane);
        //inventory.setItem(18, grayPane);
        //for (int i = 19; i < 27; i++) {
            //inventory.setItem(i, grayPane);
        //}
    }

    public static void setBlockTrailInventory() {
        ItemStack grayPane = new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE).setDisplayName("§c").build();

    }

}

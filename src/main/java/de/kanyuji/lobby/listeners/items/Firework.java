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
import org.bukkit.inventory.Inventory;

public class Firework implements Listener {

    private static Inventory inventory = Bukkit.createInventory(null, 9*6, "§b§lTeleporter");

    @EventHandler
    public void handlePlayerFireworkClickEvent(PlayerInteractEvent event) {
        if(event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if(event.getItem() == null) return;
            if(event.getItem().hasItemMeta() && event.getItem().getItemMeta() != null) {
                if(event.getItem().getType() == Material.FIREWORK_ROCKET) {
                    event.getPlayer().openInventory(inventory);
                    event.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void handlePlayerInventoryFireworkClickEvent(InventoryClickEvent event) {
        if(event.getWhoClicked() instanceof Player) {
            Player player = (Player) event.getWhoClicked();
            if(event.getClickedInventory() == null) return;
            if(event.getCurrentItem() == null) return;
            if(!(event.getCurrentItem().hasItemMeta())) return;
            if(!event.getView().getTitle().equalsIgnoreCase("§b§lTeleporter")) return;
            event.setCancelled(true);
            switch (event.getCurrentItem().getType()) {
                case FIRE_CHARGE:
                    player.teleport(new LocationUtil(Main.getInstance(), "locs.SPAWN").getLocation());
                    player.playSound(event.getWhoClicked().getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1, 1);
                    break;
                case FIREWORK_ROCKET:
                    player.teleport(new LocationUtil(Main.getInstance(), "locs.EVENT").getLocation());
                    player.playSound(event.getWhoClicked().getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1, 1);
                    break;
                case STICK:
                    player.teleport(new LocationUtil(Main.getInstance(), "locs.TTT").getLocation());
                    player.playSound(event.getWhoClicked().getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1, 1);
                    break;
                case WATER_BUCKET:
                    player.teleport(new LocationUtil(Main.getInstance(), "locs.SURF").getLocation());
                    player.playSound(event.getWhoClicked().getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1, 1);
                    break;
                case CRAFTING_TABLE:
                    player.teleport(new LocationUtil(Main.getInstance(), "locs.BINGO").getLocation());
                    player.playSound(event.getWhoClicked().getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1, 1);
                    break;
                case CLAY_BALL:
                    player.teleport(new LocationUtil(Main.getInstance(), "locs.SWITCH").getLocation());
                    player.playSound(event.getWhoClicked().getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1, 1);
                    break;
                case ELYTRA:
                    player.teleport(new LocationUtil(Main.getInstance(), "locs.ELYTRAWARS").getLocation());
                    player.playSound(event.getWhoClicked().getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1, 1);
                    break;
                case DIAMOND_BOOTS:
                    player.teleport(new LocationUtil(Main.getInstance(), "locs.TRYJUMP").getLocation());
                    player.playSound(event.getWhoClicked().getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1, 1);
                    break;
                case BARRIER:
                    player.sendMessage(Main.PREFIX + "Dieser Platz ist noch nicht belegt.");
                    break;
            }
        }
    }

    public static void setInventory() {
        inventory.setItem(3, new ItemBuilder(Material.STICK).setDisplayName("§4§lTTT").setLore("§4TTT §7ist ein Rollenspiel, wo es Mörder, Dedektive und Unschuldige gibt.", "§7Die Mörder müssen getötet werden.").build());
        inventory.setItem(5, new ItemBuilder(Material.WATER_BUCKET).setDisplayName("§3§lSURF").setLore("§3Surf §7ist ein FFA Modus.", "§7Wasser boostet dich jedoch hoch!").build());
        inventory.setItem(10, new ItemBuilder(Material.CRAFTING_TABLE).setDisplayName("§9§lBINGO").setLore("§9Bingo §7ist ein Modus, in dem du eine Liste an Items finden musst, um zu gewinnen").build());
        inventory.setItem(16, new ItemBuilder(Material.LAVA_BUCKET).setDisplayName("§2§lCHALLENGES").setLore("§2Challanges §7ist ein Modus, wo du alleine oder mit deinen Freunden herausforderungen abschliesen kannst!").build());
        inventory.setItem(22, new ItemBuilder(Material.FIREWORK_ROCKET).setDisplayName("§e§lEvent").setLore("§eEvent §7Ist der Eventplatz. Dort finden manchmal Events stadt").build());
        inventory.setItem(31, new ItemBuilder(Material.FIRE_CHARGE).setDisplayName("§7Spawn").build());
        inventory.setItem(37, new ItemBuilder(Material.CLAY_BALL).setDisplayName("§c§lSWITCH").setLore("§cSwitch §7ist ein Modus, in dem mehrere FFA Modis immer switchen").build());
        inventory.setItem(43, new ItemBuilder(Material.DIAMOND_BOOTS).setDisplayName("§b§lTRYJUMP").setLore("bTryJump §7ist ein Modus, in dem du Punkte durch JnR verdienen musst und am ende etwas damit kaufst.", "§7Mit den gekauften items wird PVP gespielt").build());
        inventory.setItem(48, new ItemBuilder(Material.ELYTRA).setDisplayName("§a§lELYTRAWARS").setLore("§aElytrawars §7ist ein Modus, in dem du während dem Elytraflug kämpfst").build());
        inventory.setItem(50, new ItemBuilder(Material.BARRIER).setDisplayName("§9Coming Soon").build());
    }
}

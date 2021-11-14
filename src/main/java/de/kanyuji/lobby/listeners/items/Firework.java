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
                case LAVA_BUCKET:
                    player.teleport(new LocationUtil(Main.getInstance(), "locs.CHALLENGES").getLocation());
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
        inventory.setItem(16, new ItemBuilder(Material.LAVA_BUCKET).setDisplayName("§2§lCHALLENGES").setLore("Hier kannst du verschiedene §2Challenges §7zusammen mit Freunden oder alleine spielen,", "Ziel ist es den Enderdrachen zu töten").build());
        inventory.setItem(22, new ItemBuilder(Material.FIREWORK_ROCKET).setDisplayName("§e§lEvent").setLore("§eEvent§7, hier finden vom Server veranstaltete Events statt").build());
        inventory.setItem(31, new ItemBuilder(Material.FIRE_CHARGE).setDisplayName("§7Spawn").build());
        inventory.setItem(37, new ItemBuilder(Material.CLAY_BALL).setDisplayName("§c§lSWITCH").setLore("§cSwitch §7ist eine Mischung aus verschiedenen FFA Modi, die immer in einem bestimmten Zeitabstand wechseln").build());
        inventory.setItem(43, new ItemBuilder(Material.DIAMOND_BOOTS).setDisplayName("§b§lTRYJUMP").setLore("§bTryJump §7ist ein Modus, in dem du Punkte durch Jump and Runs verdienst", "§7und dir damit am Ende Ausrüstung käufst die du in einem PvP Deathmatch einsetzt.").build());
        inventory.setItem(48, new ItemBuilder(Material.ELYTRA).setDisplayName("§a§lELYTRAWARS").setLore("§aElytrawars §7ist ein epischer Battle Royale Spielmodus, der nur in der Luft stattfindet.", "§7Achte darauf dass du genügend Raketen hast und sei am Ende der einzige Überlebende").build());
        inventory.setItem(50, new ItemBuilder(Material.BARRIER).setDisplayName("§9Coming Soon").build());
    }
}

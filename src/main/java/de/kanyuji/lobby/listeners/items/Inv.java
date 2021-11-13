package de.kanyuji.lobby.listeners.items;

import de.brentspine.coinsystem.mysql.MySQLCoins;
import de.kanyuji.lobby.Main;
import de.kanyuji.lobby.listeners.cosmetics.BlockTrails;
import de.kanyuji.lobby.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;

public class Inv implements Listener {

    private static org.bukkit.inventory.Inventory inventory = Bukkit.createInventory(null, 3*9, "§b§lInventar");
    private static org.bukkit.inventory.Inventory blockTrailInventory = Bukkit.createInventory(null, 6*9, "§b§lBlock Trails");

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
                handleMainInventoryClick(event.getCurrentItem().getType(), player);
                event.setCancelled(true);
                //todo event.getCurrentItem().setItemMeta(new ItemBuilder(event.getCurrentItem()).setLore("§aZum auswählen klicken").build().getItemMeta());
            }
            else if(event.getView().getTitle().equalsIgnoreCase("§b§lBlock Trails")) {
                handleBlockTrailInventoryClick(event.getCurrentItem().getType(), player);
                event.setCancelled(true);
            }
        }
    }

    public void handleMainInventoryClick(Material material, Player player) {
        switch (material) {
            case LIME_STAINED_GLASS:
                //blockTrailInventory.setItem(49, new ItemBuilder(Material.EMERALD).setDisplayName("§6" + MySQLCoins.getPoints(player.getUniqueId()) + " Pixel").build());
                player.openInventory(blockTrailInventory);
                break;
            case NETHER_STAR:

                break;
            case GOLDEN_BOOTS:
                Integer i = 0;
                break;
            case BEDROCK:
                player.sendMessage(Main.PREFIX + "Placeholder");
                break;
        }
    }

    public void handleBlockTrailInventoryClick(Material material, Player player) {
        if(material == Material.ARROW) {
            player.openInventory(inventory);
            return;
        }
        if(player.hasPermission("system.blocktrails." + material.name())) {
            player.sendMessage(Main.PREFIX + "Du hast den §c" + material.name() + "§7 Trail ausgewählt");
            BlockTrails.setEquippedTrail(player.getUniqueId(), material);
        } else
            player.sendMessage(Main.PREFIX + "Du §cbesitzt §7diesen Blocktrail §cnicht");
    }

    public static void setInventory() {
        inventory.setItem(10, new ItemBuilder(Material.LIME_STAINED_GLASS).setDisplayName("§6Block Trails").setLore("§7Wenn aktiviert erscheint beim bewegen hinter dir", "§7die ausgewählte Blockart").build());
        inventory.setItem(13, new ItemBuilder(Material.NETHER_STAR).setDisplayName("§9Partikel Effekte").setLore("§7Erhalte einen schönen Partikeleffekt um dich herum").build());
        inventory.setItem(16, new ItemBuilder(Material.GOLDEN_BOOTS).setDisplayName("§eSpielzeuge").setDisplayName("§7Habe ein bisschen Spaß zwischendurch mit Minispielen").build());
    }

    public static void setBlockTrailInventory(Player player) {
        Inventory inv = Bukkit.createInventory(null, 9*6, "Test");

        blockTrailInventory.setItem(10, new ItemBuilder(Material.BARRIER).setDisplayName("§cNone").build());
        blockTrailInventory.setItem(11, new ItemBuilder(Material.QUARTZ_BLOCK).setDisplayName("§7Quartz Trail").setLore("§8Block Trail", "", "§aKann in Kisten gefunden werden", "", "§aRarity: §7Common", "").build());
        blockTrailInventory.setItem(12, new ItemBuilder(Material.COAL_BLOCK).setDisplayName("§7Coal Trail").setLore("§8Block Trail", "", "§aKann in Kisten gefunden werden", "", "§aRarity: §7Common", "").build());
        blockTrailInventory.setItem(13, new ItemBuilder(Material.BRICKS).setDisplayName("§7Brick Trail").setLore("§8Block Trail", "", "§aKann in Kisten gefunden werden", "", "§aRarity: §7Common", "").build());
        blockTrailInventory.setItem(14, new ItemBuilder(Material.SNOW_BLOCK).setDisplayName("§7Snow Trail").setLore("§8Block Trail", "", "§aKann in Kisten gefunden werden", "", "§aRarity: §7Common", "").build());
        blockTrailInventory.setItem(15, new ItemBuilder(Material.NETHERRACK).setDisplayName("§7Netherrack Trail").setLore("§8Block Trail", "", "§aKann in Kisten gefunden werden", "", "§aRarity: §7Common", "").build());
        blockTrailInventory.setItem(16, new ItemBuilder(Material.REDSTONE_ORE).setDisplayName("§9Redstone Ore Trail").setLore("§8Block Trail", "", "§aKann in Kisten gefunden werden", "", "§aRarity: §9Rare", "").build());

        inv.setItem(19, new ItemBuilder(Material.RED_STAINED_GLASS).setDisplayName("§9Red Trail").setLore("§8Block Trail", "", "§aKann in Kisten gefunden werden", "", "§aRarity: §9Rare", "").build());
        inv.setItem(20, new ItemBuilder(Material.BLUE_STAINED_GLASS).setDisplayName("§9Blue Trail").setLore("§8Block Trail", "", "§aKann in Kisten gefunden werden", "", "§aRarity: §9Rare", "").build());
        inv.setItem(21, new ItemBuilder(Material.LIME_STAINED_GLASS).setDisplayName("§9Lime Trail").setLore("§8Block Trail", "", "§aKann in Kisten gefunden werden", "", "§aRarity: §9Rare", "").build());
        inv.setItem(22, new ItemBuilder(Material.NETHER_BRICKS).setDisplayName("§5Netherbricks Trail").setLore("§8Block Trail", "", "§aKann in Kisten gefunden werden", "", "§aRarity: §5Epic", "").build());
        inv.setItem(23, new ItemBuilder(Material.REDSTONE_BLOCK).setDisplayName("§5Redstone Trail").setLore("§8Block Trail", "", "§aKann in Kisten gefunden werden", "", "§aRarity: §5Epic", "").build());
        inv.setItem(24, new ItemBuilder(Material.WHITE_STAINED_GLASS).setDisplayName("§5White Trail").setLore("§8Block Trail", "", "§aKann in Kisten gefunden werden", "", "§aRarity: §5Epic", "").build());
        inv.setItem(25, new ItemBuilder(Material.BLACK_STAINED_GLASS).setDisplayName("§5Black Trail").setLore("§8Block Trail", "", "§aKann in Kisten gefunden werden", "", "§aRarity: §5Epic", "").build());

        inv.setItem(28, new ItemBuilder(Material.DARK_PRISMARINE).setDisplayName("§5Dark Prismarine Trail").setLore("§8Block Trail", "", "§aKann in Kisten gefunden werden", "", "§aRarity: §5Epic", "").build());
        inv.setItem(29, new ItemBuilder(Material.PRISMARINE_BRICKS).setDisplayName("§9Prismarine Trail").setLore("§8Block Trail", "", "§aKann in Kisten gefunden werden", "", "§aRarity: §9Legendary", "").build());
        inv.setItem(30, new ItemBuilder(Material.LAPIS_BLOCK).setDisplayName("§9Lapis Trail").setLore("§8Block Trail", "", "§aKann in Kisten gefunden werden", "", "§aRarity: §9Legendary", "").build());
        inv.setItem(31, new ItemBuilder(Material.OBSIDIAN).setDisplayName("§9Obisdian Trail").setLore("§8Block Trail", "", "§aKann in Kisten gefunden werden", "", "§aRarity: §9Legendary", "").build());
        inv.setItem(32, new ItemBuilder(Material.SEA_LANTERN).setDisplayName("§9Sea Lantern Trail").setLore("§8Block Trail", "", "§aGesperrte Belohnung", "", "§aRarity: §9Legendary", "").build());
        inv.setItem(33, new ItemBuilder(Material.DIAMOND_ORE).setDisplayName("§4White Trail").setLore("§8Block Trail", "", "§aKann von Admins vergeben werden", "", "§aRarity: §4Special", "").build());
        inv.setItem(34, new ItemBuilder(Material.BEDROCK).setDisplayName("§4Bedrock Trail").setLore("§8Block Trail", "", "§aFür Admins und höhere Teammitglieder", "", "§aRarity: §4Special", "").build());

        inv.setItem(48, new ItemBuilder(Material.ARROW).setDisplayName("§7Zurück").build());
        inv.setItem(49, new ItemBuilder(Material.EMERALD).setDisplayName("§6" + MySQLCoins.getPoints(player.getUniqueId()) + " Coins").build());
        player.openInventory(inv);
    }


}

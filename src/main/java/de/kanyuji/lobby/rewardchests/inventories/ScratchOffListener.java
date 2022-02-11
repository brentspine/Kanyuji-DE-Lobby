package de.kanyuji.lobby.rewardchests.inventories;

import de.brentspine.kanyujiapi.mysql.data.MySQLCoins;
import de.brentspine.kanyujiapi.mysql.data.MySQLLootChests;
import de.kanyuji.lobby.Main;
import de.kanyuji.lobby.rewardchests.games.ScratchOffGame;
import de.kanyuji.lobby.rewardchests.inventories.CoinChestListener;
import de.kanyuji.lobby.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;

import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Locale;

public class ScratchOffListener implements Listener {

    private final Main plugin;
    private CoinChestListener coinChestListener;
    private final HashMap<String, ScratchOffGame> scratchOffGames;
    private NumberFormat numberFormatter;

    public ScratchOffListener(Main plugin, CoinChestListener coinChestListener) {
        this.plugin = plugin;
        this.coinChestListener = coinChestListener;
        scratchOffGames = new HashMap<>();
        numberFormatter = NumberFormat.getInstance(new Locale("de"));
    }

    /*@EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        if(scratchOffGames.containsKey(event.getPlayer().getName())) {
            ScratchOffGame scratchOffGame = scratchOffGames.get(event.getPlayer().getName());
            while (scratchOffGame.isMoveAvailable()) {
                scratchOffGame.openNext();
            }
            scratchOffGames.remove(event.getPlayer().getName());
        }
    }*/

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if(event.getClickedInventory() == null) return;
        if(event.getClickedInventory().getType() != InventoryType.CHEST) return;
        if(event.getCurrentItem() == null) return;
        if(!(event.getWhoClicked() instanceof Player)) return;
        Player player = (Player) event.getWhoClicked();

        if(player.getOpenInventory().getTitle().equalsIgnoreCase("Coins - Scratch off")) {
            event.setCancelled(true);

            switch (event.getCurrentItem().getType()) {
                case PAPER:
                    if(MySQLLootChests.getScratchOffs(player.getUniqueId()) <= 0 && MySQLLootChests.getUniversal(player.getUniqueId()) <= 0) {
                        player.sendMessage(Main.PREFIX + "§cYou do not have any keys!");
                        return;
                    }
                    if(MySQLLootChests.getScratchOffs(player.getUniqueId()) > 0)
                        MySQLLootChests.addScratchOffs(player.getUniqueId(), -1);
                    else
                        MySQLLootChests.addUniversal(player.getUniqueId(), -1);
                    scratchOffGames.put(player.getName(), new ScratchOffGame(plugin, player));
                    openScratchOffGame(player);
                    break;
                case GOLD_INGOT:
                    if(MySQLCoins.getPoints(player.getUniqueId()) < 1000) {
                        player.sendMessage(Main.PREFIX + "§cYou need " + (1000 - MySQLCoins.getPoints(player.getUniqueId())) + " §cmore coins");
                        return;
                    }
                    MySQLLootChests.addScratchOffs(player.getUniqueId(), 1);
                    MySQLCoins.remove(player.getUniqueId(), 1000);
                    player.sendMessage(Main.PREFIX + "You bought §c1 scratch-off §7for §61000 Coins");
                    openScratchOffMain(player);
                    break;
                case ARROW:
                    coinChestListener.openGameSelector(player);
                    break;
            }
            return;
        }

        if(player.getOpenInventory().getTitle().equalsIgnoreCase("Scratch Off")) {
            event.setCancelled(true);
            if(!scratchOffGames.containsKey(player.getName())) {
                player.closeInventory();
                return;
            }
            ScratchOffGame scratchOffGame = scratchOffGames.get(player.getName());
            if(event.getCurrentItem().getType() != Material.ENDER_CHEST) return;
            if(!scratchOffGame.isMoveAvailable()) return;
            int coinsGot = scratchOffGame.openNext();
            player.getOpenInventory().setItem(event.getRawSlot(), new ItemBuilder(Material.GOLD_INGOT).setDisplayName("§6§l" + scratchOffGame.getCoinsColor(coinsGot) + numberFormatter.format(coinsGot) + " Coins").build());
            //All Chests opened, starting end phase
            if(!scratchOffGame.isMoveAvailable()) {
                scratchOffGames.remove(player.getName());
                openScratchOffEnd(player, player.getOpenInventory().getTopInventory(), scratchOffGame);
                MySQLCoins.add(player.getUniqueId(), scratchOffGame.getCoinsWon());
                player.sendMessage(Main.PREFIX + "You got §6" + numberFormatter.format(scratchOffGame.getCoinsWon()) + " Coins §7from the scratch-off");
            }
            return;
        }

        if(player.getOpenInventory().getTitle().equalsIgnoreCase("Scratch Off - Ending")) {
            if(event.getCurrentItem().getType() == Material.ENDER_CHEST) {
                if(MySQLLootChests.getScratchOffs(player.getUniqueId()) <= 0 && MySQLLootChests.getUniversal(player.getUniqueId()) <= 0) {
                    player.sendMessage(Main.PREFIX + "§cYou do not have any keys!");
                    openScratchOffMain(player);
                    return;
                }
                if(MySQLLootChests.getScratchOffs(player.getUniqueId()) > 0)
                    MySQLLootChests.addScratchOffs(player.getUniqueId(), -1);
                else
                    MySQLLootChests.addUniversal(player.getUniqueId(), -1);
                scratchOffGames.put(player.getName(), new ScratchOffGame(plugin, player));
                openScratchOffGame(player);
            }
            if(event.getCurrentItem().getType() == Material.GOLD_INGOT) {
                if(MySQLCoins.getPoints(player.getUniqueId()) < 1000) {
                    player.sendMessage(Main.PREFIX + "§cYou need " + (1000 - MySQLCoins.getPoints(player.getUniqueId()) + " more coins"));
                    return;
                }
                MySQLCoins.add(player.getUniqueId(), -1000);
                MySQLLootChests.addScratchOffs(player.getUniqueId(), 1);
                player.sendMessage(Main.PREFIX + "You bought §c1 scratch-off §7for §61000 Coins");
                player.getOpenInventory().setItem(45, new ItemBuilder(Material.ENDER_CHEST).setDisplayName("§9§lOpen next").setLore("§7You have §c" + MySQLLootChests.getScratchOffs(player.getUniqueId()) + " keys §7left", "§7and §c" + MySQLLootChests.getUniversal(player.getUniqueId()) + " §7universal keys").build());
            }
        }

    }

    public void openScratchOffMain(Player player) {
        Inventory inventory = Bukkit.createInventory(null, 3*9, "Coins - Scratch off");
        inventory.setItem(11, new ItemBuilder(Material.BOOK).setDisplayName("§aScratch Off Information").setLore("§7Open §6Scratch-Offs §7to gamble for coins", "§71 Chest costs §61000 Coins", "", "§eYour 5 recent chests:", "§61007 Coins", "§6321 Coins").build());
        inventory.setItem(13, new ItemBuilder(Material.PAPER).setDisplayName("§9Open a chest").setLore("§7You have §c" + MySQLLootChests.getScratchOffs(player.getUniqueId()) + " §7remaining chests", "§7and §c" + MySQLLootChests.getUniversal(player.getUniqueId()) + " §7universal keys").build());
        inventory.setItem(15, new ItemBuilder(Material.GOLD_INGOT).setDisplayName("§eBuy a chest").setLore("§7Costs: §61000 Coins", "", "§aClick to buy").build());
        inventory.setItem(18, new ItemBuilder(Material.ARROW).setDisplayName("§7Go back").build());
        player.openInventory(inventory);
    }

    private void openScratchOffGame(Player player) {
        Inventory inventory = Bukkit.createInventory(null, 6*9, "Scratch Off");
        for (int i = 0; i < inventory.getSize(); i++) {
            inventory.setItem(i, new ItemBuilder(Material.ENDER_CHEST).setDisplayName("§7Click to open").build());
        }
        player.openInventory(inventory);
    }

    private void openScratchOffEnd(Player player, Inventory inventory, ScratchOffGame scratchOffGame) {
        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            for (int i = 0; i < inventory.getSize(); i++) {
                inventory.setItem(i, null);
            }
            Inventory nextInv = Bukkit.createInventory(null, 6*9, "Scratch Off - Ending");
            for (int i = 0; i < scratchOffGame.getMoves().size(); i++) {
                nextInv.setItem(i + 11, new ItemBuilder(Material.GOLD_INGOT).setDisplayName("§6§l" + scratchOffGame.getCoinsColor(scratchOffGame.getMoves().get(i + 1)) + numberFormatter.format(scratchOffGame.getMoves().get(i + 1)) + " Coins").build());
            }
            for (int i = 0; i < scratchOffGame.getMoves().size(); i++) {
                nextInv.setItem(i + 20, scratchOffGame.getGlassPanes().get(i + 1));
            }
            nextInv.setItem(36, new ItemBuilder(Material.GOLD_INGOT).setDisplayName("§eBuy new chest").setLore("§7Costs: §61000 Coins", "", "§aClick to buy").build());
            nextInv.setItem(40, new ItemBuilder(Material.BOOK).setDisplayName("§6§l" + numberFormatter.format(scratchOffGame.getCoinsWon()) + " Coins").build());
            nextInv.setItem(45, new ItemBuilder(Material.ENDER_CHEST).setDisplayName("§9§lOpen next").setLore("§7You have §c" + MySQLLootChests.getScratchOffs(player.getUniqueId()) + " keys §7left", "§7and §c" + MySQLLootChests.getUniversal(player.getUniqueId()) + " §7universal keys").build());
            player.openInventory(nextInv);
        }, 20);
    }

}

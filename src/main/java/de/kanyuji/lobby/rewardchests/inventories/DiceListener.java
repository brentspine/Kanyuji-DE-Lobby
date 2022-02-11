package de.kanyuji.lobby.rewardchests.inventories;

import de.brentspine.kanyujiapi.mysql.data.MySQLCoins;
import de.brentspine.kanyujiapi.mysql.data.MySQLLootChests;
import de.kanyuji.lobby.Main;
import de.kanyuji.lobby.listeners.items.Inv;
import de.kanyuji.lobby.rewardchests.games.DicesGame;
import de.kanyuji.lobby.utils.Dice;
import de.kanyuji.lobby.utils.ItemBuilder;
import de.kanyuji.lobby.utils.MySQL;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class DiceListener implements Listener {

    private Main plugin;
    private CoinChestListener coinChestListener;
    private HashMap<String, DicesGame> games;

    public DiceListener(Main plugin, CoinChestListener coinChestListener) {
        this.plugin = plugin;
        this.coinChestListener = coinChestListener;
        this.games = new HashMap<>();
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if(event.getClickedInventory() == null) return;
        if(event.getClickedInventory().getType() != InventoryType.CHEST) return;
        if(event.getCurrentItem() == null) return;
        if(!(event.getWhoClicked() instanceof Player)) return;
        Player player = (Player) event.getWhoClicked();

        if(player.getOpenInventory().getTitle().equalsIgnoreCase("Coins - Dices")) {
            event.setCancelled(true);
            switch (event.getCurrentItem().getType()) {

                case PAPER:
                    if(MySQLLootChests.getDices(player.getUniqueId()) <= 0 && MySQLLootChests.getUniversal(player.getUniqueId()) <= 0) {
                        player.sendMessage(Main.PREFIX + "§cYou do not have any keys");
                        return;
                    }
                    if(MySQLLootChests.getDices(player.getUniqueId()) > 0)
                        MySQLLootChests.addDices(player.getUniqueId(), -1);
                    else
                        MySQLLootChests.addUniversal(player.getUniqueId(), -1);
                    games.put(player.getName(), new DicesGame(plugin, player, player.getOpenInventory().getTopInventory()));
                    openDicesGame(player);
                    break;
                case GOLD_INGOT:
                    if(MySQLCoins.getPoints(player.getUniqueId()) < 2500) {
                        player.sendMessage(Main.PREFIX + "§cYou need " + (2500 - MySQLCoins.getPoints(player.getUniqueId())) + " more coins");
                        return;
                    }
                    MySQLLootChests.addDices(player.getUniqueId(), 1);
                    MySQLCoins.add(player.getUniqueId(), -2500);
                    player.sendMessage(Main.PREFIX + "You bought 1 §cdices-key §7for §62500 coins");
                    //player.getOpenInventory().setItem(13, new ItemBuilder(Material.PAPER).setDisplayName("§9Open a chest").setLore("§7You have §c" + MySQLLootChests.getScratchOffs(player.getUniqueId()) + " §7remaining chests", "§7and §c" + MySQLLootChests.getUniversal(player.getUniqueId()) + " §7universal keys").build());
                    openDiceMain(player);
                    break;
                case ARROW:
                    coinChestListener.openGameSelector(player);
                    break;
            }
            return;
        }
        if(player.getOpenInventory().getTitle().equalsIgnoreCase("Dices")) {
            if(!games.containsKey(player.getName())) return;
            event.setCancelled(true);
            final DicesGame dicesGame = games.get(player.getName());
            switch (event.getCurrentItem().getType()) {
                case PLAYER_HEAD:
                    if(event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§2?")) {
                        dicesGame.openNext(1);
                        player.playSound(player.getLocation(), Sound.ENTITY_CHICKEN_EGG, 1f, 1f);
                        event.getClickedInventory().setItem(event.getSlot(), new ItemBuilder(Material.PLAYER_HEAD).setDisplayName("§2" + dicesGame.getLastRoll()).setCustomSkull(dicesGame.getDiceSkullURL(dicesGame.getLastRoll())).build());
                    } else if(event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§6?")) {
                        dicesGame.openNext(2);
                        if(dicesGame.getLastRoll() >= 12)
                            player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1f, 1f);
                        else
                            player.playSound(player.getLocation(), Sound.ENTITY_CHICKEN_EGG, 1f, 1f);
                        event.getClickedInventory().setItem(event.getSlot(), new ItemBuilder(Material.PLAYER_HEAD).setDisplayName("§6§l" + dicesGame.getLastRoll() + "§r §7(§62x§7)").setCustomSkull(dicesGame.getGoldDiceSkullURL(dicesGame.getLastRoll())).build());
                    }
                    event.getClickedInventory().setItem(8, new ItemBuilder(Material.BOOK).setDisplayName("§2§lCurrent Info").setLore("§aTotal: " + dicesGame.getTotal(), "§aAmount of sames: " + dicesGame.getAmountOfSames(), "§aWin: " + dicesGame.getWin()).build());
                    break;
            }
            //Game End
            if(!dicesGame.isMoveAvailable()) {
                Bukkit.getScheduler().runTaskLater(plugin, () -> {
                    player.sendMessage(Main.PREFIX + "§aTotal: " + dicesGame.getTotal());
                    openDiceEnding(player, games.get(player.getName()));
                    games.remove(player.getName());
                }, 20);
            }
            return;
        }

        if(player.getOpenInventory().getTitle().equalsIgnoreCase("Dices - Ending")) {
            event.setCancelled(true);
            switch (event.getCurrentItem().getType()) {
                case PLAYER_HEAD:
                    player.sendMessage(Main.PREFIX + event.getCurrentItem().getItemMeta().getDisplayName());
                    break;
                case GOLD_INGOT:
                    if(MySQLCoins.getPoints(player.getUniqueId()) < 2500) {
                        player.sendMessage(Main.PREFIX + "§cYou need " + (2500 - MySQLCoins.getPoints(player.getUniqueId())) + " more coins");
                        return;
                    }
                    MySQLLootChests.addDices(player.getUniqueId(), 1);
                    MySQLCoins.add(player.getUniqueId(), -2500);
                    player.sendMessage(Main.PREFIX + "You bought 1 §cdices-key §7for §62500 coins");
                    //player.getOpenInventory().setItem(13, new ItemBuilder(Material.PAPER).setDisplayName("§9Open a chest").setLore("§7You have §c" + MySQLLootChests.getScratchOffs(player.getUniqueId()) + " §7remaining chests", "§7and §c" + MySQLLootChests.getUniversal(player.getUniqueId()) + " §7universal keys").build());
                    openDiceMain(player);
                    break;
                case ENDER_CHEST:
                    if(MySQLLootChests.getDices(player.getUniqueId()) <= 0 && MySQLLootChests.getUniversal(player.getUniqueId()) <= 0) {
                        player.sendMessage(Main.PREFIX + "§cYou do not have any keys");
                        return;
                    }
                    if(MySQLLootChests.getDices(player.getUniqueId()) > 0)
                        MySQLLootChests.addDices(player.getUniqueId(), -1);
                    else
                        MySQLLootChests.addUniversal(player.getUniqueId(), -1);
                    games.put(player.getName(), new DicesGame(plugin, player, player.getOpenInventory().getTopInventory()));
                    openDicesGame(player);
                    break;
                case EMERALD:
                    player.sendMessage(Main.PREFIX + "You have §6" + MySQLCoins.getPoints(player.getUniqueId()) + " Coins");
                    break;
                case ARROW:
                    openDiceMain(player);
                    break;
            }
        }
    }

    public void openDiceMain(Player player) {
        Inventory inventory = Bukkit.createInventory(null, 3*9, "Coins - Dices");
        inventory.setItem(11, new ItemBuilder(Material.BOOK).setDisplayName("§aDices Information").setLore("§7Open §6Dice-chests §7to gamble for coins", "§71 Chest costs §62500 Coins", "", "§eYour 5 recent chests:", "§61007 Coins", "§6321 Coins").build());
        inventory.setItem(13, new ItemBuilder(Material.PAPER).setDisplayName("§9Open a chest").setLore("§7You have §c" + MySQLLootChests.getDices(player.getUniqueId()) + " §7remaining chests", "§7and §c" + MySQLLootChests.getUniversal(player.getUniqueId()) + " §7universal keys").build());
        inventory.setItem(15, new ItemBuilder(Material.GOLD_INGOT).setDisplayName("§eBuy a chest").setLore("§7Costs: §62500 Coins", "", "§aClick to buy").build());
        inventory.setItem(18, new ItemBuilder(Material.ARROW).setDisplayName("§7Go back").build());
        player.openInventory(inventory);
    }

    private void openDicesGame(Player player) {
        Inventory inventory = Bukkit.createInventory(null, 5*9, "Dices");
        ItemStack grayPane = new ItemBuilder(Material.LIGHT_GRAY_STAINED_GLASS_PANE).setDisplayName("§c").build();
        ItemStack questionMark = new ItemBuilder(Material.PLAYER_HEAD).setDisplayName("§2?").setCustomSkull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYmFkYzA0OGE3Y2U3OGY3ZGFkNzJhMDdkYTI3ZDg1YzA5MTY4ODFlNTUyMmVlZWQxZTNkYWYyMTdhMzhjMWEifX19").build();
        ItemStack goldQuestionMark = new ItemBuilder(Material.PLAYER_HEAD).setDisplayName("§6?").setCustomSkull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOWU1YmI4YjMxZjQ2YWE5YWYxYmFhODhiNzRmMGZmMzgzNTE4Y2QyM2ZhYWM1MmEzYWNiOTZjZmU5MWUyMmViYyJ9fX0=").build();
        for (int i = 0; i < 10; i++) {
            inventory.setItem(i, grayPane);
        }
        inventory.setItem(8, new ItemBuilder(Material.BOOK).setDisplayName("§2§lCurrent Info").setLore("§aTotal: §f0", "§aWin: §f0").build());
        inventory.setItem(13, questionMark);
        inventory.setItem(20, questionMark);
        inventory.setItem(22, goldQuestionMark);
        inventory.setItem(24, questionMark);
        inventory.setItem(31, questionMark);
        inventory.setItem(17, grayPane);
        inventory.setItem(18, grayPane);
        inventory.setItem(26, grayPane);
        inventory.setItem(27, grayPane);
        for (int i = 35; i < 45; i++) {
            inventory.setItem(i, grayPane);
        }
        player.openInventory(inventory);
    }

    private void openDiceEnding(Player player, DicesGame game) {
        Inventory inventory = Bukkit.createInventory(null, 5*9, "Dices - Ending");
        for (int i = 11; i < 16; i++) {
            int current = game.getMoves().get(i - 10);
            if((i - 11) == game.getGoldPosition())
                inventory.setItem(i, new ItemBuilder(Material.PLAYER_HEAD).setDisplayName("§6§l" + current + "§r §7(§62x§7)").setCustomSkull(game.getDiceSkullURL(current)).build());
            else
                inventory.setItem(i, new ItemBuilder(Material.PLAYER_HEAD).setDisplayName("§2" + current).setCustomSkull(game.getDiceSkullURL(current)).build());
        }
        inventory.setItem(27, new ItemBuilder(Material.GOLD_INGOT).setDisplayName("§aBuy new chest").setLore("§7Costs: §62500 Coins", "", "§aClick to buy").build());
        inventory.setItem(36, new ItemBuilder(Material.ENDER_CHEST).setDisplayName("§9§lOpen next").build());
        inventory.setItem(40, new ItemBuilder(Material.EMERALD).setDisplayName("§6" + MySQLCoins.getPoints(player.getUniqueId())).build());
        inventory.setItem(44, new ItemBuilder(Material.ARROW).setDisplayName("§7Back").build());
        player.openInventory(inventory);
    }

}

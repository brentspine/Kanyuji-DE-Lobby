package de.kanyuji.lobby.rewardchests.inventories;

import de.kanyuji.lobby.Main;
import de.kanyuji.lobby.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.meta.SkullMeta;

public class CoinChestListener implements Listener {

    private Main plugin;
    private ScratchOffListener scratchOffListener;
    private DiceListener diceListener;
    private SlotsListener slotsListener;

    public CoinChestListener(Main plugin) {
        this.plugin = plugin;
        this.scratchOffListener = new ScratchOffListener(plugin, this);
        this.diceListener = new DiceListener(plugin, this);
        this.slotsListener = new SlotsListener(plugin, this);
        Bukkit.getPluginManager().registerEvents(scratchOffListener, plugin);
        Bukkit.getPluginManager().registerEvents(diceListener, plugin);
        Bukkit.getPluginManager().registerEvents(slotsListener, plugin);
    }

    @EventHandler
    public void onCoinChest(PlayerInteractEvent event) {
        if(event.getClickedBlock() == null) return;
        if(event.getClickedBlock().getType() != Material.TRAPPED_CHEST && event.getClickedBlock().getType() != Material.CHEST && event.getClickedBlock().getType() != Material.ENDER_CHEST) return;
        Block block = event.getClickedBlock();
        Player player = event.getPlayer();
        if(block.equals(plugin.getRewardLocationManager().getCoinChest())) {
            event.setCancelled(true);
            openGameSelector(player);
            player.playSound(player.getLocation(), Sound.BLOCK_CHEST_OPEN, 1f, 1f);
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getClickedInventory() == null) return;
        if (event.getClickedInventory().getType() != InventoryType.CHEST) return;
        if (event.getCurrentItem() == null) return;
        if (!(event.getWhoClicked() instanceof Player)) return;
        Player player = (Player) event.getWhoClicked();
        if (player.getOpenInventory().getTitle().equalsIgnoreCase("Coins - Select a game")) {
            event.setCancelled(true);

            switch (event.getCurrentItem().getType()) {
                case ENDER_CHEST:
                    scratchOffListener.openScratchOffMain(player);
                    break;

                case PLAYER_HEAD:
                    SkullMeta skullMeta = (SkullMeta) event.getCurrentItem().getItemMeta();
                    if(skullMeta.getDisplayName().endsWith("Dices")) {
                        diceListener.openDiceMain(player);
                    }
                    else if(skullMeta.getDisplayName().endsWith("Slots")) {
                        slotsListener.openSlotsMain(player);
                    }
            }
            return;

        }
    }



    public void openGameSelector(Player player) {
        Inventory inventory = Bukkit.createInventory(null, 3*9, "Coins - Select a game");
        inventory.setItem(10, new ItemBuilder(Material.ENDER_CHEST).setDisplayName("§eScratch Off").setLore("§7Select 5 out of 54 ender chests, that", "§7contain a different amount of coins").build());
        inventory.setItem(13, new ItemBuilder(Material.PLAYER_HEAD).setDisplayName("§9Dices").setLore("§7Bet on a number the dices roll and win a lot of coins!").setCustomSkull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYzU5ODk5ZmI5ZTNhOTY0NDZlZGJjZjU5ZDJiNDM5OTNlOThjMWU5ZWM3ZDg3ZDE5M2RjMzBlNTVhNzhlOTQxZSJ9fX0=").build());
        inventory.setItem(16, new ItemBuilder(Material.PLAYER_HEAD).setDisplayName("§cSlots").setLore("§7The Classic Slots", "§7This is a game, please do not gamble with real money").setCustomSkull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvODQyNWFjYzQyY2FmOGIxZTk1Y2NiMTZkMzc5YWYwYjc2Zjk1ZWQyOWVmMmE0OTQwNzNkMGIwN2IxNWRjMjJmZCJ9fX0=").build());
        player.openInventory(inventory);
    }






    public Main getPlugin() {
        return plugin;
    }

    public ScratchOffListener getScratchOffListener() {
        return scratchOffListener;
    }

    public DiceListener getDiceListener() {
        return diceListener;
    }

    public SlotsListener getSlotsListener() {
        return slotsListener;
    }

}

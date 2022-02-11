package de.kanyuji.lobby.rewardchests.inventories;

import de.kanyuji.lobby.Main;
import de.kanyuji.lobby.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;

public class SlotsListener implements Listener {

    private Main plugin;
    private CoinChestListener coinChestListener;

    public SlotsListener(Main plugin, CoinChestListener coinChestListener) {
        this.plugin = plugin;
        this.coinChestListener = coinChestListener;
    }

    public void openSlotsMain(Player player) {
        Inventory inventory = Bukkit.createInventory(null, 3*9, "Coins - Slots");
        inventory.setItem(0, new ItemBuilder(Material.PAPER).setDisplayName("§4Todo").build());
        player.openInventory(inventory);
    }

}

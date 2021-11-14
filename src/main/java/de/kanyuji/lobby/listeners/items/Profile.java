package de.kanyuji.lobby.listeners.items;

import de.kanyuji.lobby.Main;
import de.kanyuji.lobby.utils.ItemBuilder;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.*;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;

public class Profile implements Listener {


    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if(event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if(event.getItem() == null) return;
            if(event.getItem().hasItemMeta() && event.getItem().getItemMeta() != null) {
                if(event.getItem().getType() == Material.PLAYER_HEAD) {
                    openProfileGUI(event.getPlayer());
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
            if(event.getView().getTitle().equalsIgnoreCase("§b§lProfile")) {
                switch (event.getCurrentItem().getType()) {
                    case PAPER:
                        BaseComponent[] component =
                                new ComponentBuilder(Main.PREFIX).bold(true)
                                        .append("Hier ").color(ChatColor.GRAY).event(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://kanyuji.de/discord")).bold(true)
                                        .append("geht es zum ").color(ChatColor.GRAY)
                                        .append("Discord").color(ChatColor.RED).create();

                        player.spigot().sendMessage(component);
                        player.closeInventory();
                        break;
                    case REPEATER:

                        break;
                    case PLAYER_HEAD:
                        player.sendMessage("todo");
                        break;

                }
                event.setCancelled(true);
            }
        }
    }


    public void openProfileGUI(Player player) {
        Inventory inventory = Bukkit.createInventory(null, 3*9, "§b§lProfile");
        inventory.setItem(10, new ItemBuilder(Material.REPEATER).setDisplayName("§9Einstellungen").setLore("§7Hier kannst du Einstellungen verwalten!?").build());
        inventory.setItem(16, new ItemBuilder(Material.PAPER).setDisplayName("§9Discord").setLore("§7Giveaways, Updates und mehr!").build());
        inventory.setItem(13, new ItemBuilder(Material.PLAYER_HEAD).setSkullOwner(player.getUniqueId()).setDisplayName("§9Statistiken").setLore("§7Klick hier um deine Statistiken zu sehen").build());
        player.openInventory(inventory);
    }

    public void openSettingsGUI(Player player) {
        Inventory inventory = Bukkit.createInventory(null, 3*9, "§b§lSettings");
        inventory.setItem(2, new ItemBuilder(Material.PAPER).setDisplayName("§aChat Einstellungen").setLore("§7Klicke, um deine Einstellungen zu ändern").hideMetaTags(true).build());
        inventory.setItem(3, new ItemBuilder(Material.BARRIER).setDisplayName("§aPrivatsphäre Einstellungen").setLore("§7Klicke, um deine Einstellungen zu ändern").hideMetaTags(true).build());
        inventory.setItem(5, new ItemBuilder(Material.NETHER_STAR).setDisplayName("§aLobby Einstellungen").setLore("§7Klicke, um deine Einstellungen zu ändern").hideMetaTags(true).build());
        inventory.setItem(5, new ItemBuilder(Material.COBWEB).setDisplayName("§aStatistik Einstellungen").setLore("§7Klicke, um deine Einstellungen zu ändern").hideMetaTags(true).build());
        player.openInventory(inventory);
    }

}

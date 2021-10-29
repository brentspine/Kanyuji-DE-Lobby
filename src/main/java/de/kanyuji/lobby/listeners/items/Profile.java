package de.kanyuji.lobby.listeners.items;

import de.kanyuji.lobby.Main;
import de.kanyuji.lobby.utils.ClickableMessage;
import de.kanyuji.lobby.utils.ItemBuilder;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.*;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import java.awt.*;

public class Profile implements Listener {

    private static org.bukkit.inventory.Inventory inventory = Bukkit.createInventory(null, 3*9, "§b§lProfile");

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if(event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if(event.getItem() == null) return;
            if(event.getItem().hasItemMeta() && event.getItem().getItemMeta() != null) {
                if(event.getItem().getType() == Material.PLAYER_HEAD) {
                    inventory.setItem(13, new ItemBuilder(Material.PLAYER_HEAD).setSkullOwner(event.getPlayer().getUniqueId()).setDisplayName("§9Statistiken").setLore("§7Klick hier um deine Statistiken zu sehen").build());
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
            if(event.getView().getTitle().equalsIgnoreCase("§b§lProfile")) {
                handleMainInventoryClick(event.getCurrentItem().getType(), player);
                event.setCancelled(true);
            }
        }
    }


    public void handleMainInventoryClick(Material material, Player player) {
        switch (material) {
            case PAPER:
                BaseComponent[] component =
                        new ComponentBuilder(Main.PREFIX).bold(true)
                                .append("Hier ").color(ChatColor.GRAY).event(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://kanyuji.de/discord")).event(new HoverEvent(HoverEvent.Action.SHOW_TEXT)).bold(true)
                                .append("geht es zum ").color(ChatColor.GRAY)
                                .append("Discord").color(ChatColor.RED).create();

                player.spigot().sendMessage(component);
                player.closeInventory();
                break;
            case PLAYER_HEAD:

                break;
            case BEDROCK:
                player.sendMessage(Main.PREFIX + "Placeholder");
                break;
        }
    }


    public static void setInventory() {
        inventory.setItem(10, new ItemBuilder(Material.REPEATER).setDisplayName("§9Einstellungen").setLore("§7Liest das irgendjemand?").build());
        inventory.setItem(16, new ItemBuilder(Material.PAPER).setDisplayName("§9Discord").setLore("§7Giveaways, Updates und mehr!").build());
    }

}

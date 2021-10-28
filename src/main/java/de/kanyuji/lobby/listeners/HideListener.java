package de.kanyuji.lobby.listeners;

import de.kanyuji.lobby.Main;
import de.kanyuji.lobby.utils.ItemBuilder;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.ArrayList;

public class HideListener implements Listener {

    public static ArrayList<Player> cooldown = new ArrayList<>();

    @EventHandler
    public void handlePlayerInteractEvent(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if(event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if(event.getItem() == null) return;
            if(!event.getItem().hasItemMeta()) return;
            if(!cooldown.contains(player)) {
                if(event.getItem().getType() == Material.LIME_DYE) {
                    player.getInventory().setItem(1, new ItemBuilder(Material.PURPLE_DYE).setDisplayName("§5Spieler §8» §7Nur VIP").build());
                    player.sendMessage(Main.PREFIX + "Du siehst nun jeden VIP");
                    for(Player hided : Bukkit.getOnlinePlayers()) {
                        if(!hided.hasPermission("system.vip")) {
                            player.hidePlayer(hided);
                        }
                    }
                    if(!Main.getInstance().getVisibleHandler().getHider().contains(player)) {
                        Main.getInstance().getVisibleHandler().getHider().add(player);
                    }
                    cooldown.add(player);
                    Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {
                        @Override
                        public void run() {
                            cooldown.remove(player);
                        }
                    }, 60);
                } else if(event.getItem().getType() == Material.RED_DYE) {
                    player.getInventory().setItem(1, new ItemBuilder(Material.LIME_DYE).setDisplayName("§aSpieler §8» §7Angezeigt").build());
                    player.sendMessage(Main.PREFIX + "Du siehst nun jeden Spieler wieder");
                    Bukkit.getOnlinePlayers().forEach(player::showPlayer);
                    if(Main.getInstance().getVisibleHandler().getHider().contains(player)) {
                        Main.getInstance().getVisibleHandler().getHider().remove(player);
                    }
                    cooldown.add(player);
                    Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {
                        @Override
                        public void run() {
                            cooldown.remove(player);
                        }
                    }, 60);
                } else if(event.getItem().getType() == Material.PURPLE_DYE) {
                    player.getInventory().setItem(1, new ItemBuilder(Material.RED_DYE).setDisplayName("§cSpieler §8» §7Versteckt").build());
                    player.sendMessage(Main.PREFIX + "Du siehst nun keinen Spieler Mehr");
                    Bukkit.getOnlinePlayers().forEach( player::hidePlayer);
                    if(!Main.getInstance().getVisibleHandler().getHider().contains(player)) {
                        Main.getInstance().getVisibleHandler().getHider().add(player);
                    }
                    cooldown.add(player);
                    Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {
                        @Override
                        public void run() {
                            cooldown.remove(player);
                        }
                    }, 60);
                }
            } else
                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText("§cBitte warte einen Moment!"));
        }

    }

}

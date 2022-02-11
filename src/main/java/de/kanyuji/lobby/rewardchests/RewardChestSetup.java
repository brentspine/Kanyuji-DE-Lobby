package de.kanyuji.lobby.rewardchests;

import de.kanyuji.lobby.Main;
import de.kanyuji.lobby.utils.ClickableMessage;
import de.kanyuji.lobby.utils.LocationUtil;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.data.Directional;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.plugin.Plugin;

public class RewardChestSetup implements Listener {

    private Main plugin;
    private Player player;
    private int phase;
    private boolean finished;

    private Location dailyRewards;
    private Location lottery;
    private Block coinChest;
    private Block cosmeticChest;

    public RewardChestSetup(Main plugin, Player player) {
        this.plugin = plugin;
        this.player = player;
        Bukkit.getPluginManager().registerEvents(this, plugin);
        phase = 1;
        finished = false;
        startSetup();
    }

    public void startPhase(int phase) {
        switch (phase) {
            case 1:
                player.sendMessage(Main.PREFIX + "§cSneake §7an der §cDaily-Rewards-Position §7(1/1)");
                break;
            case 2:
                player.sendMessage(Main.PREFIX + "§cSneake §7an der §cLottery-Position §7(1/1)");
                break;
            case 3:
                player.sendMessage(Main.PREFIX + "§cKlicke §7den Block der §cCoin-Chest an §7(1/1)");
                break;
            /*case 4:
                player.spigot().sendMessage(new ClickableMessage(Main.PREFIX)
                        .addText("§cSchreibe §7die §cRichtung der Coin-Chest §7in den §cChat: ")
                        .addClickHoverEvent("§aNorth", ClickEvent.Action.SUGGEST_COMMAND, "north", HoverEvent.Action.SHOW_TEXT, "§aKlicke zum ausführen").addText(" ")
                        .addClickHoverEvent("§aSouth", ClickEvent.Action.SUGGEST_COMMAND, "south", HoverEvent.Action.SHOW_TEXT, "§aKlicke zum ausführen").addText(" ")
                        .addClickHoverEvent("§aWest", ClickEvent.Action.SUGGEST_COMMAND, "west", HoverEvent.Action.SHOW_TEXT, "§aKlicke zum ausführen").addText(" ")
                        .addClickHoverEvent("§aEast", ClickEvent.Action.SUGGEST_COMMAND, "east", HoverEvent.Action.SHOW_TEXT, "§aKlicke zum ausführen").addText(" ")
                        .build());
                break;*/
            case 4:
                player.sendMessage(Main.PREFIX + "§cKlicke §7den Block der §cCosmetic-Chest an §7(1/1)");
                break;

            default:
                finishSetup();
                break;
        }
    }

    @EventHandler
    public void onSneak(PlayerToggleSneakEvent event) {
        if(finished) return;
        if(!player.getName().equalsIgnoreCase(event.getPlayer().getName())) return;
        if(!event.isSneaking()) return;
        switch (phase) {
            case 1:
                dailyRewards = player.getLocation();
                nextPhase();
                break;
            case 2:
                lottery = player.getLocation();
                nextPhase();
                break;

            default:
                break;
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        if(finished) return;
        if(!player.getName().equalsIgnoreCase(event.getPlayer().getName())) return;
        event.setCancelled(true);
        switch (phase) {
            case 3:
                if(event.getBlock().getType() != Material.ENDER_CHEST && event.getBlock().getType() != Material.CHEST && event.getBlock().getType() != Material.TRAPPED_CHEST) {
                    player.sendMessage(Main.PREFIX + "§cDer Block muss eine Kiste sein");
                    return;
                }
                coinChest = event.getBlock();
                nextPhase();
                break;
            case 4:
                if(event.getBlock().getType() != Material.ENDER_CHEST && event.getBlock().getType() != Material.CHEST && event.getBlock().getType() != Material.TRAPPED_CHEST) {
                    player.sendMessage(Main.PREFIX + "§cDer Block muss eine Kiste sein");
                    return;
                }
                cosmeticChest = event.getBlock();
                nextPhase();
                break;
            default:
                break;
        }
    }

    private void finishSetup() {
        player.sendMessage(Main.PREFIX + "§7Eingabe abgeschlossen, §cbereite Speicherung vor");
        new LocationUtil(plugin, dailyRewards, "coins.daily").saveLocation();
        new LocationUtil(plugin, lottery, "coins.lottery").saveLocation();
        new LocationUtil(plugin, coinChest.getLocation(), "coins.coinchest").saveBlockLocation();
        new LocationUtil(plugin, cosmeticChest.getLocation(), "coins.cosmeticchest").saveBlockLocation();
        player.sendMessage(Main.PREFIX + "§aDas Reward-Setup wurde abgeschlossen");
        finished = true;
    }

    private void startSetup() {
        player.sendMessage(Main.PREFIX + "§aDas Reward-Setup wurde gestartet");
        startPhase(phase);
    }

    private void nextPhase() {
        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1f, 1f);
        phase++;
        startPhase(phase);
    }

}

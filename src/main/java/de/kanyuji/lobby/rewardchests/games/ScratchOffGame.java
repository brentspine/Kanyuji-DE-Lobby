package de.kanyuji.lobby.rewardchests.games;

import de.kanyuji.lobby.Main;
import de.kanyuji.lobby.utils.Dice;
import de.kanyuji.lobby.utils.ItemBuilder;
import org.apache.commons.rng.sampling.distribution.DiscreteSampler;
import org.apache.commons.rng.sampling.distribution.RejectionInversionZipfSampler;
import org.apache.commons.rng.simple.RandomSource;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.text.NumberFormat;
import java.util.*;

public class ScratchOffGame {

    private Main plugin;
    private Player player;
    //Equal to amount of chests clicked
    private int phase;
    private int coinsWon;
    private DiscreteSampler sampler;
    private HashMap<Integer, Integer> moves;
    private HashMap<Integer, ItemStack> glassPanes;

    public ScratchOffGame(Main plugin, Player player) {
        this.plugin = plugin;
        this.player = player;
        this.moves = new HashMap<>();
        this.glassPanes = new HashMap<>();
        phase = 0;
        coinsWon = 0;
        sampler = RejectionInversionZipfSampler.of(RandomSource.JDK.create(), 999_850, 1.642D);
        //sampler = RejectionInversionZipfSampler.of(RandomSource.JDK.create(), 999_900, 1.0D);
    }

    public int openNext() {
        phase++;
        int i = sampler.sample();
        i = i + Dice.generateNumberBetween(0, 150);
        coinsWon+= i;
        moves.put(phase, i);
        if(i <= 75) {
            glassPanes.put(phase, new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE).setDisplayName("§8Common").build());
            player.playSound(player.getLocation(), Sound.ENTITY_CHICKEN_EGG, 1f, 1f);
        }
        else if(i <= 250) {
            glassPanes.put(phase, new ItemBuilder(Material.LIGHT_GRAY_STAINED_GLASS_PANE).setDisplayName("§7Uncommon").build());
            player.playSound(player.getLocation(), Sound.ENTITY_CHICKEN_EGG, 1f, 1f);
        }
        else if(i <= 500) {
            glassPanes.put(phase, new ItemBuilder(Material.BLUE_STAINED_GLASS_PANE).setDisplayName("§9Rare").build());
            player.playSound(player.getLocation(), Sound.ENTITY_CHICKEN_EGG, 1f, 1f);
        }
        else if(i <= 1000) {
            glassPanes.put(phase, new ItemBuilder(Material.PURPLE_STAINED_GLASS_PANE).setDisplayName("§5Epic").build());
            player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1f, 1f);
        }
        else if(i <= 5_000) {
            glassPanes.put(phase, new ItemBuilder(Material.YELLOW_STAINED_GLASS_PANE).setDisplayName("§6Legendary I").build());
            player.playSound(player.getLocation(), Sound.ENTITY_WITHER_DEATH, 1f, 1f);
        }
        else if(i <= 10_000) {
            glassPanes.put(phase, new ItemBuilder(Material.YELLOW_STAINED_GLASS_PANE).setDisplayName("§6§lLegendary II").build());
            playLegendarySound(player);
        }
        else if(i <= 25_000) {
            glassPanes.put(phase, new ItemBuilder(Material.YELLOW_STAINED_GLASS_PANE).setDisplayName("§6§lLegendary III").build());
            playLegendarySound(player);
        }
        else if(i <= 50_000) {
            glassPanes.put(phase, new ItemBuilder(Material.RED_STAINED_GLASS_PANE).setDisplayName("§4§lGODLIKE I").build());
            playGodLikeSounds(player);
        }
        else if(i <= 100_000) {
            glassPanes.put(phase, new ItemBuilder(Material.RED_STAINED_GLASS_PANE).setDisplayName("§4§lGODLIKE II").build());
            playGodLikeSounds(player);
        }
        else if(i <= 300_000) {
            glassPanes.put(phase, new ItemBuilder(Material.RED_STAINED_GLASS_PANE).setDisplayName("§4§lGODLIKE III").build());
            playGodLikeSounds(player);
        }
        else if(i <= 500_000) {
            glassPanes.put(phase, new ItemBuilder(Material.RED_STAINED_GLASS_PANE).setDisplayName("§4§lGODLIKE IV").build());
            playGodLikeSounds(player);
        }
        else if(i <= 750_000) {
            glassPanes.put(phase, new ItemBuilder(Material.RED_STAINED_GLASS_PANE).setDisplayName("§4§lGODLIKE V").build());
            playGodLikeSounds(player);
        } else {
            glassPanes.put(phase, new ItemBuilder(Material.RED_STAINED_GLASS_PANE).setDisplayName("§4§lJACKPOT").build());
            playJackpotSounds(player);
        }
        return i;
    }

    public String getCoinsColor(int i) {
        if(i <= 75) {
            return "" + ChatColor.DARK_GRAY;
        }
        else if(i <= 250) {
            return "" + ChatColor.GRAY;
        }
        else if(i <= 500) {
            return "" + ChatColor.BLUE;
        }
        else if(i <= 1000) {
            return "" + ChatColor.DARK_PURPLE;
        }
        else if(i <= 5_000) {
            return "" + ChatColor.GOLD;
        }
        else if(i <= 10_000) {
            return ChatColor.BOLD.toString() + ChatColor.GOLD;
        }
        else if(i <= 25_000) {
            return ChatColor.BOLD.toString() + ChatColor.GOLD;
        }
        else if(i <= 50_000) {
            return ChatColor.BOLD.toString() + ChatColor.DARK_RED;
        }
        else if(i <= 100_000) {
            return ChatColor.BOLD.toString() + ChatColor.DARK_RED;
        }
        else if(i <= 300_000) {
            return ChatColor.BOLD.toString() + ChatColor.DARK_RED;
        }
        else if(i <= 500_000) {
            return ChatColor.BOLD.toString() + ChatColor.DARK_RED;
        }
        else if(i <= 750_000) {
            return ChatColor.BOLD.toString() + ChatColor.DARK_RED;
        } else {
            return ChatColor.BOLD.toString() + ChatColor.GOLD;
        }
    }


    private void playLegendarySound(Player player) {
        //player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1f, 1f);
        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1f, 1f);
        }, 20);
        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            player.playSound(player.getLocation(), Sound.ENTITY_WITHER_DEATH, 1f, 1f);
        }, 40);
    }

    private void playGodLikeSounds(Player player) {
        int taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, () -> {
            player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1f, 1f);
        }, 5, 2);
        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            player.playSound(player.getLocation(), Sound.ENTITY_WITHER_DEATH, 1f, 1f);
            Bukkit.getScheduler().cancelTask(taskID);
        }, 60);
    }

    private void playJackpotSounds(Player player) {
        int taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, () -> {
            player.playSound(player.getLocation(), Sound.ENTITY_PARROT_IMITATE_WITHER, 1f, 1f);
        }, 5, 10);
        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            Bukkit.getScheduler().cancelTask(taskID);
            jackpotSoundsPhase2(player);
        }, 60);
    }

    private void jackpotSoundsPhase2(Player player) {
        int taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, () -> {
            player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1f, 1f);
        }, 5, 2);
        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            Bukkit.getScheduler().cancelTask(taskID);
            jackpotSoundsPhase3(player);
        }, 40);
    }

    private void jackpotSoundsPhase3(Player player) {
        int taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, () -> {
            player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1f, 1f);
        }, 5, 20);
        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            Bukkit.getScheduler().cancelTask(taskID);
        }, 40);
        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            player.playSound(player.getLocation(), Sound.ENTITY_WITHER_DEATH, 1f, 1f);
        }, 70);
    }

    public int getPhase() {
        return phase;
    }

    public int getCoinsWon() {
        return coinsWon;
    }

    public Player getPlayer() {
        return player;
    }

    public boolean isMoveAvailable() {
        return phase < 5;
        //return phase >= 5 ? false : true;
    }

    public HashMap<Integer, Integer> getMoves() {
        return moves;
    }

    public HashMap<Integer, ItemStack> getGlassPanes() {
        return glassPanes;
    }

}

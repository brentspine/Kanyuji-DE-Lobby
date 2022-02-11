package de.kanyuji.lobby.rewardchests;

import de.kanyuji.lobby.Main;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;

public class RewardVillagerListener implements Listener {

    private Main plugin;

    public RewardVillagerListener(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onDailyVillager(PlayerInteractAtEntityEvent event) {
        if(event.getRightClicked().getType() != EntityType.VILLAGER) return;
        if(!event.getRightClicked().getScoreboardTags().contains("dailyRewards")) return;
        Player player = event.getPlayer();
        player.sendMessage(Main.PREFIX + "Daily Villager");
    }

    @EventHandler
    public void onLotteryVillager(PlayerInteractAtEntityEvent event) {
        if(event.getRightClicked().getType() != EntityType.VILLAGER) return;
        if(!event.getRightClicked().getScoreboardTags().contains("lottery")) return;
        Player player = event.getPlayer();
        player.sendMessage(Main.PREFIX + "Lottery Villager");
    }

}

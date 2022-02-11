package de.kanyuji.lobby.rewardchests;

import de.kanyuji.lobby.Main;
import de.kanyuji.lobby.utils.LocationUtil;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Villager;

public class RewardLocationManager {

    private final Main plugin;

    private Location dailyRewards;
    private Location lottery;
    private Block coinChest;
    private Block cosmeticChest;

    public RewardLocationManager(Main plugin) {
        this.plugin = plugin;
        load();
    }

    private void load() {
        dailyRewards = new LocationUtil(plugin, "coins.daily").getLocation();
        lottery = new LocationUtil(plugin, "coins.lottery").getLocation();
        coinChest = new LocationUtil(plugin, "coins.coinchest").getBlockLocation().getBlock();
        cosmeticChest = new LocationUtil(plugin, "coins.cosmeticchest").getBlockLocation().getBlock();
        for(Entity entity : dailyRewards.getWorld().getNearbyEntities(dailyRewards, 1, 1, 1)) {
            entity.remove();
        }
        Villager villager = (Villager) dailyRewards.getWorld().spawnEntity(dailyRewards, EntityType.VILLAGER);
        villager.setCustomNameVisible(false);
        villager.setSilent(true);
        villager.setAware(true);
        villager.addScoreboardTag("dailyRewards");
        villager.setInvulnerable(true);
        villager.setCanPickupItems(false);
        villager.setAI(false);
        villager.setVillagerType(Villager.Type.PLAINS);
        villager.setCollidable(false);
        villager.setRotation(dailyRewards.getYaw(), dailyRewards.getPitch());
        //villager.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 999999, 255, false, false));
        for(Entity entity : lottery.getWorld().getNearbyEntities(lottery, 1, 1, 1)) {
            entity.remove();
        }
        villager = (Villager) lottery.getWorld().spawnEntity(lottery, EntityType.VILLAGER);
        villager.setCustomNameVisible(false);
        villager.setSilent(true);
        villager.setAware(true);
        villager.addScoreboardTag("lottery");
        villager.setInvulnerable(true);
        villager.setCanPickupItems(false);
        villager.setAI(false);
        villager.setVillagerType(Villager.Type.PLAINS);
        villager.setCollidable(false);
        villager.setRotation(lottery.getYaw(), lottery.getPitch());
    }

    public void reload() {
        load();
    }

    public Location getDailyRewards() {
        return dailyRewards;
    }

    public Location getLottery() {
        return lottery;
    }

    public Block getCoinChest() {
        return coinChest;
    }

    public Block getCosmeticChest() {
        return cosmeticChest;
    }

    public Main getPlugin() {
        return plugin;
    }

}

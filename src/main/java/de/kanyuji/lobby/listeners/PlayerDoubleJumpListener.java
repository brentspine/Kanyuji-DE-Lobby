package de.kanyuji.lobby.listeners;

import de.kanyuji.lobby.Main;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import de.kanyuji.lobby.Settings;
import org.bukkit.Effect;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerGameModeChangeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;

public class PlayerDoubleJumpListener implements Listener {

    public static final Integer DOUBLE_JUMP_COOLDOWN_SECONDS = 3;
    public static final Float MULTIPLY_BY = 2f;

    Map<UUID, Long> cooldowns = new HashMap();
    private Main plugin = Main.getInstance();

    public PlayerDoubleJumpListener() {
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        if (p.hasPermission("doublejump.use")) {
            if (p.getGameMode() == GameMode.SURVIVAL) {
                p.setAllowFlight(true);
            } else if (p.getGameMode() == GameMode.ADVENTURE) {
                p.setAllowFlight(true);
            } else {
                p.setAllowFlight(false);
            }
        }

    }

    @EventHandler
    public void onGameModeChange(PlayerGameModeChangeEvent e) {
        Player p = e.getPlayer();
        if (p.getGameMode() == GameMode.CREATIVE) {
            p.setAllowFlight(true);
        }

        if (p.hasPermission("doublejump.use")) {
            if (p.getGameMode() == GameMode.SURVIVAL) {
                p.setAllowFlight(true);
            } else if (p.getGameMode() == GameMode.ADVENTURE) {
                p.setAllowFlight(true);
            }
        }
    }

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        Player p = e.getPlayer();
        if (p.getGameMode() == GameMode.CREATIVE) {
            p.setAllowFlight(true);
        }

        if (this.cooldowns.containsKey(p.getUniqueId())) {
            if ((Long)this.cooldowns.get(p.getUniqueId()) > System.currentTimeMillis()) {
                return;
            }

            if (p.hasPermission("doublejump.use")) {
                if (p.getGameMode() == GameMode.SURVIVAL) {
                    p.setAllowFlight(true);
                } else if (p.getGameMode() == GameMode.ADVENTURE) {
                    p.setAllowFlight(true); //a
                }
            }
        } else if (this.cooldowns.get(p.getUniqueId()) == null) {
            p.setAllowFlight(true);
        }
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        Player p = e.getEntity();
        if (p.getGameMode() == GameMode.CREATIVE) {
            p.setAllowFlight(true);
        }

        if (p.hasPermission("doublejump.use")) {
            if (p.getGameMode() == GameMode.SURVIVAL) {
                p.setAllowFlight(true);
            } else if (p.getGameMode() == GameMode.ADVENTURE) {
                p.setAllowFlight(true);
            }
        }
    }

    @EventHandler
    public void onFall(EntityDamageEvent e) {
        if (e.getEntity() instanceof Player && e.getCause().equals(DamageCause.FALL)) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onJump(PlayerToggleFlightEvent e) {
        Player p = e.getPlayer();
        if (p.hasPermission("doublejump.use")) {
            if (p.getGameMode() != GameMode.CREATIVE) {
                if (p.getGameMode() != GameMode.SPECTATOR) {
                    e.setCancelled(true);
                    if (this.cooldowns.containsKey(p.getUniqueId()) && this.cooldowns.get(p.getUniqueId()) > System.currentTimeMillis()) {
                        long timeLeft = (this.cooldowns.get(p.getUniqueId()) - System.currentTimeMillis()) / 1000L;
                        p.setAllowFlight(false);
                        e.setCancelled(true);
                    } else {
                        this.cooldowns.put(p.getUniqueId(), System.currentTimeMillis() + (long)(Settings.DOUBLE_JUMP_COOLDOWN_SECONDS * 1000));
                        p.setVelocity(p.getPlayer().getLocation().getDirection().multiply(Settings.DOUBLE_JUMP_MULTIPLY_BY).setY(1));

                        p.playSound(p.getLocation(), Settings.DOUBLE_JUMP_BOOST_SOUND, 2.0F, 1.0F);
                        p.getWorld().playEffect(p.getLocation(), Settings.DOUBLE_JUMP_BOOST_EFFECT, 0, 20);
                    }
                }
            }
        } else {
            Block b = p.getWorld().getBlockAt(p.getLocation().subtract(0.0D, 1.0D, 0.0D));
            if (b.getType().equals(Material.AIR)) {
                //p.sendMessage(Logger.color(Config.getCustomConfig2().getString("Prefix") + Config.getCustomConfig2().getString("NoPerms")));
            }
        }
    }

}

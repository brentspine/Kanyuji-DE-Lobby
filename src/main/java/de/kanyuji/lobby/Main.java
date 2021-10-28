package de.kanyuji.lobby;

import de.kanyuji.lobby.commands.SetupCommand;
import de.kanyuji.lobby.commands.SpawnCommand;
import de.kanyuji.lobby.listeners.BlockedListeners;
import de.kanyuji.lobby.listeners.HideListener;
import de.kanyuji.lobby.listeners.PlayerConnectionListener;
import de.kanyuji.lobby.listeners.ScoreboardListener;
import de.kanyuji.lobby.fastboard.FastBoard;
import de.kanyuji.lobby.listeners.items.Firework;
import de.kanyuji.lobby.listeners.items.Inventory;
import de.kanyuji.lobby.utils.VisibleHandler;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    private static Main instance;
    public static final String PREFIX = "§b§lLobby §8» §7";
    private VisibleHandler visiblehandler;


    @Override
    public void onEnable() {
        super.onEnable();
        instance = this;
        register(Bukkit.getPluginManager());
        visiblehandler = new VisibleHandler();
    }

    public static Main getInstance() {
        return instance;
    }

    public void register(PluginManager pluginManager) {
        getCommand("setup").setExecutor(new SetupCommand());
        getCommand("spawn").setExecutor(new SpawnCommand());
        pluginManager.registerEvents(new PlayerConnectionListener(), this);
        pluginManager.registerEvents(new BlockedListeners().run(), this);
        pluginManager.registerEvents(new ScoreboardListener(), this);
        pluginManager.registerEvents(new Inventory(), this);
        pluginManager.registerEvents(new HideListener(), this);
        getServer().getScheduler().runTaskTimer(this, () -> {
            for (FastBoard board : ScoreboardListener.boards.values()) {
                ScoreboardListener.updateBoard(board);
            }
        }, 0, 20);
        pluginManager.registerEvents(new Firework(), this);
        Firework.setInventory();
        Inventory.setInventory();
    }

    public VisibleHandler getVisibleHandler() {
        return visiblehandler;
    }
}

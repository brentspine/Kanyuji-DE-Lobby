package de.kanyuji.lobby;

import de.kanyuji.lobby.commands.SetupCommand;
import de.kanyuji.lobby.listeners.BlockedListeners;
import de.kanyuji.lobby.listeners.PlayerConnectionListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    private static Main instance;
    public static final String PREFIX = "§b§lLobby §8» §7";

    @Override
    public void onEnable() {
        super.onEnable();
        instance = this;
        register(Bukkit.getPluginManager());
    }

    public static Main getInstance() {
        return instance;
    }

    public void register(PluginManager pluginManager) {
        getCommand("setup").setExecutor(new SetupCommand());
        pluginManager.registerEvents(new PlayerConnectionListener(), this);
        pluginManager.registerEvents(new BlockedListeners(), this);
    }


    //todo SetupCommand

}

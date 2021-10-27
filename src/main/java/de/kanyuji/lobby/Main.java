package de.kanyuji.lobby;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    private static Main instance;
    public static final String PREFIX = "§b§lLobby §8» §7";
    public static Location spawnLocation;

    @Override
    public void onEnable() {
        super.onEnable();
        instance = this;
    }

    public static Main getInstance() {
        return instance;
    }


    public static void setSpawnLocation(Location spawnLocation) {
        Main.spawnLocation = spawnLocation;
    }

    //todo SetupCommand

}

package de.kanyuji.lobby;

import de.kanyuji.lobby.commands.DiscordCommand;
import de.kanyuji.lobby.commands.SetupCommand;
import de.kanyuji.lobby.commands.SpawnCommand;
import de.kanyuji.lobby.listeners.BlockedListeners;
import de.kanyuji.lobby.listeners.HideListener;
import de.kanyuji.lobby.listeners.PlayerConnectionListener;
import de.kanyuji.lobby.listeners.ScoreboardListener;
import de.kanyuji.lobby.fastboard.FastBoard;
import de.kanyuji.lobby.listeners.cosmetics.BlockTrails;
import de.kanyuji.lobby.listeners.items.Firework;
import de.kanyuji.lobby.listeners.items.Inventory;
import de.kanyuji.lobby.listeners.items.Profile;
import de.kanyuji.lobby.utils.MySQL;
import de.kanyuji.lobby.utils.VisibleHandler;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Main extends JavaPlugin {

    private static Main instance;
    public static final String PREFIX = "§b§lLobby §8» §7";
    private VisibleHandler visiblehandler;

    private File customConfigFile;
    private FileConfiguration customConfig;

    @Override
    public void onEnable() {
        createCustomConfig();
        setDefaults();

        //MySQL.connect();
        instance = this;
        register(Bukkit.getPluginManager());
        visiblehandler = new VisibleHandler();

        de.kanyuji.lobby.mysql.MySQL.host = getCustomConfig().getString("MySQL.host");
        de.kanyuji.lobby.mysql.MySQL.port = getCustomConfig().getString("MySQL.port");
        de.kanyuji.lobby.mysql.MySQL.database = getCustomConfig().getString("MySQL.database");
        de.kanyuji.lobby.mysql.MySQL.user = getCustomConfig().getString("MySQL.user");
        de.kanyuji.lobby.mysql.MySQL.password = getCustomConfig().getString("MySQL.password");

        de.kanyuji.lobby.mysql.MySQL.connect();
    }

    @Override
    public void onDisable() {
        de.kanyuji.lobby.mysql.MySQL.disconnect();
    }

    public static Main getInstance() {
        return instance;
    }

    public void register(PluginManager pluginManager) {
        getCommand("setup").setExecutor(new SetupCommand());
        getCommand("spawn").setExecutor(new SpawnCommand());
        getCommand("discord").setExecutor(new DiscordCommand());
        pluginManager.registerEvents(new PlayerConnectionListener(), this);
        pluginManager.registerEvents(new BlockedListeners().run(), this);
        pluginManager.registerEvents(new ScoreboardListener(), this);
        pluginManager.registerEvents(new Inventory(), this);
        pluginManager.registerEvents(new HideListener(), this);
        pluginManager.registerEvents(new BlockTrails(), this);
        pluginManager.registerEvents(new Profile(), this);
        getServer().getScheduler().runTaskTimer(this, () -> {
            for (FastBoard board : ScoreboardListener.boards.values()) {
                ScoreboardListener.updateBoard(board);
            }
        }, 0, 20);
        pluginManager.registerEvents(new Firework(), this);
        Firework.setInventory();
        Inventory.setInventory();
        Inventory.setBlockTrailInventory();
        BlockTrails.run();
        Profile.setInventory();

    }


    private void createCustomConfig() {
        this.customConfigFile = new File(this.getDataFolder(), "mysql.yml");
        if (!this.getDataFolder().exists()) {
            this.getDataFolder().mkdir();
        }

        if (!this.customConfigFile.exists()) {
            try {
                this.customConfigFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }

            Bukkit.getConsoleSender().sendMessage("§c§lCoins §8| §7Die §cmysql.yml §7wurde erstellt");
        }

        new YamlConfiguration();
        this.customConfig = YamlConfiguration.loadConfiguration(this.customConfigFile);
    }

    public void setDefaults() {
        this.customConfig.addDefault("MySQL.host", "localhost");
        this.customConfig.addDefault("MySQL.port", "3306");
        this.customConfig.addDefault("MySQL.database", "coin");
        this.customConfig.addDefault("MySQL.user", "localhost");
        this.customConfig.addDefault("MySQL.password", "password");
        this.customConfig.options().copyDefaults(true);

        try {
            this.customConfig.save(this.customConfigFile);
        } catch (IOException var2) {
            var2.printStackTrace();
        }

    }

    public FileConfiguration getCustomConfig() {
        return this.customConfig;
    }


    public VisibleHandler getVisibleHandler() {
        return visiblehandler;
    }
}

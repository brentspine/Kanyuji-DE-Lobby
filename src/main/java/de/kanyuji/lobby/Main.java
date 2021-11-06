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
import de.kanyuji.lobby.listeners.items.Inv;
import de.kanyuji.lobby.listeners.items.Profile;
import de.kanyuji.lobby.utils.MySQLUtil;
import de.kanyuji.lobby.utils.MySQL;
import de.kanyuji.lobby.utils.VisibleHandler;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public class Main extends JavaPlugin {

    private static Main instance;
    public static final String PREFIX = "§b§lLobby §8» §7";
    public static final String NOPERM = PREFIX + "§cDazu hast du keine Berechtigungen!";
    private VisibleHandler visiblehandler;

    private File customConfigFile;
    private FileConfiguration customConfig;

    @Override
    public void onEnable() {
        createCustomConfig();
        setDefaults();

        MySQL.connect();
        instance = this;
        register(Bukkit.getPluginManager());
        visiblehandler = new VisibleHandler();
        ScoreboardListener.run();
    }

    @Override
    public void onDisable() {
        MySQL.disconnect();
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
        pluginManager.registerEvents(new Inv(), this);
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
        Inv.setInventory();
        Inv.setBlockTrailInventory();
        BlockTrails.run();
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
        this.customConfig.addDefault("coin.host", "localhost");
        this.customConfig.addDefault("coin.port", "3306");
        this.customConfig.addDefault("coin.database", "coin");
        this.customConfig.addDefault("coin.user", "root");
        this.customConfig.addDefault("coin.password", "password");

        this.customConfig.addDefault("playtime.host", "localhost");
        this.customConfig.addDefault("playtime.port", "3306");
        this.customConfig.addDefault("playtime.database", "playtime");
        this.customConfig.addDefault("playtime.user", "root");
        this.customConfig.addDefault("playtime.password", "password");
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

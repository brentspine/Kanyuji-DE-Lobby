package de.kanyuji.lobby;

import de.brentspine.kanyujiapi.KanyujiAPI;
import de.brentspine.kanyujiapi.mysql.data.MySQLPlaytime;
import de.kanyuji.lobby.commands.DiscordCommand;
import de.kanyuji.lobby.commands.MessageLaterCommand;
import de.kanyuji.lobby.commands.SetupCommand;
import de.kanyuji.lobby.commands.SpawnCommand;
import de.kanyuji.lobby.listeners.*;
import de.kanyuji.lobby.fastboard.FastBoard;
import de.kanyuji.lobby.listeners.cosmetics.BlockTrails;
import de.kanyuji.lobby.listeners.items.Firework;
import de.kanyuji.lobby.listeners.items.Inv;
import de.kanyuji.lobby.listeners.items.Profile;
import de.kanyuji.lobby.utils.MySQLUtil;
import de.kanyuji.lobby.utils.MySQL;
import de.kanyuji.lobby.utils.VisibleHandler;
import org.bukkit.Bukkit;
import org.bukkit.Material;
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
    public MessageLaterCommand messageLaterCommand;

    private File customConfigFile;
    private FileConfiguration customConfig;

    @Override
    public void onEnable() {
        KanyujiAPI.connectAll();
        instance = this;
        visiblehandler = new VisibleHandler();
        messageLaterCommand = new MessageLaterCommand();
        register(Bukkit.getPluginManager());
    }

    @Override
    public void onDisable() {
        //MySQL.disconnect();
    }

    public static Main getInstance() {
        return instance;
    }

    public void register(PluginManager pluginManager) {
        getCommand("setup").setExecutor(new SetupCommand());
        getCommand("spawn").setExecutor(new SpawnCommand());
        getCommand("discord").setExecutor(new DiscordCommand());
        getCommand("messagelater").setExecutor(messageLaterCommand);
        pluginManager.registerEvents(new PlayerConnectionListener(), this);
        pluginManager.registerEvents(new BlockedListeners().run(), this);
        pluginManager.registerEvents(new ScoreboardListener(), this);
        pluginManager.registerEvents(new Inv(), this);
        pluginManager.registerEvents(new HideListener(), this);
        pluginManager.registerEvents(new BlockTrails(), this);
        pluginManager.registerEvents(new Profile(), this);
        pluginManager.registerEvents(new PlayerDoubleJumpListener(), this);
        pluginManager.registerEvents(messageLaterCommand, this);
        getServer().getScheduler().runTaskTimer(this, () -> {
            for (FastBoard board : ScoreboardListener.boards.values()) {
                ScoreboardListener.updateBoard(board);
            }
        }, 0, 20);
        pluginManager.registerEvents(new Firework(), this);
        Firework.setInventory();
        Inv.setInventory();
        BlockTrails.run();

    }

    public VisibleHandler getVisibleHandler() {
        return visiblehandler;
    }
}

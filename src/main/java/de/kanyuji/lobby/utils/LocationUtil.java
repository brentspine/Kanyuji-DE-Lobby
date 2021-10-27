package de.kanyuji.lobby.utils;

import de.kanyuji.lobby.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class LocationUtil {

    private Main instance;
    private String root;
    private Location location;

    public LocationUtil(Main instance, Location location, String root) {
        this.instance = instance;
        this.location = location;
        this.root = root;
    }

    public LocationUtil(Main instance, String root) {
        this(instance, null, root);
    }

    public void saveLocation() {
        File f = new File(instance.getDataFolder().getPath(),"locs.yml");
        YamlConfiguration config = new YamlConfiguration().loadConfiguration(f);
        config.set(root + ".World", location.getWorld().getName());
        config.set(root + ".X", location.getX());
        config.set(root + ".Y", location.getY());
        config.set(root + ".Z", location.getZ());
        config.set(root + ".Yaw", location.getYaw());
        config.set(root + ".Pitch", location.getPitch());
        try {
            config.save(f);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveBlockLocation() {
        File f = new File(instance.getDataFolder().getPath(),"locs.yml");
        YamlConfiguration config = new YamlConfiguration().loadConfiguration(f);
        config.set(root + ".World", location.getWorld().getName());
        config.set(root + ".X", location.getX());
        config.set(root + ".Y", location.getY());
        config.set(root + ".Z", location.getZ());
        try {
            config.save(f);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public Location getLocation() {
        File f = new File(instance.getDataFolder().getPath(), "locs.yml");
        YamlConfiguration config = new YamlConfiguration().loadConfiguration(f);
        if(config.contains(root)) {
            World world = Bukkit.getWorld(config.getString(root + ".World"));
            double x = config.getDouble(root + ".X");
            double y = config.getDouble(root + ".Y");
            double z = config.getDouble(root + ".Z");
            float yaw = (float) config.getDouble(root + ".Yaw");
            float pitch = (float) config.getDouble(root + ".Pitch");
            return new Location(world, x, y, z, yaw, pitch);
        } else
            return null;
    }

    public Location getBlockLocation() {
        File f = new File(instance.getDataFolder().getPath(), "locs.yml");
        YamlConfiguration config = new YamlConfiguration().loadConfiguration(f);
        if(config.contains(root)) {
            World world = Bukkit.getWorld(config.getString(root + ".World"));
            double x = config.getDouble(root + ".X");
            double y = config.getDouble(root + ".Y");
            double z = config.getDouble(root + ".Z");
            return new Location(world, x, y, z);
        } else
            return null;
    }

}

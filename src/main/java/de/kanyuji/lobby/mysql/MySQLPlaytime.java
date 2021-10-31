package de.kanyuji.lobby.mysql;

import de.kanyuji.lobby.Main;
import org.bukkit.Bukkit;

import java.sql.*;
import java.util.UUID;

public class MySQLPlaytime {

    public static String host;
    public static String port;
    public static String database;
    public static String user;
    public static String password;
    public static Connection con;

    public static void connect() {
        if (!isConnected()) {
            try {
                con = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database, user, password);
                Bukkit.getConsoleSender().sendMessage(Main.PREFIX + "Es wurde eine Verbindung mit der Datenbank§8(§c" + database + "§8) §7aufgebaut §8(§c" + host + "§7:§c" + port + "§8)");
            } catch (SQLException var1) {
                Bukkit.getConsoleSender().sendMessage(Main.PREFIX + "Es konnte keine Verbindung mit der Datenbank§8(§c" + database + "§8) §7aufgebaut werden");
            }
        }

    }

    public static void disconnect() {
        if (isConnected()) {
            try {
                con.close();
                Bukkit.getConsoleSender().sendMessage(Main.PREFIX + "Verbundung geschlossen");
            } catch (SQLException var1) {
                var1.printStackTrace();
            }
        }

    }

    public static boolean isConnected() {
        return con != null;
    }

    public static boolean isUserExisting(UUID uuid) {
        //Bukkit.getConsoleSender().sendMessage(Main.PREFIX + "isUserExisting");
        try {
            PreparedStatement ps = MySQL.getConnection().prepareStatement("SELECT minutesPlayed FROM playtime WHERE UUID = ?");
            ps.setString(1, uuid.toString());
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            return false;
        }
    }

    public static boolean createUserIfNeeded(UUID uuid) {
        //Bukkit.getConsoleSender().sendMessage(Main.PREFIX + "createUserIfNeeded");
        boolean isUserExisting = isUserExisting(uuid);
        if(!isUserExisting) {
            try {
                PreparedStatement ps;
                ps = MySQL.getConnection().prepareStatement("INSERT INTO playtime (UUID, minutesPlayed) VALUES (?,?)");
                ps.setString(1, uuid.toString());
                ps.setInt(2, 0);
                ps.executeUpdate();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return isUserExisting;
    }

    public static Integer getMinutesPlayed(UUID uuid) {
        //Bukkit.getConsoleSender().sendMessage(Main.PREFIX + "Get Minutes Played");
        if(!createUserIfNeeded(uuid)) {
            return 0;
        }
        try {
            PreparedStatement ps = MySQL.getConnection().prepareStatement("SELECT minutesPlayed FROM playtime WHERE uuid = ?");
            ps.setString(1, uuid.toString());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("minutesPlayed");
            }
        } catch (SQLException var1) {
            return 0;
        }
        return 0;
    }


    public static String getFormattedTime(UUID uuid) {
        //Bukkit.getConsoleSender().sendMessage(Main.PREFIX + "getFormattedTime");
        Integer minutesPlayed = getMinutesPlayed(uuid);
        Integer hoursPlayed = 0;
        Integer daysPlayed = 0;
        while (minutesPlayed > 59) {
            hoursPlayed++;
            minutesPlayed = minutesPlayed - 60;
        }
        while (hoursPlayed > 23) {
            daysPlayed++;
            hoursPlayed = hoursPlayed - 24;
        }
        if(daysPlayed > 0) {
            return daysPlayed + "days " + hoursPlayed + " hours";
        }
        else if(hoursPlayed > 0) {
            return hoursPlayed + " hours";
        } else
            return minutesPlayed + " minutes";

    }


    public static void addMinutesPlayed(UUID uuid, Integer amount) {
        createUserIfNeeded(uuid);
        int minutesPlayed = getMinutesPlayed(uuid);
        try {
            PreparedStatement ps;
            ps = MySQL.getConnection().prepareStatement("UPDATE playtime SET minutesPlayed = ? WHERE UUID = ?");
            ps.setInt(1, minutesPlayed + amount);
            ps.setString(2, uuid.toString());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static boolean reset(UUID uuid) {
        if(!isUserExisting(uuid)) {
            return true;
        }
        try {
            PreparedStatement ps;
            ps = MySQL.getConnection().prepareStatement("DELETE FROM playtime WHERE UUID = ?");
            ps.setString(1, uuid.toString());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }


    public static UUID getUUIDByName(String name) {
        return Bukkit.getOfflinePlayer(name).getUniqueId();
    }

}

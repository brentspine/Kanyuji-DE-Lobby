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
                Bukkit.getConsoleSender().sendMessage(Main.PREFIX + "Es konnte keine Verbindung mit der Datenbank aufgebaut werden");
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
        try {
            PreparedStatement ps = MySQL.getConnection().prepareStatement("SELECT minutesPlayed FROM playtime WHERE UUID = ?");
            ps.setString(1, uuid.toString());
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            return false;
        }
    }

}

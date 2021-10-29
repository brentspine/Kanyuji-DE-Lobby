package de.kanyuji.lobby.mysql;

import de.kanyuji.lobby.Main;
import org.bukkit.Bukkit;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQL {


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
                var1.printStackTrace();
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

    public static Connection getConnection() {
        return con;
    }
}

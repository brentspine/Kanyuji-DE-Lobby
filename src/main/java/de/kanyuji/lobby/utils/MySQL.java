package de.kanyuji.lobby.utils;

import de.kanyuji.lobby.Main;
import org.bukkit.Bukkit;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQL {

    public static String host = "localhost";
    public static String port = "3306";
    public static String database = "kanyuji";
    public static String username = "root";
    public static String password = "";
    public static Connection connection;

    public static void connect() {
        if(!isConnected()) {
            try {
                connection = DriverManager.getConnection("jdbc://" + host + ":" + port + "/" + database + "?autoReconnect=true", username, password);
            } catch (SQLException e) {
                Bukkit.getConsoleSender().sendMessage(Main.PREFIX + "Die MySQL wurde nicht verbunden!");
            }
        }
    }

    public static void disconnect() {
        if(isConnected()) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static boolean isConnected() {
        return (connection != null);
    }

    public static Connection getConnection() {
        return connection;
    }
}

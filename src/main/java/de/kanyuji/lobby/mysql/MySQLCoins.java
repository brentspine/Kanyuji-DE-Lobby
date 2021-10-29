package de.kanyuji.lobby.mysql;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.UUID;

public class MySQLCoins {

    public static boolean isUserExisting(UUID uuid) {
        try {
            PreparedStatement ps = MySQL.getConnection().prepareStatement("SELECT Coins FROM pixel WHERE UUID = ?");
            ps.setString(1, uuid.toString());
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException var3) {
            return false;
        }
    }

    public static boolean isMaximumReached(UUID uuid) {
        if(isUserExisting(uuid)) {
            if(getPoints(uuid) >= 2000000000) {
                return true;
            }
        }
        return false;
    }

    public static boolean isAbleToBuy(UUID uuid, Integer price) {
        if(getPoints(uuid) >= price) {
            return true;
        }
        return false;
    }

    public static boolean isAbleToReceive(UUID uuid) {
        if(getPoints(uuid) >= 2000000000) {
            return false;
        }
        return true;
    }

    public static void add(UUID uuid, int amount) {
        int coins = getPoints(uuid);
        PreparedStatement ps;
        if (isUserExisting(uuid)) {
            try {
                ps = MySQL.getConnection().prepareStatement("UPDATE pixel SET Coins = ? WHERE UUID = ?");
                ps.setInt(1, coins + amount);
                ps.setString(2, uuid.toString());
                ps.executeUpdate();
            } catch (SQLException var6) {
                var6.printStackTrace();
            }
        } else {
            try {
                ps = MySQL.getConnection().prepareStatement("INSERT INTO pixel (UUID, Coins) VALUES (?,?)");
                ps.setString(1, uuid.toString());
                ps.setInt(2, amount);
                ps.executeUpdate();
            } catch (SQLException var5) {
                var5.printStackTrace();
            }
        }
    }


    public static void remove(UUID uuid, int amount) {
        int coins = getPoints(uuid);
        PreparedStatement ps;
        if (isUserExisting(uuid)) {
            try {
                ps = MySQL.getConnection().prepareStatement("UPDATE pixel SET Coins = ? WHERE UUID = ?");
                ps.setInt(1, coins - amount);
                ps.setString(2, uuid.toString());
                ps.executeUpdate();
            } catch (SQLException var6) {
                var6.printStackTrace();
            }
        } else {
            try {
                ps = MySQL.getConnection().prepareStatement("INSERT INTO pixel (UUID, Coins) VALUES (?,?)");
                ps.setString(1, uuid.toString());
                ps.setInt(2, amount);
                ps.executeUpdate();
            } catch (SQLException var5) {
                var5.printStackTrace();
            }
        }
    }


    public static UUID getUUIDByName(String name) {
        return Bukkit.getOfflinePlayer(name).getUniqueId();
    }


    public static void createUser(UUID uuid) throws SQLException {
        PreparedStatement ps;
        ps = MySQL.getConnection().prepareStatement("INSERT INTO pixel (UUID, Coins) VALUES (?,?)");
        ps.setString(1, uuid.toString());
        ps.setInt(2, 100);
        ps.executeUpdate();
    }


    public static void update(UUID uuid, int amount, boolean remove) {
        int coins = getPoints(uuid);
        PreparedStatement ps;
        if (isUserExisting(uuid)) {
            try {
                ps = MySQL.getConnection().prepareStatement("UPDATE pixel SET Coins = ? WHERE UUID = ?");
                if(remove) {
                    ps.setInt(1, coins - amount);
                } else {
                    ps.setInt(1, coins + amount);
                }
                ps.setString(2, uuid.toString());
                ps.executeUpdate();
            } catch (SQLException var6) {
                var6.printStackTrace();
            }
        } else {
            try {
                ps = MySQL.getConnection().prepareStatement("INSERT INTO pixel (UUID, Coins) VALUES (?,?)");
                ps.setString(1, uuid.toString());
                if(remove) {
                    ps.setInt(1, 0 - amount);
                } else {
                    ps.setInt(1, 0 + amount);
                }
                ps.executeUpdate();
            } catch (SQLException var5) {
                var5.printStackTrace();
            }
        }
    }

    public static void delete(UUID uuid) {
        PreparedStatement ps;
        if (isUserExisting(uuid)) {
            try {
                ps = MySQL.getConnection().prepareStatement("UPDATE pixel SET Wert = ? WHERE UUID = ?");
                ps.setInt(1, 0);
                ps.setString(2, uuid.toString());
                ps.executeUpdate();
            } catch (SQLException var6) {
                var6.printStackTrace();
            }
        } else {
            try {
                createUser(uuid);
            } catch (SQLException var5) {
                var5.printStackTrace();
            }
        }

    }

    public static int getPoints(UUID uuid) {
        try {
            if(!isUserExisting(uuid)) {
                createUser(uuid);
                return 0;
            }
            PreparedStatement ps = MySQL.getConnection().prepareStatement("SELECT Coins FROM pixel WHERE UUID = ?");
            ps.setString(1, uuid.toString());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("Coins");
            }
        } catch (SQLException var3) {
            var3.printStackTrace();
        }

        return -1;
    }

    public static String getFormatedPoints(UUID uuid) {
        NumberFormat numberFormat = NumberFormat.getInstance(new Locale("de"));
        return numberFormat.format(getPoints(uuid));
    }

    public static String getName(UUID uuid) {
        try {
            PreparedStatement ps = MySQL.getConnection().prepareStatement("SELECT Name FROM pixel WHERE UUID = ?");
            ps.setString(1, uuid.toString());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("Name");
            }
        } catch (SQLException var3) {
            var3.printStackTrace();
        }
        return null;
    }

    public static void set(UUID uuid, int amount) {
        delete(uuid);
        update(uuid, amount, false);
    }

}

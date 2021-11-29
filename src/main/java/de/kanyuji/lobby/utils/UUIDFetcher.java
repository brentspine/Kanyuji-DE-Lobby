package de.kanyuji.lobby.utils;

import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mojang.util.UUIDTypeAdapter;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.UUID;

//Code by JavaSyntaxError, edited by Brentspine
public class UUIDFetcher {
    private static Gson gson = (new GsonBuilder()).registerTypeAdapter(UUID.class, new UUIDTypeAdapter()).create();
    private static final String UUID_URL = "https://api.mojang.com/users/profiles/minecraft/%s?at=%d";
    private static final String NAME_URL = "https://api.mojang.com/user/profiles/%s/names";
    private static Map<String, UUID> uuidCache = Maps.newHashMap();
    private static Map<UUID, String> nameCache = Maps.newHashMap();
    private String name;
    private UUID id;

    public UUIDFetcher() {
    }

    public static UUID getUUID(String name) {
        return getUUIDAt(name, System.currentTimeMillis());
    }

    public static UUID getUUIDAt(String name, long timestamp) {
        name = name.toLowerCase();
        if (uuidCache.containsKey(name)) {
            return (UUID)uuidCache.get(name);
        } else {
            try {
                HttpURLConnection connection = (HttpURLConnection)(new URL(String.format("https://api.mojang.com/users/profiles/minecraft/%s?at=%d", name, timestamp / 1000L))).openConnection();
                connection.setReadTimeout(5000);
                UUIDFetcher data = (UUIDFetcher)gson.fromJson(new BufferedReader(new InputStreamReader(connection.getInputStream())), UUIDFetcher.class);
                if (data == null) {
                    connection.disconnect();
                    return null;
                } else {
                    uuidCache.put(name, data.id);
                    nameCache.put(data.id, data.name);
                    connection.disconnect();
                    return data.id;
                }
            } catch (Exception var5) {
                var5.printStackTrace();
                return null;
            }
        }
    }

    public static String getName(UUID uuid) {
        if (nameCache.containsKey(uuid)) {
            return (String)nameCache.get(uuid);
        } else {
            try {
                HttpURLConnection connection = (HttpURLConnection)(new URL(String.format("https://api.mojang.com/user/profiles/%s/names", UUIDTypeAdapter.fromUUID(uuid)))).openConnection();
                connection.setReadTimeout(5000);
                UUIDFetcher[] nameHistory = (UUIDFetcher[])gson.fromJson(new BufferedReader(new InputStreamReader(connection.getInputStream())), UUIDFetcher[].class);
                UUIDFetcher currentNameData = nameHistory[nameHistory.length - 1];
                uuidCache.put(currentNameData.name.toLowerCase(), uuid);
                nameCache.put(uuid, currentNameData.name);
                connection.disconnect();
                return currentNameData.name;
            } catch (Exception var4) {
                var4.printStackTrace();
                return null;
            }
        }
    }

    public static String getName(String uuid) {
        return getName(UUID.fromString(uuid));
    }
}

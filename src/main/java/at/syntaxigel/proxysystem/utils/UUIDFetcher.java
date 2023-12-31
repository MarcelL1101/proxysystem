package at.syntaxigel.proxysystem.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;

public class UUIDFetcher {

	public static final long FEBRUARY_2015 = 1422748800000L;

    private static final Gson gson = (new GsonBuilder()).registerTypeAdapter(UUID.class, new UUIDTypeAdapter()).create();

    private static final String UUID_URL = "https://api.mojang.com/users/profiles/minecraft/%s?at=%d";

    private static final String NAME_URL = "https://sessionserver.mojang.com/session/minecraft/profile/%s";

    private static final Map<String, UUID> uuidCache = new HashMap<>();

    private static final Map<UUID, String> nameCache = new HashMap<>();

    private String name;

    private UUID id;

    public static void getUUID(String name, Consumer<UUID> action) {
       Util.pool.execute(() -> action.accept(getUUID(name)));
    }

    public static UUID getUUID(String name) {
       return getUUIDAt(name, System.currentTimeMillis());
    }

    public static void getUUIDAt(String name, long timestamp, Consumer<UUID> action) {
       Util.pool.execute(() -> action.accept(getUUIDAt(name, timestamp)));
    }

    public static UUID getUUIDAt(String name, long timestamp) {
        name = name.toLowerCase();
        if (uuidCache.containsKey(name))
           return uuidCache.get(name);
        try {
            HttpURLConnection connection = (HttpURLConnection)(new URL(String.format("https://api.mojang.com/users/profiles/minecraft/%s?at=%d", new Object[] { name, Long.valueOf(timestamp / 1000L) }))).openConnection();
            connection.setReadTimeout(5000);
            UUIDFetcher data = (UUIDFetcher)gson.fromJson(new BufferedReader(new InputStreamReader(connection.getInputStream())), UUIDFetcher.class);
            uuidCache.put(name, data.id);
            nameCache.put(data.id, data.name);
            return data.id;
        } catch (Exception exception) {
           return null;
        }
    }

    public static void getName(UUID uuid, Consumer<String> action) {
    Util.pool.execute(() -> action.accept(getName(uuid)));
    }

    public static String getName(UUID uuid) {
        if (nameCache.containsKey(uuid))
           return nameCache.get(uuid);
        try {
            HttpURLConnection connection = (HttpURLConnection)(new URL(String.format("https://sessionserver.mojang.com/session/minecraft/profile/%s", new Object[] { UUIDTypeAdapter.fromUUID(uuid) }))).openConnection();
            connection.setReadTimeout(5000);
            UUIDFetcher data = (UUIDFetcher)gson.fromJson(new BufferedReader(new InputStreamReader(connection.getInputStream())), UUIDFetcher.class);
            uuidCache.put(data.name.toLowerCase(), uuid);
            nameCache.put(uuid, data.name);
            return data.name;
        } catch (Exception exception) {
           return null;
        }
    }
	
}

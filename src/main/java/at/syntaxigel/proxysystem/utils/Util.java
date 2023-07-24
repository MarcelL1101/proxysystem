package at.syntaxigel.proxysystem.utils;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.BaseComponent;

import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

public class Util {

	public static final ExecutorService pool = Executors.newCachedThreadPool();

    public static void broadcastMessage(BaseComponent[] message, String permission) {
        ProxyServer.getInstance().getPlayers().stream().filter(player -> player.hasPermission(permission)).forEach(player -> player.sendMessage(message));
        ProxyServer.getInstance().getConsole().sendMessage(message);
    }

    public static void UUIDtoName(String uuid, Consumer<String> acceptor) {
        if (uuid == null || uuid.equals("")) {
            acceptor.accept(null);
        } else {
            try {
                UUIDFetcher.getName(UUID.fromString(uuid), acceptor);
            } catch (IllegalArgumentException ex) {
                acceptor.accept(uuid);
            }
        }
    }
	
}

package at.syntaxigel.proxysystem.listener;

import at.syntaxigel.proxysystem.ProxySystem;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class ServerListener implements Listener {

    @SuppressWarnings("deprecation")
    @EventHandler
    public void onChatEvent(final ChatEvent event) {
        final ProxiedPlayer player = (ProxiedPlayer) event.getSender();

        if (ProxySystem.getInstance().serverManager.getGlobalmute() == 1) {
            for(final ProxiedPlayer all : ProxySystem.getInstance().getProxy().getPlayers()) {
                if (!all.hasPermission("proxysystem.globalmute.write")) {
                    String messageKey = "globalmute";
                    String localizedMessage = ProxySystem.getInstance().langeLanguageManager.getLocalizedMessage(player.getUniqueId(), messageKey);
                    player.sendMessage(localizedMessage);
                    event.setCancelled(true);
                }
            }
        }
    }

}

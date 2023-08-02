package at.syntaxigel.proxysystem.listener;

import at.syntaxigel.proxysystem.ProxySystem;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ServerPing;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.event.ProxyPingEvent;
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

    @EventHandler
    public void onProxyPingEvent(final ProxyPingEvent event) {
        final ServerPing serverPing = event.getResponse();
        ServerPing.Players players = serverPing.getPlayers();

        if (ProxySystem.getInstance().serverManager.getMaintance() == 1) {
            serverPing.setPlayers(players);
            serverPing.setVersion(new ServerPing.Protocol(ProxySystem.getInstance().configManager.getMaintance(), serverPing.getVersion().getProtocol() - 1));
            serverPing.setDescription(ProxySystem.getInstance().configManager.getMOtdLine1() + "\n" + ProxySystem.getInstance().configManager.getMOtdLine2());
        } else {
            serverPing.setDescription(ProxySystem.getInstance().configManager.getMOtdLine1() + "\n" + ProxySystem.getInstance().configManager.getMOtdLine2());
        }
    }

    @EventHandler
    public void onChat(ChatEvent event) {
        if (!(event.getSender() instanceof ProxiedPlayer)) {
            return;
        }

        String originalMessage = event.getMessage();
        String coloredMessage = ChatColor.translateAlternateColorCodes('&', originalMessage);

        event.setMessage(coloredMessage);
    }

}

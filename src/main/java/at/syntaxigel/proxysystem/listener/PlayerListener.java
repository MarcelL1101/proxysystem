package at.syntaxigel.proxysystem.listener;

import at.syntaxigel.proxysystem.ProxySystem;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ServerConnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class PlayerListener implements Listener {
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onServerConnectEvent(final ServerConnectEvent event) {
		final ProxiedPlayer player = event.getPlayer();
		
		if (!ProxySystem.getInstance().playerManager.existsPlayer(player.getUniqueId())) {
			ProxySystem.getInstance().playerManager.createPlayer(player.getUniqueId());
		}

		if (ProxySystem.getInstance().serverManager.getMaintance() == 1) {
			if (!ProxySystem.getInstance().teamManager.isInTeam(player.getUniqueId())) {
				player.disconnect(ProxySystem.getInstance().configManager.getMaintanceMessageForPlayer());
			}
		}
	}

}

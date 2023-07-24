package at.syntaxigel.proxysystem.commands;

import at.syntaxigel.proxysystem.ProxySystem;
import at.syntaxigel.proxysystem.listener.PlayerListener;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class PingCommand extends Command {

	public PingCommand(String name) {
		super(name);
	}

	@SuppressWarnings("deprecation")
	@Override
	public void execute(CommandSender sender, String[] args) {
		if (!(sender instanceof ProxiedPlayer)) {
			sender.sendMessage(ProxySystem.getInstance().configManager.getMessageNoPlayer());
			return;
		}
		
		final ProxiedPlayer player = (ProxiedPlayer) sender;
		
		if (args.length == 0) {
			String messageKey = "pingCommand";
            String localizedMessage = ProxySystem.getInstance().langeLanguageManager.getLocalizedMessage(player.getUniqueId(), messageKey);
            player.sendMessage(localizedMessage);
		} else if (args.length == 1) {
			final String name = args[0];
			final ProxiedPlayer target = ProxySystem.getInstance().getProxy().getPlayer(name);

			if (target == null) {
				String messageKey = "noOnline";
				String localizedMessagePlayerArgument = ProxySystem.getInstance().langeLanguageManager.getLocalizedMessagePlayerArgument(player.getUniqueId(), messageKey, name);
				player.sendMessage(localizedMessagePlayerArgument);
				return;
			}

			String messageKey = "pingCommandTarget";
			String localizedMessagePlayerArgument = ProxySystem.getInstance().langeLanguageManager.getLocalizedMessagePlayerArgument(player.getUniqueId(), messageKey, name);
			player.sendMessage(localizedMessagePlayerArgument);
		} else {
			String messageKey = "onUseMoreArguments";
			String localizedMessageOnUseArguements = ProxySystem.getInstance().langeLanguageManager.getLocalizedMessageOnUseArguments(player.getUniqueId(), messageKey, "ping", "name");
			player.sendMessage(localizedMessageOnUseArguements);
		}
	}

}

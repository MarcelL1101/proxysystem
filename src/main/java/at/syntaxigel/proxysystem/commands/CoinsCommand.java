package at.syntaxigel.proxysystem.commands;

import at.syntaxigel.proxysystem.ProxySystem;
import at.syntaxigel.proxysystem.utils.UUIDFetcher;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class CoinsCommand extends Command {

    public CoinsCommand(String name) {
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
            String messageKey = "coins";
            String localizedMessage = ProxySystem.getInstance().langeLanguageManager.getLocalizedMessage(player.getUniqueId(), messageKey);
            player.sendMessage(localizedMessage);
        } else if (args.length == 1) {
            final String name = args[0];
            final ProxiedPlayer target = ProxySystem.getInstance().getProxy().getPlayer(name);

            if (!ProxySystem.getInstance().playerManager.existsPlayer(UUIDFetcher.getUUID(name))) {
                String messageKey = "neverPlayed";
                String localizedMessage = ProxySystem.getInstance().langeLanguageManager.getLocalizedMessage(player.getUniqueId(), messageKey);
                player.sendMessage(localizedMessage);
                return;
            }

            if (target == null) {
                String messageKey = "noOnline";
                String localizedMessagePlayerArgument = ProxySystem.getInstance().langeLanguageManager.getLocalizedMessagePlayerArgument(player.getUniqueId(), messageKey, name);
                player.sendMessage(localizedMessagePlayerArgument);
                return;
            }

            if (name.equalsIgnoreCase(player.getName())) {
                String messageKey = "coins";
                String localizedMessage = ProxySystem.getInstance().langeLanguageManager.getLocalizedMessage(player.getUniqueId(), messageKey);
                player.sendMessage(localizedMessage);
            }

            String messageKey = "coinsTarget";
            String localizedMessage = ProxySystem.getInstance().langeLanguageManager.getLocalizedMessageTarget(player.getUniqueId(), messageKey, name);
            player.sendMessage(localizedMessage);
        } else {
            String messageKey = "onUseMoreArguments";
            String localizedMessageOnUseArguements = ProxySystem.getInstance().langeLanguageManager.getLocalizedMessageOnUseArguments(player.getUniqueId(), messageKey, "coins", "name");
            player.sendMessage(localizedMessageOnUseArguements);
        }
    }

}

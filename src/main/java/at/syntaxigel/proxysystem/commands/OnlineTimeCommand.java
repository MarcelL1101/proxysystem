package at.syntaxigel.proxysystem.commands;

import at.syntaxigel.proxysystem.ProxySystem;
import at.syntaxigel.proxysystem.utils.UUIDFetcher;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class OnlineTimeCommand extends Command {

    public OnlineTimeCommand(String name) {
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
            String messageKey = "onlineTime";
            String localizedMessagePlayerArgument = ProxySystem.getInstance().langeLanguageManager.getLocalizedMessage(player.getUniqueId(), messageKey);
            player.sendMessage(localizedMessagePlayerArgument);
        } else if (args.length == 1) {
            final String name = args[0];

            if (!ProxySystem.getInstance().playerManager.existsPlayer(UUIDFetcher.getUUID(name))) {
                String messageKey = "neverPlayed";
                String localizedMessage = ProxySystem.getInstance().langeLanguageManager.getLocalizedMessageTarget(player.getUniqueId(), messageKey, name);
                player.sendMessage(localizedMessage);
                return;
            }

            String messageKey = "onlineTimeTarget";
            String localizedMessagePlayerArgument = ProxySystem.getInstance().langeLanguageManager.getLocalizedMessageTarget(player.getUniqueId(), messageKey, name);
            player.sendMessage(localizedMessagePlayerArgument);
        } else {
            String messageKey = "onUse";
            String localizedMessage = ProxySystem.getInstance().langeLanguageManager.getLocalizedMessageOnUseArguments(player.getUniqueId(), messageKey, "onlinetime", "name");
            player.sendMessage(localizedMessage);
        }
    }

}

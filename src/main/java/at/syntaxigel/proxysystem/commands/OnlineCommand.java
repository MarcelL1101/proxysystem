package at.syntaxigel.proxysystem.commands;

import at.syntaxigel.proxysystem.ProxySystem;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class OnlineCommand extends Command {

    public OnlineCommand(String name) {
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
            if (ProxySystem.getInstance().getProxy().getOnlineCount() == 1) {
                String messageKey = "onlineCountOnePlayer";
                String localizedMessage = ProxySystem.getInstance().langeLanguageManager.getLocalizedMessage(player.getUniqueId(), messageKey);
                player.sendMessage(localizedMessage);
                return;
            }

            if (ProxySystem.getInstance().getProxy().getOnlineCount() >= 2) {
                String messageKey = "onlineCountMoreThenOnePlayer";
                String localizedMessage = ProxySystem.getInstance().langeLanguageManager.getLocalizedMessage(player.getUniqueId(), messageKey);
                player.sendMessage(localizedMessage);
                return;
            }
        } else {
            String messageKey = "onUse";
            String localizedMessageOnUseArguements = ProxySystem.getInstance().langeLanguageManager.getLocalizedMessageOnUse(player.getUniqueId(), messageKey, "online");
            player.sendMessage(localizedMessageOnUseArguements);
        }
    }

}

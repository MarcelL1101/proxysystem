package at.syntaxigel.proxysystem.commands;

import at.syntaxigel.proxysystem.ProxySystem;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class GetIPCommand extends Command {

    public GetIPCommand(String name) {
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

        if (!player.hasPermission("proxysystem.getip")) {
            String messageKey = "noPerm";
            String localizedMessage = ProxySystem.getInstance().langeLanguageManager.getLocalizedMessage(player.getUniqueId(), messageKey);
            player.sendMessage(localizedMessage);
            return;
        }

        if (!ProxySystem.getInstance().teamManager.isInTeam(player.getUniqueId())) {
            ProxySystem.getInstance().teamManager.addToTeam(player.getUniqueId());
            return;
        }

        if (args.length == 1) {
            final String name = args[0];
            final ProxiedPlayer target = ProxySystem.getInstance().getProxy().getPlayer(name);

            if (target == null) {
                String messageKey = "noOnline";
                String localizedMessagePlayerArgument = ProxySystem.getInstance().langeLanguageManager.getLocalizedMessagePlayerArgument(player.getUniqueId(), messageKey, name);
                player.sendMessage(localizedMessagePlayerArgument);
                return;
            }

            if (name.equalsIgnoreCase(player.getName())) {
                String messageKey = "getip";
                String localizedMessage = ProxySystem.getInstance().langeLanguageManager.getLocalizedMessageTarget(player.getUniqueId(), messageKey, player.getName());
                player.sendMessage(localizedMessage);
                return;
            }

            String messageKey = "getip";
            String localizedMessage = ProxySystem.getInstance().langeLanguageManager.getLocalizedMessageTarget(player.getUniqueId(), messageKey, player.getName());
            player.sendMessage(localizedMessage);
        } else {
            String messageKey = "onUseMoreArguments";
            String localizedMessageOnUseArguements = ProxySystem.getInstance().langeLanguageManager.getLocalizedMessageOnUseArguments(player.getUniqueId(), messageKey, "getip", "name");
            player.sendMessage(localizedMessageOnUseArguements);
        }
    }

}

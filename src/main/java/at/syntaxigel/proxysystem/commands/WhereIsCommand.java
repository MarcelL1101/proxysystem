package at.syntaxigel.proxysystem.commands;

import at.syntaxigel.proxysystem.ProxySystem;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class WhereIsCommand extends Command {

    public WhereIsCommand(String name) {
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

        if (!player.hasPermission("proxysystem.whereis")) {
            String messageKey = "noPerm";
            String localizedMessage = ProxySystem.getInstance().langeLanguageManager.getLocalizedMessage(player.getUniqueId(), messageKey);
            player.sendMessage(localizedMessage);
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

            String messageKey = "whereis";
            String localizedMessage = ProxySystem.getInstance().langeLanguageManager.getLocalizedMessageTarget(player.getUniqueId(), messageKey, target);
            player.sendMessage(localizedMessage);
        } else {
            String messageKey = "onUse";
            String localizedMessage = ProxySystem.getInstance().langeLanguageManager.getLocalizedMessageOnUse(player.getUniqueId(), messageKey, "whereis");
            player.sendMessage(localizedMessage);
        }
    }

}

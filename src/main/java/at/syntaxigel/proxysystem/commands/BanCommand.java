package at.syntaxigel.proxysystem.commands;

import at.syntaxigel.proxysystem.ProxySystem;
import at.syntaxigel.proxysystem.utils.UUIDFetcher;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class BanCommand extends Command {

    public BanCommand(String name) {
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

        if (!player.hasPermission("proxysystem.ban")) {
            String messageKey = "noPerm";
            String localizedMessage = ProxySystem.getInstance().langeLanguageManager.getLocalizedMessage(player.getUniqueId(), messageKey);
            player.sendMessage(localizedMessage);
            return;
        }

        if (args.length == 2) {
            final String name = args[1];
            if (args[0].equalsIgnoreCase("1")) {
                ProxySystem.getInstance().banManager.ban(UUIDFetcher.getUUID(name), name, "Unerlaubt Clientmodifikationen", 2592000, player.getUniqueId(), player.getName());
            }
        }
    }


}

package at.syntaxigel.proxysystem.commands;

import at.syntaxigel.proxysystem.ProxySystem;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class DiscordCommand extends Command {

    public DiscordCommand(String name) {
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
            String messageKey = "discord";
            String localizedMessage = ProxySystem.getInstance().langeLanguageManager.getLocalizedMessage(player.getUniqueId(), messageKey);
            player.sendMessage(localizedMessage);
        } else {
            String messageKey = "onUse";
            String localizedMessage = ProxySystem.getInstance().langeLanguageManager.getLocalizedMessageOnUse(player.getUniqueId(), messageKey, "discord");
            player.sendMessage(localizedMessage);
        }
    }

}

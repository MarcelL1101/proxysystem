package at.syntaxigel.proxysystem.commands;

import at.syntaxigel.proxysystem.ProxySystem;
import at.syntaxigel.proxysystem.utils.UUIDFetcher;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class ReportCommand extends Command {

    public ReportCommand(String name) {
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

        if (args.length >= 1) {
            final String name = args[0];

            String message = "";
            for (int i = 1; i < args.length; i++) {
                message = String.valueOf(message) + args[i] + " ";
            }

            ProxySystem.getInstance().reportManager.createReport(UUIDFetcher.getUUID(name), player.getUniqueId(), message, player.getServer().getInfo().getName());
            String messageKey = "report";
            String localizedMessage = ProxySystem.getInstance().langeLanguageManager.getLocalizedMessageCreateReport(player.getUniqueId(), messageKey, name, message);
            player.sendMessage(localizedMessage);
        } else {
            String messageKey = "onUseMoreArguments";
            String localizedMessage = ProxySystem.getInstance().langeLanguageManager.getLocalizedMessageOnUseArguments(player.getUniqueId(), messageKey, "report", "name, reason");
            player.sendMessage(localizedMessage);
        }
    }

}

package at.syntaxigel.proxysystem.commands;

import at.syntaxigel.proxysystem.ProxySystem;
import at.syntaxigel.proxysystem.utils.UUIDFetcher;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class UnbanCommand extends Command {

    public UnbanCommand(String name) {
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

        if (!player.hasPermission("proxysystem.unban")) {
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

            if (!ProxySystem.getInstance().banManager.isBanned(UUIDFetcher.getUUID(name))) {
                String messageKey = "isNotBanned";
                String localizedMessage = ProxySystem.getInstance().langeLanguageManager.getLocalizedMessageTarget(player.getUniqueId(), messageKey, name);
                player.sendMessage(localizedMessage);
                return;
            }

            ProxySystem.getInstance().banManager.unBan(UUIDFetcher.getUUID(name));
            for(final ProxiedPlayer team : ProxySystem.getInstance().getProxy().getPlayers()) {
                if (team.hasPermission("proxysystem.unban.see")) {
                    String messageKey = "unban";
                    String localizedMessage = ProxySystem.getInstance().langeLanguageManager.getLocalizedMessageTarget(player.getUniqueId(), messageKey, name);
                    team.sendMessage(localizedMessage);
                }
            }
        } else {
            String messageKey = "onUseMoreArguments";
            String localizedMessage = ProxySystem.getInstance().langeLanguageManager.getLocalizedMessageOnUseArguments(player.getUniqueId(), messageKey, "unban", "name");
            player.sendMessage(localizedMessage);
        }
    }

}

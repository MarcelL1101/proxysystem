package at.syntaxigel.proxysystem.commands;

import at.syntaxigel.proxysystem.ProxySystem;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class TeamChatCommand extends Command {

    public TeamChatCommand(String name) {
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

        if (!player.hasPermission("proxysystem.teamchat")) {
            String messageKey = "noPerm";
            String localizedMessage = ProxySystem.getInstance().langeLanguageManager.getLocalizedMessage(player.getUniqueId(), messageKey);
            player.sendMessage(localizedMessage);
            return;
        }

        if (!ProxySystem.getInstance().teamManager.isInTeam(player.getUniqueId())) {
            ProxySystem.getInstance().teamManager.addToTeam(player.getUniqueId());
            return;
        }

        if (args.length >= 1) {
            String message = "";
            for (int i = 0; i < args.length; i++) {
                message = String.valueOf(message) + args[i] + " ";
            }

            for(final ProxiedPlayer team : ProxySystem.getInstance().getProxy().getPlayers()) {
                if (team.hasPermission("proxysystem.teamchat")) {
                    team.sendMessage(ProxySystem.getInstance().configManager.getMessageTeamChat(player.getName(), message));
                }
            }
        } else {
            String messageKey = "onUse";
            String localizedMessage = ProxySystem.getInstance().langeLanguageManager.getLocalizedMessageOnUseArguments(player.getUniqueId(), messageKey, "teamchat", "Message");
            player.sendMessage(localizedMessage);
        }
    }

}

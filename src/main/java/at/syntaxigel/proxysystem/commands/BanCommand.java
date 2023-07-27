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

            if (ProxySystem.getInstance().banManager.isBanned(UUIDFetcher.getUUID(name))) {
                String messageKey = "alreadyBanned";
                String localizedMessage = ProxySystem.getInstance().langeLanguageManager.getLocalizedMessageTarget(player.getUniqueId(), messageKey, name);
                player.sendMessage(localizedMessage);
                return;
            }

            if (args[0].equalsIgnoreCase("1")) {
                ProxySystem.getInstance().banManager.ban(UUIDFetcher.getUUID(name), "Unerlaubt Clientmodifikationen", 2592000, player.getUniqueId());

                for(final ProxiedPlayer team : ProxySystem.getInstance().getProxy().getPlayers()) {
                    if (team.hasPermission("proxysystem.bansee")) {
                        String messageKeyBanMessage = "banTeamMessage";
                        String localizedMessageBanMessage = ProxySystem.getInstance().langeLanguageManager.getLocalizedMessageTarget(player.getUniqueId(), messageKeyBanMessage, name);
                        String messageKeyBanID = "banID";
                        String localizedMessageBanID = ProxySystem.getInstance().langeLanguageManager.getLocalizedMessageBanMute(player.getUniqueId(), messageKeyBanID, UUIDFetcher.getUUID(name));
                        String messageKeyBanReason = "banReason";
                        String localizedMessageBanReason = ProxySystem.getInstance().langeLanguageManager.getLocalizedMessageBanMute(player.getUniqueId(), messageKeyBanReason, UUIDFetcher.getUUID(name));
                        String messageKeyBannedBy = "banBannedby";
                        String localizedMessageBannedBy = ProxySystem.getInstance().langeLanguageManager.getLocalizedMessageBanMute(player.getUniqueId(), messageKeyBannedBy, UUIDFetcher.getUUID(name));
                        String messageKeyBanDuration = "banDuration";
                        String localizedMessageBanDuration = ProxySystem.getInstance().langeLanguageManager.getLocalizedMessageBanMute(player.getUniqueId(), messageKeyBanDuration, UUIDFetcher.getUUID(name));
                        player.sendMessage("");
                        player.sendMessage(localizedMessageBanMessage);
                        player.sendMessage("");
                        player.sendMessage(localizedMessageBanID);
                        player.sendMessage(localizedMessageBanReason);
                        player.sendMessage(localizedMessageBannedBy);
                        player.sendMessage(localizedMessageBanDuration);
                        player.sendMessage("");
                    }
                }
            }
        }
    }


}

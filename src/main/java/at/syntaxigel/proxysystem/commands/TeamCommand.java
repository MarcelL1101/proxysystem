package at.syntaxigel.proxysystem.commands;

import at.syntaxigel.proxysystem.ProxySystem;
import at.syntaxigel.proxysystem.utils.UUIDFetcher;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

import java.util.List;
import java.util.UUID;

public class TeamCommand extends Command {

    public TeamCommand(String name) {
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

        if (!player.hasPermission("proxysystem.team")) {
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
            if (args[0].equalsIgnoreCase("list")) {
                sendTeamList(player, 1);
            } else {
                String messageKey = "onUse";
                String localizedMessage = ProxySystem.getInstance().langeLanguageManager.getLocalizedMessageOnUseArguments(player.getUniqueId(), messageKey, "team", "list");
                player.sendMessage(localizedMessage);
                return;
            }
        } else if (args.length == 2) {
            if (args[0].equalsIgnoreCase("list")) {
                sendTeamList(player, Integer.parseInt(args[1]));
            } else if (args[0].equalsIgnoreCase("add")) {
                final String name = args[1];

                if (!ProxySystem.getInstance().playerManager.existsPlayer(UUIDFetcher.getUUID(name))) {
                    String messageKey = "neverPlayed";
                    String localizedMessage = ProxySystem.getInstance().langeLanguageManager.getLocalizedMessage(player.getUniqueId(), messageKey);
                    player.sendMessage(localizedMessage);
                    return;
                }

                if (ProxySystem.getInstance().teamManager.isInTeam(UUIDFetcher.getUUID(name))) {
                    String messageKey = "teamAlready";
                    String localizedMessage = ProxySystem.getInstance().langeLanguageManager.getLocalizedMessageTarget(player.getUniqueId(), messageKey, name);
                    player.sendMessage(localizedMessage);
                    return;
                }

                ProxySystem.getInstance().teamManager.addToTeam(UUIDFetcher.getUUID(name));
                String messageKey = "teamAdd";
                String localizedMessage = ProxySystem.getInstance().langeLanguageManager.getLocalizedMessageTarget(player.getUniqueId(), messageKey, name);
                player.sendMessage(localizedMessage);
            } else if (args[0].equalsIgnoreCase("remove")) {
                final String name = args[1];

                if (!ProxySystem.getInstance().teamManager.isInTeam(UUIDFetcher.getUUID(name))) {
                    String messageKey = "teamIsNot";
                    String localizedMessage = ProxySystem.getInstance().langeLanguageManager.getLocalizedMessageTarget(player.getUniqueId(), messageKey, name);
                    player.sendMessage(localizedMessage);
                    return;
                }

                ProxySystem.getInstance().teamManager.removeFromTeam(UUIDFetcher.getUUID(name));
                String messageKey = "teamRemove";
                String localizedMessage = ProxySystem.getInstance().langeLanguageManager.getLocalizedMessageTarget(player.getUniqueId(), messageKey, name);
                player.sendMessage(localizedMessage);
            } else {
                String messageKey = "onUseMoreArguments";
                String localizedMessage = ProxySystem.getInstance().langeLanguageManager.getLocalizedMessageOnUseArguments(player.getUniqueId(), messageKey, "team", "add, remove");
                player.sendMessage(localizedMessage);
            }
        } else {
            String messageKey = "onUseMoreArguments";
            String localizedMessage = ProxySystem.getInstance().langeLanguageManager.getLocalizedMessageOnUseArguments(player.getUniqueId(), messageKey, "team", "add, remove, list");
            player.sendMessage(localizedMessage);
        }

    }

    @SuppressWarnings("deprecation")
    public void sendTeamList(ProxiedPlayer player, int page) {
        int playersPerPage = 5;
        List<String> teamUUIDs = ProxySystem.getInstance().teamManager.getTeamUUIDs();
        int totalPages = (int) Math.ceil((double) teamUUIDs.size() / playersPerPage);

        if (page < 1 || page > totalPages) {
            String messageKey = "invalidPage";
            String localizedMessage = ProxySystem.getInstance().langeLanguageManager.getLocalizedMessage(player.getUniqueId(), messageKey);
            player.sendMessage(localizedMessage);
            return;
        }

        int startIndex = (page - 1) * playersPerPage;
        int endIndex = Math.min(startIndex + playersPerPage, teamUUIDs.size());

        String messageKey = "team";
        String localizedMessage = ProxySystem.getInstance().langeLanguageManager.getLocalizedMessage(player.getUniqueId(), messageKey);
        player.sendMessage(localizedMessage);

        player.sendMessage(" ");

        for (int i = startIndex; i < endIndex; i++) {
            String team = teamUUIDs.get(i);
            String name = UUIDFetcher.getName(UUID.fromString(team));

            String messageKeyBan = "banCount";
            String localizedMessageBan = ProxySystem.getInstance().langeLanguageManager.getLocalizedMessageTarget(player.getUniqueId(), messageKeyBan, name);
            String messageKeyMute = "muteCount";
            String localizedMessageMute = ProxySystem.getInstance().langeLanguageManager.getLocalizedMessageTarget(player.getUniqueId(), messageKeyMute, name);
            String messageKeyReport = "reportCount";
            String localizedMessageReport = ProxySystem.getInstance().langeLanguageManager.getLocalizedMessageTarget(player.getUniqueId(), messageKeyReport, name);

            TextComponent textComponent = new TextComponent(ProxySystem.getInstance().configManager.getTeamlist(name));
            textComponent.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(localizedMessageBan + "\n" + localizedMessageMute + "\n" + localizedMessageReport).create()));
            player.sendMessage(textComponent);
        }

        player.sendMessage(" ");

        if (page < totalPages) {
            String messageKeyNext = "next";
            String localizedMessageNext = ProxySystem.getInstance().langeLanguageManager.getLocalizedMessage(player.getUniqueId(), messageKeyNext);
            TextComponent nextPageComponent = new TextComponent(localizedMessageNext);
            nextPageComponent.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/team list " + (page + 1)));
            player.sendMessage(nextPageComponent);
            player.sendMessage(" ");
        }
    }

}

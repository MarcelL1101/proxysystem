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

public class ReportEditCommand extends Command {

    public ReportEditCommand(String name) {
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

        if (!player.hasPermission("proxysystem.report.see")) {
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
                sendReportList(player, 1);
            }
        } else if (args.length == 2) {
            if (args[0].equalsIgnoreCase("list")) {
                sendReportList(player, Integer.parseInt(args[1]));
            }
        }
    }

    @SuppressWarnings("deprecation")
    public void sendReportList(ProxiedPlayer player, int page) {
        int playersPerPage = 5;
        List<String> reportUUIDs = ProxySystem.getInstance().reportManager.getReportedUUIDs();
        int totalPages = (int) Math.ceil((double) reportUUIDs.size() / playersPerPage);

        if (page < 1 || page > totalPages) {
            String messageKey = "invalidPage";
            String localizedMessage = ProxySystem.getInstance().langeLanguageManager.getLocalizedMessage(player.getUniqueId(), messageKey);
            player.sendMessage(localizedMessage);
            return;
        }

        int startIndex = (page - 1) * playersPerPage;
        int endIndex = Math.min(startIndex + playersPerPage, reportUUIDs.size());

        String messageKey = "openReports";
        String localizedMessage = ProxySystem.getInstance().langeLanguageManager.getLocalizedMessage(player.getUniqueId(), messageKey);
        player.sendMessage(localizedMessage);

        player.sendMessage(" ");

        for (int i = startIndex; i < endIndex; i++) {
            String report = reportUUIDs.get(i);
            String name = UUIDFetcher.getName(UUID.fromString(report));

            String messageKeyReportID = "reportID";
            String localizedMessageReportID = ProxySystem.getInstance().langeLanguageManager.getLocalizedMessageReport(player.getUniqueId(), messageKeyReportID, name);
            String messageKeyReported = "reported";
            String localizedMessageReported = ProxySystem.getInstance().langeLanguageManager.getLocalizedMessageReport(player.getUniqueId(), messageKeyReported, name);
            String messageKeyReporter = "reporter";
            String localizedMessageReporter = ProxySystem.getInstance().langeLanguageManager.getLocalizedMessageReport(player.getUniqueId(), messageKeyReporter, name);
            String messageKeyReportReason = "reportReason";
            String localizedMessageReportReason = ProxySystem.getInstance().langeLanguageManager.getLocalizedMessageReport(player.getUniqueId(), messageKeyReportReason, name);
            String messageKeyReportServer = "reportServer";
            String localizedMessageReportServer = ProxySystem.getInstance().langeLanguageManager.getLocalizedMessageReport(player.getUniqueId(), messageKeyReportServer, name);

            TextComponent textComponent = new TextComponent(ProxySystem.getInstance().configManager.getReportList(name));
            textComponent.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(localizedMessageReportID + "\n" + localizedMessageReported + "\n" + localizedMessageReporter + "\n" + localizedMessageReportReason + "\n" + localizedMessageReportServer).create()));
            player.sendMessage(textComponent);
        }

        player.sendMessage(" ");

        if (page < totalPages) {
            String messageKeyNext = "next";
            String localizedMessageNext = ProxySystem.getInstance().langeLanguageManager.getLocalizedMessage(player.getUniqueId(), messageKeyNext);
            TextComponent nextPageComponent = new TextComponent(localizedMessageNext);
            nextPageComponent.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/report list " + (page + 1)));
            player.sendMessage(nextPageComponent);
            player.sendMessage(" ");
        }
    }
}

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
                String banReason = "banReason1";
                String messageBanReason = ProxySystem.getInstance().langeLanguageManager.getLocalizedMessage(player.getUniqueId(), banReason);
                ProxySystem.getInstance().banManager.ban(UUIDFetcher.getUUID(name), messageBanReason, 2592000, player.getUniqueId());

                for(final ProxiedPlayer team : ProxySystem.getInstance().getProxy().getPlayers()) {
                    if (team.hasPermission("proxysystem.ban.see")) {
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
                        team.sendMessage("");
                        team.sendMessage(localizedMessageBanMessage);
                        team.sendMessage("");
                        team.sendMessage(localizedMessageBanID);
                        team.sendMessage(localizedMessageBanReason);
                        team.sendMessage(localizedMessageBannedBy);
                        team.sendMessage(localizedMessageBanDuration);
                        team.sendMessage("");
                    }
                }
            } else if (args[0].equalsIgnoreCase("2")) {
                String banReason = "banReason2";
                String messageBanReason = ProxySystem.getInstance().langeLanguageManager.getLocalizedMessage(player.getUniqueId(), banReason);
                ProxySystem.getInstance().banManager.ban(UUIDFetcher.getUUID(name), messageBanReason, 259200, player.getUniqueId());

                for(final ProxiedPlayer team : ProxySystem.getInstance().getProxy().getPlayers()) {
                    if (team.hasPermission("proxysystem.ban.see")) {
                        String messageKeyBanMessage = "banTeamMessage";
                        String localizedMessageBanMessage = ProxySystem.getInstance().langeLanguageManager.getLocalizedMessageTarget(player.getUniqueId(), messageKeyBanMessage, name);
                        String messageKeyBanID = "banID";
                        String localizedMessageBanID = ProxySystem.getInstance().langeLanguageManager.getLocalizedMessageBanMute(player.getUniqueId(), messageKeyBanID, UUIDFetcher.getUUID(name));
                        String messageKeyBanReason = "banReason";
                        String localizedMessageBanReason = ProxySystem.getInstance().langeLanguageManager.getLocalizedMessageBanMute(player.getUniqueId(), messageKeyBanReason, UUIDFetcher.getUUID(name));
                        String messageKeyBannedBy = "banBannedby";
                        String localizedMessageBannedBy = ProxySystem.getInstance().langeLanguageManager.getLocalizedMessage(player.getUniqueId(), messageKeyBannedBy);
                        String messageKeyBanDuration = "banDuration";
                        String localizedMessageBanDuration = ProxySystem.getInstance().langeLanguageManager.getLocalizedMessageBanMute(player.getUniqueId(), messageKeyBanDuration, UUIDFetcher.getUUID(name));
                        team.sendMessage("");
                        team.sendMessage(localizedMessageBanMessage);
                        team.sendMessage("");
                        team.sendMessage(localizedMessageBanID);
                        team.sendMessage(localizedMessageBanReason);
                        team.sendMessage("&8» &7Gebannt von &8|&3 " + player.getName());
                        team.sendMessage(localizedMessageBanDuration);
                        team.sendMessage("");
                    }
                }
            } else if (args[0].equalsIgnoreCase("3")) {
                String banReason = "banReason3";
                String messageBanReason = ProxySystem.getInstance().langeLanguageManager.getLocalizedMessage(player.getUniqueId(), banReason);
                ProxySystem.getInstance().banManager.ban(UUIDFetcher.getUUID(name), messageBanReason, 36000, player.getUniqueId());

                for(final ProxiedPlayer team : ProxySystem.getInstance().getProxy().getPlayers()) {
                    if (team.hasPermission("proxysystem.ban.see")) {
                        String messageKeyBanMessage = "banTeamMessage";
                        String localizedMessageBanMessage = ProxySystem.getInstance().langeLanguageManager.getLocalizedMessageTarget(player.getUniqueId(), messageKeyBanMessage, name);
                        String messageKeyBanID = "banID";
                        String localizedMessageBanID = ProxySystem.getInstance().langeLanguageManager.getLocalizedMessageBanMute(player.getUniqueId(), messageKeyBanID, UUIDFetcher.getUUID(name));
                        String messageKeyBanReason = "banReason";
                        String localizedMessageBanReason = ProxySystem.getInstance().langeLanguageManager.getLocalizedMessageBanMute(player.getUniqueId(), messageKeyBanReason, UUIDFetcher.getUUID(name));
                        String messageKeyBannedBy = "banBannedby";
                        String localizedMessageBannedBy = ProxySystem.getInstance().langeLanguageManager.getLocalizedMessage(player.getUniqueId(), messageKeyBannedBy);
                        String messageKeyBanDuration = "banDuration";
                        String localizedMessageBanDuration = ProxySystem.getInstance().langeLanguageManager.getLocalizedMessageBanMute(player.getUniqueId(), messageKeyBanDuration, UUIDFetcher.getUUID(name));
                        team.sendMessage("");
                        team.sendMessage(localizedMessageBanMessage);
                        team.sendMessage("");
                        team.sendMessage(localizedMessageBanID);
                        team.sendMessage(localizedMessageBanReason);
                        team.sendMessage("&8» &7Gebannt von &8|&3 " + player.getName());
                        team.sendMessage(localizedMessageBanDuration);
                        team.sendMessage("");
                    }
                }
            } else if (args[0].equalsIgnoreCase("4")) {
                String banReason = "banReason4";
                String messageBanReason = ProxySystem.getInstance().langeLanguageManager.getLocalizedMessage(player.getUniqueId(), banReason);

                ProxySystem.getInstance().banManager.ban(UUIDFetcher.getUUID(name), messageBanReason, 2592000, player.getUniqueId());

                for(final ProxiedPlayer team : ProxySystem.getInstance().getProxy().getPlayers()) {
                    if (team.hasPermission("proxysystem.ban.see")) {
                        String messageKeyBanMessage = "banTeamMessage";
                        String localizedMessageBanMessage = ProxySystem.getInstance().langeLanguageManager.getLocalizedMessageTarget(player.getUniqueId(), messageKeyBanMessage, name);
                        String messageKeyBanID = "banID";
                        String localizedMessageBanID = ProxySystem.getInstance().langeLanguageManager.getLocalizedMessageBanMute(player.getUniqueId(), messageKeyBanID, UUIDFetcher.getUUID(name));
                        String messageKeyBanReason = "banReason";
                        String localizedMessageBanReason = ProxySystem.getInstance().langeLanguageManager.getLocalizedMessageBanMute(player.getUniqueId(), messageKeyBanReason, UUIDFetcher.getUUID(name));
                        String messageKeyBannedBy = "banBannedby";
                        String localizedMessageBannedBy = ProxySystem.getInstance().langeLanguageManager.getLocalizedMessage(player.getUniqueId(), messageKeyBannedBy);
                        String messageKeyBanDuration = "banDuration";
                        String localizedMessageBanDuration = ProxySystem.getInstance().langeLanguageManager.getLocalizedMessageBanMute(player.getUniqueId(), messageKeyBanDuration, UUIDFetcher.getUUID(name));
                        team.sendMessage("");
                        team.sendMessage(localizedMessageBanMessage);
                        team.sendMessage("");
                        team.sendMessage(localizedMessageBanID);
                        team.sendMessage(localizedMessageBanReason);
                        team.sendMessage("&8» &7Gebannt von &8|&3 " + player.getName());
                        team.sendMessage(localizedMessageBanDuration);
                        team.sendMessage("");
                    }
                }
            } else if (args[0].equalsIgnoreCase("5")) {
                String banReason = "banReason5";
                String messageBanReason = ProxySystem.getInstance().langeLanguageManager.getLocalizedMessage(player.getUniqueId(), banReason);

                ProxySystem.getInstance().banManager.ban(UUIDFetcher.getUUID(name), messageBanReason, 432000, player.getUniqueId());

                for(final ProxiedPlayer team : ProxySystem.getInstance().getProxy().getPlayers()) {
                    if (team.hasPermission("proxysystem.ban.see")) {
                        String messageKeyBanMessage = "banTeamMessage";
                        String localizedMessageBanMessage = ProxySystem.getInstance().langeLanguageManager.getLocalizedMessageTarget(player.getUniqueId(), messageKeyBanMessage, name);
                        String messageKeyBanID = "banID";
                        String localizedMessageBanID = ProxySystem.getInstance().langeLanguageManager.getLocalizedMessageBanMute(player.getUniqueId(), messageKeyBanID, UUIDFetcher.getUUID(name));
                        String messageKeyBanReason = "banReason";
                        String localizedMessageBanReason = ProxySystem.getInstance().langeLanguageManager.getLocalizedMessageBanMute(player.getUniqueId(), messageKeyBanReason, UUIDFetcher.getUUID(name));
                        String messageKeyBannedBy = "banBannedby";
                        String localizedMessageBannedBy = ProxySystem.getInstance().langeLanguageManager.getLocalizedMessage(player.getUniqueId(), messageKeyBannedBy);
                        String messageKeyBanDuration = "banDuration";
                        String localizedMessageBanDuration = ProxySystem.getInstance().langeLanguageManager.getLocalizedMessageBanMute(player.getUniqueId(), messageKeyBanDuration, UUIDFetcher.getUUID(name));
                        team.sendMessage("");
                        team.sendMessage(localizedMessageBanMessage);
                        team.sendMessage("");
                        team.sendMessage(localizedMessageBanID);
                        team.sendMessage(localizedMessageBanReason);
                        team.sendMessage("&8» &7Gebannt von &8|&3 " + player.getName());
                        team.sendMessage(localizedMessageBanDuration);
                        team.sendMessage("");
                    }
                }
            } else if (args[0].equalsIgnoreCase("6")) {
                String banReason = "banReason6";
                String messageBanReason = ProxySystem.getInstance().langeLanguageManager.getLocalizedMessage(player.getUniqueId(), banReason);

                ProxySystem.getInstance().banManager.ban(UUIDFetcher.getUUID(name), messageBanReason, 864000, player.getUniqueId());

                for(final ProxiedPlayer team : ProxySystem.getInstance().getProxy().getPlayers()) {
                    if (team.hasPermission("proxysystem.ban.see")) {
                        String messageKeyBanMessage = "banTeamMessage";
                        String localizedMessageBanMessage = ProxySystem.getInstance().langeLanguageManager.getLocalizedMessageTarget(player.getUniqueId(), messageKeyBanMessage, name);
                        String messageKeyBanID = "banID";
                        String localizedMessageBanID = ProxySystem.getInstance().langeLanguageManager.getLocalizedMessageBanMute(player.getUniqueId(), messageKeyBanID, UUIDFetcher.getUUID(name));
                        String messageKeyBanReason = "banReason";
                        String localizedMessageBanReason = ProxySystem.getInstance().langeLanguageManager.getLocalizedMessageBanMute(player.getUniqueId(), messageKeyBanReason, UUIDFetcher.getUUID(name));
                        String messageKeyBannedBy = "banBannedby";
                        String localizedMessageBannedBy = ProxySystem.getInstance().langeLanguageManager.getLocalizedMessage(player.getUniqueId(), messageKeyBannedBy);
                        String messageKeyBanDuration = "banDuration";
                        String localizedMessageBanDuration = ProxySystem.getInstance().langeLanguageManager.getLocalizedMessageBanMute(player.getUniqueId(), messageKeyBanDuration, UUIDFetcher.getUUID(name));
                        team.sendMessage("");
                        team.sendMessage(localizedMessageBanMessage);
                        team.sendMessage("");
                        team.sendMessage(localizedMessageBanID);
                        team.sendMessage(localizedMessageBanReason);
                        team.sendMessage("&8» &7Gebannt von &8|&3 " + player.getName());
                        team.sendMessage(localizedMessageBanDuration);
                        team.sendMessage("");
                    }
                }
            } else if (args[0].equalsIgnoreCase("7")) {
                String banReason = "banReason7";
                String messageBanReason = ProxySystem.getInstance().langeLanguageManager.getLocalizedMessage(player.getUniqueId(), banReason);

                ProxySystem.getInstance().banManager.ban(UUIDFetcher.getUUID(name), messageBanReason, 864000, player.getUniqueId());

                for(final ProxiedPlayer team : ProxySystem.getInstance().getProxy().getPlayers()) {
                    if (team.hasPermission("proxysystem.ban.see")) {
                        String messageKeyBanMessage = "banTeamMessage";
                        String localizedMessageBanMessage = ProxySystem.getInstance().langeLanguageManager.getLocalizedMessageTarget(player.getUniqueId(), messageKeyBanMessage, name);
                        String messageKeyBanID = "banID";
                        String localizedMessageBanID = ProxySystem.getInstance().langeLanguageManager.getLocalizedMessageBanMute(player.getUniqueId(), messageKeyBanID, UUIDFetcher.getUUID(name));
                        String messageKeyBanReason = "banReason";
                        String localizedMessageBanReason = ProxySystem.getInstance().langeLanguageManager.getLocalizedMessageBanMute(player.getUniqueId(), messageKeyBanReason, UUIDFetcher.getUUID(name));
                        String messageKeyBannedBy = "banBannedby";
                        String localizedMessageBannedBy = ProxySystem.getInstance().langeLanguageManager.getLocalizedMessage(player.getUniqueId(), messageKeyBannedBy);
                        String messageKeyBanDuration = "banDuration";
                        String localizedMessageBanDuration = ProxySystem.getInstance().langeLanguageManager.getLocalizedMessageBanMute(player.getUniqueId(), messageKeyBanDuration, UUIDFetcher.getUUID(name));
                        team.sendMessage("");
                        team.sendMessage(localizedMessageBanMessage);
                        team.sendMessage("");
                        team.sendMessage(localizedMessageBanID);
                        team.sendMessage(localizedMessageBanReason);
                        team.sendMessage("&8» &7Gebannt von &8|&3 " + player.getName());
                        team.sendMessage(localizedMessageBanDuration);
                        team.sendMessage("");
                    }
                }
            } else if (args[0].equalsIgnoreCase("8")) {
                String banReason = "banReason1";
                String messageBanReason = ProxySystem.getInstance().langeLanguageManager.getLocalizedMessage(player.getUniqueId(), banReason);

                ProxySystem.getInstance().banManager.ban(UUIDFetcher.getUUID(name), messageBanReason, 2592000, player.getUniqueId());

                for(final ProxiedPlayer team : ProxySystem.getInstance().getProxy().getPlayers()) {
                    if (team.hasPermission("proxysystem.ban.see")) {
                        String messageKeyBanMessage = "banTeamMessage";
                        String localizedMessageBanMessage = ProxySystem.getInstance().langeLanguageManager.getLocalizedMessageTarget(player.getUniqueId(), messageKeyBanMessage, name);
                        String messageKeyBanID = "banID";
                        String localizedMessageBanID = ProxySystem.getInstance().langeLanguageManager.getLocalizedMessageBanMute(player.getUniqueId(), messageKeyBanID, UUIDFetcher.getUUID(name));
                        String messageKeyBanReason = "banReason";
                        String localizedMessageBanReason = ProxySystem.getInstance().langeLanguageManager.getLocalizedMessageBanMute(player.getUniqueId(), messageKeyBanReason, UUIDFetcher.getUUID(name));
                        String messageKeyBannedBy = "banBannedby";
                        String localizedMessageBannedBy = ProxySystem.getInstance().langeLanguageManager.getLocalizedMessage(player.getUniqueId(), messageKeyBannedBy);
                        String messageKeyBanDuration = "banDuration";
                        String localizedMessageBanDuration = ProxySystem.getInstance().langeLanguageManager.getLocalizedMessageBanMute(player.getUniqueId(), messageKeyBanDuration, UUIDFetcher.getUUID(name));
                        team.sendMessage("");
                        team.sendMessage(localizedMessageBanMessage);
                        team.sendMessage("");
                        team.sendMessage(localizedMessageBanID);
                        team.sendMessage(localizedMessageBanReason);
                        team.sendMessage("&8» &7Gebannt von &8|&3 " + player.getName());
                        team.sendMessage(localizedMessageBanDuration);
                        team.sendMessage("");
                    }
                }
            } else if (args[0].equalsIgnoreCase("9")) {
                String banReason = "banReason9";
                String messageBanReason = ProxySystem.getInstance().langeLanguageManager.getLocalizedMessage(player.getUniqueId(), banReason);

                ProxySystem.getInstance().banManager.ban(UUIDFetcher.getUUID(name), messageBanReason, 31536000, player.getUniqueId());

                for(final ProxiedPlayer team : ProxySystem.getInstance().getProxy().getPlayers()) {
                    if (team.hasPermission("proxysystem.ban.see")) {
                        String messageKeyBanMessage = "banTeamMessage";
                        String localizedMessageBanMessage = ProxySystem.getInstance().langeLanguageManager.getLocalizedMessageTarget(player.getUniqueId(), messageKeyBanMessage, name);
                        String messageKeyBanID = "banID";
                        String localizedMessageBanID = ProxySystem.getInstance().langeLanguageManager.getLocalizedMessageBanMute(player.getUniqueId(), messageKeyBanID, UUIDFetcher.getUUID(name));
                        String messageKeyBanReason = "banReason";
                        String localizedMessageBanReason = ProxySystem.getInstance().langeLanguageManager.getLocalizedMessageBanMute(player.getUniqueId(), messageKeyBanReason, UUIDFetcher.getUUID(name));
                        String messageKeyBannedBy = "banBannedby";
                        String localizedMessageBannedBy = ProxySystem.getInstance().langeLanguageManager.getLocalizedMessage(player.getUniqueId(), messageKeyBannedBy);
                        String messageKeyBanDuration = "banDuration";
                        String localizedMessageBanDuration = ProxySystem.getInstance().langeLanguageManager.getLocalizedMessageBanMute(player.getUniqueId(), messageKeyBanDuration, UUIDFetcher.getUUID(name));
                        team.sendMessage("");
                        team.sendMessage(localizedMessageBanMessage);
                        team.sendMessage("");
                        team.sendMessage(localizedMessageBanID);
                        team.sendMessage(localizedMessageBanReason);
                        team.sendMessage("&8» &7Gebannt von &8|&3 " + player.getName());
                        team.sendMessage(localizedMessageBanDuration);
                        team.sendMessage("");
                    }
                }
            } else if (args[0].equalsIgnoreCase("10")) {
                String banReason = "banReason10";
                String messageBanReason = ProxySystem.getInstance().langeLanguageManager.getLocalizedMessage(player.getUniqueId(), banReason);

                ProxySystem.getInstance().banManager.ban(UUIDFetcher.getUUID(name), messageBanReason, 31536000, player.getUniqueId());

                for(final ProxiedPlayer team : ProxySystem.getInstance().getProxy().getPlayers()) {
                    if (team.hasPermission("proxysystem.ban.see")) {
                        String messageKeyBanMessage = "banTeamMessage";
                        String localizedMessageBanMessage = ProxySystem.getInstance().langeLanguageManager.getLocalizedMessageTarget(player.getUniqueId(), messageKeyBanMessage, name);
                        String messageKeyBanID = "banID";
                        String localizedMessageBanID = ProxySystem.getInstance().langeLanguageManager.getLocalizedMessageBanMute(player.getUniqueId(), messageKeyBanID, UUIDFetcher.getUUID(name));
                        String messageKeyBanReason = "banReason";
                        String localizedMessageBanReason = ProxySystem.getInstance().langeLanguageManager.getLocalizedMessageBanMute(player.getUniqueId(), messageKeyBanReason, UUIDFetcher.getUUID(name));
                        String messageKeyBannedBy = "banBannedby";
                        String localizedMessageBannedBy = ProxySystem.getInstance().langeLanguageManager.getLocalizedMessage(player.getUniqueId(), messageKeyBannedBy);
                        String messageKeyBanDuration = "banDuration";
                        String localizedMessageBanDuration = ProxySystem.getInstance().langeLanguageManager.getLocalizedMessageBanMute(player.getUniqueId(), messageKeyBanDuration, UUIDFetcher.getUUID(name));
                        team.sendMessage("");
                        team.sendMessage(localizedMessageBanMessage);
                        team.sendMessage("");
                        team.sendMessage(localizedMessageBanID);
                        team.sendMessage(localizedMessageBanReason);
                        team.sendMessage("&8» &7Gebannt von &8|&3 " + player.getName());
                        team.sendMessage(localizedMessageBanDuration);
                        team.sendMessage("");
                    }
                }
            } else if (args[0].equalsIgnoreCase("11")) {
                String banReason = "banReason11";
                String messageBanReason = ProxySystem.getInstance().langeLanguageManager.getLocalizedMessage(player.getUniqueId(), banReason);

                ProxySystem.getInstance().banManager.ban(UUIDFetcher.getUUID(name), messageBanReason, 31536000, player.getUniqueId());

                for(final ProxiedPlayer team : ProxySystem.getInstance().getProxy().getPlayers()) {
                    if (team.hasPermission("proxysystem.ban.see")) {
                        String messageKeyBanMessage = "banTeamMessage";
                        String localizedMessageBanMessage = ProxySystem.getInstance().langeLanguageManager.getLocalizedMessageTarget(player.getUniqueId(), messageKeyBanMessage, name);
                        String messageKeyBanID = "banID";
                        String localizedMessageBanID = ProxySystem.getInstance().langeLanguageManager.getLocalizedMessageBanMute(player.getUniqueId(), messageKeyBanID, UUIDFetcher.getUUID(name));
                        String messageKeyBanReason = "banReason";
                        String localizedMessageBanReason = ProxySystem.getInstance().langeLanguageManager.getLocalizedMessageBanMute(player.getUniqueId(), messageKeyBanReason, UUIDFetcher.getUUID(name));
                        String messageKeyBannedBy = "banBannedby";
                        String localizedMessageBannedBy = ProxySystem.getInstance().langeLanguageManager.getLocalizedMessage(player.getUniqueId(), messageKeyBannedBy);
                        String messageKeyBanDuration = "banDuration";
                        String localizedMessageBanDuration = ProxySystem.getInstance().langeLanguageManager.getLocalizedMessageBanMute(player.getUniqueId(), messageKeyBanDuration, UUIDFetcher.getUUID(name));
                        team.sendMessage("");
                        team.sendMessage(localizedMessageBanMessage);
                        team.sendMessage("");
                        team.sendMessage(localizedMessageBanID);
                        team.sendMessage(localizedMessageBanReason);
                        team.sendMessage("&8» &7Gebannt von &8|&3 " + player.getName());
                        team.sendMessage(localizedMessageBanDuration);
                        team.sendMessage("");
                    }
                }
            } else {
                sendHelp(player);
            }
        } else {
            sendHelp(player);
        }
    }

    @SuppressWarnings("deprecation")
    private void sendHelp(final ProxiedPlayer player) {
        player.sendMessage("");
        String banReason1 = "banReason1";
        String messageBanReason1 = ProxySystem.getInstance().langeLanguageManager.getLocalizedMessage(player.getUniqueId(), banReason1);
        String banReason2 = "banReason2";
        String messageBanReason2 = ProxySystem.getInstance().langeLanguageManager.getLocalizedMessage(player.getUniqueId(), banReason2);
        String banReason3 = "banReason3";
        String messageBanReason3 = ProxySystem.getInstance().langeLanguageManager.getLocalizedMessage(player.getUniqueId(), banReason3);
        String banReason4 = "banReason4";
        String messageBanReason4 = ProxySystem.getInstance().langeLanguageManager.getLocalizedMessage(player.getUniqueId(), banReason4);
        String banReason5 = "banReason5";
        String messageBanReason5 = ProxySystem.getInstance().langeLanguageManager.getLocalizedMessage(player.getUniqueId(), banReason5);
        String banReason6 = "banReason6";
        String messageBanReason6 = ProxySystem.getInstance().langeLanguageManager.getLocalizedMessage(player.getUniqueId(), banReason6);
        String banReason7 = "banReason7";
        String messageBanReason7 = ProxySystem.getInstance().langeLanguageManager.getLocalizedMessage(player.getUniqueId(), banReason7);
        String banReason8 = "banReason8";
        String messageBanReason8 = ProxySystem.getInstance().langeLanguageManager.getLocalizedMessage(player.getUniqueId(), banReason8);
        String banReason9 = "banReason9";
        String messageBanReason9 = ProxySystem.getInstance().langeLanguageManager.getLocalizedMessage(player.getUniqueId(), banReason9);
        String banReason10 = "banReason10";
        String messageBanReason10 = ProxySystem.getInstance().langeLanguageManager.getLocalizedMessage(player.getUniqueId(), banReason10);
        String banReason11 = "banReason1";
        String messageBanReason11 = ProxySystem.getInstance().langeLanguageManager.getLocalizedMessage(player.getUniqueId(), banReason11);
        player.sendMessage("§8» §7/ban §8«§a1§8» §8«§aName§8» §7| " + messageBanReason1);
        player.sendMessage("§8» §7/ban §8«§a2§8» §8«§aName§8» §7| " + messageBanReason2);
        player.sendMessage("§8» §7/ban §8«§a3§8» §8«§aName§8» §7| " + messageBanReason3);
        player.sendMessage("§8» §7/ban §8«§a4§8» §8«§aName§8» §7| " + messageBanReason4);
        player.sendMessage("§8» §7/ban §8«§a5§8» §8«§aName§8» §7| " + messageBanReason5);
        player.sendMessage("§8» §7/ban §8«§a6§8» §8«§aName§8» §7| " + messageBanReason6);
        player.sendMessage("§8» §7/ban §8«§a7§8» §8«§aName§8» §7| " + messageBanReason7);
        player.sendMessage("§8» §7/ban §8«§a8§8» §8«§aName§8» §7| " + messageBanReason8);
        player.sendMessage("§8» §7/ban §8«§a9§8» §8«§aName§8» §7| " + messageBanReason9);
        player.sendMessage("§8» §7/ban §8«§a10§8» §8«§aName§8»  §7| " + messageBanReason10);
        player.sendMessage("§8» §7/ban §8«§a11§8» §8«§aName§8»  §7| " + messageBanReason11);
        player.sendMessage("");
    }

}

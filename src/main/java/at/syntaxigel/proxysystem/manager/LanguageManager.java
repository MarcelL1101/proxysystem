package at.syntaxigel.proxysystem.manager;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.UUID;
import java.util.logging.Level;

import at.syntaxigel.proxysystem.ProxySystem;
import at.syntaxigel.proxysystem.utils.UUIDFetcher;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

public class LanguageManager {
	
	public String getLanguage(final UUID uuid) {
		try (Connection connection = ProxySystem.getInstance().mysql.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement("SELECT language FROM player WHERE uuid = ?;")) {
			preparedStatement.setString(1, uuid.toString());
			ResultSet resultSet = preparedStatement.executeQuery();
			
			if (resultSet.next()) {
				return resultSet.getString("language");
			}
			
		} catch (SQLException sqlException) {
			ProxySystem.getInstance().logger().log(Level.WARNING, ProxySystem.getInstance().configManager.getMessagePrefix() + "Die Sprache von dem Spieler §3" + UUIDFetcher.getName(uuid) + " §7konnte §cnicht §7abgefragt werden. Fehler: §c" + sqlException);
		}
		
		return "de";
	}

    public void changeLanguage(final UUID uuid) {
        if (getLanguage(uuid).equalsIgnoreCase("de")) {
            try (Connection connection = ProxySystem.getInstance().mysql.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement("UPDATE player SET language = 'en' WHERE uuid = ?;")) {
                preparedStatement.setString(1, uuid.toString());
                preparedStatement.execute();
            } catch (SQLException sqlException) {
                ProxySystem.getInstance().logger().log(Level.WARNING, ProxySystem.getInstance().configManager.getMessagePrefix() + "Die Sprache von dem Spieler §3" + UUIDFetcher.getName(uuid) + " §7konnte §cnicht §7geändert werden auf Deutsch. Fehler: §c" + sqlException);
            }
        } else {
            try (Connection connection = ProxySystem.getInstance().mysql.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement("UPDATE player SET language = 'de' WHERE uuid = ?;")) {
                preparedStatement.setString(1, uuid.toString());
                preparedStatement.execute();
            } catch (SQLException sqlException) {
                ProxySystem.getInstance().logger().log(Level.WARNING, ProxySystem.getInstance().configManager.getMessagePrefix() + "Die Sprache von dem Spieler §3" + UUIDFetcher.getName(uuid) + " §7konnte §cnicht §7geändert werden auf Englisch. Fehler: §c" + sqlException);
            }
        }
    }

	private File file;
    private Configuration configuration;

    public void createConfig() {
        if (!(ProxySystem.getInstance().getDataFolder().exists())) {
            ProxySystem.getInstance().getDataFolder().mkdirs();
        }

        file = new File(ProxySystem.getInstance().getDataFolder(), "messages.yml");

        if (!(file.exists())) {
            try {
                file.createNewFile();
                configuration = ConfigurationProvider.getProvider(YamlConfiguration.class).load(file);
                
                configuration.set("message.de.noPerm", "%prefix% Du hast dafür &ckeine &7Berechtigung!"); 
                configuration.set("message.de.onUse", "%prefix% Verwende: /%command%"); 
                configuration.set("message.de.onUseMoreArguments", "%prefix% Verwende: /%command% oder /%command% %arguments%");
                configuration.set("message.en.noPerm", "%prefix% You &cdon't &7have authorization for this!");
                configuration.set("message.en.onUse", "%prefix% Use: /%command%");
                configuration.set("message.en.onUseMoreArguments", "%prefix% Use: /%command% or /%command% %arguments%");
                configuration.set("message.de.noOnline", "%prefix% Der Spieler &3%player-name% &7ist derzeit &cnicht &7online!");
                configuration.set("message.en.noOnline", "%prefix% The player &3%player-name% &7is currently &cnot &7online!");
                configuration.set("message.de.pingCommand", "%prefix% Du hast einen Server Ping von &e%player-ping% &7ms.");
                configuration.set("message.en.pingCommand", "%prefix% You have a server ping of &e%player-ping% &7ms.");
                configuration.set("message.de.pingCommandTarget", "%prefix% Der Spieler &3%target-name% &7hat einen Server Ping von &e%target-ping% &7ms.");
                configuration.set("message.en.pingCommandTarget", "%prefix% The player &3%target-name% &7has a server ping of &e%target-ping% &7ms.");
                configuration.set("message.de.onlineCountOnePlayer", "%prefix% Es ist derzeit &e%playercount% &7Spieler online.");
                configuration.set("message.en.onlineCountOnePlayer", "%prefix% There is currently &e%playercount% &7players online.");
                configuration.set("message.de.onlineCountMoreThenOnePlayer", "%prefix% Es sind derzeit &e%playercount% &7Spieler online.");
                configuration.set("message.en.onlineCountMoreThenOnePlayer", "%prefix% There are currently &e%playercount% &7players online.");
                configuration.set("message.de.discord", "%prefix% Du kommst über diesen Discord Link: &3https://discord.gg/P5YsWk7qrG &7auf unseren Discord Server.");
                configuration.set("message.en.discord", "%prefix% You come via this Discord link: &3https://discord.gg/P5YsWk7qrG &7to our Discord server.");
                configuration.set("message.de.forum", "%prefix% Unser Forum: &3https://vanaty.de");
                configuration.set("message.en.forum", "%prefix% Our forum: &3https://vanaty.de");
                configuration.set("message.de.whereami", "%prefix% Du befindest dich derzeit auf dem &3%player-server% &7Server.");
                configuration.set("message.en.whereami", "%prefix% You are currently on the &3%player-server% &7server.");
                configuration.set("message.en.whereis", "%prefix% The player &3%player-name% &7is currently on the &3%target-server% &7server.");
                configuration.set("message.de.whereis", "%prefix% Der Spieler &3%player-name% &7befindet sich derzeit auf dem &3%target-server% &7Server.");
                configuration.set("message.de.coins", "%prefix% Du hast derzeit &e%player-coins% &7Coins auf deinem Konto.");
                configuration.set("message.en.coins", "%prefix% You currently have &e%player-coins% &7coins on your account.");
                configuration.set("message.de.coinsTarget", "%prefix% Der Spieler &3%player-name% &7hat derzeit &e%target-coins% &7Coins auf dem Konto.");
                configuration.set("message.en.coinsTarget", "%prefix% The player &3%player-name% &7currently has &e%target-coins% &7coins in the account.");
                configuration.set("message.de.language", "%prefix% Du hast &aerfolgreich &7die Sprache geändert.");
                configuration.set("message.en.language", "%prefix% You have &asuccessfully &7changed the language.");
                configuration.set("message.de.globalmuteOn", "%prefix% Du hast &aerfolgreich &7den Globalenchat aktiviert.");
                configuration.set("message.en.globalmuteOn", "%prefix% You have &asuccessfully &7activated the global chat.");
                configuration.set("message.de.globalmuteOff", "%prefix% Du hast &aerfolgreich &7den Globalenchat deaktiviert.");
                configuration.set("message.en.globalmuteOff", "%prefix% You have &asuccessfully &7disabled the global chat.");
                configuration.set("message.de.bauserver", "%prefix% Du hast dich &aerfolgreich &7auf den Bauserver verbunden.");
                configuration.set("message.en.bauserver", "%prefix% You have &successfully &7connected to the build server.");
                configuration.set("message.de.alreadybauserver", "%prefix% Du bist bereits auf dem Bauserver.");
                configuration.set("message.en.alreadybauserver", "%prefix% You are already on the build server.");
                configuration.set("message.de.broadcast", "%prefix% Warnung &8[>>] &7%broadcast%");
                configuration.set("message.en.broadcast", "%prefix% Warning &8[>>] &7%broadcast%");
                configuration.set("message.de.globalclear", "%prefix% Du hast den Globalenchat geleert.");
                configuration.set("message.en.globalclear", "%prefix% You have emptied the global chat.");
                configuration.set("message.de.team", "%prefix% Zurzeit sind &e%teamcount% &7im Team.");
                configuration.set("message.en.team", "%prefix% Currently, &e%teamcount% &7are in the team.");
                configuration.set("message.de.teamAdd", "%prefix% Du hast &aerfolgreich &7den Spieler &3%player-name% &7zum Team hinzugefügt.");
                configuration.set("message.en.teamAdd", "%prefix% You have &asuccessfully &7added the player &3%player-name% &7to the team.");
                configuration.set("message.de.teamRemove", "%prefix% Du hast &aerfolgreich &7den Spieler &3%player-name% &7vom Team entfernt.");
                configuration.set("message.en.teamRemove", "%prefix% You have &asuccessfully &7removed the player &3%player-name% &7from the team.");
                configuration.set("message.de.teamAlready", "%prefix% Der Spieler &3%target-name% &7ist bereits im Team.");
                configuration.set("message.en.teamAlready", "%prefix% The player &3%target-name% &7is already in the team.");
                configuration.set("message.de.teamIsNot", "%prefix% Der Spieler &3%target-name% &7ist &cnicht &7im Team.");
                configuration.set("message.en.teamIsNot", "%prefix% The player &3%target-name% &7is &cnot &7on the team.");
                configuration.set("message.de.banCount", "&8[|] &7Gebannte Spieler &8[>>] &7%banCount%");
                configuration.set("message.en.banCount", "&8[|] &7Banned Player &8[>>] &7%banCount%");
                configuration.set("message.de.muteCount", "&8[|] &7Gemuted Spieler &8[>>] &7%muteCount%");
                configuration.set("message.en.muteCount", "&8[|] &7Muted Player &8[>>] &7%muteCount%");
                configuration.set("message.de.reportCount", "&8[|] &7Reports bearbeitet &8[>>] &7%reportCount%");
                configuration.set("message.en.reportCount", "&8[|] &7Reports edited &8[>>] &7%reportCount%");
                configuration.set("message.de.registeredPlayerCount", "%prefix% Es sind derzeit &e%player-count% &7Spieler registriert.");
                configuration.set("message.en.registeredPlayerCount", "%prefix% There are currently &e%player-count% &7players registered.");
                configuration.set("message.de.globalmute", "%prefix% Der Globalechat ist derzeit &cdeaktiviert&7.");
                configuration.set("message.en.globalmute", "%prefix% Global chat is currently &cdeactivated&7.");
                configuration.set("message.de.getip", "%prefix% Der Spieler &3%player-name% &7hat die IPv4 Adresse &e%player-ip%&7.");
                configuration.set("message.en.getip", "%prefix% The player &3%player-name% &7has the IPv4 address &e%player-ip%&7.");
                configuration.set("message.de.next", "&8[>>] &aWeiter");
                configuration.set("message.en.next", "&8[>>] &aNext");
                configuration.set("message.de.back", "&8[>>] &cZurück");
                configuration.set("message.en.back", "&8[>>] &cBack");
                configuration.set("message.de.banTeamMessage", "%prefix% Der Spieler &3%player-name% &7wurde vom Netzwerk gebannt!");
                configuration.set("message.en.banTeamMessage", "%prefix% The player &3%player-name% &7has been banned from the network!");
                configuration.set("message.de.notBanTeam", "%prefix% Du darfst &ckeine &7Teammitglieder bannen!");
                configuration.set("message.en.notBanTeam", "%prefix% The player &3%ban-name% &7is &cnot &7banned!");
                configuration.set("message.de.isNotBanned", "%prefix% Der Spieler &3%ban-name% &7ist &cnicht &7gebannt!");
                configuration.set("message.en.isNotBanned", "%prefix% You are allowed to ban &ckany &7team members!");
                configuration.set("message.de.alreadyBanned", "%prefix% Der Spieler &3%target-name% &7ist bereits gebannt!");
                configuration.set("message.en.alreadyBanned", "%prefix% The player &3%target-name% &7is already banned!");
                configuration.set("message.de.invalidPage", "%prefix% Die Seite wurde &cnicht &7gefunden.");
                configuration.set("message.en.invalidPage", "%prefix% The page was &cnot &7found.");
                configuration.set("message.de.banReason", "&8[>>] &7Ban Grund &8[|]&3 %ban-reason%");
                configuration.set("message.en.banReason", "&8[>>] &7Ban Reason &8[|]&3 %ban-reason%");
                configuration.set("message.de.banDuration", "&8[>>] &7Ban Dauer &8[|]&3 %ban-duration%");
                configuration.set("message.en.banDuration", "&8[>>] &7Ban Duration &8[|]&3 %ban-duration%");
                configuration.set("message.de.banBannedBy", "&8[>>] &7Gebannt von &8[|]&3 %ban-banner%");
                configuration.set("message.en.banBannedBy", "&8[>>] &7Banned by &8[|]&3 %ban-banner%");
                configuration.set("message.de.banID", "&8[>>] &7Ban ID &8[|]&3 %ban-banid%");
                configuration.set("message.en.banID", "&8[>>] &7Ban ID &8[|]&3 %ban-banid%");
                configuration.set("message.de.banMessageFirstLine", "&8[>>] &7Du wurdest von unserem Netzwerk gesperrt.");
                configuration.set("message.en.banMessageFirstLine", "&8[>>] &7You were banned from our network.");
                configuration.set("message.de.banMessageLastLine", "&8[>>] §7Falls der Ban §cnicht &7gerechtfertigt ist dann kannst du einen Antrag im Forum stellen unter §ehttps://vanaty.de");
                configuration.set("message.en.banMessageLastLine", "&8[>>] §7If the ban §cis not &7justified then you can make a request in the forum at §ehttps://vanaty.de");
                configuration.set("message.de.unban", "%prefix% Der Spieler &3%ban-name% &7wurde von &3%unbannedby-name% &7entbannt.");
                configuration.set("message.en.unban", "%prefix% The player &3%ban-name% &7has been &7unbanned by &3%unbannedby-name%.");
                configuration.set("message.de.banReason1", "Unerlaubt Clientmodifikationen");
                configuration.set("message.en.banReason1", "Unauthorized client modifications");
                configuration.set("message.de.banReason2", "Griefing");
                configuration.set("message.en.banReason2", "Griefing");
                configuration.set("message.de.banReason3", "Spielername");
                configuration.set("message.en.banReason3", "Username");
                configuration.set("message.de.banReason4", "Skin");
                configuration.set("message.en.banReason4", "Skin");
                configuration.set("message.de.banReason5", "Bugusing");
                configuration.set("message.en.banReason5", "Bugusing");
                configuration.set("message.de.banReason6", "Trolling");
                configuration.set("message.en.banReason6", "Trolling");
                configuration.set("message.de.banReason7", "Ausnutzung der Rechte");
                configuration.set("message.en.banReason7", "Exploitation of rights");
                configuration.set("message.de.banReason8", "Werbung");
                configuration.set("message.en.banReason8", "Advertising");
                configuration.set("message.de.banReason9", "Griefing");
                configuration.set("message.en.banReason9", "Griefing");
                configuration.set("message.de.banReason10", "Rassismus/Sexismus");
                configuration.set("message.en.banReason10", "Racism/Sexism");
                configuration.set("message.de.banReason11", "Wiederbetätigung");
                configuration.set("message.en.banReason11", "Reactivation");
                configuration.set("message.de.onlineTime", "%prefix% Du hast eine Online Zeit von &e%player-onlinetime% &7Minute(n)/Stunde(n).");
                configuration.set("message.en.onlineTime", "%prefix% You have an online time of &e%player-onlinetime% &7Minute(s)/Hour(s).");
                configuration.set("message.de.onlineTimeTarget", "%prefix% Der Spieler %player-name% hat eine Online Zeit von &e%target-onlinetime% &7Minute(n)/Stunde(n).");
                configuration.set("message.en.onlineTimeTarget", "%prefix% he player %player-name% has an online time of &e%target-onlinetime% &7Minute(s)/Hour(s).");
                configuration.set("message.de.neverPlayed", "%prefix% Der Spieler &3%target-name% &7hat noch &cnie &7auf diesem Netzwerk gespielt.");
                configuration.set("message.en.neverPlayed", "%prefix% The player &3%target-name% &7has never &7played on this network.");
                configuration.set("message.de.commandSpyOn", "%prefix% Du kannst jetzt alle Befehle aller Spieler sehen.");
                configuration.set("message.en.commandSpyOn", "%prefix% You can now see all commands of all players.");
                configuration.set("message.de.commandSpyOff", "%prefix% Du siehst jetzt die Befehle &cnicht &7mehr.");
                configuration.set("message.en.commandSpyOff", "%prefix% You will now see the commands &cnot &7anymore.");
                configuration.set("message.de.commandSpySee", "%prefix% Du kannst jetzt alle Befehle aller Spieler sehen.");
                configuration.set("message.en.commandSpySee", "%prefix% You can now see all commands of all players.");
                configuration.set("message.de.report", "%prefix% Du hast dem Spieler &3%player-name% &7mit Grund &3%report-reason% &7gemeldet.");
                configuration.set("message.en.report", "%prefix% You have reported &3%player-name% &7with reason &3%report-reason% &7to the player.");
                configuration.set("message.de.reportReason", "&8[|] &7Report Grund &8[>>] &7%report-reason%");
                configuration.set("message.en.reportReason", "&8[|] &7Report Reason &8[>>] &7%report-reason%");
                configuration.set("message.de.reporter", "&8[|] &7Reporter &8[>>] &7%report-reporter%");
                configuration.set("message.en.reporter", "&8[|] &7Reported &8[>>] &7%report-reporter%");;
                configuration.set("message.de.reportID", "&8[|] &7Report ID &8[>>] &7%report-reportid%");
                configuration.set("message.en.reportID", "&8[|] &7Report ID &8[>>] &7%report-reportid%");
                configuration.set("message.de.reported", "&8[|] &7%report-reported%");
                configuration.set("message.en.reported", "&8[|] &7%report-reported%");
                configuration.set("message.de.reportServer", "&8[|] &7Report Server &8[>>] &7%report-server%");
                configuration.set("message.en.reportServer", "&8[|] &7Reported Server &8[>>] &7%report-server%");
                configuration.set("message.de.openReports", "%prefix% Es ist/sind derzeit &e%report-count% &7Reports offen.");
                configuration.set("message.en.openReports", "%prefix% There is/are currently &e%report-count% &7reports open.");
                configuration.set("message.de.header", "\n&8[>>] &bVanaty&3DE &7Netzwerk &8[<<]\n\n &8[>>] &7Ping &8[|] &e%player-ping% &8[<<]\n &8[>>] &7Uhrzeit &8[|] &e%systemTime% &8[<<]\n&8[>>] &7Server &8[|] &e%player-server% &8[<<]\n &8[>>] &7Spieler &8[|] &a%playercount%&7/&c%maxPlayer% &8[<<] \n");
                configuration.set("message.en.header", "\n&8[>>] &bVanaty&3DE &7Network &8[<<]\n\n &8[>>] &7Ping &8[|] &e%player-ping% &8[<<]\n &8[>>] &7Time &8[|] &e%systemTime% &8[<<]\n&8[>>] &7Server &8[|] &e%player-server% &8[<<]\n &8[>>] &7Player &8[|] &a%playercount%&7/&c%maxPlayer% &8[<<] \n");
                configuration.set("message.de.footer", "\n&8[>>] &7Panel &8[|] &3https://vanaty.de &8[<<]\n &8[>>] &7Discord &8[|] &3https://discord.vanaty.de &8[<<]\n");
                configuration.set("message.en.footer", "\n&8[>>] &7Panel &8[|] &3https://vanaty.de &8[<<]\n &8[>>] &7Discord &8[|] &3https://discord.vanaty.de &8[<<]\n");
                configuration.set("message.de.createServer", "%prefix% Der Server &3%servername% &7mit der Adresse &3%server-address% &7und Port &3%server-port%&7.");
                configuration.set("message.de.createServer", "%prefix% The server &3%servername% &7with the address &3%server-address% &7and port &3%server-port%&7.");

                ConfigurationProvider.getProvider(YamlConfiguration.class).save(configuration, file);
            } catch (IOException ioException) {
                throw new RuntimeException(ioException);
            }
        }
    } 
    
    public String getMessage(final String messageKey, final String language) {
        try {
            configuration = ConfigurationProvider.getProvider(YamlConfiguration.class).load(file);
        } catch (IOException ioException) {
            throw new RuntimeException(ioException);
        }

        String path = "message." + language + "." + messageKey;
        String message = configuration.getString(path);

        if (message == null && !language.equalsIgnoreCase("en")) {
            path = "message.de." + messageKey;
            message = configuration.getString(path);
        }

        if (message == null) {
            message = messageKey;
        }

        return ChatColor.translateAlternateColorCodes('&', message).replace("%prefix%", ProxySystem.getInstance().configManager.getMessagePrefix()).replace("[>>]", "»").replace("[<<]", "«").replace("[|]", "|");
    }

    public String getLocalizedMessageCreateReport(final UUID playerUUID, final String messageKey, final String playername, final String reason) {
        String playerLanguage = getLanguage(playerUUID);

        String message = getMessage(messageKey, playerLanguage);

        if (message == null) {
            message = getMessage(messageKey, "de");
        }

        if (message.contains("%player-name%")) {
            message = message.replace("%player-name%", playername);
        }

        if (message.contains("%report-reason%")) {
            message = message.replace("%report-reason%", reason);
        }

        return message;
    }

    public String getLocalizedMessageCreateServer(final UUID playerUUID, final String messageKey, final String servername, final String address, final String port) {
        String playerLanuage = getLanguage(playerUUID);

        String message = getMessage(messageKey, playerLanuage);

        if (message == null) {
            message = getMessage(messageKey, "de");
        }

        if (message.contains("%servername%")) {
            message = message.replace("%servername%", servername);
        }

        if (message.contains("%server-address%")) {
            message = message.replace("%server-address%", address);
        }

        if (message.contains("%server-port%")) {
            message = message.replace("%server-port%", port);
        }

        return message;
    }

    public String getLocalizedMessageReport(final UUID playerUUID, final String messageKey, final String playername) {
        String playerLanguage = getLanguage(playerUUID);

        String message = getMessage(messageKey, playerLanguage);

        if (message == null) {
            message = getMessage(messageKey, "de");
        }

        if (message.contains("%report-reason%")) {
            message = message.replace("%report-reason%", ProxySystem.getInstance().reportManager.getReportReason(UUIDFetcher.getUUID(playername)));
        }

        if (message.contains("%report-server%")) {
            message = message.replace("%report-server%", ProxySystem.getInstance().reportManager.getReportServer(UUIDFetcher.getUUID(playername)));
        }

        if (message.contains("%report-reportid%")) {
            message = message.replace("%report-reportid%", String.valueOf(ProxySystem.getInstance().reportManager.getReportID(UUIDFetcher.getUUID(playername))));
        }

        if (message.contains("%report-reporter%")) {
            message = message.replace("%report-reporter%", ProxySystem.getInstance().reportManager.getReporterName(UUIDFetcher.getUUID(playername)));
        }

        if (message.contains("%report-reported%")) {
            message = message.replace("%report-reported%", ProxySystem.getInstance().reportManager.getReporterUUID(UUIDFetcher.getUUID(playername)));
        }

        return message;
    }

    @SuppressWarnings("deprecation")
    public String getLocalizedMessageTarget(final UUID playerUUID, final String messageKey, final String playername) {
        String playerLanguage = getLanguage(playerUUID);

        String message = getMessage(messageKey, playerLanguage);

        if (message == null) {
            message = getMessage(messageKey, "de");
        }

        if (message.contains("%player-server%")) {
            ProxiedPlayer target = ProxySystem.getInstance().getProxy().getPlayer(playername);
            message = message.replace("%player-server%", target.getServer().getInfo().getName());
        }

        if (message.contains("%target-coins%")) {
            ProxiedPlayer target = ProxySystem.getInstance().getProxy().getPlayer(playername);
            message = message.replace("%target-coins%", String.valueOf(ProxySystem.getInstance().playerManager.getCoins(target.getUniqueId())));
        }

        if (message.contains("%player-name%")) {
            message = message.replace("%player-name%", playername);
        }

        if (message.contains("%target-name%")) {
            message = message.replace("%target-name%", playername);
        }

        if (message.contains("%banCount%")) {
            message = message.replace("%banCount%", String.valueOf(ProxySystem.getInstance().teamManager.getBans(UUIDFetcher.getUUID(playername))));
        }

        if (message.contains("%muteCount%")) {
            message = message.replace("%muteCount%", String.valueOf(ProxySystem.getInstance().teamManager.getMutes(UUIDFetcher.getUUID(playername))));
        }

        if (message.contains("%reportCount%")) {
            message = message.replace("%reportCount%", String.valueOf(ProxySystem.getInstance().teamManager.getReports(UUIDFetcher.getUUID(playername))));
        }

        if (message.contains("%player-ip%")) {
            ProxiedPlayer target = ProxySystem.getInstance().getProxy().getPlayer(playername);
            message = message.replace("%player-ip%", String.valueOf(target.getAddress().toString()));
        }

        if (message.contains("%ban-name%")) {
            message = message.replace("%ban-name%", playername);
        }

        if (message.contains("%unbannedby-name%")) {
            final ProxiedPlayer player = ProxySystem.getInstance().getProxy().getPlayer(playerUUID);
            message = message.replace("%unbannedby-name%", player.getName());
        }

        if (message.contains("%target-onlinetime%")) {
            int time = ProxySystem.getInstance().playerManager.getOnlineTime(UUIDFetcher.getUUID(playername));
            boolean hour = (time >= 60);

            if (hour) {
                time /= 60;
                message = message.replace("%target-onlinetime%", String.valueOf(time));
            }

            message = message.replace("%target-onlinetime%", String.valueOf(time));
        }

        return message;
    }
    
    public String getLocalizedMessage(final UUID playerUUID, final String messageKey) {
        String playerLanguage = getLanguage(playerUUID);

        String message = getMessage(messageKey, playerLanguage);
        
        if (message == null) {
            message = getMessage(messageKey, "de");
        }

        if (message.contains("%player-name%")) {
            final ProxiedPlayer player = ProxySystem.getInstance().getProxy().getPlayer(playerUUID);
            message = message.replace("%player-name%", player.getName());
        }
        
        if (message.contains("%player-ping%")) {
            ProxiedPlayer player = ProxySystem.getInstance().getProxy().getPlayer(playerUUID);
            if (player != null) {
                String ping = String.valueOf(player.getPing());
                message = message.replace("%player-ping%", ping);
            } else {
                message = message.replace("%player-ping%", "");
            }
        }

        if (message.contains("%player-server%")) {
            ProxiedPlayer player = ProxySystem.getInstance().getProxy().getPlayer(playerUUID);
            message = message.replace("%player-server%", player.getServer().getInfo().getName());
        }

        if (message.contains("%systemTime%")) {
            LocalDateTime now = LocalDateTime.now();

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
            String formattedDateTime = now.format(formatter);

            message = message.replace("%systemTime%", formattedDateTime);
        }

        if (message.contains("%maxPlayer%")) {
            message = message.replace("%maxPlayer%", String.valueOf(100));
        }

        if (message.contains("%playercount%")) {
            message = message.replace("%playercount%", String.valueOf(ProxySystem.getInstance().getProxy().getOnlineCount()));
        }

        if (message.contains("%teamcount%")) {
            message = message.replace("%teamcount%", String.valueOf(ProxySystem.getInstance().teamManager.getTeamUUIDs().size()));
        }

        if (message.contains("%report-count%")) {
            message = message.replace("%report-count%", String.valueOf(ProxySystem.getInstance().reportManager.getReportedUUIDs().size()));
        }

        if (message.contains("%player-count%")) {
            message = message.replace("%player-count%", String.valueOf(ProxySystem.getInstance().serverManager.getPlayerCount()));
        }

        if (message.contains("%player-coins%")) {
            message = message.replace("%player-coins%", String.valueOf(ProxySystem.getInstance().playerManager.getCoins(playerUUID)));
        }

        if (message.contains("%player-onlinetime%")) {
            int time = ProxySystem.getInstance().playerManager.getOnlineTime(playerUUID);
            boolean hour = (time >= 60);

            if (hour) {
                time /= 60;
                message = message.replace("%player-onlinetime%", String.valueOf(time));
            }

            message = message.replace("%player-onlinetime%", String.valueOf(time));
        }

        return message;
    }

    public String getLocalizedMessageBanMute(final UUID playerUUID, final String messageKey, final UUID uuid) {
        String playerLanguage = getLanguage(playerUUID);

        String message = getMessage(messageKey, playerLanguage);

        if (message == null) {
            message = getMessage(messageKey, "de");
        }

        if (message.contains("%ban-reason%")) {
            message = message.replace("%ban-reason%", ProxySystem.getInstance().banManager.getBanReason(uuid));
        }

        if (message.contains("%ban-duration%")) {
            message = message.replace("%ban-duration%", ProxySystem.getInstance().banManager.getRemainingTime(uuid));
        }

        if (message.contains("%ban-banid%")) {
            message = message.replace("%ban-banid%", String.valueOf(ProxySystem.getInstance().banManager.getBanID(uuid)));
        }

        if (message.contains("%ban-banner%")) {
            message = message.replace("%ban-banner%", ProxySystem.getInstance().banManager.getBanBannedBy(uuid));
        }

        return message;
    }

    public String getLocalizedMessages(final UUID playerUUID, final String messageKey, final String messages) {
        String playerLanguage = getLanguage(playerUUID);

        String message = getMessage(messageKey, playerLanguage);

        if (message == null) {
            message = getMessage(messageKey, "de");
        }

        if (message.contains("%broadcast%")) {
            message = message.replace("%broadcast%", messages);
        }

        return message;
    }

    public String getLocalizedMessagePlayerArgument(final UUID playerUUID, final String messageKey, final String name) {
        String playerLanguage = getLanguage(playerUUID);

        String message = getMessage(messageKey, playerLanguage);

        if (message == null) {
            message = getMessage(messageKey, "de");
        }

        if (message.contains("%player-name%")) {
            message = message.replace("%player-name%", name);
        }

        if (message.contains("%target-name%")) {
            message = message.replace("%target-name%", name);
        }

        if (message.contains("%target-ping%")) {
            ProxiedPlayer target = ProxySystem.getInstance().getProxy().getPlayer(name);
            if (target != null) {
                String ping = String.valueOf(target.getPing());
                message = message.replace("%target-ping%", ping);
            } else {
                message = message.replace("%target-ping%", "");
            }
        }

        return message;
    }

    public String getLocalizedMessageOnUse(final UUID playerUUID, final String messageKey, final String command) {
        String playerLanguage = getLanguage(playerUUID);

        String message = getMessage(messageKey, playerLanguage);

        if (message == null) {
            message = getMessage(messageKey, "de");
        }

        if (message.contains("%command%")) {
            message = message.replace("%command%", command);
        }

        return message;
    }

    public String getLocalizedMessageOnUseArguments(final UUID playerUUID, final String messageKey, final String command, final String arguments) {
        String playerLanguage = getLanguage(playerUUID);

        String message = getMessage(messageKey, playerLanguage);

        if (message == null) {
            message = getMessage(messageKey, "de");
        }

        if (message.contains("%command%")) {
            message = message.replace("%command%", command);
        }

        if (message.contains("%arguments%")) {
            message = message.replace("%arguments%", arguments);
        }

        return message;
    }

}

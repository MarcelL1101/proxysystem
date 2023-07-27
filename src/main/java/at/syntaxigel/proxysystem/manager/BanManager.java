package at.syntaxigel.proxysystem.manager;

import at.syntaxigel.proxysystem.ProxySystem;
import at.syntaxigel.proxysystem.utils.UUIDFetcher;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class BanManager {

    public boolean isBanned(final UUID uuid) {
        boolean isBanend = false;

        try (Connection connection = ProxySystem.getInstance().mysql.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement("SELECT COUNT(*) FROM ban WHERE uuid = ?;")) {
            preparedStatement.setString(1, uuid.toString());
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                isBanend = resultSet.getInt(1) == 1 ? true : false;
                return isBanend;
            }

            preparedStatement.close();
            resultSet.close();
        } catch (SQLException sqlException) {
            throw new RuntimeException(sqlException);
        }

        return isBanend;
    }

    @SuppressWarnings("deprecation")
    public void ban(UUID uuid, final String reason, final long seconds, final UUID bannedByUUID) {
        long end = 0;

        if (seconds == -1) {
            end = -1;
        } else {
            long current = System.currentTimeMillis();
            long millis = seconds * 1000;
            end = current + millis;
        }

        try (Connection connection = ProxySystem.getInstance().mysql.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO ban(uuid, reason, ende, bannedByUUID) VALUES (?, ?, ?, ?);")) {
            preparedStatement.setString(1, uuid.toString());
            preparedStatement.setString(2, reason);
            preparedStatement.setLong(3, end);
            preparedStatement.setString(4, bannedByUUID.toString());
            preparedStatement.execute();
        } catch (SQLException sqlException) {
            throw new RuntimeException(sqlException);
        }

        if (ProxySystem.getInstance().getProxy().getPlayer(uuid) != null) {
            String messageKeyBanID = "banID";
            String localizedMessageBanID = ProxySystem.getInstance().langeLanguageManager.getLocalizedMessage(uuid, messageKeyBanID);
            String messageKeyBanReason = "banReason";
            String localizedMessageBanReason = ProxySystem.getInstance().langeLanguageManager.getLocalizedMessage(uuid, messageKeyBanReason);
            String messageKeyBannedBy = "banBannedby";
            String localizedMessageBannedBy = ProxySystem.getInstance().langeLanguageManager.getLocalizedMessage(uuid, messageKeyBannedBy);
            String messageKeyBanDuration = "banDuration";
            String localizedMessageBanDuration = ProxySystem.getInstance().langeLanguageManager.getLocalizedMessage(uuid, messageKeyBanDuration);
            String messageKeyLastLine = "banMessageLastLine";
            String localizedMessageLastLine = ProxySystem.getInstance().langeLanguageManager.getLocalizedMessage(uuid, messageKeyLastLine);
            String messageKeyFirstLine = "banMessageFirstLine";
            String localizedMessageFirstLine = ProxySystem.getInstance().langeLanguageManager.getLocalizedMessage(uuid, messageKeyFirstLine);

            ProxySystem.getInstance().getProxy().getPlayer(uuid).disconnect(localizedMessageFirstLine + "\n\n" + localizedMessageBanID + " \n" + localizedMessageBanReason + "\n" + localizedMessageBannedBy + "\n" + localizedMessageBanDuration + "\n\n" + localizedMessageLastLine);
        }
    }

    public Integer getBanID(final UUID uuid) {
        try (Connection connection = ProxySystem.getInstance().mysql.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement("SELECT banID FROM ban WHERE uuid = ?;")) {
            preparedStatement.setString(1, uuid.toString());
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt("banID");
            }

        } catch (SQLException sqlException) {
            throw new RuntimeException(sqlException);
        }

        return -1;
    }

    public void unBan(final UUID uuid) {
        try (Connection connection = ProxySystem.getInstance().mysql.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM ban WHERE uuid = ?;")) {
            preparedStatement.setString(1, uuid.toString());
            preparedStatement.execute();
        } catch (SQLException sqlException) {
            throw new RuntimeException(sqlException);
        }
    }

    public String getBanReason(final UUID uuid) {
        try (Connection connection = ProxySystem.getInstance().mysql.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM ban WHERE uuid = ?;")) {
            preparedStatement.setString(1, uuid.toString());
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getString("reason");
            }

            preparedStatement.close();
            resultSet.close();
        } catch (SQLException sqlException) {
            throw new RuntimeException(sqlException);
        }

        return ProxySystem.getInstance().configManager.getMessagePrefix() + "Der Spieler §e" + UUIDFetcher.getName(uuid) + " §7ist derzeit §cnicht §7gebannt.";
    }

    public String getBanBannedBy(final UUID uuid) {
        try (Connection connection = ProxySystem.getInstance().mysql.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM ban WHERE uuid = ?;")) {
            preparedStatement.setString(1, uuid.toString());
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getString("bannedByUUID");
            }

            preparedStatement.close();
            resultSet.close();
        } catch (SQLException sqlException) {
            throw new RuntimeException(sqlException);
        }

        return ProxySystem.getInstance().configManager.getMessagePrefix() + "Der Spieler §e" + UUIDFetcher.getName(uuid) + " §7ist derzeit §cnicht §7gebannt.";
    }

    public long getBanEnd(final UUID uuid) {
        try (Connection connection = ProxySystem.getInstance().mysql.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM ban WHERE uuid = ?;")) {
            preparedStatement.setString(1, uuid.toString());
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getLong("ende");
            }

            preparedStatement.close();
            resultSet.close();
        } catch (SQLException sqlException) {
            throw new RuntimeException(sqlException);
        }

        return -1;
    }

    public List<String> getBannedUUIDs() {
        final List<String> uuids = new ArrayList<>();

        try (Connection connection = ProxySystem.getInstance().mysql.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM ban;")) {
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                uuids.add(resultSet.getString("uuid"));
            }
        } catch (SQLException sqlException) {
            throw new RuntimeException(sqlException);
        }

        return uuids;
    }

    public String getRemainingTime(final UUID uuid) {
        long current = System.currentTimeMillis();
        long end = getBanEnd(uuid);

        long millis = end - current;
        long seconds = 0;
        long minutes = 0;
        long hours = 0;
        long days = 0;
        long weeks = 0;
        long months = 0;
        long years = 0;

        while (millis > 1000) {
            millis -= 1000;
            ++seconds;
        }

        while (seconds > 60) {
            seconds -= 60;
            ++minutes;
        }

        while (minutes > 60) {
            minutes -= 60;
            ++hours;
        }

        while (hours > 24) {
            hours -= 24;
            ++days;
        }

        while (days > 7) {
            days -= 7;
            ++weeks;
        }

        while (weeks > 4) {
            weeks -= 4;
            ++months;
        }

        while (months > 12) {
            months -= 12;
            ++years;
        }

        return "§e" + years + " §7Jahr(e) §e" + months + " §7Monat(e) §e" + weeks + " §7Woche(n) §e" + days + " §7Tag(e) §e" + hours + " §7Stunde(n) §e" + minutes + " §7Minute(n) §e" + seconds + " §7Sekunde(n)";
    }

}

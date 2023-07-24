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
    public void ban(UUID uuid, final String bannedName, final String reason, final long seconds, final UUID bannedByUUID, final String bannedByName) {
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
            ProxySystem.getInstance().getProxy().getPlayer(uuid).disconnect(ProxySystem.getInstance().configManager.getMessageNoPlayer() + "Du wurdest von unserem Netzwerk gesperrt! \n\n §8» §7Ban ID §8| §e " + getBanID(uuid) + " \n §8» §7Grund §8|§e " + reason + "\n §8» §7Gebannt von §8|§e " + UUIDFetcher.getName(bannedByUUID) + "\n §8» §7Dauer §8|§e " + getRemainingTime(uuid) + "\n\n §8» §7Falls der Ban §cnicht §7gerechtfertigt ist dann kannst du einen Antrag im Forum stellen unter §ehttps://vanaty.de");
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

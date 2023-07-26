package at.syntaxigel.proxysystem.manager;

import at.syntaxigel.proxysystem.ProxySystem;
import at.syntaxigel.proxysystem.utils.UUIDFetcher;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;

public class TeamManager {

    public boolean isInTeam(final UUID uuid) {
        boolean existsPlayer = false;

        try (Connection connection = ProxySystem.getInstance().mysql.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement("SELECT COUNT(*) FROM team WHERE uuid = ?;")) {
            preparedStatement.setString(1, uuid.toString());
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                existsPlayer = resultSet.getInt(1) == 1 ? true : false;
                return existsPlayer;
            }

            preparedStatement.close();
            resultSet.close();
        } catch (SQLException sqlException) {
            ProxySystem.getInstance().logger().log(Level.WARNING, ProxySystem.getInstance().configManager.getMessagePrefix() + " Es ist ein Fehler aufgetreten beim abfragen ob der Spieler im Team ist. Fehler: §c" + sqlException);
        }

        return existsPlayer;
    }

    public void addToTeam(final UUID uuid) {
        try (Connection connection = ProxySystem.getInstance().mysql.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO team(uuid, ban, mute, report) VALUES (?, 0, 0, 0);")) {
            preparedStatement.setString(1, uuid.toString());
            preparedStatement.execute();
        } catch (SQLException sqlException) {
            ProxySystem.getInstance().logger().log(Level.WARNING, ProxySystem.getInstance().configManager.getMessagePrefix() + " Es ist ein Fehler aufgetreten beim anlegen von dem Team Mitglied §3" + UUIDFetcher.getName(uuid) + "§7. Fehler: §c" + sqlException);
        }
    }

    public Integer getBans(final UUID uuid) {
        try (Connection connection = ProxySystem.getInstance().mysql.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement("SELECT ban FROM team WHERE uuid = ?;")) {
            preparedStatement.setString(1, uuid.toString());
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt("ban");
            }

        } catch (SQLException sqlException) {
            throw new RuntimeException(sqlException);
        }

        return -1;
    }

    public Integer getMutes(final UUID uuid) {
        try (Connection connection = ProxySystem.getInstance().mysql.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement("SELECT mute FROM team WHERE uuid = ?;")) {
            preparedStatement.setString(1, uuid.toString());
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt("mute");
            }

        } catch (SQLException sqlException) {
            throw new RuntimeException(sqlException);
        }

        return -1;
    }

    public Integer getReports(final UUID uuid) {
        try (Connection connection = ProxySystem.getInstance().mysql.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement("SELECT report FROM team WHERE uuid = ?;")) {
            preparedStatement.setString(1, uuid.toString());
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt("report");
            }

        } catch (SQLException sqlException) {
            throw new RuntimeException(sqlException);
        }

        return -1;
    }

    public List<String> getTeamUUIDs() {
        final List<String> uuids = new ArrayList<>();

        try (Connection connection = ProxySystem.getInstance().mysql.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM team;")) {
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                uuids.add(resultSet.getString("uuid"));
            }
        } catch (SQLException sqlException) {
            throw new RuntimeException(sqlException);
        }

        return uuids;
    }

}

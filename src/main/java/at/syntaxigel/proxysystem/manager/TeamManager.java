package at.syntaxigel.proxysystem.manager;

import at.syntaxigel.proxysystem.ProxySystem;
import at.syntaxigel.proxysystem.utils.UUIDFetcher;
import net.md_5.bungee.api.connection.ProxiedPlayer;

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

    public void addBan(final UUID uuid) {
        try (Connection connection = ProxySystem.getInstance().mysql.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement("UPDATE team SET ban = ? WHERE uuid = ?;")) {
            preparedStatement.setString(1, uuid.toString());
            preparedStatement.setInt(2, getBans(uuid) + 1);
            preparedStatement.execute();
        } catch (SQLException sqlException) {
            ProxySystem.getInstance().logger().log(Level.WARNING, ProxySystem.getInstance().configManager.getMessagePrefix() + " Es ist ein Fehler aufgetreten beim updaten der Team Tabelle. Fehler §c" + sqlException);
        }
    }

    public void addMute(final UUID uuid) {
        try (Connection connection = ProxySystem.getInstance().mysql.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement("UPDATE team SET mute = ? WHERE uuid = ?;")) {
            preparedStatement.setString(1, uuid.toString());
            preparedStatement.setInt(2, getMutes(uuid) + 1);
            preparedStatement.execute();
        } catch (SQLException sqlException) {
            ProxySystem.getInstance().logger().log(Level.WARNING, ProxySystem.getInstance().configManager.getMessagePrefix() + " Es ist ein Fehler aufgetreten beim updaten der Team Tabelle. Fehler §c" + sqlException);
        }
    }

    public void addReport(final UUID uuid) {
        try (Connection connection = ProxySystem.getInstance().mysql.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement("UPDATE team SET report = ? WHERE uuid = ?;")) {
            preparedStatement.setString(1, uuid.toString());
            preparedStatement.setInt(2, getReports(uuid) + 1);
            preparedStatement.execute();
        } catch (SQLException sqlException) {
            ProxySystem.getInstance().logger().log(Level.WARNING, ProxySystem.getInstance().configManager.getMessagePrefix() + " Es ist ein Fehler aufgetreten beim updaten der Team Tabelle. Fehler §c" + sqlException);
        }
    }

    public void removeFromTeam(final UUID uuid) {
        try (Connection connection = ProxySystem.getInstance().mysql.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM team WHERE uuid = ?;")) {
            preparedStatement.setString(1, uuid.toString());
            preparedStatement.execute();
        } catch (SQLException sqlException) {
            ProxySystem.getInstance().logger().log(Level.WARNING, ProxySystem.getInstance().configManager.getMessagePrefix() + " Es ist ein Fehler aufgetreten beim löschen vom Team Mitglied. Fehler: §c" + sqlException);
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
            ProxySystem.getInstance().logger().log(Level.WARNING, ProxySystem.getInstance().configManager.getMessagePrefix() + "Es ist ein Fehler aufgetreten beim Abfragen von den Bans von dem Spieler §3" + UUIDFetcher.getName(uuid) + "§7. Fehler: §c" + sqlException);
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
            ProxySystem.getInstance().logger().log(Level.WARNING, ProxySystem.getInstance().configManager.getMessagePrefix() + "Es ist ein Fehler aufgetreten beim Abfragen von den Mutes von dem Spieler §3" + UUIDFetcher.getName(uuid) + "§7. Fehler: §c" + sqlException);
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
            ProxySystem.getInstance().logger().log(Level.WARNING, ProxySystem.getInstance().configManager.getMessagePrefix() + "Es ist ein Fehler aufgetreten beim Abfragen von den Reports von dem Spieler §3" + UUIDFetcher.getName(uuid) + "§7. Fehler: §c" + sqlException);
        }

        return -1;
    }

    public Integer getCommandSpy(final UUID uuid) {
        try (Connection connection = ProxySystem.getInstance().mysql.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement("SELECT commandSpy FROM team WHERE uuid = ?:")) {
            preparedStatement.setString(1, uuid.toString());
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt("commandSpy");
            }
        } catch (SQLException sqlException) {
            ProxySystem.getInstance().logger().log(Level.WARNING, ProxySystem.getInstance().configManager.getMessagePrefix() + "Es ist ein Fehler aufgetreten beim Abfragen ob Spieler §3" + UUIDFetcher.getName(uuid) + "§7 den Wert CommandSpy aktiviert hat oder nicht. Fehler: §c" + sqlException);
        }

        return -1;
    }

    public void changeCommandSpy(final UUID uuid, final Integer value) {
        try (Connection connection = ProxySystem.getInstance().mysql.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement("UPDATE team SET commandSpy = ? WHERE uuid = ?;")) {
            preparedStatement.setInt(1, value);
            preparedStatement.setString(2, uuid.toString());
            preparedStatement.execute();
        } catch (SQLException sqlException) {
            ProxySystem.getInstance().logger().log(Level.WARNING, ProxySystem.getInstance().configManager.getMessagePrefix() + "Es konnte der CommandSpy nicht geändert werden. Fehler: §c" + sqlException);
        }
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

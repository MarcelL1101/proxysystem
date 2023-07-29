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

public class ReportManager {

    public void createReport(final UUID reportedUUID, final String reportedName, final UUID reporterUUID, final String reporterName, final String reason, final String server) {
        try (Connection connection = ProxySystem.getInstance().mysql.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO report(reportedUUID, reportedName, reporterUUID, reporterName, reason, server) VALUES (?, ?, ?, ?);")) {
            preparedStatement.setString(1, reportedUUID.toString());
            preparedStatement.setString(2, reportedName);
            preparedStatement.setString(3, reporterUUID.toString());
            preparedStatement.setString(4, reporterName);
            preparedStatement.setString(5, reason);
            preparedStatement.setString(6, server);
            preparedStatement.execute();
        } catch (SQLException sqlException) {
            ProxySystem.getInstance().logger().log(Level.WARNING, ProxySystem.getInstance().configManager.getMessagePrefix() + "Es ist ein Fehler aufgetreten beim erstellen vom Report. Fehler: §c" + sqlException);
        }
    }

    public List<String> getReportedUUIDs() {
        final List<String> uuids = new ArrayList<>();

        try (Connection connection = ProxySystem.getInstance().mysql.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM report;")) {
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                uuids.add(resultSet.getString("reportedUUID"));
            }
        } catch (SQLException sqlException) {
            throw new RuntimeException(sqlException);
        }

        return uuids;
    }

    public String getReportedName(final UUID reportedUUID) {
        try (Connection connection = ProxySystem.getInstance().mysql.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement("SELECT reportedName FROM report WHERE reportedUUID = ?;")) {
            preparedStatement.setString(1, reportedUUID.toString());
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getString("reportedName");
            }

            preparedStatement.close();
            resultSet.close();
        } catch (SQLException sqlException) {
            throw new RuntimeException(sqlException);
        }

        return ProxySystem.getInstance().configManager.getMessagePrefix() + " Der Spieler §e" + UUIDFetcher.getName(reportedUUID) + " §7wurde §cnicht §7reported.";
    }

    public String getReporterName(final UUID reportedUUID) {
        try (Connection connection = ProxySystem.getInstance().mysql.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement("SELECT reporterName FROM report WHERE reportedUUID = ?;")) {
            preparedStatement.setString(1, reportedUUID.toString());
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getString("reporterName");
            }

            preparedStatement.close();
            resultSet.close();
        } catch (SQLException sqlException) {
            throw new RuntimeException(sqlException);
        }

        return ProxySystem.getInstance().configManager.getMessagePrefix() + " Der Spieler §e" + UUIDFetcher.getName(reportedUUID) + " §7wurde §cnicht §7reported.";
    }

    public String getReporterUUID(final UUID reportedUUID) {
        try (Connection connection = ProxySystem.getInstance().mysql.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement("SELECT reporterUUID FROM report WHERE reportedUUID = ?;")) {
            preparedStatement.setString(1, reportedUUID.toString());
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getString("reporterUUID");
            }

            preparedStatement.close();
            resultSet.close();
        } catch (SQLException sqlException) {
            throw new RuntimeException(sqlException);
        }

        return ProxySystem.getInstance().configManager.getMessagePrefix() + " Der Spieler §e" + UUIDFetcher.getName(reportedUUID) + " §7wurde §cnicht §7reported.";
    }

    public String getReportedUUID(final UUID reporterUUID) {
        try (Connection connection = ProxySystem.getInstance().mysql.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement("SELECT reportedUUID FROM report WHERE reporterUUID = ?;")) {
            preparedStatement.setString(1, reporterUUID.toString());
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getString("reportedUUID");
            }

            preparedStatement.close();
            resultSet.close();
        } catch (SQLException sqlException) {
            throw new RuntimeException(sqlException);
        }

        return ProxySystem.getInstance().configManager.getMessagePrefix() + " Der Spieler §e" + UUIDFetcher.getName(reporterUUID) + " §7wurde §cnicht §7reported.";
    }

    public String getReportReason(final UUID reportedUUID) {
        try (Connection connection = ProxySystem.getInstance().mysql.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement("SELECT reason FROM report WHERE reportedUUID = ?;")) {
            preparedStatement.setString(1, reportedUUID.toString());
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getString("reason");
            }

            preparedStatement.close();
            resultSet.close();
        } catch (SQLException sqlException) {
            throw new RuntimeException(sqlException);
        }

        return ProxySystem.getInstance().configManager.getMessagePrefix() + " Der Spieler §e" + UUIDFetcher.getName(reportedUUID) + " §7wurde §cnicht §7reported.";
    }

    public String getReportServer(final UUID reportedUUID) {
        try (Connection connection = ProxySystem.getInstance().mysql.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement("SELECT server FROM report WHERE reportedUUID = ?;")) {
            preparedStatement.setString(1, reportedUUID.toString());
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getString("server");
            }

            preparedStatement.close();
            resultSet.close();
        } catch (SQLException sqlException) {
            throw new RuntimeException(sqlException);
        }

        return ProxySystem.getInstance().configManager.getMessagePrefix() + " Der Spieler §e" + UUIDFetcher.getName(reportedUUID) + " §7wurde §cnicht §7reported.";
    }

    public Integer getReportID(final UUID repotedUUID) {
        try (Connection connection = ProxySystem.getInstance().mysql.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement("SELECT reportID FROM report WHERE reportedUUID = ?;")) {
            preparedStatement.setString(1, repotedUUID.toString());
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt("reportID");
            }

            preparedStatement.close();
            resultSet.close();
        } catch (SQLException sqlException) {
            throw new RuntimeException(sqlException);
        }

        return -1;
    }

    public void deleteReport(final Integer id) {
        try (Connection connection = ProxySystem.getInstance().mysql.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM report WHERE reportID = ?;")) {
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
        } catch (SQLException sqlException) {
            throw new RuntimeException(sqlException);
        }
    }

}

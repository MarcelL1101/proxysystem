package at.syntaxigel.proxysystem.manager;

import at.syntaxigel.proxysystem.ProxySystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;

public class ChatFilterAPI {

    public void addWord(final String word) {
        try (Connection connection = ProxySystem.getInstance().mysql.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO team(word) VALUES (?);")) {
            preparedStatement.setString(1, word);
            preparedStatement.execute();
        } catch (SQLException sqlException) {
            ProxySystem.getInstance().logger().log(Level.WARNING, ProxySystem.getInstance().configManager.getMessagePrefix() + " Es ist ein Fehler aufgetreten beim hinzufügen vom Word §3" + word + "§7. Fehler: §c" + sqlException);
        }
    }

    public String getWord(final String word) {
        try (Connection connection = ProxySystem.getInstance().mysql.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement("SELECT word FROM chatfilter WHERE reportedUUID = ?;")) {
            preparedStatement.setString(1, word);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getString("reporterUUID");
            }

            preparedStatement.close();
            resultSet.close();
        } catch (SQLException sqlException) {
            throw new RuntimeException(sqlException);
        }

        return ProxySystem.getInstance().configManager.getMessagePrefix() + " Es konnte das Wort §3" + word + " §7aus der Datenbank §cnicht §7gefunden werden.";
    }

}

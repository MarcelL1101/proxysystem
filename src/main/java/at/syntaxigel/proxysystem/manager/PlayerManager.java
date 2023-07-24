package at.syntaxigel.proxysystem.manager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;
import java.util.logging.Level;

import at.syntaxigel.proxysystem.ProxySystem;

public class PlayerManager {
	
	public boolean existsPlayer(final UUID uuid) {
        boolean existsPlayer = false;

        try (Connection connection = ProxySystem.getInstance().mysql.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement("SELECT COUNT(*) FROM player WHERE uuid = ?;")) {
            preparedStatement.setString(1, uuid.toString());
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
            	existsPlayer = resultSet.getInt(1) == 1 ? true : false;
                return existsPlayer;
            }

            preparedStatement.close();
            resultSet.close();
        } catch (SQLException sqlException) {
            ProxySystem.getInstance().logger().log(Level.WARNING, ProxySystem.getInstance().configManager.getMessagePrefix() + " Es ist ein Fehler aufgetreten beim abfragen ob der Spieler existiert. Fehler: §c" + sqlException);
        }

        return existsPlayer;
    }
	
	public void createPlayer(final UUID uuid) {
		try (Connection connection = ProxySystem.getInstance().mysql.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO player(uuid, onlineTime, lastSeen, coins, language) VALUES (?, ?, ?, ?, ?);")) {
			preparedStatement.setString(1, uuid.toString());
			preparedStatement.setInt(2, 0);
			preparedStatement.setString(3, "null");
			preparedStatement.setDouble(4, ProxySystem.getInstance().configManager.getCoinsStartValue());
			preparedStatement.setString(5, "de");
			preparedStatement.execute();
		} catch (SQLException sqlException) {
			ProxySystem.getInstance().logger().log(Level.WARNING, ProxySystem.getInstance().configManager.getMessagePrefix() + " Es ist ein Fehler aufgetreten beim erstellen des Spielers. Fehler: §c" + sqlException);
		}
	}

}

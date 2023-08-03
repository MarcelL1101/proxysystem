package at.syntaxigel.proxysystem.manager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;
import java.util.logging.Level;

import at.syntaxigel.proxysystem.ProxySystem;
import at.syntaxigel.proxysystem.utils.UUIDFetcher;

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

    public String getUsername(final UUID uuid) {
        try (Connection connection = ProxySystem.getInstance().mysql.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement("SELECT username FROM player WHERE uuid = ?;")) {
            preparedStatement.setString(1, uuid.toString());
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getString("username");
            }

            preparedStatement.close();
            resultSet.close();
        } catch (SQLException sqlException) {
            ProxySystem.getInstance().logger().log(Level.WARNING, ProxySystem.getInstance().configManager.getMessagePrefix() + "Es ist ein Fehler aufgetreten beim Abfragen vom Benutzernamen. Fehler: §c" + sqlException);
        }

        return ProxySystem.getInstance().configManager.getMessagePrefix() + "Benutzername vom Spieler §3" + uuid + " §7wurde §cnicht §7gefunden.";
    }

    public Double getCoins(final UUID uuid) {
        try (Connection connection = ProxySystem.getInstance().mysql.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement("SELECT coins FROM player WHERE uuid = ?;")) {
            preparedStatement.setString(1, uuid.toString());
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getDouble("coins");
            }

            preparedStatement.close();
            resultSet.close();
        } catch (SQLException sqlException) {
            ProxySystem.getInstance().logger().log(Level.WARNING, ProxySystem.getInstance().configManager.getMessagePrefix() + "Es ist ein Fehler aufgetreten beim Abfragen von den Coins von dem Spieler §3" + UUIDFetcher.getName(uuid) + "§7. Fehler: §c" + sqlException);
        }

        return -1.0;
    }

    public Integer getOnlineTime(final UUID uuid) {
        try (Connection connection = ProxySystem.getInstance().mysql.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement("SELECT onlineTime FROM player WHERE uuid = ?;")) {
            preparedStatement.setString(1, uuid.toString());
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt("onlineTime");
            }

            preparedStatement.close();
            resultSet.close();
        } catch (SQLException sqlException) {
            throw new RuntimeException(sqlException);
        }

        return 0;
    }

    public void updateTime(final UUID uuid) {
        try (Connection connection = ProxySystem.getInstance().mysql.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement("UPDATE player SET onlineTime = ? WHERE uuid = ?;")) {
            preparedStatement.setInt(1, (getOnlineTime(uuid) + 1));
            preparedStatement.setString(2, uuid.toString());
            preparedStatement.execute();
        } catch (SQLException sqlException) {
            throw new RuntimeException(sqlException);
        }
    }

}

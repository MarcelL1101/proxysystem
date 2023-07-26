package at.syntaxigel.proxysystem.manager;

import at.syntaxigel.proxysystem.ProxySystem;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;
import java.util.logging.Level;

public class ServerManager {

    public Integer getGlobalmute() {
        try (Connection connection = ProxySystem.getInstance().mysql.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement("SELECT globalmute FROM globalmute;")) {
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt("banID");
            }

        } catch (SQLException sqlException) {
            ProxySystem.getInstance().logger().log(Level.WARNING, ProxySystem.getInstance().configManager.getMessagePrefix() + "Es konnte der Wert von der Globalmute Datenbank abgefragt werden. Fehler: §c" + sqlException);
        }

        return -1;
    }

    @SuppressWarnings("deprecation")
    public void changeGlobalMute(final ProxiedPlayer player) {
        if (getGlobalmute() == 0) {
            try (Connection connection = ProxySystem.getInstance().mysql.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement("UPDATE globalmute SET globalmute = 1 WHERE globalmute = 0;")) {
                preparedStatement.execute();
                String messageKey = "globalmuteOn";
                String localizedMessage = ProxySystem.getInstance().langeLanguageManager.getLocalizedMessage(player.getUniqueId(), messageKey);
                player.sendMessage(localizedMessage);
            } catch (SQLException sqlException) {
                ProxySystem.getInstance().logger().log(Level.WARNING, ProxySystem.getInstance().configManager.getMessagePrefix() + " Es konnte der Wert von dem GlobalMute §cnicht §7geändert werden. Fehler: §c" + sqlException);
            }
        } else {
            try (Connection connection = ProxySystem.getInstance().mysql.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement("UPDATE globalmute SET globalmute = 0 WHERE globalmute = 1;")) {
                preparedStatement.execute();
                String messageKey = "globalmuteOff";
                String localizedMessage = ProxySystem.getInstance().langeLanguageManager.getLocalizedMessage(player.getUniqueId(), messageKey);
                player.sendMessage(localizedMessage);
            } catch (SQLException sqlException) {
                ProxySystem.getInstance().logger().log(Level.WARNING, ProxySystem.getInstance().configManager.getMessagePrefix() + " Es konnte der Wert von dem GlobalMute §cnicht §7geändert werden. Fehler: §c" + sqlException);
            }
        }
    }

    public int getPlayerCount() {
        int playerCount = 0;
        try (Connection connection = ProxySystem.getInstance().mysql.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement("SELECT COUNT(*) as player_count FROM player")) {
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                playerCount = resultSet.getInt("player_count");
            }

        } catch (SQLException sqlException) {
            ProxySystem.getInstance().logger().log(Level.WARNING, ProxySystem.getInstance().configManager.getMessagePrefix() + " Es konnte nicht abgefragt werden wie viele Spieler existierten. Fehler: §c" + sqlException);
        }

        return playerCount;
    }

}

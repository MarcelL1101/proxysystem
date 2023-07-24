package at.syntaxigel.proxysystem.mysql;

import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;

import com.google.common.io.CharStreams;

import at.syntaxigel.proxysystem.ProxySystem;

public class MySQL {

	public String hostname;
	public Integer port;
	public String username;
	public String database;
	public String password;

	private Connection connection;
	
	public MySQL(final String hostname, final Integer port, final String username, final String database, final String password) {
		this.hostname = hostname;
		this.port = port;
		this.username = username;
		this.database = database;
		this.password = password;
	}
	
	public Connection openConnection() {
		try {
			connection = DriverManager.getConnection("jdbc:mysql://" + hostname + ":" + port + "/" + database + "?autoReconnect=true&useUnicode=yes", username, password);
			ProxySystem.getInstance().logger().log(Level.INFO, ProxySystem.getInstance().configManager.getMessagePrefix() + " Die Verbindung zur MySQL Datenbank war §eerfolgreich§7.");
		} catch (SQLException sqlException) {
			throw new RuntimeException(sqlException);
		}

		return connection;
	}
	
	public void closeConnection() {
		if(connection != null) {
			connection = null;
			ProxySystem.getInstance().logger().log(Level.INFO, ProxySystem.getInstance().configManager.getMessagePrefix() + " Die Trennung von der Datenbank war §eerfolgreich§7.");
		}
	}
	
	public Connection getConnection() {
		try {
			openConnection();
			return connection;
		} catch (Exception exception) {
			throw new RuntimeException(exception);
		}
	}
	
	public void createTables() throws Exception {
		Statement s;
		
		s = getConnection().createStatement();
		
		if(ProxySystem.getInstance().getResourceAsStream("databaseSetup.sql") == null) {
			ProxySystem.getInstance().logger().log(Level.WARNING, ProxySystem.getInstance().configManager.getMessagePrefix() + "Die Datei database.sql konnte §cnicht §7gefunden werden.");
            return;
		}
		
		String[] queries = CharStreams.toString(new InputStreamReader(ProxySystem.getInstance().getResourceAsStream("databaseSetup.sql"))).split(";");
		for(String query : queries) {
			if(query != null && query != "") {
				s.execute(query);
			}
		}
	}
	
}

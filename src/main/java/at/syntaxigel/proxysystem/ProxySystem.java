package at.syntaxigel.proxysystem;

import java.io.File;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import at.syntaxigel.proxysystem.commands.*;
import at.syntaxigel.proxysystem.config.ConfigManager;
import at.syntaxigel.proxysystem.config.MySQLConfigManager;
import at.syntaxigel.proxysystem.listener.PlayerListener;
import at.syntaxigel.proxysystem.listener.ServerListener;
import at.syntaxigel.proxysystem.manager.*;
import at.syntaxigel.proxysystem.mysql.MySQL;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Plugin;

public class ProxySystem extends Plugin {
	
	private static ProxySystem instance;
	
	public ConfigManager configManager;
	public MySQLConfigManager mySQLConfigManager;
	
	public LanguageManager langeLanguageManager;
	public PlayerManager playerManager;
	public BanManager banManager;
	public ServerManager serverManager;
	public TeamManager teamManager;
	public ReportManager reportManager;
	
	public MySQL mysql;
	
	@Override
	public void onEnable() {
		instance = this;
		
		configManager = new ConfigManager();
		mySQLConfigManager = new MySQLConfigManager();
		langeLanguageManager = new LanguageManager();
		playerManager = new PlayerManager();
		banManager = new BanManager();
		serverManager = new ServerManager();
		teamManager = new TeamManager();
		reportManager = new ReportManager();
		
		configManager.createConfig();
		mySQLConfigManager.createMySQLConfig();
		langeLanguageManager.createConfig();
		
		mysql = new MySQL(mySQLConfigManager.getMySQLHostname(), mySQLConfigManager.getMySQLPort(), mySQLConfigManager.getMySQLDatabase(), mySQLConfigManager.getMySQLUsername(), mySQLConfigManager.getMySQLPassword());
		mysql.openConnection();
		try {
			mysql.createTables();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		String headerKey = "header";
		String footerKey = "footer";

		getInstance().getProxy().getScheduler().schedule(this, () -> {
			for(final ProxiedPlayer all : getInstance().getProxy().getPlayers()) {
				playerManager.updateTime(all.getUniqueId());
			}
		}, 0L, 1L, TimeUnit.MINUTES);

		getInstance().getProxy().getScheduler().schedule(this, () -> {
			for(final ProxiedPlayer all : getInstance().getProxy().getPlayers()) {
				String headerMessage = ProxySystem.getInstance().langeLanguageManager.getLocalizedMessage(all.getUniqueId(), headerKey);
				String footerMessage = ProxySystem.getInstance().langeLanguageManager.getLocalizedMessage(all.getUniqueId(), footerKey);
				all.setTabHeader(new TextComponent(headerMessage), new TextComponent(footerMessage));
			}
		}, 0L, 1L, TimeUnit.SECONDS);

		loadCommands();
		loadListener();
		
		System.out.println(configManager.getMessagePrefix() + " Das ProxySystem wurde §aerfolgreich §7gestartet!");
		
	}

	@Override
	public void onDisable() {
		mysql.closeConnection();

		System.out.println(configManager.getMessagePrefix() + " Das ProxySystem wurde §aerfolgreich §7gestoppt!");
	}
	
	private void loadCommands() {
		ProxySystem.getInstance().getProxy().getPluginManager().registerCommand(this, new PingCommand("ping"));
		ProxySystem.getInstance().getProxy().getPluginManager().registerCommand(this, new OnlineCommand("online"));
		ProxySystem.getInstance().getProxy().getPluginManager().registerCommand(this, new OnlineCommand("players"));
		ProxySystem.getInstance().getProxy().getPluginManager().registerCommand(this, new OnlineCommand("count"));
		ProxySystem.getInstance().getProxy().getPluginManager().registerCommand(this, new DiscordCommand("discord"));
		ProxySystem.getInstance().getProxy().getPluginManager().registerCommand(this, new DiscordCommand("dc"));
		ProxySystem.getInstance().getProxy().getPluginManager().registerCommand(this, new ForumCommand("forum"));
		ProxySystem.getInstance().getProxy().getPluginManager().registerCommand(this, new LanguageCommand("language"));
		ProxySystem.getInstance().getProxy().getPluginManager().registerCommand(this, new GlobalMuteCommand("globalmute"));
		ProxySystem.getInstance().getProxy().getPluginManager().registerCommand(this, new WhereAmICommand("whereami"));
		ProxySystem.getInstance().getProxy().getPluginManager().registerCommand(this, new WhereIsCommand("whereis"));
		ProxySystem.getInstance().getProxy().getPluginManager().registerCommand(this, new TeamChatCommand("teamchat"));
		ProxySystem.getInstance().getProxy().getPluginManager().registerCommand(this, new TeamChatCommand("tc"));
		ProxySystem.getInstance().getProxy().getPluginManager().registerCommand(this, new AdminChatCommand("adminchat"));
		ProxySystem.getInstance().getProxy().getPluginManager().registerCommand(this, new AdminChatCommand("ac"));
		ProxySystem.getInstance().getProxy().getPluginManager().registerCommand(this, new BauserverCommand("bauserver"));
		ProxySystem.getInstance().getProxy().getPluginManager().registerCommand(this, new TeamCommand("team"));
		ProxySystem.getInstance().getProxy().getPluginManager().registerCommand(this, new GlobalClearCommand("globalclear"));
		ProxySystem.getInstance().getProxy().getPluginManager().registerCommand(this, new GlobalClearCommand("gcc"));
		ProxySystem.getInstance().getProxy().getPluginManager().registerCommand(this, new RegisteredPlayer("registered"));
		ProxySystem.getInstance().getProxy().getPluginManager().registerCommand(this, new BroadcastCommand("broadcast"));
		ProxySystem.getInstance().getProxy().getPluginManager().registerCommand(this, new BroadcastCommand("bc"));
		ProxySystem.getInstance().getProxy().getPluginManager().registerCommand(this, new BanCommand("ban"));
		ProxySystem.getInstance().getProxy().getPluginManager().registerCommand(this, new GetIPCommand("getip"));
		ProxySystem.getInstance().getProxy().getPluginManager().registerCommand(this, new OnlineTimeCommand("onlinetime"));
		ProxySystem.getInstance().getProxy().getPluginManager().registerCommand(this, new OnlineTimeCommand("ot"));
		ProxySystem.getInstance().getProxy().getPluginManager().registerCommand(this, new CoinsCommand("coins"));
		ProxySystem.getInstance().getProxy().getPluginManager().registerCommand(this, new CoinsCommand("money"));
		ProxySystem.getInstance().getProxy().getPluginManager().registerCommand(this, new CoinsCommand("coin"));
		ProxySystem.getInstance().getProxy().getPluginManager().registerCommand(this, new CoinsCommand("konto"));
		ProxySystem.getInstance().getProxy().getPluginManager().registerCommand(this, new CoinsCommand("wallet"));
		ProxySystem.getInstance().getProxy().getPluginManager().registerCommand(this, new CommandSpyCommand("commandspy"));
		ProxySystem.getInstance().getProxy().getPluginManager().registerCommand(this, new CommandSpyCommand("cmdspy"));
		ProxySystem.getInstance().getProxy().getPluginManager().registerCommand(this, new ReportCommand("report"));
		ProxySystem.getInstance().getProxy().getPluginManager().registerCommand(this, new ReportEditCommand("reportedit"));
		ProxySystem.getInstance().getProxy().getPluginManager().registerCommand(this, new UnbanCommand("unban"));
		ProxySystem.getInstance().getProxy().getPluginManager().registerCommand(this, new ServerCommand("manageserver"));
	}
	
	private void loadListener() {
		ProxySystem.getInstance().getProxy().getPluginManager().registerListener(this, new PlayerListener());
		ProxySystem.getInstance().getProxy().getPluginManager().registerListener(this, new ServerListener());
	}
	
	public static ProxySystem getInstance() {
		return instance;
	}
	
	public Logger logger() {
		return super.getLogger();
	}
	
}

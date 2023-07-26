package at.syntaxigel.proxysystem.config;

import java.io.File;
import java.io.IOException;

import at.syntaxigel.proxysystem.ProxySystem;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;


public class ConfigManager {
	
	private File file;
    private Configuration configuration;

    public void createConfig() {
        if (!(ProxySystem.getInstance().getDataFolder().exists())) {
            ProxySystem.getInstance().getDataFolder().mkdirs();
        }

        file = new File(ProxySystem.getInstance().getDataFolder(), "config.yml");

        if (!(file.exists())) {
            try {
                file.createNewFile();
                configuration = ConfigurationProvider.getProvider(YamlConfiguration.class).load(file);
                
                configuration.set("message.prefix", "&8[>>] &e&lProxySystem &8[|]&7");
                configuration.set("message.noPlayer", "%prefix% Du musst ein Spieler sein!");
                configuration.set("coins.startValue", 1500.0);
                configuration.set("message.teamchat", "&8[>>] &6Team&eChat &8[|] &3%player-name% &8[>>]&7 %player-message%");
                configuration.set("message.adminchat", "&8[>>] &4Admin&cChat &8[|] &3%player-name% &8[>>]&7 %player-message%");

                ConfigurationProvider.getProvider(YamlConfiguration.class).save(configuration, file);
            } catch (IOException ioException) {
                throw new RuntimeException(ioException);
            }
        }
    }

    public String getMessagePrefix() {
        try {
            configuration = ConfigurationProvider.getProvider(YamlConfiguration.class).load(file);
        } catch (IOException ioException) {
            throw new RuntimeException(ioException);
        }

        return configuration.getString("message.prefix").replaceAll("&", "§").replace("[>>]", "»").replace("[<<]", "«").replace("[|]", "|");
    } 
    
    public String getMessageNoPlayer() {
        try {
            configuration = ConfigurationProvider.getProvider(YamlConfiguration.class).load(file);
        } catch (IOException ioException) {
            throw new RuntimeException(ioException);
        }

        return configuration.getString("message.noPlayer").replaceAll("&", "§").replace("%prefix%", getMessagePrefix()).replace("[>>]", "»").replace("[<<]", "«").replace("[|]", "|");
    } 
    
    public Double getCoinsStartValue() {
    	try {
            configuration = ConfigurationProvider.getProvider(YamlConfiguration.class).load(file);
        } catch (IOException ioException) {
            throw new RuntimeException(ioException);
        }
    	
    	return configuration.getDouble("coins.startValue");
    }
    
    public String getMessageTeamChat(final String playername, final String message) {
        try {
            configuration = ConfigurationProvider.getProvider(YamlConfiguration.class).load(file);
        } catch (IOException ioException) {
            throw new RuntimeException(ioException);
        }

        return configuration.getString("message.teamchat").replaceAll("&", "§").replace("%prefix%", getMessagePrefix()).replace("[>>]", "»").replace("[<<]", "«").replace("[|]", "|").replace("%player-name%", playername).replace("%player-message%", message);
    }

    public String getMessageAdminChat(final String playername, final String message) {
        try {
            configuration = ConfigurationProvider.getProvider(YamlConfiguration.class).load(file);
        } catch (IOException ioException) {
            throw new RuntimeException(ioException);
        }

        return configuration.getString("message.adminchat").replaceAll("&", "§").replace("%prefix%", getMessagePrefix()).replace("[>>]", "»").replace("[<<]", "«").replace("[|]", "|").replace("%player-name%", playername).replace("%player-message%", message);
    }

}

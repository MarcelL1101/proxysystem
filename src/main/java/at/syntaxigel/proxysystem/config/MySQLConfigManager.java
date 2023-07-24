package at.syntaxigel.proxysystem.config;

import java.io.File;
import java.io.IOException;

import at.syntaxigel.proxysystem.ProxySystem;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

public class MySQLConfigManager {
	
	private File file;
    private Configuration configuration;

    public void createMySQLConfig() {
        if (!(ProxySystem.getInstance().getDataFolder().exists())) {
            ProxySystem.getInstance().getDataFolder().mkdirs();
        }

        file = new File(ProxySystem.getInstance().getDataFolder(), "mysql.yml");

        if (!(file.exists())) {
            try {
                file.createNewFile();
                configuration = ConfigurationProvider.getProvider(YamlConfiguration.class).load(file);
                
                configuration.set("mysql.hostname", "localhost");
                configuration.set("mysql.port", 3306);
                configuration.set("mysql.database", "minecraft");
                configuration.set("mysql.username", "minecraft");
                configuration.set("mysql.password", "Passwort");
                
                ConfigurationProvider.getProvider(YamlConfiguration.class).save(configuration, file);
            } catch (IOException ioException) {
                throw new RuntimeException(ioException);
            }
        }
    }

    public String getMySQLHostname() {
        try {
            configuration = ConfigurationProvider.getProvider(YamlConfiguration.class).load(file);
        } catch (IOException ioException) {
            throw new RuntimeException(ioException);
        }

        return configuration.getString("mysql.hostname");
    }
    
    public Integer getMySQLPort() {
        try {
            configuration = ConfigurationProvider.getProvider(YamlConfiguration.class).load(file);
        } catch (IOException ioException) {
            throw new RuntimeException(ioException);
        }

        return configuration.getInt("mysql.port");
    }
    
    public String getMySQLDatabase() {
        try {
            configuration = ConfigurationProvider.getProvider(YamlConfiguration.class).load(file);
        } catch (IOException ioException) {
            throw new RuntimeException(ioException);
        }

        return configuration.getString("mysql.database");
    }
    
    public String getMySQLUsername() {
        try {
            configuration = ConfigurationProvider.getProvider(YamlConfiguration.class).load(file);
        } catch (IOException ioException) {
            throw new RuntimeException(ioException);
        }

        return configuration.getString("mysql.username");
    }
    
    public String getMySQLPassword() {
        try {
            configuration = ConfigurationProvider.getProvider(YamlConfiguration.class).load(file);
        } catch (IOException ioException) {
            throw new RuntimeException(ioException);
        }

        return configuration.getString("mysql.password");
    }
    

}

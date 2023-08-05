package at.syntaxigel.proxysystem.commands;

import at.syntaxigel.proxysystem.ProxySystem;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URL;

public class ServerCommand extends Command {

    public ServerCommand(String name) {
        super(name);
    }

    @SuppressWarnings("deprecation")
    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!(sender instanceof ProxiedPlayer)) {
            sender.sendMessage(ProxySystem.getInstance().configManager.getMessageNoPlayer());
            return;
        }

        final ProxiedPlayer player = (ProxiedPlayer) sender;

        if (!player.hasPermission("proxysystem.createserver")) {
            String messageKey = "noPerm";
            String localizedMessage = ProxySystem.getInstance().langeLanguageManager.getLocalizedMessage(player.getUniqueId(), messageKey);
            player.sendMessage(localizedMessage);
            return;
        }

        if (!ProxySystem.getInstance().teamManager.isInTeam(player.getUniqueId())) {
            ProxySystem.getInstance().teamManager.addToTeam(player.getUniqueId());
            return;
        }

        if(args.length == 4) {
            if (args[0].equalsIgnoreCase("create")) {
                final String serverName = args[1];
                final String serverAddress = args[2];
                final String serverPort = args[3];

                ServerInfo serverInfo = ProxySystem.getInstance().getProxy().constructServerInfo(serverName, new InetSocketAddress(serverAddress, Integer.parseInt(serverPort)), serverName, false);
                ProxySystem.getInstance().getProxy().getServers().put(serverName, serverInfo);

                File serverFolder = new File(ProxySystem.getInstance().getProxy().getPluginsFolder(), serverName);
                serverFolder.mkdirs();

                String spigotDownloadUrl = "https://hub.spigotmc.org/jenkins/job/Spigot/lastSuccessfulBuild/artifact/spigot-1.17.1.jar";
                File spigotJar = new File(serverFolder, "spigot.jar");

                try {
                    FileUtils.copyURLToFile(new URL(spigotDownloadUrl), spigotJar);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                String messageKey = "createServer";
                String localizedMessage = ProxySystem.getInstance().langeLanguageManager.getLocalizedMessageCreateServer(player.getUniqueId(), messageKey, serverName, serverAddress, serverPort);
                player.sendMessage(localizedMessage);
            }
        }
    }

}

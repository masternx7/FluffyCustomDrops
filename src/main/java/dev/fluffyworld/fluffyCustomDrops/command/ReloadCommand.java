package dev.fluffyworld.fluffyCustomDrops.command;

import dev.fluffyworld.fluffyCustomDrops.FluffyCustomDrops;
import dev.fluffyworld.fluffyCustomDrops.config.MessageManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;

public class ReloadCommand implements CommandExecutor, TabCompleter {

    private final FluffyCustomDrops plugin;
    private final MessageManager messageManager;

    public ReloadCommand(FluffyCustomDrops plugin, MessageManager messageManager) {
        this.plugin = plugin;
        this.messageManager = messageManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("fluffydrops.reload")) {
            sender.sendMessage(messageManager.getMessage("no-permission"));
            return true;
        }

        try {
            plugin.reloadPlugin();
            sender.sendMessage(messageManager.getMessage("reload-success"));
        } catch (Exception e) {
            sender.sendMessage(messageManager.getMessage("reload-failed"));
            e.printStackTrace();
        }

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        return new ArrayList<>();
    }
}

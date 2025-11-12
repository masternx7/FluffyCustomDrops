package dev.fluffyworld.fluffyCustomDrops;

import dev.fluffyworld.fluffyCustomDrops.command.ReloadCommand;
import dev.fluffyworld.fluffyCustomDrops.config.ConfigManager;
import dev.fluffyworld.fluffyCustomDrops.config.MessageManager;
import dev.fluffyworld.fluffyCustomDrops.listener.BlockBreakListener;
import dev.fluffyworld.fluffyCustomDrops.listener.EntityDeathListener;
import dev.fluffyworld.fluffyCustomDrops.reward.RewardManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class FluffyCustomDrops extends JavaPlugin {

    private ConfigManager configManager;
    private MessageManager messageManager;
    private RewardManager rewardManager;

    @Override
    public void onEnable() {
        configManager = new ConfigManager(this);
        messageManager = new MessageManager(configManager);
        rewardManager = new RewardManager(configManager);

        registerListeners();
        registerCommands();

        getLogger().info("FluffyCustomDrops has been enabled!");
    }

    @Override
    public void onDisable() {
        getLogger().info("FluffyCustomDrops has been disabled!");
    }

    private void registerListeners() {
        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new BlockBreakListener(rewardManager), this);
        pm.registerEvents(new EntityDeathListener(rewardManager), this);
    }

    private void registerCommands() {
        ReloadCommand reloadCommand = new ReloadCommand(this, messageManager);
        getCommand("fluffydrops").setExecutor(reloadCommand);
        getCommand("fluffydrops").setTabCompleter(reloadCommand);
    }

    public void reloadPlugin() {
        configManager.reload();
        messageManager = new MessageManager(configManager);
        rewardManager.reload();
    }
}

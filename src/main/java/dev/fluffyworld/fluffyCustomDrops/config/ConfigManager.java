package dev.fluffyworld.fluffyCustomDrops.config;

import dev.fluffyworld.fluffyCustomDrops.FluffyCustomDrops;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class ConfigManager {

    private final FluffyCustomDrops plugin;
    private FileConfiguration config;
    private FileConfiguration messages;

    public ConfigManager(FluffyCustomDrops plugin) {
        this.plugin = plugin;
        loadConfigs();
    }

    public void loadConfigs() {
        saveDefaultConfig("config.yml");
        saveDefaultConfig("message.yml");

        config = loadConfig("config.yml");
        messages = loadConfig("message.yml");
    }

    private void saveDefaultConfig(String fileName) {
        File file = new File(plugin.getDataFolder(), fileName);
        if (!file.exists()) {
            plugin.saveResource(fileName, false);
        }
    }

    private FileConfiguration loadConfig(String fileName) {
        File file = new File(plugin.getDataFolder(), fileName);
        return YamlConfiguration.loadConfiguration(file);
    }

    public void reload() {
        loadConfigs();
    }

    public FileConfiguration getConfig() {
        return config;
    }

    public FileConfiguration getMessages() {
        return messages;
    }
}

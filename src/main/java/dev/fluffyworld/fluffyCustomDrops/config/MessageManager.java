package dev.fluffyworld.fluffyCustomDrops.config;

import dev.fluffyworld.fluffyCustomDrops.util.ColorUtil;
import org.bukkit.configuration.file.FileConfiguration;

public class MessageManager {

    private final ConfigManager configManager;

    public MessageManager(ConfigManager configManager) {
        this.configManager = configManager;
    }

    public String getMessage(String path) {
        FileConfiguration messages = configManager.getMessages();
        String message = messages.getString(path, "&cMessage not found: " + path);
        return ColorUtil.colorize(message);
    }

    public String getMessage(String path, String... replacements) {
        String message = getMessage(path);

        for (int i = 0; i < replacements.length; i += 2) {
            if (i + 1 < replacements.length) {
                message = message.replace(replacements[i], replacements[i + 1]);
            }
        }

        return message;
    }
}

package dev.fluffyworld.fluffyCustomDrops.reward;

import dev.fluffyworld.fluffyCustomDrops.config.ConfigManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RewardManager {

    private final ConfigManager configManager;
    private final Map<Material, List<Reward>> blockRewards;
    private final Map<Material, List<Reward>> cropRewards;
    private final Map<EntityType, List<Reward>> entityRewards;

    public RewardManager(ConfigManager configManager) {
        this.configManager = configManager;
        this.blockRewards = new HashMap<>();
        this.cropRewards = new HashMap<>();
        this.entityRewards = new HashMap<>();
        loadRewards();
    }

    public void loadRewards() {
        blockRewards.clear();
        cropRewards.clear();
        entityRewards.clear();

        loadBlockRewards();
        loadCropRewards();
        loadEntityRewards();
    }

    private void loadBlockRewards() {
        ConfigurationSection section = configManager.getConfig().getConfigurationSection("drops.blocks");
        if (section == null) return;

        for (String key : section.getKeys(false)) {
            try {
                Material material = Material.valueOf(key.toUpperCase());
                List<Reward> rewards = loadRewardList(section, key);
                blockRewards.put(material, rewards);
            } catch (IllegalArgumentException ignored) {
            }
        }
    }

    private void loadCropRewards() {
        ConfigurationSection section = configManager.getConfig().getConfigurationSection("drops.crops");
        if (section == null) return;

        for (String key : section.getKeys(false)) {
            try {
                Material material = Material.valueOf(key.toUpperCase());
                List<Reward> rewards = loadRewardList(section, key);
                cropRewards.put(material, rewards);
            } catch (IllegalArgumentException ignored) {
            }
        }
    }

    private void loadEntityRewards() {
        ConfigurationSection section = configManager.getConfig().getConfigurationSection("drops.entities");
        if (section == null) return;

        for (String key : section.getKeys(false)) {
            try {
                EntityType entityType = EntityType.valueOf(key.toUpperCase());
                List<Reward> rewards = loadRewardList(section, key);
                entityRewards.put(entityType, rewards);
            } catch (IllegalArgumentException ignored) {
            }
        }
    }

    private List<Reward> loadRewardList(ConfigurationSection section, String key) {
        List<Reward> rewards = new ArrayList<>();
        List<Map<?, ?>> rewardMaps = section.getMapList(key);

        for (Map<?, ?> map : rewardMaps) {
            String command = (String) map.get("command");
            Object chanceObj = map.get("chance");
            double chance = chanceObj instanceof Number ? ((Number) chanceObj).doubleValue() : 1.0;

            if (command != null) {
                rewards.add(new Reward(command, chance));
            }
        }

        return rewards;
    }

    public void giveBlockRewards(Player player, Material material) {
        List<Reward> rewards = blockRewards.get(material);
        if (rewards != null) {
            processRewards(player, rewards);
        }
    }

    public void giveCropRewards(Player player, Material material) {
        List<Reward> rewards = cropRewards.get(material);
        if (rewards != null) {
            processRewards(player, rewards);
        }
    }

    public void giveEntityRewards(Player player, EntityType entityType) {
        List<Reward> rewards = entityRewards.get(entityType);
        if (rewards != null) {
            processRewards(player, rewards);
        }
    }

    private void processRewards(Player player, List<Reward> rewards) {
        for (Reward reward : rewards) {
            if (reward.shouldDrop()) {
                String command = reward.getCommand().replace("%player%", player.getName());
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command);
            }
        }
    }

    public void reload() {
        loadRewards();
    }
}

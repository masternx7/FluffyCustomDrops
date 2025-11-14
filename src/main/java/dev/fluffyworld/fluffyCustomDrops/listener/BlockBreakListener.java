package dev.fluffyworld.fluffyCustomDrops.listener;

import dev.fluffyworld.fluffyCustomDrops.reward.RewardManager;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.Ageable;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class BlockBreakListener implements Listener {

    private final RewardManager rewardManager;
    private final BlockPlaceListener blockPlaceListener;

    public BlockBreakListener(RewardManager rewardManager, BlockPlaceListener blockPlaceListener) {
        this.rewardManager = rewardManager;
        this.blockPlaceListener = blockPlaceListener;
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();

        if (player.getGameMode() == GameMode.CREATIVE) {
            return;
        }

        Block block = event.getBlock();
        Material material = block.getType();
        Location location = block.getLocation();

        if (blockPlaceListener.isPlacedBlock(location)) {
            blockPlaceListener.removeBlock(location);
            return;
        }

        if (isCrop(block)) {
            if (block.getBlockData() instanceof Ageable ageable) {
                if (ageable.getAge() == ageable.getMaximumAge()) {
                    rewardManager.giveCropRewards(player, material);
                }
            }
        } else {
            rewardManager.giveBlockRewards(player, material);
        }
    }

    private boolean isCrop(Block block) {
        Material material = block.getType();
        return material == Material.WHEAT ||
                material == Material.CARROTS ||
                material == Material.POTATOES ||
                material == Material.BEETROOTS ||
                material == Material.NETHER_WART ||
                material == Material.COCOA ||
                material == Material.SWEET_BERRY_BUSH ||
                material == Material.CHORUS_FLOWER ||
                material == Material.CHORUS_PLANT;
    }
}

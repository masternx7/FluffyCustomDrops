package dev.fluffyworld.fluffyCustomDrops.listener;

import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

import java.util.HashSet;
import java.util.Set;

public class BlockPlaceListener implements Listener {

    private final Set<Location> placedBlocks;
    private final boolean preventPlacedBlockDrops;

    public BlockPlaceListener(boolean preventPlacedBlockDrops) {
        this.placedBlocks = new HashSet<>();
        this.preventPlacedBlockDrops = preventPlacedBlockDrops;
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onBlockPlace(BlockPlaceEvent event) {
        if (!preventPlacedBlockDrops) {
            return;
        }

        Location location = event.getBlock().getLocation();
        placedBlocks.add(location);
    }

    public boolean isPlacedBlock(Location location) {
        return placedBlocks.contains(location);
    }

    public void removeBlock(Location location) {
        placedBlocks.remove(location);
    }

    public void clearPlacedBlocks() {
        placedBlocks.clear();
    }
}

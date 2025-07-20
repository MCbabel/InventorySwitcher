package de.mcbabel.inventoryswitcher;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerGameModeChangeEvent;
import org.bukkit.GameMode;

public class InventoryListener implements Listener {

    private final InventorySwitcher plugin;

    public InventoryListener(InventorySwitcher plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onGameModeChange(PlayerGameModeChangeEvent event) {
        Player player = event.getPlayer();
        GameMode oldMode = player.getGameMode();
        GameMode newMode = event.getNewGameMode();

        // Check if both modes have separate inventories enabled
        boolean oldModeEnabled = plugin.getConfig().getBoolean("enabled-modes." + oldMode.name().toLowerCase(), false);
        boolean newModeEnabled = plugin.getConfig().getBoolean("enabled-modes." + newMode.name().toLowerCase(), false);

        if (plugin.isDebugMode()) {
            plugin.getLogger().info("Player " + player.getName() + " switching from " + oldMode.name() + " to " + newMode.name());
            plugin.getLogger().info("Old mode enabled: " + oldModeEnabled + ", New mode enabled: " + newModeEnabled);
        }

        // Always save current inventory to old mode if it's enabled
        if (oldModeEnabled) {
            plugin.saveInventory(player, oldMode);
        }

        // Load inventory for new mode if it's enabled (scheduled for next tick to ensure gamemode change is completed)
        if (newModeEnabled) {
            plugin.getServer().getScheduler().runTask(plugin, () -> {
                plugin.loadInventory(player, newMode);
            });
        }
    }
}
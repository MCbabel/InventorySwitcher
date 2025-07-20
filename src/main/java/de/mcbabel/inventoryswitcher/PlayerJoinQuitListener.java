package de.mcbabel.inventoryswitcher;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerJoinQuitListener implements Listener {

    private final InventorySwitcher plugin;

    public PlayerJoinQuitListener(InventorySwitcher plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        
        // Delay loading to ensure player is fully loaded
        plugin.getServer().getScheduler().runTaskLater(plugin, () -> {
            if (player.isOnline()) {
                plugin.loadInventory(player, player.getGameMode());
                
                if (plugin.isDebugMode()) {
                    plugin.getLogger().info("Loaded inventory for " + player.getName() + " on join (" + player.getGameMode().name() + ")");
                }
            }
        }, 10L); // 0.5 second delay
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        
        // Save current inventory when player quits
        plugin.saveInventory(player, player.getGameMode());
        
        if (plugin.isDebugMode()) {
            plugin.getLogger().info("Saved inventory for " + player.getName() + " on quit (" + player.getGameMode().name() + ")");
        }
    }
}
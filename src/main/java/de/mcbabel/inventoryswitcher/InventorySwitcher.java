package de.mcbabel.inventoryswitcher;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.GameMode;

import java.io.File;
import java.io.IOException;
import java.util.UUID;
import java.util.logging.Level;

public class InventorySwitcher extends JavaPlugin {

    private File playersDir;
    private boolean debugMode;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        
        // Create players directory for inventory storage
        playersDir = new File(getDataFolder(), "players");
        if (!playersDir.exists()) {
            playersDir.mkdirs();
        }
        
        // Load config settings
        debugMode = getConfig().getBoolean("settings.debug-mode", false);
        
        // Register event listeners
        getServer().getPluginManager().registerEvents(new InventoryListener(this), this);
        getServer().getPluginManager().registerEvents(new PlayerJoinQuitListener(this), this);
        
        // Start auto-save task if enabled
        int autoSaveInterval = getConfig().getInt("settings.auto-save-interval", 300);
        if (autoSaveInterval > 0) {
            startAutoSaveTask(autoSaveInterval);
        }
        
        getLogger().info("InventorySwitcher v" + getDescription().getVersion() + " enabled!");
        getLogger().info("Compatible with Minecraft 1.18 - 1.21.7");
        
        if (debugMode) {
            getLogger().info("Debug mode enabled");
        }
    }

    @Override
    public void onDisable() {
        // Save all online players' inventories before shutdown
        for (Player player : getServer().getOnlinePlayers()) {
            saveInventory(player, player.getGameMode());
        }
        getLogger().info("InventorySwitcher disabled!");
    }

    private void startAutoSaveTask(int intervalSeconds) {
        new BukkitRunnable() {
            @Override
            public void run() {
                int savedCount = 0;
                for (Player player : getServer().getOnlinePlayers()) {
                    saveInventory(player, player.getGameMode());
                    savedCount++;
                }
                if (debugMode && savedCount > 0) {
                    getLogger().info("Auto-saved " + savedCount + " player inventories");
                }
            }
        }.runTaskTimerAsynchronously(this, intervalSeconds * 20L, intervalSeconds * 20L);
    }

    public void saveInventory(Player player, GameMode gameMode) {
        if (!getConfig().getBoolean("enabled-modes." + gameMode.name().toLowerCase(), false)) {
            return;
        }

        UUID playerUUID = player.getUniqueId();
        File playerFile = new File(playersDir, playerUUID.toString() + ".yml");
        YamlConfiguration playerConfig = YamlConfiguration.loadConfiguration(playerFile);

        String modePath = gameMode.name().toLowerCase();
        
        // Clear old inventory data for this mode
        playerConfig.set(modePath, null);
        
        // Save main inventory
        ItemStack[] contents = player.getInventory().getContents();
        for (int i = 0; i < contents.length; i++) {
            if (contents[i] != null) {
                playerConfig.set(modePath + ".inventory." + i, contents[i]);
            }
        }
        
        // Save armor contents
        ItemStack[] armorContents = player.getInventory().getArmorContents();
        for (int i = 0; i < armorContents.length; i++) {
            if (armorContents[i] != null) {
                playerConfig.set(modePath + ".armor." + i, armorContents[i]);
            }
        }
        
        // Save off-hand
        ItemStack offHand = player.getInventory().getItemInOffHand();
        if (offHand != null) {
            playerConfig.set(modePath + ".offhand", offHand);
        }

        try {
            playerConfig.save(playerFile);
            if (debugMode) {
                getLogger().info("Saved inventory for " + player.getName() + " (" + gameMode.name() + ")");
            }
            
            // Send message if configured
            String saveMessage = getConfig().getString("messages.inventory-saved", "");
            if (!saveMessage.isEmpty()) {
                player.sendMessage(saveMessage.replace("{mode}", gameMode.name()));
            }
        } catch (IOException e) {
            getLogger().log(Level.WARNING, "Failed to save inventory for player " + player.getName() + ": " + e.getMessage(), e);
        }
    }

    public void loadInventory(Player player, GameMode gameMode) {
        if (!getConfig().getBoolean("enabled-modes." + gameMode.name().toLowerCase(), false)) {
            return;
        }

        UUID playerUUID = player.getUniqueId();
        File playerFile = new File(playersDir, playerUUID.toString() + ".yml");
        
        if (!playerFile.exists()) {
            if (getConfig().getBoolean("settings.clear-on-first-switch", false)) {
                player.getInventory().clear();
            }
            return; // No saved inventory for this mode
        }

        YamlConfiguration playerConfig = YamlConfiguration.loadConfiguration(playerFile);
        String modePath = gameMode.name().toLowerCase();

        // Clear current inventory first
        player.getInventory().clear();
        
        // Load main inventory
        for (int i = 0; i < 41; i++) { // 36 main slots + 4 armor + 1 offhand
            ItemStack item = playerConfig.getItemStack(modePath + ".inventory." + i);
            if (item != null) {
                player.getInventory().setItem(i, item);
            }
        }

        // Load armor contents
        ItemStack[] armorContents = new ItemStack[4];
        for (int i = 0; i < 4; i++) {
            armorContents[i] = playerConfig.getItemStack(modePath + ".armor." + i);
        }
        player.getInventory().setArmorContents(armorContents);

        // Load off-hand
        ItemStack offHand = playerConfig.getItemStack(modePath + ".offhand");
        if (offHand != null) {
            player.getInventory().setItemInOffHand(offHand);
        }
        
        if (debugMode) {
            getLogger().info("Loaded inventory for " + player.getName() + " (" + gameMode.name() + ")");
        }
        
        // Send message if configured
        String switchMessage = getConfig().getString("messages.inventory-switched", "");
        if (!switchMessage.isEmpty()) {
            player.sendMessage(switchMessage.replace("{mode}", gameMode.name()));
        }
    }

    public boolean isDebugMode() {
        return debugMode;
    }
}
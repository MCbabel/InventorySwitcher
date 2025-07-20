# InventorySwitcher

A powerful and lightweight Minecraft plugin that provides separate inventories for different game modes.

## Features

- **Separate Inventories**: Each game mode (Survival, Creative, Adventure, Spectator) can have its own inventory
- **Universal Compatibility**: Works with Minecraft 1.18 - 1.21.7
- **Server Software Support**: Compatible with Paper, Spigot, Bukkit, and their forks
- **Automatic Saving**: Inventories are automatically saved when switching modes or on server shutdown
- **Persistent Storage**: Inventories persist across server restarts
- **Auto-Save**: Configurable auto-save interval for extra safety
- **Lightweight**: Minimal performance impact
- **Highly Configurable**: Extensive configuration options

## Compatibility

- **Minecraft Versions**: 1.18, 1.18.1, 1.18.2, 1.19, 1.19.1, 1.19.2, 1.19.3, 1.19.4, 1.20, 1.20.1, 1.20.2, 1.20.3, 1.20.4, 1.20.5, 1.20.6, 1.21, 1.21.1, 1.21.2, 1.21.3, 1.21.4, 1.21.6, 1.21.7
- **Server Software**: Paper (recommended), Spigot, Bukkit, Purpur, Pufferfish, and other forks
- **Java Version**: Java 17+ required

## Installation

1. Download the latest `InventorySwitcher-1.0.0.jar` from the releases
2. Place it in your server's `plugins` folder
3. Restart your server
4. Configure the plugin in `plugins/InventorySwitcher/config.yml`

## Configuration

The plugin creates a `config.yml` file with the following options:

```yaml
# Enable separate inventories for specific game modes
enabled-modes:
  survival: true     # Separate inventory for Survival mode
  creative: true     # Separate inventory for Creative mode  
  adventure: true    # Separate inventory for Adventure mode
  spectator: true    # Separate inventory for Spectator mode

# General settings
settings:
  persist-across-restarts: true  # Keep inventories after restart
  auto-save-interval: 300        # Auto-save every 5 minutes (0 to disable)
  debug-mode: false              # Enable debug logging
  clear-on-first-switch: false   # Clear inventory on first mode switch

# Messages (leave empty to disable)
messages:
  inventory-switched: ""  # Message when switching modes
  inventory-saved: ""     # Message when inventory is saved
```

## How it Works

1. When a player switches game modes (via command, F3+F4, or plugins), their current inventory is automatically saved
2. The inventory for the new game mode is loaded
3. Each game mode maintains its own separate inventory, armor, and off-hand items
4. Inventories are stored in YAML files in `plugins/InventorySwitcher/players/`

## Commands

This plugin works automatically - no commands needed! It detects all game mode changes including:
- `/gamemode` and `/gm` commands
- F3+F4 debug key combination
- Changes made by other plugins

## Permissions

No permissions required - the plugin works for all players automatically.

## Soft Dependencies

The plugin has soft dependencies on common plugins for better compatibility:
- WorldEdit
- WorldGuard  
- Essentials

## Support

- Compatible with all major server software
- Tested on Minecraft versions 1.18 through 1.21.7
- Minimal performance impact
- Safe for production servers

## Data Storage

Player inventories are stored in individual YAML files at:
`plugins/InventorySwitcher/players/<player-uuid>.yml`

Each file contains separate sections for each game mode's inventory.

## License

This plugin is open source and free to use.

## Author

Created by MCbabel
# Changelog

All notable changes to the InventorySwitcher plugin will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [1.0.0] - 2025-01-20

### Added
- Initial release of InventorySwitcher plugin
- Separate inventories for Survival, Creative, Adventure, and Spectator modes
- Automatic inventory saving and loading on gamemode changes
- Support for Minecraft versions 1.18 - 1.21.7
- Compatibility with Paper, Spigot, Bukkit, and forks
- Persistent inventory storage across server restarts
- Configurable auto-save system
- Debug mode for troubleshooting
- Extensive configuration options
- Support for F3+F4 gamemode switching
- Support for all gamemode commands (/gamemode, /gm)
- Soft dependencies on WorldEdit, WorldGuard, and Essentials
- Clean YAML-based player data storage
- Automatic config file generation and updates

### Features
- **Multi-Mode Support**: All 4 game modes supported
- **Universal Compatibility**: Works on MC 1.18-1.21.7
- **Server Software Support**: Paper, Spigot, Bukkit, Purpur, etc.
- **Automatic Operation**: No commands needed, works transparently
- **Safe Data Handling**: Inventories never lost during mode switches
- **Performance Optimized**: Minimal server impact
- **Highly Configurable**: Extensive customization options

## [Unreleased]

### Planned
- Multiple world support
- Inventory restoration commands
- Permission-based inventory separation
- Database storage option
- API for other plugins
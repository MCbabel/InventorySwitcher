# Building InventorySwitcher

## Requirements

- **Java 17** or higher
- **Gradle** (included via Wrapper)

## Quick Build

```bash
# Windows
gradlew.bat jar

# Linux/Mac
./gradlew jar
```

The compiled plugin will be located at `build/libs/InventorySwitcher-1.0.0.jar`

## Development Setup

1. **Clone the repository**
   ```bash
   git clone https://github.com/mcbabel/InventorySwitcher.git
   cd InventorySwitcher
   ```

2. **Import into IDE**
   - **IntelliJ IDEA**: Open the `build.gradle` file
   - **Eclipse**: Import as Gradle project
   - **VS Code**: Install Java Extension Pack

3. **Build and test**
   ```bash
   # Clean build
   gradlew.bat clean jar
   
   # Run tests (when available)
   gradlew.bat test
   ```

## Gradle Tasks

- `gradlew.bat jar` - Build the plugin JAR
- `gradlew.bat clean` - Clean build directory
- `gradlew.bat build` - Full build with tests
- `gradlew.bat shadowJar` - Build with dependencies (if needed)

## Project Structure

```
InventorySwitcher/
├── src/
│   └── main/
│       ├── java/de/mcbabel/inventoryswitcher/
│       │   ├── InventorySwitcher.java      # Main plugin class
│       │   ├── InventoryListener.java      # Game mode change handler
│       │   └── PlayerJoinQuitListener.java # Player join/quit handler
│       └── resources/
│           └── config.yml                  # Default configuration
├── gradle/wrapper/                         # Gradle wrapper files
├── build.gradle                            # Build configuration
├── gradlew.bat                             # Windows Gradle wrapper
├── README.md                               # Documentation
├── LICENSE                                 # MIT License
├── CHANGELOG.md                            # Version history
└── .gitignore                              # Git ignore rules
```

## Configuration

The plugin uses Bukkit's automatic plugin.yml generation via the `net.minecrell.plugin-yml.bukkit` plugin.
Configuration is defined in `build.gradle` under the `bukkit` block.

## Dependencies

- **Paper API 1.20.1** (compile-only) - For maximum compatibility with MC 1.18-1.21.7
- **bStats 3.0.2** - For usage statistics (optional)

## Version Compatibility

- **Minecraft**: 1.18 - 1.21.7
- **Server Software**: Paper (recommended), Spigot, Bukkit, Purpur, Pufferfish
- **Java**: 17+ (recommended 21 for latest MC versions)

## Release Process

1. Update version in `build.gradle`
2. Update `CHANGELOG.md`
3. Build: `gradlew.bat clean jar`
4. Test on target Minecraft versions
5. Create GitHub release with JAR file
6. Publish to plugin repositories (SpigotMC, PaperMC Hangar, etc.)
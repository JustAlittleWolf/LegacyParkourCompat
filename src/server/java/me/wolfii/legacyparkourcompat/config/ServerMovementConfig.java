package me.wolfii.legacyparkourcompat.config;

import me.wolfii.legacyparkourcompat.LegacyParkourCompat;
import me.wolfii.legacyparkourcompat.api.MovementController;
import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import net.fabricmc.loader.api.FabricLoader;

import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

/**
 * Dedicated-server movement version, stored in {@code config/legacyparkourcompat.properties}.
 */
public final class ServerMovementConfig {
    public static final String FILE_NAME = "legacyparkourcompat.properties";
    public static final String PROPERTY = "movementVersion";

    private static final String DEFAULT_FILE = """
        # Legacy Parkour Compat — dedicated server settings
        #
        # Minecraft version whose player movement should be emulated.
        # Leave empty (or the running Minecraft version, e.g. 26.2) for vanilla movement.
        # Examples: 1.8.9, 1.12.2, 1.14.4
        movementVersion=
        """;

    private ServerMovementConfig() {
    }

    public static Path path() {
        return FabricLoader.getInstance().getConfigDir().resolve(FILE_NAME);
    }

    public static void load() {
        Path file = path();
        try {
            if (Files.notExists(file)) {
                Files.createDirectories(file.getParent());
                Files.writeString(file, DEFAULT_FILE, StandardCharsets.UTF_8);
                LegacyParkourCompat.LOGGER.info("Created {} (vanilla movement)", file);
                return;
            }
        } catch (IOException exception) {
            LegacyParkourCompat.LOGGER.error("Failed to create {}", file, exception);
            return;
        }

        Properties properties = new Properties();
        try (Reader reader = Files.newBufferedReader(file, StandardCharsets.UTF_8)) {
            properties.load(reader);
        } catch (IOException exception) {
            LegacyParkourCompat.LOGGER.error("Failed to read {}", file, exception);
            return;
        }

        String value = properties.getProperty(PROPERTY, "").trim();
        if (value.isEmpty() || value.equals(ParkourVersion.nativeGameVersion())) {
            MovementController.get().disable();
            LegacyParkourCompat.LOGGER.info("Movement version is vanilla ({})", ParkourVersion.nativeGameVersion());
            return;
        }

        ParkourVersion version = ParkourVersion.of(value);
        if (version.isCurrent()) {
            MovementController.get().disable();
            LegacyParkourCompat.LOGGER.error(
                "Movement version '{}' in {} is not a known parkour version; using vanilla movement ({})",
                value,
                file,
                ParkourVersion.nativeGameVersion()
            );
            return;
        }

        MovementController.get().select(version);
        LegacyParkourCompat.LOGGER.info("Movement version set to {}", MovementController.get().selectedVersion());
    }
}

package me.wolfii.legacyparkourcompat.api;

import net.fabricmc.loader.api.FabricLoader;

import java.util.List;

/**
 * Suggested Minecraft releases the version UI can list, plus the native game version.
 *
 * <p>Any parseable id can still be selected; this list is only the curated parkour set.
 */
public final class ParkourVersions {
    private ParkourVersions() {
    }

    /**
     * Releases that historically changed player movement, collision, or parkour-relevant
     * block behaviour, from 1.8 through the current native version.
     */
    public static final List<MinecraftVersion> SUGGESTED = List.of(
            MinecraftVersion.parse("1.8"),
            MinecraftVersion.parse("1.8.8"),
            MinecraftVersion.parse("1.8.9"),
            MinecraftVersion.parse("1.9"),
            MinecraftVersion.parse("1.9.4"),
            MinecraftVersion.parse("1.10.2"),
            MinecraftVersion.parse("1.11.2"),
            MinecraftVersion.parse("1.12.2"),
            MinecraftVersion.parse("1.13.2"),
            MinecraftVersion.parse("1.14.4"),
            MinecraftVersion.parse("1.15.2"),
            MinecraftVersion.parse("1.16.1"),
            MinecraftVersion.parse("1.16.5"),
            MinecraftVersion.parse("1.17.1"),
            MinecraftVersion.parse("1.18.2"),
            MinecraftVersion.parse("1.19.2"),
            MinecraftVersion.parse("1.19.4"),
            MinecraftVersion.parse("1.20.1"),
            MinecraftVersion.parse("1.20.4"),
            MinecraftVersion.parse("1.20.6"),
            MinecraftVersion.parse("1.21"),
            MinecraftVersion.parse("1.21.1"),
            MinecraftVersion.parse("1.21.3"),
            MinecraftVersion.parse("1.21.4"),
            MinecraftVersion.parse("1.21.5"),
            MinecraftVersion.parse("1.21.8"),
            MinecraftVersion.parse("1.21.10"),
            MinecraftVersion.parse("1.21.11"),
            MinecraftVersion.parse("26.1"),
            MinecraftVersion.parse("26.2")
    );

    public static MinecraftVersion nativeVersion() {
        return FabricLoader.getInstance()
                .getModContainer("minecraft")
                .map(container -> MinecraftVersion.parse(container.getMetadata().getVersion().getFriendlyString()))
                .orElseGet(() -> MinecraftVersion.parse("26.2"));
    }
}

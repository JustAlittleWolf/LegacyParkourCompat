package me.wolfii.legacyparkourcompat.api;

import net.fabricmc.loader.api.FabricLoader;

import java.util.List;

/**
 * Native game version and the selectable parkour era ids for the UI.
 */
public final class ParkourVersions {
    private ParkourVersions() {
    }

    /**
     * Canonical ids the version UI should list. Patch releases that share
     * parkour mechanics are omitted (selecting {@code 1.9} also covers
     * {@code 1.9.1} and {@code 1.9.2}). The last entry is the native game
     * version, which disables all movement changes.
     */
    public static List<MinecraftVersion> suggested() {
        return ParkourEras.selectable().stream()
                .map(era -> era.isVanilla() ? nativeVersion() : MinecraftVersion.parse(era.id()))
                .toList();
    }

    public static MinecraftVersion nativeVersion() {
        return FabricLoader.getInstance()
                .getModContainer("minecraft")
                .map(container -> MinecraftVersion.parse(container.getMetadata().getVersion().getFriendlyString()))
                .orElseGet(() -> MinecraftVersion.parse("26.2"));
    }
}

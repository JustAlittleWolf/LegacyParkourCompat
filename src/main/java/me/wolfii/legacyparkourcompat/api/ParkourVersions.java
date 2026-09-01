package me.wolfii.legacyparkourcompat.api;

import net.fabricmc.loader.api.FabricLoader;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Parkour-relevant selectable versions and the running game's native version.
 *
 * <p>Patch versions with no parkour-mechanic difference share one selectable
 * version so the UI can show {@code 1.9} instead of {@code 1.9}, {@code 1.9.1},
 * and {@code 1.9.2}.
 */
public final class ParkourVersions {
    /**
     * First version of each parkour version group, in chronological order. Each
     * entry consumes all following patches until the next entry.
     */
    private static final List<String> VERSION_STARTS = List.of(
            "1.8",
            "1.9",
            "1.10",
            "1.11",
            "1.12",
            "1.13",
            "1.14",
            "1.15",
            "1.16",
            "1.17",
            "1.18",
            "1.19",
            "1.19.4",
            "1.20",
            "1.20.5",
            "1.21",
            "1.21.2",
            "1.21.4",
            "1.21.5",
            "1.21.11",
            "26.1",
            "26.2"
    );

    private ParkourVersions() {
    }

    public static MinecraftVersion nativeVersion() {
        return FabricLoader.getInstance()
                .getModContainer("minecraft")
                .map(container -> MinecraftVersion.parse(container.getMetadata().getVersion().getFriendlyString()))
                .orElseGet(() -> MinecraftVersion.parse("26.2"));
    }

    public static ParkourVersion vanilla() {
        return ParkourVersion.vanilla(nativeVersion());
    }

    public static boolean isVanillaAlias(String id) {
        String key = id.trim().toLowerCase(Locale.ROOT);
        return key.equals("vanilla")
                || key.equals("disabled")
                || key.equals("disable")
                || key.equals("off")
                || key.equals("latest")
                || key.equals("native")
                || key.equals("current");
    }

    /**
     * Historical versions older than the native game version, plus vanilla/disabled last.
     */
    public static List<ParkourVersion> selectable() {
        List<ParkourVersion> versions = new ArrayList<>(historical());
        versions.add(vanilla());
        return List.copyOf(versions);
    }

    public static List<ParkourVersion> historical() {
        MinecraftVersion nativeVersion = nativeVersion();
        List<MinecraftVersion> starts = VERSION_STARTS.stream().map(MinecraftVersion::parse).toList();
        List<ParkourVersion> versions = new ArrayList<>();
        for (int i = 0; i < starts.size(); i++) {
            MinecraftVersion from = starts.get(i);
            if (from.newerThanOrEqual(nativeVersion)) {
                break;
            }
            MinecraftVersion until = i + 1 < starts.size() ? starts.get(i + 1) : nativeVersion;
            if (until.newerThan(nativeVersion)) {
                until = nativeVersion;
            }
            if (!from.olderThan(until)) {
                continue;
            }
            versions.add(new ParkourVersion(from.id(), from, until, false));
        }
        return List.copyOf(versions);
    }

    /**
     * Maps any Minecraft version (including patches like {@code 1.9.2}) onto the
     * selectable version the user should pick. Versions at or above native become vanilla.
     */
    public static ParkourVersion of(MinecraftVersion version) {
        MinecraftVersion nativeVersion = nativeVersion();
        if (version.newerThanOrEqual(nativeVersion)) {
            return vanilla();
        }
        ParkourVersion match = null;
        for (ParkourVersion parkourVersion : historical()) {
            if (parkourVersion.contains(version)) {
                match = parkourVersion;
            }
        }
        if (match != null) {
            return match;
        }
        List<ParkourVersion> historical = historical();
        if (historical.isEmpty() || version.olderThan(historical.getFirst().fromInclusive())) {
            return historical.isEmpty() ? vanilla() : historical.getFirst();
        }
        return vanilla();
    }

    public static ParkourVersion of(String id) {
        if (isVanillaAlias(id)) {
            return vanilla();
        }
        MinecraftVersion parsed = MinecraftVersion.tryParse(id);
        if (parsed == null) {
            throw new IllegalArgumentException("Not a Minecraft version or vanilla alias: '" + id + "'");
        }
        return of(parsed);
    }

    /**
     * Canonical ids the version UI should list. Patch releases that share
     * parkour mechanics are omitted (selecting {@code 1.9} also covers
     * {@code 1.9.1} and {@code 1.9.2}). The last entry is the native game
     * version, which disables all movement changes.
     */
    public static List<MinecraftVersion> suggested() {
        return selectable().stream()
                .map(version -> version.isVanilla() ? nativeVersion() : MinecraftVersion.parse(version.id()))
                .toList();
    }
}

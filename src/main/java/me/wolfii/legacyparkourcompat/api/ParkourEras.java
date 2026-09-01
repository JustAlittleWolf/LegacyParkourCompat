package me.wolfii.legacyparkourcompat.api;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Parkour-relevant version eras. Patch versions with no parkour-mechanic
 * difference share an era so the UI can show {@code 1.9} instead of
 * {@code 1.9}, {@code 1.9.1}, and {@code 1.9.2}.
 *
 * <p>An era starts at a known movement breakpoint and runs until the next
 * breakpoint (exclusive). Later agents can insert a start id if a patch is
 * found to change player movement.
 */
public final class ParkourEras {
    /**
     * First version of each parkour era, in chronological order. Each entry
     * consumes all following patches until the next entry.
     */
    private static final List<String> ERA_STARTS = List.of(
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

    private ParkourEras() {
    }

    public static ParkourEra vanilla() {
        return ParkourEra.vanilla(ParkourVersions.nativeVersion());
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
     * Historical eras older than the native game version, plus vanilla/disabled last.
     */
    public static List<ParkourEra> selectable() {
        List<ParkourEra> eras = new ArrayList<>(historical());
        eras.add(vanilla());
        return List.copyOf(eras);
    }

    public static List<ParkourEra> historical() {
        MinecraftVersion nativeVersion = ParkourVersions.nativeVersion();
        List<MinecraftVersion> starts = ERA_STARTS.stream().map(MinecraftVersion::parse).toList();
        List<ParkourEra> eras = new ArrayList<>();
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
            eras.add(new ParkourEra(from.id(), from, until, false));
        }
        return List.copyOf(eras);
    }

    /**
     * Maps any Minecraft version (including patches like {@code 1.9.2}) onto the
     * era the user should select. Versions at or above native become vanilla.
     */
    public static ParkourEra of(MinecraftVersion version) {
        MinecraftVersion nativeVersion = ParkourVersions.nativeVersion();
        if (version.newerThanOrEqual(nativeVersion)) {
            return vanilla();
        }
        ParkourEra match = null;
        for (ParkourEra era : historical()) {
            if (era.contains(version)) {
                match = era;
            }
        }
        if (match != null) {
            return match;
        }
        List<ParkourEra> historical = historical();
        if (historical.isEmpty() || version.olderThan(historical.getFirst().fromInclusive())) {
            return historical.isEmpty() ? vanilla() : historical.getFirst();
        }
        return vanilla();
    }

    public static ParkourEra of(String id) {
        if (isVanillaAlias(id)) {
            return vanilla();
        }
        MinecraftVersion parsed = MinecraftVersion.tryParse(id);
        if (parsed == null) {
            throw new IllegalArgumentException("Not a Minecraft version or vanilla alias: '" + id + "'");
        }
        return of(parsed);
    }
}

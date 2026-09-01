package me.wolfii.legacyparkourcompat.api;

import java.util.Objects;

/**
 * A selectable parkour movement version. Patch releases that share the same
 * parkour mechanics (for example {@code 1.9}, {@code 1.9.1}, and {@code 1.9.2})
 * collapse into one version.
 *
 * <p>The {@link #vanilla()} version is "latest / disabled": mixins must not alter
 * any Minecraft mechanics while it is selected.
 */
public final class ParkourVersion {
    private final String id;
    private final MinecraftVersion fromInclusive;
    private final MinecraftVersion untilExclusive;
    private final boolean vanilla;

    ParkourVersion(String id, MinecraftVersion fromInclusive, MinecraftVersion untilExclusive, boolean vanilla) {
        this.id = Objects.requireNonNull(id, "id");
        this.fromInclusive = Objects.requireNonNull(fromInclusive, "fromInclusive");
        this.untilExclusive = Objects.requireNonNull(untilExclusive, "untilExclusive");
        this.vanilla = vanilla;
    }

    public static ParkourVersion vanilla(MinecraftVersion nativeVersion) {
        return new ParkourVersion("vanilla", nativeVersion, nativeVersion, true);
    }

    /**
     * Stable id for the UI ({@code 1.9}, {@code 1.14}, {@code vanilla}, …).
     */
    public String id() {
        return this.id;
    }

    public MinecraftVersion fromInclusive() {
        return this.fromInclusive;
    }

    /**
     * First version after this one. Historical deltas introduced at or after
     * this version are candidates when this version is selected.
     */
    public MinecraftVersion untilExclusive() {
        return this.untilExclusive;
    }

    public boolean isVanilla() {
        return this.vanilla;
    }

    public boolean contains(MinecraftVersion version) {
        if (this.vanilla) {
            return version.newerThanOrEqual(this.fromInclusive);
        }
        return version.newerThanOrEqual(this.fromInclusive) && version.olderThan(this.untilExclusive);
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof ParkourVersion other && this.id.equals(other.id) && this.vanilla == other.vanilla;
    }

    @Override
    public int hashCode() {
        return this.id.hashCode();
    }

    @Override
    public String toString() {
        return this.vanilla ? "vanilla" : this.id;
    }
}

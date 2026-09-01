package me.wolfii.legacyparkourcompat.api;

import net.fabricmc.loader.api.Version;
import net.fabricmc.loader.api.VersionParsingException;
import org.jspecify.annotations.Nullable;

import java.util.Objects;

/**
 * A comparable Minecraft version id ({@code 1.8.9}, {@code 1.21.11}, {@code 26.2}, …).
 *
 * <p>Comparison uses Fabric Loader's Minecraft-aware version parser so that
 * {@code 1.8.9 < 1.9 < 1.21.11 < 26.1 < 26.2} holds.
 */
public final class MinecraftVersion implements Comparable<MinecraftVersion> {
    private final String id;
    private final Version fabricVersion;

    private MinecraftVersion(String id, Version fabricVersion) {
        this.id = id;
        this.fabricVersion = fabricVersion;
    }

    public static MinecraftVersion parse(String id) {
        Objects.requireNonNull(id, "id");
        String trimmed = id.trim();
        if (trimmed.isEmpty()) {
            throw new IllegalArgumentException("Minecraft version id is empty");
        }
        try {
            return new MinecraftVersion(trimmed, Version.parse(trimmed));
        } catch (VersionParsingException e) {
            throw new IllegalArgumentException("Not a Minecraft version: '" + trimmed + "'", e);
        }
    }

    public static @Nullable MinecraftVersion tryParse(String id) {
        try {
            return parse(id);
        } catch (RuntimeException e) {
            return null;
        }
    }

    public String id() {
        return this.id;
    }

    public boolean olderThan(MinecraftVersion other) {
        return this.compareTo(other) < 0;
    }

    public boolean olderThanOrEqual(MinecraftVersion other) {
        return this.compareTo(other) <= 0;
    }

    public boolean newerThan(MinecraftVersion other) {
        return this.compareTo(other) > 0;
    }

    public boolean newerThanOrEqual(MinecraftVersion other) {
        return this.compareTo(other) >= 0;
    }

    @Override
    public int compareTo(MinecraftVersion other) {
        return this.fabricVersion.compareTo(other.fabricVersion);
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof MinecraftVersion other && this.fabricVersion.equals(other.fabricVersion);
    }

    @Override
    public int hashCode() {
        return this.fabricVersion.hashCode();
    }

    @Override
    public String toString() {
        return this.id;
    }
}

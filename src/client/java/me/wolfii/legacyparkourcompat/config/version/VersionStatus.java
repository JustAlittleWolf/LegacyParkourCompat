package me.wolfii.legacyparkourcompat.config.version;

/**
 * How a typed movement version should be treated.
 *
 * <p>{@link #VANILLA} is empty or the running Minecraft version.
 * {@link #VALID} is a {@link me.wolfii.legacyparkourcompat.api.ParkourVersion}
 * patch id. Completeness (partial vs full) lives on the enum itself.
 * {@link #INVALID} is not a ParkourVersion id.
 */
public enum VersionStatus {
    VANILLA,
    VALID,
    INVALID
}

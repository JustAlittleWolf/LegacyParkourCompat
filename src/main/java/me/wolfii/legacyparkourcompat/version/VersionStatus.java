package me.wolfii.legacyparkourcompat.version;

/**
 * How a typed movement version should be treated.
 *
 * <p>{@link #VANILLA} is empty, a current alias, or the running game version.
 * {@link #VALID} is a {@link me.wolfii.legacyparkourcompat.api.ParkourVersion}
 * patch id. Completeness (partial vs full) lives on the enum itself.
 * {@link #INVALID} is not a ParkourVersion id.
 */
public enum VersionStatus {
    VANILLA,
    VALID,
    INVALID
}

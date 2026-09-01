package me.wolfii.legacyparkourcompat.version;

/**
 * How a typed movement version should be treated.
 *
 * <p>{@link #VANILLA} is empty, a current alias, or the running game version.
 * {@link #IMPLEMENTED} is a {@link me.wolfii.legacyparkourcompat.api.ParkourVersion}
 * with registered movement changes. {@link #UNIMPLEMENTED} is a ParkourVersion
 * with no changes yet. {@link #INVALID} is not a ParkourVersion id.
 */
public enum VersionStatus {
    VANILLA,
    IMPLEMENTED,
    UNIMPLEMENTED,
    INVALID
}

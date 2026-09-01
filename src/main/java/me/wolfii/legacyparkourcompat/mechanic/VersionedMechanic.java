package me.wolfii.legacyparkourcompat.mechanic;

/**
 * Marker for mixin-free movement hooks.
 *
 * <p>Keyed mechanics (block shapes, slime bounce, …) override {@link #variant()}
 * with a stable id such as {@code minecraft:ladder}. Singleton mechanics leave
 * it empty.
 */
public interface VersionedMechanic {
    default String variant() {
        return "";
    }
}

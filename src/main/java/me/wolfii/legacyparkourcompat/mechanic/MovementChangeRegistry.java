package me.wolfii.legacyparkourcompat.mechanic;

import me.wolfii.legacyparkourcompat.api.MinecraftVersion;

/**
 * Registry of historical movement deltas. Change authors register implementations
 * here; they never write mixins.
 */
public interface MovementChangeRegistry {
    /**
     * Registers an annotated {@link MovementChange} implementation.
     */
    void register(Object implementation);

    /**
     * Registers {@code implementation} for {@code type} as the behaviour vanilla
     * replaced in {@code vanillaChangedIn}.
     */
    <T extends VersionedMechanic> void register(
            Class<T> type,
            MinecraftVersion vanillaChangedIn,
            T implementation
    );

    <T extends VersionedMechanic> void register(
            Class<T> type,
            MinecraftVersion vanillaChangedIn,
            String emulates,
            T implementation
    );
}

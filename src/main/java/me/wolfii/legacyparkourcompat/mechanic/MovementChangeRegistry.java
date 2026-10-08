package me.wolfii.legacyparkourcompat.mechanic;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;

/**
 * Internal registry of historical movement deltas. The static catalog registers
 * implementations here; they never write mixins.
 */
public interface MovementChangeRegistry {
    /**
     * Registers {@code implementation} as the behaviour of {@code emulates}.
     * Vanilla replaced it in {@link ParkourVersion#next()}.
     */
    <T extends VersionedMechanic> void register(Class<T> type, ParkourVersion emulates, T implementation);
}

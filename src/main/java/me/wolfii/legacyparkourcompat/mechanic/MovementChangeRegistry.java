package me.wolfii.legacyparkourcompat.mechanic;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;

/**
 * Registry of historical movement deltas. Change authors register implementations
 * here; they never write mixins.
 */
public interface MovementChangeRegistry {
    /**
     * Registers an annotated {@link MovementChange} implementation. If the class
     * implements several {@link MechanicType} hooks, each is registered.
     */
    void register(Object implementation);

    /**
     * Registers {@code implementation} as the behaviour of {@code emulates}.
     * Vanilla replaced it in {@link ParkourVersion#next()}.
     */
    <T extends VersionedMechanic> void register(Class<T> type, ParkourVersion emulates, T implementation);
}

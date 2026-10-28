package me.wolfii.legacyparkourcompat.mechanic.fluid;

/** Stores the WATER probe result selected once during {@code Entity#baseTick}. */
public interface WaterFirstLavaProjectionContext {
    boolean legacyparkourcompat$hasWaterProbeForBaseTick();

    boolean legacyparkourcompat$waterProbeForBaseTick();

    void legacyparkourcompat$setWaterProbeForBaseTick(boolean waterPresent);

    void legacyparkourcompat$clearWaterProbeForBaseTick();
}

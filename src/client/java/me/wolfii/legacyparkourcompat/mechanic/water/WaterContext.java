package me.wolfii.legacyparkourcompat.mechanic.water;

import org.jspecify.annotations.Nullable;

/** A single completed fluid refresh, bounded to its entity tick and level. */
public interface WaterContext {
    @Nullable WaterScan.Result legacyparkourcompat$waterScan();

    default boolean legacyparkourcompat$hasSupportedWaterContext() {
        return legacyparkourcompat$waterScan() != null;
    }
}

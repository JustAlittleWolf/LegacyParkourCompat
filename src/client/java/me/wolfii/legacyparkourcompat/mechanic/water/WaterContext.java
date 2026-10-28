package me.wolfii.legacyparkourcompat.mechanic.water;

import org.jspecify.annotations.Nullable;

/** A single completed fluid refresh, bounded to its entity tick and level. */
public interface WaterContext {
    WaterScan.@Nullable Result legacyparkourcompat$waterScan();

    default boolean legacyparkourcompat$hasSupportedWaterContext() {
        WaterScan.@Nullable Result result = legacyparkourcompat$waterScan();
        return result != null && result.replacesContact();
    }
}

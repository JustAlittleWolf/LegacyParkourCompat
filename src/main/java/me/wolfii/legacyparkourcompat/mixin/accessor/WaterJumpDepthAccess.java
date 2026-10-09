package me.wolfii.legacyparkourcompat.mixin.accessor;

/** Per-entity state needed to reproduce the historical skipped fluid-depth write. */
public interface WaterJumpDepthAccess {
    double legacyparkourcompat$getRetainedWaterDepth();

    void legacyparkourcompat$setRetainedWaterDepth(double depth);
}

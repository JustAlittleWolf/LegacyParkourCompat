package me.wolfii.legacyparkourcompat.change.v1_8_9;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.world.WaterFlowScanBoundHook;

import java.util.OptionalInt;

/** Restores the 1.8.0-1.8.9 exclusive upper bound for liquid-flow scans. */
@MovementChange(emulates = ParkourVersion.V1_8_9)
public final class LegacyWaterFlowScanBound implements WaterFlowScanBoundHook {
    public LegacyWaterFlowScanBound() {
    }

    /*
     * Exact bounded source comparison:
     * Raw 1.8.0/1.8.1 clients mapped by Feather Gen2 build 2 Tiny and the
     * byte-identical Ornithe Feather World.java sources for 1.8.2-1.8.9 use
     * World.applyLiquidDrag(AABB,Material,Entity) to compute the exclusive end
     * as floor(bounds.maxAxis + 1.0). The matching 1.8.8 source is
     * World.java:1486-1492; the raw 1.8/1.8.1 method bodies were independently
     * rebound to the same named operation and bounds.
     *
     * 1.9, Ornithe Feather mappings feather-gen2-1.9+build.2.tiny,
     * net.minecraft.world.World.applyLiquidDrag(Box, Material, Entity), is the
     * first stable release using MathHelper.ceil(bounds.maxAxis).
     * 1.9.4, Ornithe Feather mappings feather-gen2-1.9.4+build.2.tiny,
     * net.minecraft.world.World.applyLiquidDrag(Box, Material, Entity), confirms
     * that rule across the 1.9 patch line. This override returns the
     * 1.8.0-1.8.9 rule. The lower endpoint in both methods is
     * MathHelper.floor(bounds.minAxis).
     */
    @Override
    public OptionalInt exclusiveUpperBound(double boundsMax) {
        return OptionalInt.of(floorToInt(boundsMax + 1.0));
    }

    /** Equivalent to the source MathHelper.floor operation for finite, int-range world coordinates. */
    private static int floorToInt(double value) {
        int truncated = (int) value;
        return value < (double) truncated ? truncated - 1 : truncated;
    }
}

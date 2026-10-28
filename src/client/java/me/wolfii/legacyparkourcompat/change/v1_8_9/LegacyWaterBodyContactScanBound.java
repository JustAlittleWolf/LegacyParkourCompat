package me.wolfii.legacyparkourcompat.change.v1_8_9;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.water.WaterBodyContactScanBoundHook;

import java.util.OptionalInt;

/** Restores the 1.8.0-1.8.9 exclusive upper bound for body-contact scans. */
@MovementChange(emulates = ParkourVersion.V1_8_9)
public final class LegacyWaterBodyContactScanBound implements WaterBodyContactScanBoundHook {
    public LegacyWaterBodyContactScanBound() {
    }

    /*
     * Exact bounded source comparison:
     * Raw 1.8.0/1.8.1 clients mapped by Feather Gen2 build 2 Tiny and the
     * byte-identical Ornithe Feather World.java sources for 1.8.2-1.8.9 use
     * World.applyLiquidDrag(AABB,Material,Entity) to compute the exclusive end
     * as floor(bounds.maxAxis + 1.0). The matching 1.8.8 source is
     * World.java:1486-1492; all five relevant source files are identical over
     * 1.8.2-1.8.9. The corrected 1.8/1.8.1 raw mapping binds the same named
     * method and bound arithmetic.
     *
     * 1.9 Ornithe Feather / Feather Gen2 build 2, mappings
     * feather-gen2-1.9+build.2.tiny, the same member changes the exclusive
     * end to MathHelper.ceil(bounds.maxAxis) (World.java:1554-1560). Both retain
     * floor(bounds.minAxis) and half-open loops. This hook changes body contact
     * bounds only; flow keeps its independent WaterFlowScanBoundHook. Resolver
     * applicability uses this provider through V1_8_9, before the V1_9 native
     * contact boundary.
     */
    @Override
    public OptionalInt exclusiveUpperBound(double boundsMax) {
        return OptionalInt.of(floorToInt(boundsMax + 1.0));
    }

    /** Equivalent to source MathHelper.floor for finite, int-range coordinates. */
    private static int floorToInt(double value) {
        int truncated = (int) value;
        return value < (double) truncated ? truncated - 1 : truncated;
    }
}

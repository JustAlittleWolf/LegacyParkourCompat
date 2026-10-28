package me.wolfii.legacyparkourcompat.change.v1_8_5;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.water.WaterBodyContactScanBoundHook;

import java.util.OptionalInt;

/**
 * Keeps profiles before the bounded 1.8.9 integration on native bounds.
 * This fallback fence does not assert historical equivalence for earlier profiles.
 */
@MovementChange(emulates = ParkourVersion.V1_8_5)
public final class NativeWaterBodyContactScanBound implements WaterBodyContactScanBoundHook {
    public NativeWaterBodyContactScanBound() {
    }

    @Override
    public OptionalInt exclusiveUpperBound(double boundsMax) {
        // Ornithe Feather 1.8.9 World.applyLiquidDrag (World.java:1486-1492)
        // and 1.9 World.applyLiquidDrag (World.java:1554-1560) establish the
        // accepted boundary. This provider leaves earlier profile coverage native.
        return OptionalInt.empty();
    }
}

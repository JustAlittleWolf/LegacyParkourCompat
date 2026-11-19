package me.wolfii.legacyparkourcompat.change.v1_15_2;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.fluid.PostMoveLavaDepthHook;

/**
 * Mojmap 1.15.2 Entity#checkAndHandleWater stores waterHeight, while
 * Entity#isInLava reads a presence flag. Mojmap 1.16 adds a tagged depth
 * cache read by Entity#getFluidHeight. The water-only call in
 * LivingEntity#checkFallDamage leaves its lava entry intact.
 */
@MovementChange(emulates = ParkourVersion.V1_15_2)
public final class NativePostMoveLavaDepth implements PostMoveLavaDepthHook {
    @Override
    public boolean retainsLavaDepth() {
        return false;
    }
}

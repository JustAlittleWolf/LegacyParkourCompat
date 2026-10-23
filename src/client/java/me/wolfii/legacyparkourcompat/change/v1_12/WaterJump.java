package me.wolfii.legacyparkourcompat.change.v1_12;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.water.WaterJumpSelectionHook;

/**
 * 1.12/1.12.1/1.12.2 Feather LivingEntity.mobTick prioritizes cached inWater
 * before lava/ground and calls jumpInWater (0.04F). 1.13 Feather mobTick adds
 * shallow-depth ordinary jumps; 26.2 unobfuscated LivingEntity.aiStep retains
 * that decision. A selection-only infinite depth takes the existing water-impulse
 * branch without writing the native depth cache or duplicating jumping/travel.
 */
@MovementChange(emulates = ParkourVersion.V1_12)
public final class WaterJump implements WaterJumpSelectionHook {
    public double selectionDepth(boolean inWater, double nativeDepth) {
        return inWater ? Double.POSITIVE_INFINITY : nativeDepth;
    }
}

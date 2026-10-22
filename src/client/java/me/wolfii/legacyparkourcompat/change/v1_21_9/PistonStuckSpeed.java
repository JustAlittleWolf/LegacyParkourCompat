package me.wolfii.legacyparkourcompat.change.v1_21_9;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.entity.PistonStuckSpeedHook;
import net.minecraft.world.phys.Vec3;

/**
 * 1.14.4 and 1.21.9, Mojmap,
 * net.minecraft.world.entity.Entity#move first limits MoverType.PISTON
 * displacement with #limitPistonMovement, then multiplies it by
 * stuckSpeedMultiplier when its squared length exceeds 1.0E-7, before clearing
 * the multiplier and zeroing velocity. 1.21.10, Mojmap,
 * net.minecraft.world.entity.Entity#move skips only that multiplication for
 * PISTON.
 */
@MovementChange(emulates = ParkourVersion.V1_21_9)
public final class PistonStuckSpeed implements PistonStuckSpeedHook {
    public PistonStuckSpeed() {
    }

    @Override
    public Vec3 transformPistonMovement(Vec3 limitedMovement, Vec3 stuckSpeedMultiplier) {
        return limitedMovement.multiply(stuckSpeedMultiplier);
    }
}

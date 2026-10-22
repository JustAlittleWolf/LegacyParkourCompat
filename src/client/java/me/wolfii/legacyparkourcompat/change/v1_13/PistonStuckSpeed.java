package me.wolfii.legacyparkourcompat.change.v1_13;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.entity.PistonStuckSpeedHook;
import net.minecraft.world.phys.Vec3;

/**
 * 1.13.2, Ornithe Feather, net.minecraft.entity.Entity#move consumes the
 * separate inCobweb flag; this field-based hook has no operation to emulate
 * in that era.
 */
@MovementChange(emulates = ParkourVersion.V1_13)
public final class PistonStuckSpeed implements PistonStuckSpeedHook {
    public PistonStuckSpeed() {
    }

    @Override
    public Vec3 transformPistonMovement(Vec3 limitedMovement, Vec3 stuckSpeedMultiplier) {
        return limitedMovement;
    }
}

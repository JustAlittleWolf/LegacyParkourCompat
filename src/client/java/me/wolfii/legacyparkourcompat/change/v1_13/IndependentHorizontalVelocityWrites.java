package me.wolfii.legacyparkourcompat.change.v1_13;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.collision.HorizontalVelocityWriteHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.collision.HorizontalVelocityCollisionHook;
import net.minecraft.world.phys.Vec3;
import java.util.Optional;

/**
 * 1.13.2 Ornithe Feather Entity.move clears velocityX and velocityZ independently.
 * 1.14 Ornithe Feather Entity.move and 1.14.4 Mojmap Entity.move capture the
 * velocity once; a later Z-clear restores the captured X. Override that later
 * operation for the old profiles, including when both axes are clipped.
 * The 1.13.2 clearing predicates are separately resolved raw n != x / p != z,
 * independent of the approximate comparison used by later collision flags.
 * Even a sub-1.0E-5 clipped component must clear its velocity.
 */
@MovementChange(emulates = ParkourVersion.V1_13_1)
public final class IndependentHorizontalVelocityWrites implements HorizontalVelocityWriteHook,
    HorizontalVelocityCollisionHook {
    @Override
    public Optional<Boolean> shouldClear(double requested, double resolved) {
        return Optional.of(requested != resolved);
    }

    @Override
    public Optional<Vec3> clear(Vec3 captured, boolean clearX, boolean clearZ) {
        return Optional.of(new Vec3(clearX ? 0.0 : captured.x, captured.y, clearZ ? 0.0 : captured.z));
    }
}

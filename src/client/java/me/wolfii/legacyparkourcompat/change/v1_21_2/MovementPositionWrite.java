package me.wolfii.legacyparkourcompat.change.v1_21_2;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.collision.MovementPositionWriteHook;
import net.minecraft.world.phys.Vec3;

/**
 * 1.21.2 adds the requested-to-resolved length-difference alternative to the
 * 1.21.1 resolved-length position-write gate.
 *
 * <p>Compared Mojmap Entity.move in 1.21.1 and 1.21.3; the latter formula is
 * retained in 26.2. The requested vector is the post-edge-backoff movement
 * passed to collision, and the resolved vector is its collision result.
 */
@MovementChange(emulates = ParkourVersion.V1_21_2)
public final class MovementPositionWrite implements MovementPositionWriteHook {
    @Override
    public boolean shouldWrite(ParkourVersion selected, double movementLengthSquared) {
        return movementLengthSquared > 1.0E-7;
    }

    @Override
    public boolean shouldWrite(ParkourVersion selected, Vec3 requestedMovement, Vec3 resolvedMovement) {
        double resolvedLengthSquared = resolvedMovement.lengthSqr();
        return resolvedLengthSquared > 1.0E-7
            || requestedMovement.lengthSqr() - resolvedLengthSquared < 1.0E-7;
    }
}

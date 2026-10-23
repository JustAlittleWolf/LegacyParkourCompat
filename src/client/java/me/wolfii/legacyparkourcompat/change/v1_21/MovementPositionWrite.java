package me.wolfii.legacyparkourcompat.change.v1_21;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.collision.MovementPositionWriteHook;

/**
 * Ornithe Feather 1.14 Entity.move and Mojmap 1.16.5 / 1.21.1 Entity.move
 * write translated position/box only when
 * actualMovement.lengthSqr() > 1.0E-7. Mojmap 1.21.2 adds the alternative
 * requested.lengthSqr() - actual.lengthSqr() < 1.0E-7, retained by 26.2.
 * Suppress only the tiny movement's position/box write and its modern contact
 * segment; collision flags and subsequent movement processing remain native.
 * The fall-distance raycast in the enclosing branch requires actual length
 * squared >= 1.0, so it cannot run for the suppressed case. Pre-1.14 movement
 * has a separate unconditional writer; noPhysics uses a separate native call.
 */
@MovementChange(emulates = ParkourVersion.V1_21)
public final class MovementPositionWrite implements MovementPositionWriteHook {
    @Override
    public boolean shouldWrite(ParkourVersion selected, double movementLengthSquared) {
        return movementLengthSquared > 1.0E-7;
    }
}

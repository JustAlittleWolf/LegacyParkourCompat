package me.wolfii.legacyparkourcompat.change.v1_13;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.collision.MovementPositionWriteHook;

/**
 * 1.13.2 Ornithe Feather Entity.move always calls setPositionFromShape after
 * clipping, including zero and tiny results. 1.14 Ornithe Feather Entity.move
 * and 1.14.4 Mojmap Entity.move gate translation and reconstruction on resolved
 * length squared > 1.0E-7. Admission must also open the enclosing modern branch,
 * not merely allow its eventual position write after native admission.
 */
@MovementChange(emulates = ParkourVersion.V1_13_1)
public final class MovementPositionWrite implements MovementPositionWriteHook {
    @Override
    public boolean alwaysEnterWriteBranch() {
        return true;
    }

    @Override
    public boolean shouldWrite(ParkourVersion selected, double movementLengthSquared) {
        return true;
    }
}

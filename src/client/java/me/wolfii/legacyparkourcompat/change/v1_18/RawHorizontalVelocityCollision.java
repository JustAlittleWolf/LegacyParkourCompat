package me.wolfii.legacyparkourcompat.change.v1_18;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.collision.HorizontalVelocityCollisionHook;
import java.util.Optional;

/**
 * 1.17.1, 1.18 and 1.18.1 Mojmap Entity.move compare displacement with raw !=
 * for horizontal velocity writes. 1.18.2 Mojmap Entity.move instead reuses
 * !Mth.equal, whose Mth.equal(double,double) threshold is the widened 1.0E-5F.
 * The separate horizontalCollision field already used Mth.equal in 1.17.1.
 * V1_18 includes both inspected 1.18 and 1.18.1.
 */
@MovementChange(emulates = ParkourVersion.V1_18)
public final class RawHorizontalVelocityCollision implements HorizontalVelocityCollisionHook {
    @Override
    public Optional<Boolean> shouldClear(double requested, double resolved) {
        return Optional.of(requested != resolved);
    }
}

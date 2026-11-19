package me.wolfii.legacyparkourcompat.change.v1_18;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.collision.HorizontalVelocityCollisionHook;
import java.util.Optional;

/**
 * Entity.move compares displacement with raw != for horizontal velocity
 * writes in 1.14 Ornithe Feather, 1.14.1–1.14.3 Yarn and 1.14.4–1.18.1 Mojmap.
 * 1.18.2 Mojmap Entity.move reuses !Mth.equal for the write gates. The
 * Mth.equal(double, double) threshold is the widened 1.0E-5F.
 */
@MovementChange(emulates = ParkourVersion.V1_18)
public final class RawHorizontalVelocityCollision implements HorizontalVelocityCollisionHook {
    @Override
    public Optional<Boolean> shouldClear(double requested, double resolved) {
        return Optional.of(requested != resolved);
    }
}

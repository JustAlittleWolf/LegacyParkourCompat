package me.wolfii.legacyparkourcompat.change.v1_17;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.collision.EmptyShapeMovementHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.collision.HorizontalVelocityCollisionHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.collision.HorizontalVelocityWriteHook;
import net.minecraft.world.phys.Vec3;
import java.util.Optional;

/**
 * Lower evidence boundary for these newly integrated operations. The exact
 * evidence starts at 1.17.1 Mojmap Entity.move/collide, compared with 1.18,
 * 1.18.1 and 1.18.2 Mojmap counterparts; it proves no earlier range. Empty
 * results retain the product fallback. Existing collision flag, border admission
 * and sprint operations keep their independently established earlier coverage.
 */
@MovementChange(emulates = ParkourVersion.V1_17)
public final class UnprovedEarlierCollisionBoundary implements HorizontalVelocityCollisionHook,
    HorizontalVelocityWriteHook, EmptyShapeMovementHook {
    @Override
    public Optional<Boolean> shouldClear(double requested, double resolved) {
        return Optional.empty();
    }

    @Override
    public Optional<Vec3> clear(Vec3 capturedVelocity, boolean clearX, boolean clearZ) {
        return Optional.empty();
    }

    @Override
    public Optional<Vec3> resolve(Vec3 requested) {
        return Optional.empty();
    }
}

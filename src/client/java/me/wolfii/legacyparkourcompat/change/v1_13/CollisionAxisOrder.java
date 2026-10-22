package me.wolfii.legacyparkourcompat.change.v1_13;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.collision.CollisionAxisOrderHook;

/**
 * In 1.13.2 (Ornithe Feather), net.minecraft.entity.Entity.move(MoverType, double, double, double)
 * resolves Y, X, then Z. In 1.14.1 (Yarn), net.minecraft.entity.Entity.method_17833(Vec3d, BoundingBox,
 * ViewableWorld, EntityContext, LoopingStream) retains Y first and selects Z-before-X only when
 * Math.abs(x) < Math.abs(z); equality stays X-before-Z. The 26.2 native Direction.axisStepOrder(Vec3)
 * selector uses that same strict comparison, so this delta supplies only the older fixed horizontal order.
 */
@MovementChange(emulates = ParkourVersion.V1_13)
public final class CollisionAxisOrder implements CollisionAxisOrderHook {
    public CollisionAxisOrder() {
    }

    @Override
    public boolean zBeforeX(double x, double z) {
        return false;
    }
}

package me.wolfii.legacyparkourcompat.change.v1_13;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.entity.StepUpCollisionHook;
import net.minecraft.core.Direction;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

/**
 * 1.13.2 Ornithe Feather Entity.move compares two full-height routes using one
 * cached World.getCollisions sweep: rise under the horizontally expanded box,
 * then rise under the original box. Both clip X before Z; a tie selects the
 * second route. The selected box settles by minus its rise, without requested Y.
 * 1.14 Ornithe Feather Entity.m_73670363 instead settles by -rise + requested Y,
 * also retained by 1.14.4 Mojmap Entity.collide. The old y local remains the
 * downward settle displacement for collision flags and fall callbacks, while
 * setPositionFromShape reads the box after both vertical translations. Carry
 * that box separately to preserve this distinction and floating-point order.
 */
@MovementChange(emulates = ParkourVersion.V1_13)
public final class EntityStepUpCollision implements StepUpCollisionHook {
    @Override
    public Result resolveStepUp(Vec3 requested, Vec3 ordinary, AABB box,
                                float maxUpStep, boolean onGround, CollisionCall collisionCall) {
        boolean grounded = onGround || requested.y != ordinary.y && requested.y < 0.0;
        if (!(maxUpStep > 0.0F && grounded && (requested.x != ordinary.x || requested.z != ordinary.z))) {
            return new Result(ordinary);
        }

        AxisCollisionCall sweep = collisionCall.sweep(new Vec3(requested.x, maxUpStep, requested.z), box);
        Route expanded = route(requested, box, box.expandTowards(requested.x, 0.0, requested.z), maxUpStep, sweep);
        Route direct = route(requested, box, box, maxUpStep, sweep);
        Route selected = expanded.distanceSquared() > direct.distanceSquared() ? expanded : direct;
        double down = sweep.collide(Direction.Axis.Y, selected.box(), -selected.rise());
        AABB settled = down != 0.0 ? selected.box().move(0.0, down, 0.0) : selected.box();
        if (ordinary.x * ordinary.x + ordinary.z * ordinary.z >= selected.distanceSquared()) {
            return new Result(ordinary);
        }
        return new Result(new Vec3(selected.x(), down, selected.z()), settled);
    }

    private static Route route(Vec3 requested, AABB original, AABB riseBox,
                               float height, AxisCollisionCall sweep) {
        double rise = sweep.collide(Direction.Axis.Y, riseBox, height);
        AABB box = rise != 0.0 ? original.move(0.0, rise, 0.0) : original;
        double x = sweep.collide(Direction.Axis.X, box, requested.x);
        if (x != 0.0) {
            box = box.move(x, 0.0, 0.0);
        }
        double z = sweep.collide(Direction.Axis.Z, box, requested.z);
        if (z != 0.0) {
            box = box.move(0.0, 0.0, z);
        }
        return new Route(x, rise, z, box);
    }

    private record Route(double x, double rise, double z, AABB box) {
        double distanceSquared() {
            return x * x + z * z;
        }
    }
}

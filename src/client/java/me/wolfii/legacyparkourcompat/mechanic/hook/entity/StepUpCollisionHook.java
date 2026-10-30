package me.wolfii.legacyparkourcompat.mechanic.hook.entity;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.core.Direction;
import org.jspecify.annotations.Nullable;

/** Selects the historical step-up result while the mixin owns collision collection. */
@MechanicType("entity.step_up_collision")
public interface StepUpCollisionHook extends VersionedMechanic {
    /** Whether the selected era keeps the running game's step-up resolver. */
    default boolean usesNativeResolver() {
        return false;
    }

    Result resolveStepUp(Vec3 requestedMovement, Vec3 ordinaryResult, AABB boundingBox,
                       float maxUpStep, boolean onGround, CollisionCall collisionCall);

    /** A historical resolver may retain a translated box independently of its logical Y result. */
    record Result(Vec3 movement, @Nullable AABB boundingBox) {
        public Result(Vec3 movement) {
            this(movement, null);
        }
    }

    interface CollisionCall {
        Vec3 collide(Vec3 movement, AABB box);

        /** Select the requested descent after a route wins; callers still clip it against that route. */
        default double downwardReturn(double nativeDescent, float configuredStepHeight) {
            return nativeDescent;
        }

        /** Collect once from this sweep, then reuse for both routes and the settling pass. */
        AxisCollisionCall sweep(Vec3 movement, AABB box);
    }

    @FunctionalInterface
    interface AxisCollisionCall {
        double collide(Direction.Axis axis, AABB box, double distance);
    }
}

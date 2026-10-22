package me.wolfii.legacyparkourcompat.mechanic.hook.entity;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

/** Selects the historical step-up result while the mixin owns collision collection. */
@MechanicType("entity.step_up_collision")
public interface StepUpCollisionHook extends VersionedMechanic {
    /** Whether the selected era keeps the running game's step-up resolver. */
    default boolean usesNativeResolver() {
        return false;
    }

    Vec3 resolveStepUp(Vec3 requestedMovement, Vec3 ordinaryResult, AABB boundingBox,
                       float maxUpStep, boolean onGround, CollisionCall collisionCall);

    @FunctionalInterface
    interface CollisionCall {
        Vec3 collide(Vec3 movement, AABB box);
    }
}

package me.wolfii.legacyparkourcompat.change.v1_20_2;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.entity.StepUpCollisionHook;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

/**
 * Retains the native step-up resolver for profiles before 1.20.5. The current
 * V1_20_2 group covers 1.20.2 through 1.20.4; earlier selected profiles also
 * resolve here until their collision providers have separate source-backed
 * integrations. The two-route resolver begins at V1_20_5 and candidate shape
 * heights begin at V1_21.
 */
@MovementChange(emulates = ParkourVersion.V1_20_2)
public final class NativeStepUpCollision implements StepUpCollisionHook {
    public NativeStepUpCollision() {
    }

    @Override
    public boolean usesNativeResolver() {
        return true;
    }

    @Override
    public Vec3 resolveStepUp(Vec3 requestedMovement, Vec3 ordinaryResult, AABB boundingBox,
                              float maxUpStep, boolean onGround, CollisionCall collisionCall) {
        return ordinaryResult;
    }
}

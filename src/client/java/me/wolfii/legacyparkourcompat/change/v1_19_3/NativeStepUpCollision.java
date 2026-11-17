package me.wolfii.legacyparkourcompat.change.v1_19_3;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.entity.StepUpCollisionHook;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

/**
 * Retains the earlier profile fallback below the verified two-route range.
 * Mojmap 1.19.4 through 1.20.6 Entity.collide uses the fixed-height routes;
 * Mojmap 1.21 Entity.collide and collectCandidateStepUpHeights use shape heights.
 */
@MovementChange(emulates = ParkourVersion.V1_19_3)
public final class NativeStepUpCollision implements StepUpCollisionHook {
    public NativeStepUpCollision() {
    }

    @Override
    public boolean usesNativeResolver() {
        return true;
    }

    @Override
    public Result resolveStepUp(Vec3 requestedMovement, Vec3 ordinaryResult, AABB boundingBox,
                              float maxUpStep, boolean onGround, CollisionCall collisionCall) {
        return new Result(ordinaryResult);
    }
}

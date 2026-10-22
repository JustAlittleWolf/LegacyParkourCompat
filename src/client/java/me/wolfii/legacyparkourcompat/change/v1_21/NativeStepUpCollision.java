package me.wolfii.legacyparkourcompat.change.v1_21;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.entity.StepUpCollisionHook;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

/**
 * Keeps the candidate-height resolver introduced by Minecraft 1.21.
 * 1.21.1 Mojmap {@code net.minecraft.world.entity.Entity.collide(Vec3)} and
 * {@code Entity.collectCandidateStepUpHeights} enumerate shape Y coordinates,
 * cast candidates to float, unstable-sort them and accept the first strict
 * horizontal-distance improvement. 26.2 unobfuscated
 * {@code net.minecraft.world.entity.Entity.collide(Vec3)} and
 * {@code Entity.collectCandidateStepUpHeights} retain that candidate-height
 * selection path; the collision provider remains supplied by the running game.
 */
@MovementChange(emulates = ParkourVersion.V1_21)
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

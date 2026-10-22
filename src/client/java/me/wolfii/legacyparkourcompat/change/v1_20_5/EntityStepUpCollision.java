package me.wolfii.legacyparkourcompat.change.v1_20_5;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.entity.StepUpCollisionHook;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

/**
 * Restores the two-route step-up resolver for Minecraft 1.20.5 and 1.20.6.
 *
 * <p>1.20.5 and 1.20.6 Mojmap {@code net.minecraft.world.entity.Entity.collide(Vec3)},
 * {@code Entity.collideBoundingBox(Entity, Vec3, AABB, Level, List)}, and
 * {@code CollisionGetter.getBlockCollisions(Entity, AABB)} are byte-identical
 * across this bounded pair. The resolver caches entity collisions once from the
 * expanded requested box and reuses that list for all routes. Each route then
 * adds the world-border shape using its swept box and enumerates block
 * collisions from that same box. The two full-height routes compare strict
 * squared horizontal distances against the ordinary result, then settle
 * vertically. In 1.21.1 Mojmap,
 * {@code Entity.collide(Vec3)} and {@code Entity.collectCandidateStepUpHeights}
 * use shape heights as candidate routes instead.
 */
@MovementChange(emulates = ParkourVersion.V1_20_5)
public final class EntityStepUpCollision implements StepUpCollisionHook {
    public EntityStepUpCollision() {
    }

    @Override
    public Vec3 resolveStepUp(Vec3 requestedMovement, Vec3 ordinaryResult, AABB boundingBox,
                              float maxUpStep, boolean onGround, CollisionCall collisionCall) {
        boolean clippedX = requestedMovement.x != ordinaryResult.x;
        boolean clippedY = requestedMovement.y != ordinaryResult.y;
        boolean clippedZ = requestedMovement.z != ordinaryResult.z;
        boolean groundedOrDescendingIntoCollision = onGround || clippedY && requestedMovement.y < 0.0;
        if (!(maxUpStep > 0.0F && groundedOrDescendingIntoCollision && (clippedX || clippedZ))) {
            return ordinaryResult;
        }

        Vec3 firstRoute = collisionCall.collide(
            new Vec3(requestedMovement.x, maxUpStep, requestedMovement.z), boundingBox);
        Vec3 secondRoute = collisionCall.collide(
            new Vec3(0.0, maxUpStep, 0.0),
            boundingBox.expandTowards(requestedMovement.x, 0.0, requestedMovement.z));
        if (secondRoute.y < maxUpStep) {
            Vec3 horizontalAfterRise = collisionCall.collide(
                new Vec3(requestedMovement.x, 0.0, requestedMovement.z),
                boundingBox.move(secondRoute)).add(secondRoute);
            if (horizontalAfterRise.horizontalDistanceSqr() > firstRoute.horizontalDistanceSqr()) {
                firstRoute = horizontalAfterRise;
            }
        }

        if (firstRoute.horizontalDistanceSqr() > ordinaryResult.horizontalDistanceSqr()) {
            return firstRoute.add(collisionCall.collide(
                new Vec3(0.0, -firstRoute.y + requestedMovement.y, 0.0),
                boundingBox.move(firstRoute)));
        }
        return ordinaryResult;
    }
}

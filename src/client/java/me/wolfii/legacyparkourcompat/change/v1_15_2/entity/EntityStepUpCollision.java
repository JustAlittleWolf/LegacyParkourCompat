package me.wolfii.legacyparkourcompat.change.v1_15_2.entity;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.entity.StepUpCollisionHook;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

/**
 * Resolves historical step-up routes for profiles through 1.15.2.
 *
 * <p>Mojmap {@code net.minecraft.world.entity.Entity#collide(Vec3)} uses the
 * same step-up gates, two candidate routes, strict horizontal-distance
 * comparisons and requested-Y correction in 1.14.4, 1.15, 1.15.1 and 1.15.2.
 * Mojmap {@code Entity#move(MoverType, Vec3)} calls {@code collide} after
 * {@code applySneaking} in 1.14.4 and after {@code maybeBackOffFromEdge} in
 * the 1.15 releases. The collision hook receives that post-caller movement;
 * the separately resolved player edge operation remains in effect. This
 * provider supplies the nearest later implementation where no closer step-up
 * provider applies.
 */
@MovementChange(emulates = ParkourVersion.V1_15_2)
public final class EntityStepUpCollision implements StepUpCollisionHook {
    @Override
    public Result resolveStepUp(Vec3 requested, Vec3 ordinary, AABB box,
                                float maxUpStep, boolean onGround, CollisionCall collisionCall) {
        boolean clippedY = requested.y != ordinary.y;
        boolean groundedOrDescendingIntoCollision = onGround || clippedY && requested.y < 0.0;
        boolean clippedHorizontally = requested.x != ordinary.x || requested.z != ordinary.z;
        if (!(maxUpStep > 0.0F && groundedOrDescendingIntoCollision && clippedHorizontally)) {
            return new Result(ordinary);
        }

        Vec3 selected = collisionCall.collide(
            new Vec3(requested.x, maxUpStep, requested.z), box);
        Vec3 verticalOnly = collisionCall.collide(
            new Vec3(0.0, maxUpStep, 0.0), box.expandTowards(requested.x, 0.0, requested.z));
        if (verticalOnly.y < maxUpStep) {
            Vec3 secondRoute = collisionCall.collide(
                new Vec3(requested.x, 0.0, requested.z), box.move(verticalOnly)).add(verticalOnly);
            if (horizontalDistanceSquared(secondRoute) > horizontalDistanceSquared(selected)) {
                selected = secondRoute;
            }
        }

        if (horizontalDistanceSquared(selected) <= horizontalDistanceSquared(ordinary)) {
            return new Result(ordinary);
        }

        Vec3 selectedRoute = selected;
        double returnedY = collisionCall.stepAssistVerticalReturn(
            selectedRoute.y,
            requested.y,
            correctionY -> collisionCall.collide(
                new Vec3(0.0, correctionY, 0.0), box.move(selectedRoute)).y
        );
        // The return vector carries rise plus clipped correction; unlike the
        // V1_13_1 resolver, its Y is also the final box displacement.
        return new Result(new Vec3(selectedRoute.x, returnedY, selectedRoute.z));
    }

    private static double horizontalDistanceSquared(Vec3 movement) {
        return movement.x * movement.x + movement.z * movement.z;
    }
}

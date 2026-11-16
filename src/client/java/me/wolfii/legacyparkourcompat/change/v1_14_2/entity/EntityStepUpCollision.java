package me.wolfii.legacyparkourcompat.change.v1_14_2.entity;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.entity.StepUpCollisionHook;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

/**
 * Uses the 1.14 step routes with the independently resolved requested-Y return.
 *
 * <p>1.14.4 Ornithe Feather {@code net.minecraft.entity.Entity.m_72373519}
 * tests the same grounded-or-downward-clipped gate, step-height bound, two
 * candidates and strict horizontal-distance comparisons as 1.13.2 Ornithe
 * Feather {@code net.minecraft.entity.Entity.move}; its final vertical return adds the clipped
 * {@code -routeY + requestedY} correction. The formula and route gates persist
 * in 1.14.0–1.14.4. This provider is annotated at V1_14_2: earlier selected
 * profiles retain V1_13_1, and profiles from V1_15 onward retain the later
 * V1_20_2 provider until a separate source boundary is accepted.
 */
@MovementChange(emulates = ParkourVersion.V1_14_2)
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

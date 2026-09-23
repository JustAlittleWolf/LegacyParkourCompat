package me.wolfii.legacyparkourcompat.change.v1_20_5;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.VanillaFn;
import me.wolfii.legacyparkourcompat.mechanic.hook.CollisionAlgorithm;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.List;

/** Through 1.20.6, step-up compared simultaneous and vertical-first routes. */
@MovementChange(emulates = ParkourVersion.V1_20_5)
public final class TwoRouteStepUp implements CollisionAlgorithm {
    @Override
    public Vec3 collide(Entity entity, Vec3 movement, VanillaFn<Vec3> vanilla) {
        AABB box = entity.getBoundingBox();
        List<VoxelShape> entityCollisions = entity.level().getEntityCollisions(entity, box.expandTowards(movement));
        Vec3 resolved = movement.lengthSqr() == 0.0
            ? movement
            : Entity.collideBoundingBox(entity, movement, box, entity.level(), entityCollisions);
        boolean xCollision = movement.x != resolved.x;
        boolean yCollision = movement.y != resolved.y;
        boolean zCollision = movement.z != resolved.z;
        boolean onGroundAfterCollision = entity.onGround() || yCollision && movement.y < 0.0;
        float maxStep = entity.maxUpStep();
        if (maxStep > 0.0F && onGroundAfterCollision && (xCollision || zCollision)) {
            Vec3 simultaneous = Entity.collideBoundingBox(
                entity, new Vec3(movement.x, maxStep, movement.z), box, entity.level(), entityCollisions
            );
            Vec3 verticalFirst = Entity.collideBoundingBox(
                entity, new Vec3(0.0, maxStep, 0.0), box.expandTowards(movement.x, 0.0, movement.z),
                entity.level(), entityCollisions
            );
            if (verticalFirst.y < maxStep) {
                Vec3 horizontalAfterRise = Entity.collideBoundingBox(
                    entity, new Vec3(movement.x, 0.0, movement.z), box.move(verticalFirst),
                    entity.level(), entityCollisions
                ).add(verticalFirst);
                if (horizontalAfterRise.horizontalDistanceSqr() > simultaneous.horizontalDistanceSqr()) {
                    simultaneous = horizontalAfterRise;
                }
            }
            if (simultaneous.horizontalDistanceSqr() > resolved.horizontalDistanceSqr()) {
                return simultaneous.add(Entity.collideBoundingBox(
                    entity, new Vec3(0.0, -simultaneous.y + movement.y, 0.0), box.move(simultaneous),
                    entity.level(), entityCollisions
                ));
            }
        }
        return resolved;
    }
}

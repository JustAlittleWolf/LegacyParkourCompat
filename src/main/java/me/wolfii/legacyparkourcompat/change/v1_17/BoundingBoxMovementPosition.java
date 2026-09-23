package me.wolfii.legacyparkourcompat.change.v1_17;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.VanillaCall;
import me.wolfii.legacyparkourcompat.mechanic.hook.MovementPositionBehavior;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

/** Through 1.16, movement translated the box and derived position from its center. */
@MovementChange(emulates = ParkourVersion.V1_16_2)
public final class BoundingBoxMovementPosition implements MovementPositionBehavior {
    @Override
    public void updatePosition(Entity entity, Vec3 resolvedMovement, VanillaCall vanilla) {
        AABB box = entity.getBoundingBox().move(resolvedMovement);
        entity.setPosRaw((box.minX + box.maxX) / 2.0, box.minY, (box.minZ + box.maxZ) / 2.0);
        entity.setBoundingBox(box);
    }
}

package me.wolfii.legacyparkourcompat.change.v1_8_9;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.change.common.StrictAxisClipping;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.collision.AxisCollisionConstraintHook;
import net.minecraft.core.Direction;
import net.minecraft.world.phys.AABB;

/**
 * 1.8 through 1.8.9 Ornithe Feather Gen2 build.2 MovingBlock.getCollisionShape
 * samples MovingBlockEntity.getProgress(0.0F). For extending Stone at previous
 * progress zero, Block.addCollisions can retain the resulting zero-width box.
 * Entity.move uses Box.intersectY before intersectX: strict transverse contact
 * keeps Y, then the touching X face limits the positive request to zero.
 * 1.9 Ornithe Feather Gen2 build.2 MovingBlock.getCollisionShape delegates to
 * MovingBlockEntity.getShape, which unions the current and previous moved boxes.
 */
@MovementChange(emulates = ParkourVersion.V1_8_9)
public final class AxisCollisionConstraint implements AxisCollisionConstraintHook {
    @Override
    public double clip(Direction.Axis axis, AABB moving, AABB constraint, double distance) {
        return StrictAxisClipping.clipBox(constraint, axis, moving, distance);
    }
}

package me.wolfii.legacyparkourcompat.change.v1_8_9;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.change.common.StrictAxisClipping;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.collision.AxisCollisionConstraintHook;
import net.minecraft.core.Direction;
import net.minecraft.world.phys.AABB;

/**
 * 1.8 through 1.8.9 Ornithe Feather Gen2 build.2 MovingBlock.getCollisionShape
 * (118–130, 162–197) samples MovingBlockEntity.getProgress(0.0F). For extending
 * Stone at previous progress zero, Block.addCollisions (339–344) can retain
 * the resulting zero-width box at local X=0 for East or X=1 for West.
 * Entity.move (439–460) uses Box.intersectY before intersectX (93–130):
 * strict transverse contact keeps Y, then the touching X face limits the
 * corresponding positive or negative X request to zero.
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

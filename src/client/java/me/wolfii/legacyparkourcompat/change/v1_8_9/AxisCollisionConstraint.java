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
 * the resulting zero-width box. Its local coordinate is 0 for East/South
 * and 1 for West/North along the movement axis. Entity.move (439–460)
 * uses Box.intersectY before the horizontal clips (Box.java:93–149).
 * Strict transverse contact keeps Y, then inclusive contact in intersectX
 * or intersectZ limits the signed horizontal request to zero.
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

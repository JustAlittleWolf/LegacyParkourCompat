package me.wolfii.legacyparkourcompat.change.v1_14;

import com.google.common.collect.ImmutableList;
import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.CollisionAxisOrder;
import net.minecraft.core.Direction;
import net.minecraft.world.phys.Vec3;

/**
 * Pre-1.14 collision is always Y→X→Z. 1.14 picks Y→Z→X when {@code |Vz| > |Vx|}.
 */
@MovementChange(emulates = ParkourVersion.V1_13)
public final class AlwaysYxzCollision implements CollisionAxisOrder {
    private static final ImmutableList<Direction.Axis> YXZ = ImmutableList.of(
        Direction.Axis.Y,
        Direction.Axis.X,
        Direction.Axis.Z
    );

    @Override
    public ImmutableList<Direction.Axis> axisOrder(Vec3 movement, ImmutableList<Direction.Axis> vanilla) {
        return YXZ;
    }
}

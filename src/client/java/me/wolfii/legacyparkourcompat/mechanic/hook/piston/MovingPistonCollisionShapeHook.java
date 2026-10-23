package me.wolfii.legacyparkourcompat.mechanic.hook.piston;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.Optional;

/** Ordinary player collision assembly; an empty result preserves native assembly. */
@MechanicType("piston.moving_collision_shape")
public interface MovingPistonCollisionShapeHook extends VersionedMechanic {
    Optional<VoxelShape> collisionShape(BlockState movedState, Direction direction,
                                       boolean extending, boolean source,
                                       float progress, float previousProgress);
}

package me.wolfii.legacyparkourcompat.mechanic.hook.block;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.Optional;

/** Historical horizontal wall collision outline, retaining the active vertical extent. */
@MechanicType("block.wall.horizontal-collision-shape")
public interface WallCollisionShapeHook extends VersionedMechanic {
    Optional<VoxelShape> horizontalCollisionShape(
        BlockState state,
        BlockGetter level,
        BlockPos pos,
        VoxelShape vanillaShape
    );
}

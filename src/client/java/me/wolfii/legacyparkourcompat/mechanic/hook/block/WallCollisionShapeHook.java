package me.wolfii.legacyparkourcompat.mechanic.hook.block;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.Optional;
import java.util.OptionalDouble;

/** Historical horizontal wall collision outline, retaining the active vertical extent. */
@MechanicType("block.wall.horizontal-collision-shape")
public interface WallCollisionShapeHook extends VersionedMechanic {
    String blockId();

    @Override
    default String variant() {
        return this.blockId();
    }

    Optional<VoxelShape> horizontalCollisionShape(
        BlockState state,
        BlockGetter level,
        BlockPos pos,
        VoxelShape vanillaShape,
        OptionalDouble historicalMaxY
    );

    /** Adds resolved, block-local directional connections to the historical wall mask. */
    default Optional<VoxelShape> horizontalCollisionShape(
        BlockState state,
        BlockGetter level,
        BlockPos pos,
        VoxelShape vanillaShape,
        OptionalDouble historicalMaxY,
        int restoredConnectionMask
    ) {
        return horizontalCollisionShape(state, level, pos, vanillaShape, historicalMaxY);
    }
}

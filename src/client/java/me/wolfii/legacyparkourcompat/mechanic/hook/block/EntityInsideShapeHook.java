package me.wolfii.legacyparkourcompat.mechanic.hook.block;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.Optional;

/** Selects a historical entity-inside shape for a specific block. */
@MechanicType("block.entity_inside_shape")
public interface EntityInsideShapeHook extends VersionedMechanic {
    Optional<VoxelShape> entityInsideShape(
        BlockBehaviour.BlockStateBase state, BlockGetter level, BlockPos pos, Entity entity
    );
}

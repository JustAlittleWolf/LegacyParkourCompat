package me.wolfii.legacyparkourcompat.mechanic.hook;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.Optional;

/**
 * Historical collision / outline boxes for one block.
 *
 * <p>Return {@link Optional#empty()} from a method to keep vanilla for that
 * query. {@link #blockId()} is the mechanic variant, so ladder and soul-sand
 * shapes resolve independently.
 */
@MechanicType("block.collision")
public interface BlockCollisionShape extends VersionedMechanic {
    String blockId();

    @Override
    default String variant() {
        return this.blockId();
    }

    default Optional<VoxelShape> collisionShape(
        BlockState state,
        BlockGetter level,
        BlockPos pos,
        CollisionContext context
    ) {
        return Optional.empty();
    }

    default Optional<VoxelShape> outlineShape(
        BlockState state,
        BlockGetter level,
        BlockPos pos,
        CollisionContext context
    ) {
        return this.collisionShape(state, level, pos, context);
    }
}

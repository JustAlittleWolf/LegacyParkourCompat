package me.wolfii.legacyparkourcompat.change.v1_10;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.block.BlockCollisionShapeHook;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.Optional;

/**
 * Ornithe Feather 1.10 {@code net/minecraft/block/FarmlandBlock.java},
 * {@code getCollisionShape(BlockState, World, BlockPos)}, returns
 * {@code FULL_BLOCK_SHAPE}. In 1.10.1, the same class's
 * {@code getShape(BlockState, WorldView, BlockPos)} returns the 15/16-high
 * outline, and {@code net/minecraft/block/Block.java#getCollisionShape}
 * delegates to {@code state.getShape(world, pos)}, so player collision uses
 * that shorter shape.
 */
@MovementChange(emulates = ParkourVersion.V1_10)
public final class FarmlandFullCollision implements BlockCollisionShapeHook {
    @Override
    public String blockId() {
        return "minecraft:farmland";
    }

    @Override
    public Optional<VoxelShape> collisionShape(
        BlockState state,
        BlockGetter level,
        BlockPos pos,
        CollisionContext context
    ) {
        return Optional.of(Shapes.block());
    }
}

package me.wolfii.legacyparkourcompat.change.v1_13;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.fence.FenceGlassCollisionHook;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FenceBlock;
import net.minecraft.world.level.block.StainedGlassBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.Optional;
import java.util.Set;

/**
 * 1.13.2, Ornithe Feather: FenceBlock.shouldConnectTo rejects glass through
 * Block.isExceptionForAttachment. 1.14.4, Mojmap: FenceBlock.connectsTo accepts
 * a sturdy glass face because Block.isExceptionForConnection no longer lists
 * glass. Trim only the glass-facing outer rail from the native player shape;
 * retain the native block state and all other native connection decisions.
 * The sources establish the glass exception, not a replacement neighbor table.
 * 1.14.4 CrossCollisionBlock.makeShapes/getCollisionShape supplies the
 * 6/16-to-1 rail bounds used here, while the central post remains untouched.
 */
@MovementChange(emulates = ParkourVersion.V1_13_1)
public final class FenceGlassCollision implements FenceGlassCollisionHook {
    private static final Set<Block> ERA_FENCES = Set.of(
        Blocks.OAK_FENCE,
        Blocks.SPRUCE_FENCE,
        Blocks.BIRCH_FENCE,
        Blocks.JUNGLE_FENCE,
        Blocks.ACACIA_FENCE,
        Blocks.DARK_OAK_FENCE,
        Blocks.NETHER_BRICK_FENCE
    );

    private static final VoxelShape NORTH_RAIL = Shapes.box(6.0 / 16.0, 0.0, 0.0, 10.0 / 16.0, 24.0 / 16.0, 6.0 / 16.0);
    private static final VoxelShape EAST_RAIL = Shapes.box(10.0 / 16.0, 0.0, 6.0 / 16.0, 1.0, 24.0 / 16.0, 10.0 / 16.0);
    private static final VoxelShape SOUTH_RAIL = Shapes.box(6.0 / 16.0, 0.0, 10.0 / 16.0, 10.0 / 16.0, 24.0 / 16.0, 1.0);
    private static final VoxelShape WEST_RAIL = Shapes.box(0.0, 0.0, 6.0 / 16.0, 6.0 / 16.0, 24.0 / 16.0, 10.0 / 16.0);

    @Override
    public Optional<VoxelShape> collisionShape(BlockState state, BlockGetter level, BlockPos pos, VoxelShape shape) {
        if (!ERA_FENCES.contains(state.getBlock())) {
            return Optional.empty();
        }

        VoxelShape historical = shape;
        historical = removeGlassRail(state, level, pos, Direction.NORTH, FenceBlock.NORTH, NORTH_RAIL, historical);
        historical = removeGlassRail(state, level, pos, Direction.EAST, FenceBlock.EAST, EAST_RAIL, historical);
        historical = removeGlassRail(state, level, pos, Direction.SOUTH, FenceBlock.SOUTH, SOUTH_RAIL, historical);
        historical = removeGlassRail(state, level, pos, Direction.WEST, FenceBlock.WEST, WEST_RAIL, historical);
        return historical == shape ? Optional.empty() : Optional.of(historical);
    }

    private static VoxelShape removeGlassRail(
        BlockState state,
        BlockGetter level,
        BlockPos pos,
        Direction direction,
        BooleanProperty connection,
        VoxelShape rail,
        VoxelShape shape
    ) {
        if (!state.getValue(connection) || !isGlass(level.getBlockState(pos.relative(direction)))) {
            return shape;
        }
        return Shapes.join(shape, rail, BooleanOp.ONLY_FIRST);
    }

    private static boolean isGlass(BlockState state) {
        return state.is(Blocks.GLASS) || state.getBlock() instanceof StainedGlassBlock;
    }
}

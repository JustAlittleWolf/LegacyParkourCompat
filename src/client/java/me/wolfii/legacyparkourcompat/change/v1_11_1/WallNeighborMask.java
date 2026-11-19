package me.wolfii.legacyparkourcompat.change.v1_11_1;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.change.common.WallCollisionGeometry;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.block.WallNeighborMaskHook;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.StainedGlassBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

import java.util.OptionalInt;
import java.util.Set;

/**
 * Ornithe Feather 1.11.1 and 1.11.2 {@code WallBlock#shouldConnectTo}
 * connect to the same wall block (both metadata variants) and fence gates
 * without testing the gate axis. The remaining neighbors require a solid
 * blocking material and a cube. {@code StairsBlock#isCube} and
 * {@code Material#isSolidBlocking} reject stairs, TNT and glass. The
 * predicate rejects barriers and pumpkin material explicitly. These
 * decisions feed the index in {@code WallBlock#addCollisions}, including
 * mixed-neighbor masks.
 * Sides outside these projections retain their current connection flag.
 */
@MovementChange(emulates = ParkourVersion.V1_11_1)
public final class WallNeighborMask implements WallNeighborMaskHook {
    private static final Direction[] DIRECTIONS = {
        Direction.SOUTH, Direction.WEST, Direction.NORTH, Direction.EAST
    };
    private static final Set<Block> GATES = Set.of(
        Blocks.OAK_FENCE_GATE, Blocks.SPRUCE_FENCE_GATE, Blocks.BIRCH_FENCE_GATE,
        Blocks.JUNGLE_FENCE_GATE, Blocks.ACACIA_FENCE_GATE, Blocks.DARK_OAK_FENCE_GATE
    );
    private static final Set<Block> STAIRS = Set.of(
        Blocks.OAK_STAIRS, Blocks.SPRUCE_STAIRS, Blocks.BIRCH_STAIRS,
        Blocks.JUNGLE_STAIRS, Blocks.ACACIA_STAIRS, Blocks.DARK_OAK_STAIRS,
        Blocks.COBBLESTONE_STAIRS, Blocks.BRICK_STAIRS, Blocks.STONE_BRICK_STAIRS,
        Blocks.NETHER_BRICK_STAIRS, Blocks.SANDSTONE_STAIRS, Blocks.RED_SANDSTONE_STAIRS,
        Blocks.QUARTZ_STAIRS, Blocks.PURPUR_STAIRS
    );

    public WallNeighborMask() {
    }

    @Override
    public OptionalInt selectMask(BlockState target, BlockGetter level, BlockPos pos) {
        if (!target.is(Blocks.COBBLESTONE_WALL) && !target.is(Blocks.MOSSY_COBBLESTONE_WALL)) {
            return OptionalInt.empty();
        }
        int mask = WallCollisionGeometry.mask(target);
        if (target.getValue(BlockStateProperties.WATERLOGGED)) {
            return OptionalInt.of(mask);
        }

        for (int index = 0; index < DIRECTIONS.length; index++) {
            BlockState neighbor = level.getBlockState(pos.relative(DIRECTIONS[index]));
            Block block = neighbor.getBlock();
            int bit = 1 << index;
            if (GATES.contains(block)
                || ((neighbor.is(Blocks.COBBLESTONE_WALL) || neighbor.is(Blocks.MOSSY_COBBLESTONE_WALL))
                    && neighbor.getFluidState().isEmpty())) {
                mask |= bit;
            } else if (neighbor.isAir() || neighbor.is(Blocks.TNT) || neighbor.is(Blocks.BARRIER)
                || neighbor.is(Blocks.GLASS) || block instanceof StainedGlassBlock
                || neighbor.is(Blocks.CARVED_PUMPKIN) || neighbor.is(Blocks.JACK_O_LANTERN)
                || (STAIRS.contains(block) && neighbor.getFluidState().isEmpty())) {
                mask &= ~bit;
            }
        }
        return OptionalInt.of(mask);
    }
}

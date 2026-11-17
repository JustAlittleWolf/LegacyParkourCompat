package me.wolfii.legacyparkourcompat.change.common;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.WallBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

import java.util.Optional;
import java.util.OptionalInt;
import java.util.Set;

/**
 * Applies the bounded 1.12 directional stair-face rule to an otherwise native
 * wall-side mask. Other neighbor decisions stay in the current wall state.
 */
public final class WallNeighborMaskSelection {
    private static final Set<String> WALL_IDS = Set.of(
        "minecraft:cobblestone_wall", "minecraft:mossy_cobblestone_wall"
    );
    private static final Set<String> STAIR_IDS = Set.of(
        "minecraft:oak_stairs", "minecraft:stone_stairs", "minecraft:brick_stairs",
        "minecraft:stone_brick_stairs", "minecraft:sandstone_stairs", "minecraft:spruce_stairs",
        "minecraft:birch_stairs", "minecraft:jungle_stairs", "minecraft:nether_brick_stairs",
        "minecraft:quartz_stairs", "minecraft:acacia_stairs", "minecraft:dark_oak_stairs",
        "minecraft:red_sandstone_stairs", "minecraft:purpur_stairs"
    );
    private static final Direction[] DIRECTIONS = {
        Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST
    };
    private static final int[] BITS = {4, 8, 1, 2};

    private WallNeighborMaskSelection() {
    }

    public static OptionalInt select(BlockState wall, BlockGetter level, BlockPos pos) {
        if (!(wall.getBlock() instanceof WallBlock) || !hasKey(wall, WALL_IDS)
            || !wall.hasProperty(BlockStateProperties.WATERLOGGED)
            || wall.getValue(BlockStateProperties.WATERLOGGED)) {
            return OptionalInt.empty();
        }

        int mask = WallCollisionGeometry.mask(wall);
        for (int index = 0; index < DIRECTIONS.length; index++) {
            Direction directionToNeighbor = DIRECTIONS[index];
            BlockPos neighborPos = pos.relative(directionToNeighbor);
            BlockState neighbor = level.getBlockState(neighborPos);
            if (!(neighbor.getBlock() instanceof StairBlock)) {
                continue;
            }
            if (!hasKey(neighbor, STAIR_IDS)
                || !neighbor.hasProperty(StairBlock.FACING)
                || !neighbor.hasProperty(StairBlock.SHAPE)
                || !neighbor.hasProperty(BlockStateProperties.WATERLOGGED)
                || neighbor.getValue(BlockStateProperties.WATERLOGGED)) {
                return OptionalInt.empty();
            }

            Optional<Boolean> hasSolidFace = PaneNeighborFaceShape.stairFaceIsSolid(
                neighbor, level, neighborPos, directionToNeighbor.getOpposite());
            if (hasSolidFace.isEmpty()) {
                return OptionalInt.empty();
            }
            mask &= ~BITS[index];
            if (hasSolidFace.get()) {
                mask |= BITS[index];
            }
        }
        return OptionalInt.of(mask);
    }

    private static boolean hasKey(BlockState state, Set<String> keys) {
        Identifier key = BuiltInRegistries.BLOCK.getKey(state.getBlock());
        return key != null && keys.contains(key.toString());
    }
}

package me.wolfii.legacyparkourcompat.change.v1_11_2;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.change.common.PaneNeighborFaceShape;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.block.WallNeighborMaskHook;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.StairsShape;

import java.util.OptionalInt;
import java.util.Set;

/**
 * Selects the complete 1.11.2 wall mask for the mapped wall, stair and gate domain
 * and for the bounded single-TNT and mixed TNT/Oak Stairs tuples.
 *
 * <p>1.11.2 Ornithe Feather build.2: {@code WallBlock.resolveVirtualProperties}
 * uses {@code shouldConnectTo} for each side. {@code Material.isSolidBlocking}
 * rejects {@code TntBlock}'s translucent material and {@code StairsBlock.isCube}
 * rejects stairs. {@code WallBlock.getShapeIndex} selects the tall center box
 * after both bits are clear. In 1.12 Ornithe Feather build.2,
 * {@code WallBlock.shouldConnectTo} accepts TNT through {@code Block.getFaceShape}
 * and accepts a straight stair facing the wall through {@code StairsBlock.getFaceShape}
 * after {@code resolveVirtualProperties} recomputes its shape.
 *
 * <p>Ornithe Feather 1.11.2 and 1.12.2
 * {@code net.minecraft.block.WallBlock.shouldConnectTo} classify the four
 * neighbors before {@code getShapeIndex} consumes all four bits. Its
 * {@code shouldConnectTo(WorldView, BlockPos)} accepts each historically registered
 * fence gate regardless of facing; the 1.12 overload adds a
 * direction and accepts only the gate's matching axis. For air and mapped
 * stairs the 1.11.2 {@code StairsBlock.isCube} result is false. The 26.2 unobfuscated
 * {@code BlockStateData.register/create} mappings identify the current wall
 * and stair keys. Current {@code WallBlock} and {@code StairBlock} defaults
 * plus the {@code BlockState.CODEC} decode path establish
 * {@code waterlogged=false} for mapped states.
 */
@MovementChange(emulates = ParkourVersion.V1_11_2)
public final class WallNeighborMask implements WallNeighborMaskHook {
    private static final Direction[] NEIGHBOR_DIRECTIONS = {
        Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST
    };

    private static final Set<String> TARGET_KEYS = Set.of(
        "minecraft:cobblestone_wall",
        "minecraft:mossy_cobblestone_wall"
    );

    private static final Set<String> STAIR_KEYS = Set.of(
        "minecraft:oak_stairs",
        "minecraft:spruce_stairs",
        "minecraft:birch_stairs",
        "minecraft:jungle_stairs",
        "minecraft:acacia_stairs",
        "minecraft:dark_oak_stairs",
        "minecraft:cobblestone_stairs",
        "minecraft:brick_stairs",
        "minecraft:stone_brick_stairs",
        "minecraft:nether_brick_stairs",
        "minecraft:sandstone_stairs",
        "minecraft:red_sandstone_stairs",
        "minecraft:quartz_stairs",
        "minecraft:purpur_stairs"
    );

    public WallNeighborMask() {
    }

    @Override
    public OptionalInt selectMask(BlockState target, BlockGetter level, BlockPos pos) {
        if (!TARGET_KEYS.contains(key(target)) || !isFalseWaterlogged(target)) {
            return OptionalInt.empty();
        }
        BlockState north = level.getBlockState(pos.north());
        BlockState east = level.getBlockState(pos.east());
        BlockState south = level.getBlockState(pos.south());
        BlockState west = level.getBlockState(pos.west());
        if (!isAdmittedNeighbor(north)
            || !isAdmittedNeighbor(east)
            || !isAdmittedNeighbor(south)
            || !isAdmittedNeighbor(west)) {
            return isTntWitness(target, level, pos, north, east, south, west)
                ? OptionalInt.of(0) : OptionalInt.empty();
        }

        int mask = 0;
        if (HistoricalFenceGates.contains(north)) mask |= 1 << 2;
        if (HistoricalFenceGates.contains(east)) mask |= 1 << 3;
        if (HistoricalFenceGates.contains(south)) mask |= 1;
        if (HistoricalFenceGates.contains(west)) mask |= 1 << 1;
        return OptionalInt.of(mask);
    }

    private static boolean isTntWitness(BlockState wall, BlockGetter level, BlockPos pos, BlockState... neighbors) {
        int tntCount = 0;
        boolean dryTnt = false;
        int stairIndex = -1;
        for (int index = 0; index < neighbors.length; index++) {
            BlockState neighbor = neighbors[index];
            if (neighbor.is(Blocks.TNT)) {
                tntCount++;
                dryTnt = neighbor.getFluidState().isEmpty();
            } else if (neighbor.is(Blocks.OAK_STAIRS) && stairIndex == -1) {
                stairIndex = index;
            } else if (!neighbor.is(Blocks.AIR)) {
                return false;
            }
        }
        if (tntCount != 1) {
            return false;
        }
        if (stairIndex == -1) {
            return true;
        }

        if (!dryTnt || !wall.is(Blocks.COBBLESTONE_WALL) || !(level instanceof LevelReader world)) {
            return false;
        }
        BlockState stair = neighbors[stairIndex];
        Direction side = NEIGHBOR_DIRECTIONS[stairIndex];
        if (stair.getValue(StairBlock.WATERLOGGED) || stair.getValue(StairBlock.FACING) != side.getOpposite()) {
            return false;
        }

        BlockPos stairPos = pos.relative(side);
        BlockPos above = pos.above();
        if (!isLoaded(world, pos) || !isLoaded(world, above)) {
            return false;
        }
        for (Direction direction : NEIGHBOR_DIRECTIONS) {
            if (!isLoaded(world, pos.relative(direction)) || !isLoaded(world, stairPos.relative(direction))) {
                return false;
            }
        }
        return level.getBlockState(above).is(Blocks.AIR)
            && PaneNeighborFaceShape.resolveStairShape(stair, level, stairPos).orElse(null) == StairsShape.STRAIGHT;
    }

    private static boolean isLoaded(LevelReader world, BlockPos pos) {
        return !world.isOutsideBuildHeight(pos) && world.hasChunkAt(pos);
    }

    private static boolean isAdmittedNeighbor(BlockState state) {
        String key = key(state);
        if ("minecraft:air".equals(key)) {
            return true;
        }
        if (HistoricalFenceGates.contains(state)) {
            return true;
        }
        return STAIR_KEYS.contains(key) && isFalseWaterlogged(state);
    }

    private static boolean isFalseWaterlogged(BlockState state) {
        return state.hasProperty(BlockStateProperties.WATERLOGGED)
            && !state.getValue(BlockStateProperties.WATERLOGGED);
    }

    private static String key(BlockState state) {
        Identifier id = BuiltInRegistries.BLOCK.getKey(state.getBlock());
        return id == null ? "" : id.toString();
    }
}

package me.wolfii.legacyparkourcompat.change.v1_11_2;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.block.WallNeighborMaskHook;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.FenceGateBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

import java.util.OptionalInt;
import java.util.Set;

/**
 * Selects the complete 1.11.2 wall mask for the mapped wall, stair and gate domain.
 *
 * <p>Ornithe Feather 1.11.2 and 1.12.2
 * {@code net.minecraft.block.WallBlock.shouldConnectTo} classify the four
 * neighbors before {@code getShapeIndex} consumes all four bits. Its
 * {@code shouldConnectTo(WorldView, BlockPos)} accepts every neighboring
 * {@code FenceGateBlock} regardless of facing; the 1.12 overload adds a
 * direction and accepts only the gate's matching axis. For air and mapped
 * stairs the 1.11.2 {@code StairsBlock.isCube} result is false. The 26.2 unobfuscated
 * {@code BlockStateData.register/create} mappings identify the current wall
 * and stair keys. Current {@code WallBlock} and {@code StairBlock} defaults
 * plus the {@code BlockState.CODEC} decode path establish
 * {@code waterlogged=false} for mapped states.
 */
@MovementChange(emulates = ParkourVersion.V1_11_2)
public final class WallNeighborMask implements WallNeighborMaskHook {
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
            return OptionalInt.empty();
        }

        int mask = 0;
        if (north.getBlock() instanceof FenceGateBlock) mask |= 1 << 2;
        if (east.getBlock() instanceof FenceGateBlock) mask |= 1 << 3;
        if (south.getBlock() instanceof FenceGateBlock) mask |= 1;
        if (west.getBlock() instanceof FenceGateBlock) mask |= 1 << 1;
        return OptionalInt.of(mask);
    }

    private static boolean isAdmittedNeighbor(BlockState state) {
        String key = key(state);
        if ("minecraft:air".equals(key)) {
            return true;
        }
        if (state.getBlock() instanceof FenceGateBlock) {
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

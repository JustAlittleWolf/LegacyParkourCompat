package me.wolfii.legacyparkourcompat.change.v1_11_2;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.block.WallNeighborMaskHook;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

import java.util.OptionalInt;
import java.util.Set;

/**
 * Selects the complete 1.11.2 wall mask for the mapped wall and stair domain.
 *
 * <p>Ornithe Feather 1.11.2 and 1.12.2
 * {@code net.minecraft.block.WallBlock.shouldConnectTo} classify the four
 * neighbors before {@code getShapeIndex} consumes all four bits. For air and
 * mapped stairs the 1.11.2 {@code StairsBlock.isCube} result is false, so the
 * complete mask is zero. The 26.2 unobfuscated
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
        if (!isAdmittedNeighbor(level.getBlockState(pos.north()))
            || !isAdmittedNeighbor(level.getBlockState(pos.east()))
            || !isAdmittedNeighbor(level.getBlockState(pos.south()))
            || !isAdmittedNeighbor(level.getBlockState(pos.west()))) {
            return OptionalInt.empty();
        }

        // Every admitted old predicate result is false; return the complete mask.
        return OptionalInt.of(0);
    }

    private static boolean isAdmittedNeighbor(BlockState state) {
        String key = key(state);
        if ("minecraft:air".equals(key)) {
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

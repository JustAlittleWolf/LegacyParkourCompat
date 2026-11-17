package me.wolfii.legacyparkourcompat.change.v1_11_2;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.VanillaFn;
import me.wolfii.legacyparkourcompat.mechanic.hook.fluid.FallingFluidFaceSturdyHook;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluids;

import java.util.List;

/**
 * Restores 1.11.2's solid-material side-face result for vanilla stairs in the
 * level-eight-and-higher water-flow query.
 *
 * <p>Source: 1.11.2 Ornithe Feather, {@code LiquidBlock.isFaceSolid} and
 * {@code Block.isFaceSolid}; 1.12 Ornithe Feather,
 * {@code LiquidBlock.isFaceSolid} and {@code StairsBlock.getFaceShape}.
 * The former delegates side faces to material solidity; the latter excludes
 * stairs before testing a solid face shape. The native predicate remains the
 * fallback for all inputs outside this accepted water/stair side-face slice.
 */
@MovementChange(emulates = ParkourVersion.V1_11_2)
public final class StairFallingFluidFace implements FallingFluidFaceSturdyHook {
    private static final List<String> STAIR_IDS = List.of(
        "minecraft:oak_stairs",
        "minecraft:stone_stairs",
        "minecraft:brick_stairs",
        "minecraft:stone_brick_stairs",
        "minecraft:nether_brick_stairs",
        "minecraft:sandstone_stairs",
        "minecraft:spruce_stairs",
        "minecraft:birch_stairs",
        "minecraft:jungle_stairs",
        "minecraft:quartz_stairs",
        "minecraft:acacia_stairs",
        "minecraft:dark_oak_stairs",
        "minecraft:red_sandstone_stairs",
        "minecraft:purpur_stairs"
    );

    private final String variant;

    private StairFallingFluidFace(String variant) {
        this.variant = variant;
    }

    @MovementChange.Factory
    public static List<StairFallingFluidFace> variants() {
        return STAIR_IDS.stream().map(StairFallingFluidFace::new).toList();
    }

    @Override
    public String variant() {
        return this.variant;
    }

    @Override
    public boolean isFaceSturdy(FlowingFluid fluid, Player player, BlockState state, BlockGetter level,
                                BlockPos pos, Direction direction, VanillaFn<Boolean> vanilla) {
        if (fluid != Fluids.WATER || direction == Direction.UP || direction == Direction.DOWN
            || !(state.getBlock() instanceof StairBlock)) {
            return vanilla.get();
        }

        // Every accepted vanilla stair inherits a solid material in 1.11.2.
        // Its partial collision shape does not affect Block.isFaceSolid.
        return true;
    }
}

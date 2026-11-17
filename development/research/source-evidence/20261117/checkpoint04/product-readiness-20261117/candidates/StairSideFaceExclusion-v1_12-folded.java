package me.wolfii.legacyparkourcompat.change.v1_12.fluid;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.VanillaFn;
import me.wolfii.legacyparkourcompat.mechanic.hook.fluid.FallingFluidFaceSturdyHook;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluids;

import java.util.List;

/**
 * Reproduces the 1.12 exclusion of stairs from horizontal falling-water face checks.
 *
 * <p>1.11.2, Ornithe Feather, {@code net/minecraft/block/LiquidBlock.java},
 * {@code isFaceSolid(WorldView, BlockPos, Direction)} (83–92), delegates side faces
 * to the neighboring material's solid predicate. 1.12, Ornithe Feather, the same
 * class and member (82–100), explicitly excludes {@code StairsBlock}. The 1.12
 * {@code getFlow(...)} method (139–177) owns the falling and
 * level-at-least-eight gates; this hook preserves those call-site gates.
 */
@MovementChange(emulates = ParkourVersion.V1_12)
public final class StairSideFaceExclusion implements FallingFluidFaceSturdyHook {
    private static final List<String> STAIR_BLOCK_IDS = List.of(
        "minecraft:oak_stairs",
        "minecraft:cobblestone_stairs",
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

    private final String blockId;

    private StairSideFaceExclusion(String blockId) {
        this.blockId = blockId;
    }

    @MovementChange.Factory
    public static List<StairSideFaceExclusion> variants() {
        return STAIR_BLOCK_IDS.stream().map(StairSideFaceExclusion::new).toList();
    }

    @Override
    public String variant() {
        return this.blockId;
    }

    @Override
    public boolean isFaceSturdy(FlowingFluid fluid, Player player, BlockState state, BlockGetter level,
                                BlockPos pos, Direction direction, VanillaFn<Boolean> vanilla) {
        if (fluid.isSame(Fluids.WATER) && direction.getAxis().isHorizontal()) {
            return false;
        }
        return vanilla.get();
    }
}

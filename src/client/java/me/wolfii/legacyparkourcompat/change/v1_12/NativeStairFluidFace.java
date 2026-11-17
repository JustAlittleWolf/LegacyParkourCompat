package me.wolfii.legacyparkourcompat.change.v1_12;

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
 * Uses native face support except for the bounded 1.12 stair-side exclusion.
 * 1.12 Ornithe Feather {@code LiquidBlock.isFaceSolid} rejects stairs on side
 * faces; 1.11.2 delegates those faces to material solidity. 26.2
 * {@code FlowingFluid.isSolidFace} supplies the native result for other inputs.
 */
@MovementChange(emulates = ParkourVersion.V1_12)
public final class NativeStairFluidFace implements FallingFluidFaceSturdyHook {
    private static final List<String> V1_12_STAIR_IDS = List.of(
        "minecraft:oak_stairs", "minecraft:cobblestone_stairs", "minecraft:brick_stairs",
        "minecraft:stone_brick_stairs", "minecraft:nether_brick_stairs", "minecraft:sandstone_stairs",
        "minecraft:spruce_stairs", "minecraft:birch_stairs", "minecraft:jungle_stairs",
        "minecraft:quartz_stairs", "minecraft:acacia_stairs", "minecraft:dark_oak_stairs",
        "minecraft:red_sandstone_stairs", "minecraft:purpur_stairs"
    );

    private final String blockId;

    private NativeStairFluidFace(String blockId) {
        this.blockId = blockId;
    }

    @MovementChange.Factory
    public static List<NativeStairFluidFace> variants() {
        return V1_13_STAIR_IDS.stream().map(NativeStairFluidFace::new).toList();
    }

    @Override
    public String variant() {
        return this.blockId;
    }

    @Override
    public boolean isFaceSturdy(FlowingFluid fluid, Player player, BlockState state, BlockGetter level,
                                BlockPos pos, Direction direction, VanillaFn<Boolean> vanilla) {
        if (V1_12_STAIR_IDS.contains(this.blockId)
            && fluid.isSame(Fluids.WATER)
            && direction.getAxis().isHorizontal()) {
            return false;
        }
        return vanilla.get();
    }
}

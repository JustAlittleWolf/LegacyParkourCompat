package me.wolfii.legacyparkourcompat.change.v1_10_1;

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

/**
 * The accepted audit confirms the old material predicate in Ornithe Feather 1.10.2
 * LiquidBlock.isFaceSolid and BedBlock, but does not inspect 1.10.1. Since V1_10_1 aliases
 * both releases, keep this group native until the uninspected member is source-confirmed.
 */
@MovementChange(emulates = ParkourVersion.V1_10_1)
public final class NativeWaterBedFallingFluidFace implements FallingFluidFaceSturdyHook {
    @Override
    public String variant() {
        return "minecraft:red_bed";
    }

    @Override
    public boolean isFaceSturdy(FlowingFluid fluid, Player player, BlockState state, BlockGetter level,
                                BlockPos pos, Direction direction, VanillaFn<Boolean> vanilla) {
        return vanilla.get();
    }
}

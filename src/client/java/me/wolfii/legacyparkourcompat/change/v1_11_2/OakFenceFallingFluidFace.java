package me.wolfii.legacyparkourcompat.change.v1_11_2;

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

/**
 * In 1.11.2 Ornithe Feather, LiquidBlock.isFaceSolid delegates the ordinary
 * side check to Block.isFaceSolid, which accepts the oak fence's solid wood
 * material. In 1.12 Ornithe Feather, LiquidBlock.isFaceSolid requires a SOLID
 * face, and FenceBlock.getFaceShape returns MIDDLE_POLE for horizontal faces.
 * LiquidBlock.getFlow in both releases tests the side cell and the one above it.
 * The native falling-water scan and flow vector arithmetic remain in the caller.
 */
@MovementChange(emulates = ParkourVersion.V1_11_2)
public final class OakFenceFallingFluidFace implements FallingFluidFaceSturdyHook {
    @Override
    public String variant() {
        return "minecraft:oak_fence";
    }

    @Override
    public boolean isFaceSturdy(FlowingFluid fluid, Player player, BlockState state, BlockGetter level,
                               BlockPos pos, Direction direction, VanillaFn<Boolean> vanilla) {
        if (fluid.isSame(Fluids.WATER) && direction.getAxis().isHorizontal()
            && state.getFluidState().isEmpty() && !player.getAbilities().flying && !player.isPassenger()) {
            return true;
        }
        return vanilla.get();
    }
}

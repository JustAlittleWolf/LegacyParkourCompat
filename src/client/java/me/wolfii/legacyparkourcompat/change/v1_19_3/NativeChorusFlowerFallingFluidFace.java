package me.wolfii.legacyparkourcompat.change.v1_19_3;

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
 * Selected profiles through 1.19.3 use this native predicate. Mojmap 1.19.4
 * and 1.20/1.20.1 ChorusFlowerBlock has no getBlockSupportShape override;
 * 1.20.2 adds the inset shape, so the separate V1_20 hook supplies the old
 * horizontal answer for profiles 1.19.4 through 1.20.1.
 */
@MovementChange(emulates = ParkourVersion.V1_19_3)
public final class NativeChorusFlowerFallingFluidFace implements FallingFluidFaceSturdyHook {
    @Override
    public String variant() {
        return "minecraft:chorus_flower";
    }

    @Override
    public boolean isFaceSturdy(FlowingFluid fluid, Player player, BlockState state, BlockGetter level,
                                BlockPos pos, Direction direction, VanillaFn<Boolean> vanilla) {
        return vanilla.get();
    }
}

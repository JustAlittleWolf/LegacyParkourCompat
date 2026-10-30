package me.wolfii.legacyparkourcompat.change.v1_21;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.VanillaCall;
import me.wolfii.legacyparkourcompat.mechanic.hook.callback.SweptBlockResponseHook;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;

/** Leaves callback bodies and fluid dispatch native before the accepted 1.21.2 boundary. */
@MovementChange(emulates = ParkourVersion.V1_21)
public final class SweptBlockResponseNativeFence implements SweptBlockResponseHook {
    @Override
    public boolean usesHistoricalResponses() {
        return false;
    }

    @Override
    public boolean apply(BlockState state, Level level, BlockPos pos, Entity entity, VanillaCall nativeCallback) {
        return false;
    }

    @Override
    public boolean omitSweptFluidCallback(FluidState state) {
        return false;
    }
}

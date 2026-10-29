package me.wolfii.legacyparkourcompat.change.v1_11_1;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.block.WallNeighborMaskHook;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;

import java.util.OptionalInt;

/** Keeps earlier, unproved wall-neighbor selectors on the native wall shape path. */
@MovementChange(emulates = ParkourVersion.V1_11_1)
public final class NativeBeforeWallNeighborMask implements WallNeighborMaskHook {
    public NativeBeforeWallNeighborMask() {
    }

    @Override
    public boolean suppliesHistoricalMask() {
        return false;
    }

    @Override
    public OptionalInt selectMask(BlockState target, BlockGetter level, BlockPos pos) {
        return OptionalInt.empty();
    }
}

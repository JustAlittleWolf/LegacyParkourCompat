package me.wolfii.legacyparkourcompat.mechanic.hook.block;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;

import java.util.OptionalInt;

/** Selects a complete historical wall mask only for a closed current-state domain. */
@MechanicType("block.wall.neighbor-mask")
public interface WallNeighborMaskHook extends VersionedMechanic {
    /** False for resolver fences that preserve the pre-existing shape path. */
    default boolean suppliesHistoricalMask() {
        return true;
    }

    OptionalInt selectMask(BlockState target, BlockGetter level, BlockPos pos);
}

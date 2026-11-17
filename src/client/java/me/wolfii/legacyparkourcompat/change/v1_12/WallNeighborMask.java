package me.wolfii.legacyparkourcompat.change.v1_12;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.change.common.WallNeighborMaskSelection;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.block.WallNeighborMaskHook;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;

import java.util.OptionalInt;

/**
 * 1.11.2 Ornithe Feather {@code WallBlock.shouldConnectTo} rejects stairs
 * because {@code StairsBlock.isCube} is false. In 1.12 Ornithe Feather,
 * {@code WallBlock.resolveVirtualProperties/shouldConnectTo} use the neighbor's
 * directional face and {@code StairsBlock.getFaceShape}; supported SOLID stair
 * faces set the corresponding collision bit. The checked 1.12.1 and 1.12.2
 * wall/stair sources are byte-identical to 1.12.
 */
@MovementChange(emulates = ParkourVersion.V1_12)
public final class WallNeighborMask implements WallNeighborMaskHook {
    public WallNeighborMask() {
    }

    @Override
    public boolean suppliesHistoricalMask() {
        return true;
    }

    @Override
    public OptionalInt selectMask(BlockState target, BlockGetter level, BlockPos pos) {
        return WallNeighborMaskSelection.select(target, level, pos);
    }
}

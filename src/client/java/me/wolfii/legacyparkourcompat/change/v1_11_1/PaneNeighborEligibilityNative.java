package me.wolfii.legacyparkourcompat.change.v1_11_1;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.block.pane.PaneNeighborEligibilityHook;
import net.minecraft.world.level.block.state.BlockState;

/** Native barrier: the 1.11.2 selector does not apply at this version boundary. */
@MovementChange(emulates = ParkourVersion.V1_11_1)
public final class PaneNeighborEligibilityNative implements PaneNeighborEligibilityHook {
    public PaneNeighborEligibilityNative() {
    }

    @Override
    public Result classify(BlockState neighborState) {
        return Result.NATIVE;
    }
}

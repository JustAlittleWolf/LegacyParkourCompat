package me.wolfii.legacyparkourcompat.mechanic.hook.block.pane;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Optional;

/** Selects the historical pane-neighbor arm eligibility policy. */
@MechanicType("block.pane.neighbor-eligibility")
public interface PaneNeighborEligibilityHook extends VersionedMechanic {
    /**
     * NATIVE asks the integration to leave its original predicate untouched. This is
     * required for unrecognized blocks and for slab states without the audited TYPE
     * selector, because the old registered block identity cannot otherwise be decided.
     */
    Result classify(BlockState neighborState);

    /** Resolves one neighbor arm using the selected version's pane policy. */
    default Optional<Boolean> connects(BlockState neighborState, BlockGetter level, BlockPos pos, Direction face) {
        return switch (classify(neighborState)) {
            case ELIGIBLE -> Optional.of(true);
            case INELIGIBLE -> Optional.of(false);
            case NATIVE -> Optional.empty();
        };
    }

    enum Result {
        ELIGIBLE,
        INELIGIBLE,
        NATIVE
    }
}

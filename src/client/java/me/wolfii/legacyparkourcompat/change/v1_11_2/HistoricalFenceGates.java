package me.wolfii.legacyparkourcompat.change.v1_11_2;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

/** The six gates in Ornithe Feather 1.11–1.11.2 Blocks, with FENCE_GATE mapped to oak. */
final class HistoricalFenceGates {
    private HistoricalFenceGates() {
    }

    static boolean contains(BlockState state) {
        return state.is(Blocks.OAK_FENCE_GATE) || state.is(Blocks.SPRUCE_FENCE_GATE)
            || state.is(Blocks.BIRCH_FENCE_GATE) || state.is(Blocks.JUNGLE_FENCE_GATE)
            || state.is(Blocks.ACACIA_FENCE_GATE) || state.is(Blocks.DARK_OAK_FENCE_GATE);
    }
}

package me.wolfii.legacyparkourcompat.change.common;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

/** Trapdoor block identities from the Ornithe Feather 1.12.2 and 1.13 Blocks registries. */
public final class HistoricalTrapdoors {
    private HistoricalTrapdoors() {}

    public static boolean existedBefore1_13(BlockState state) {
        return state.is(Blocks.OAK_TRAPDOOR) || state.is(Blocks.IRON_TRAPDOOR);
    }

    public static boolean existedIn1_13(BlockState state) {
        return existedBefore1_13(state) || state.is(Blocks.SPRUCE_TRAPDOOR)
            || state.is(Blocks.BIRCH_TRAPDOOR) || state.is(Blocks.JUNGLE_TRAPDOOR)
            || state.is(Blocks.ACACIA_TRAPDOOR) || state.is(Blocks.DARK_OAK_TRAPDOOR);
    }
}

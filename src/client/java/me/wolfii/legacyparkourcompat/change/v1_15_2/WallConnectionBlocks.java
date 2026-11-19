package me.wolfii.legacyparkourcompat.change.v1_15_2;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Set;

/**
 * 1.15.2 Mojmap Blocks registers fourteen walls, iron bars, a plain pane and sixteen stained panes.
 * 26.2 unobfuscated Blocks also registers newer walls and copper bars using the same block classes.
 */
final class WallConnectionBlocks {
    private static final Set<Block> WALLS = Set.of(
        Blocks.COBBLESTONE_WALL, Blocks.MOSSY_COBBLESTONE_WALL, Blocks.BRICK_WALL,
        Blocks.PRISMARINE_WALL, Blocks.RED_SANDSTONE_WALL, Blocks.MOSSY_STONE_BRICK_WALL,
        Blocks.GRANITE_WALL, Blocks.STONE_BRICK_WALL, Blocks.NETHER_BRICK_WALL,
        Blocks.ANDESITE_WALL, Blocks.RED_NETHER_BRICK_WALL, Blocks.SANDSTONE_WALL,
        Blocks.END_STONE_BRICK_WALL, Blocks.DIORITE_WALL
    );
    private static final Set<Block> STAINED_PANES = Set.copyOf(Blocks.STAINED_GLASS_PANE.asList());

    private WallConnectionBlocks() {
    }

    static boolean isWall(BlockState state) {
        return WALLS.contains(state.getBlock());
    }

    static boolean isPaneOrBars(BlockState state) {
        return state.is(Blocks.GLASS_PANE) || state.is(Blocks.IRON_BARS) || STAINED_PANES.contains(state.getBlock());
    }
}

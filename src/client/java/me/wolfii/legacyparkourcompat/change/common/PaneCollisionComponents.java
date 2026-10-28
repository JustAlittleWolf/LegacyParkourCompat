package me.wolfii.legacyparkourcompat.change.common;

import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.ArrayList;
import java.util.List;

/**
 * Exact pane component geometry and emission order shared by the 1.11.2 and
 * 1.12+ provider policies. Ornithe Feather 1.11.2 and 1.12/1.12.1/1.12.2
 * {@code PaneBlock.SHAPES} and {@code PaneBlock.addCollisions} emit the center
 * post, then north, south, east, west arms selected by their respective
 * neighbor predicates. These are separate collision boxes; do not reconstruct
 * them from the mask-indexed outline union.
 */
public final class PaneCollisionComponents {
    public static final List<String> BLOCK_IDS = List.of(
        "minecraft:iron_bars", "minecraft:glass_pane",
        "minecraft:white_stained_glass_pane", "minecraft:orange_stained_glass_pane",
        "minecraft:magenta_stained_glass_pane", "minecraft:light_blue_stained_glass_pane",
        "minecraft:yellow_stained_glass_pane", "minecraft:lime_stained_glass_pane",
        "minecraft:pink_stained_glass_pane", "minecraft:gray_stained_glass_pane",
        "minecraft:light_gray_stained_glass_pane", "minecraft:cyan_stained_glass_pane",
        "minecraft:purple_stained_glass_pane", "minecraft:blue_stained_glass_pane",
        "minecraft:brown_stained_glass_pane", "minecraft:green_stained_glass_pane",
        "minecraft:red_stained_glass_pane", "minecraft:black_stained_glass_pane"
    );

    private static final VoxelShape CENTER = Shapes.box(7.0 / 16.0, 0.0, 7.0 / 16.0,
        9.0 / 16.0, 1.0, 9.0 / 16.0);
    private static final VoxelShape NORTH = Shapes.box(7.0 / 16.0, 0.0, 0.0,
        9.0 / 16.0, 1.0, 9.0 / 16.0);
    private static final VoxelShape SOUTH = Shapes.box(7.0 / 16.0, 0.0, 7.0 / 16.0,
        9.0 / 16.0, 1.0, 1.0);
    private static final VoxelShape EAST = Shapes.box(7.0 / 16.0, 0.0, 7.0 / 16.0,
        1.0, 1.0, 9.0 / 16.0);
    private static final VoxelShape WEST = Shapes.box(0.0, 0.0, 7.0 / 16.0,
        9.0 / 16.0, 1.0, 9.0 / 16.0);

    private PaneCollisionComponents() {
    }

    /** Returns source-ordered block-local components for the four resolved directions. */
    public static List<VoxelShape> ordered(boolean north, boolean south, boolean east, boolean west) {
        List<VoxelShape> components = new ArrayList<>(5);
        components.add(CENTER);
        if (north) components.add(NORTH);
        if (south) components.add(SOUTH);
        if (east) components.add(EAST);
        if (west) components.add(WEST);
        return List.copyOf(components);
    }

    /** Forms the provider union for ordinary shape consumers; movement keeps {@link #ordered} parts. */
    public static VoxelShape union(List<VoxelShape> components) {
        VoxelShape shape = Shapes.empty();
        for (VoxelShape component : components) {
            shape = Shapes.or(shape, component);
        }
        return shape;
    }
}

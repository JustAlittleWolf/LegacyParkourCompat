package me.wolfii.legacyparkourcompat.change.v1_8;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.block.BlockCollisionShapeHook;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.StainedGlassBlock;
import net.minecraft.world.level.block.StainedGlassPaneBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Source evidence (paths are relative to decompiled_minecraft/):
 * 1.8.9/ornithe-feather/net/minecraft/block/PaneBlock.java:
 *   public void addCollisions(World world, BlockPos pos, BlockState state, Box shape, List&lt;Box&gt; collisions, Entity entity).
 * 1.9.4/ornithe-feather/net/minecraft/block/PaneBlock.java:
 *   public void addCollisions(BlockState state, World world, BlockPos pos, Box shape, List&lt;Box&gt; collisions, @Nullable Entity entity).
 * Remaining source/applicability gates:
 *   Later applicability gate: exact introduction of campaign-COLL-02-pane-collision-shapes is unknown within (1.8.9, 1.9.4]
 * Source-content correspondence and compilation do not establish tick-level runtime parity.
 */
@MovementChange(emulates = ParkourVersion.V1_8)
public final class PaneCollisionShape implements BlockCollisionShapeHook {
    public static final List<String> BLOCK_IDS = List.of(
        "minecraft:iron_bars",
        "minecraft:glass_pane",
        "minecraft:white_stained_glass_pane",
        "minecraft:orange_stained_glass_pane",
        "minecraft:magenta_stained_glass_pane",
        "minecraft:light_blue_stained_glass_pane",
        "minecraft:yellow_stained_glass_pane",
        "minecraft:lime_stained_glass_pane",
        "minecraft:pink_stained_glass_pane",
        "minecraft:gray_stained_glass_pane",
        "minecraft:light_gray_stained_glass_pane",
        "minecraft:cyan_stained_glass_pane",
        "minecraft:purple_stained_glass_pane",
        "minecraft:blue_stained_glass_pane",
        "minecraft:brown_stained_glass_pane",
        "minecraft:green_stained_glass_pane",
        "minecraft:red_stained_glass_pane",
        "minecraft:black_stained_glass_pane"
    );

    private static final VoxelShape X = Shapes.box(0.0, 0.0, 7.0 / 16.0, 1.0, 1.0, 9.0 / 16.0);
    private static final VoxelShape X_WEST = Shapes.box(0.0, 0.0, 7.0 / 16.0, 8.0 / 16.0, 1.0, 9.0 / 16.0);
    private static final VoxelShape X_EAST = Shapes.box(8.0 / 16.0, 0.0, 7.0 / 16.0, 1.0, 1.0, 9.0 / 16.0);
    private static final VoxelShape Z = Shapes.box(7.0 / 16.0, 0.0, 0.0, 9.0 / 16.0, 1.0, 1.0);
    private static final VoxelShape Z_NORTH = Shapes.box(7.0 / 16.0, 0.0, 0.0, 9.0 / 16.0, 1.0, 8.0 / 16.0);
    private static final VoxelShape Z_SOUTH = Shapes.box(7.0 / 16.0, 0.0, 8.0 / 16.0, 9.0 / 16.0, 1.0, 1.0);

    private final String blockId;

    @MovementChange.Factory
    public static List<PaneCollisionShape> variants() {
        return BLOCK_IDS.stream().map(PaneCollisionShape::new).toList();
    }

    public PaneCollisionShape(String blockId) {
        this.blockId = Objects.requireNonNull(blockId, "blockId");
    }

    @Override
    public String blockId() {
        return this.blockId;
    }

    @Override
    public Optional<VoxelShape> collisionShape(
        BlockState state,
        BlockGetter level,
        BlockPos pos,
        CollisionContext context
    ) {
        boolean north = this.connectsTo(level, pos.north());
        boolean east = this.connectsTo(level, pos.east());
        boolean south = this.connectsTo(level, pos.south());
        boolean west = this.connectsTo(level, pos.west());

        if (!north && !east && !south && !west) {
            return Optional.of(Shapes.or(X, Z));
        }

        VoxelShape shape = Shapes.empty();
        if (east || west) {
            VoxelShape xShape = east ? (west ? X : X_EAST) : X_WEST;
            shape = Shapes.or(shape, xShape);
        }
        if (north || south) {
            VoxelShape zShape = north ? (south ? Z : Z_NORTH) : Z_SOUTH;
            shape = Shapes.or(shape, zShape);
        }
        return Optional.of(shape);
    }

    private boolean connectsTo(BlockGetter level, BlockPos pos) {
        BlockState neighbor = level.getBlockState(pos);
        var block = neighbor.getBlock();
        return neighbor.isSolidRender()
            || block == Blocks.IRON_BARS
            || block == Blocks.GLASS_PANE
            || block instanceof StainedGlassPaneBlock
            || block == Blocks.GLASS
            || block instanceof StainedGlassBlock;
    }
}

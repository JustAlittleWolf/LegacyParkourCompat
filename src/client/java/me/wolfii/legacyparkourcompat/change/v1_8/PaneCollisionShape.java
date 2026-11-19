package me.wolfii.legacyparkourcompat.change.v1_8;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.block.BlockCollisionShapeHook;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CrossCollisionBlock;
import net.minecraft.world.level.block.IronBarsBlock;
import net.minecraft.world.level.block.StainedGlassBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

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
@MovementChange(emulates = ParkourVersion.V1_8_9)
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
    private static final VoxelShape NATIVE_POST = Block.column(2.0, 0.0, 16.0);
    private static final Set<Block> STAINED_PANES = Set.copyOf(Blocks.STAINED_GLASS_PANE.asList());

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
        BlockState connections = state;
        boolean nativePost = false;
        for (Direction direction : Direction.Plane.HORIZONTAL) {
            BlockState neighbor = level.getBlockState(pos.relative(direction));
            Block block = neighbor.getBlock();
            // 26.2 IronBarsBlock.attachsTo includes later bars such as copper.
            // Keep that side's native state bit and CrossCollisionBlock.makeShapes
            // center post while resolving the other sides independently.
            if (block instanceof IronBarsBlock && block != Blocks.IRON_BARS
                && block != Blocks.GLASS_PANE && !STAINED_PANES.contains(block)) {
                nativePost = true;
                continue;
            }
            connections = connections.setValue(
                CrossCollisionBlock.PROPERTY_BY_DIRECTION.get(direction), connectsTo(neighbor)
            );
        }
        boolean north = connections.getValue(CrossCollisionBlock.NORTH);
        boolean east = connections.getValue(CrossCollisionBlock.EAST);
        boolean south = connections.getValue(CrossCollisionBlock.SOUTH);
        boolean west = connections.getValue(CrossCollisionBlock.WEST);

        if (!nativePost && !north && !east && !south && !west) {
            return Optional.of(Shapes.or(X, Z));
        }

        VoxelShape shape = nativePost ? NATIVE_POST : Shapes.empty();
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

    private static boolean connectsTo(BlockState neighbor) {
        var block = neighbor.getBlock();
        // 1.8–1.8.9 Feather PaneBlock.shouldConnectTo reads Block.isOpaque,
        // cached from PistonBaseBlock.isSolidRender() == false for both pistons.
        // In 26.2 the unextended PistonBaseBlock.getShape is a full occluder.
        if (block == Blocks.PISTON || block == Blocks.STICKY_PISTON) {
            return false;
        }
        return neighbor.isSolidRender()
            || block instanceof IronBarsBlock
            || block == Blocks.GLASS
            || block instanceof StainedGlassBlock;
    }
}

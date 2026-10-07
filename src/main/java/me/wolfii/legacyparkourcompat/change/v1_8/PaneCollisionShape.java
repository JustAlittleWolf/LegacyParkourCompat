package me.wolfii.legacyparkourcompat.change.v1_8;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.BlockCollisionShape;
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

@MovementChange(emulates = ParkourVersion.V1_8)
public final class PaneCollisionShape implements BlockCollisionShape {
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

        VoxelShape xShape = east == west ? X : east ? X_EAST : X_WEST;
        VoxelShape zShape = north == south ? Z : north ? Z_NORTH : Z_SOUTH;
        return Optional.of(Shapes.or(xShape, zShape));
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

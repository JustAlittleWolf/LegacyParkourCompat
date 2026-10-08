package me.wolfii.legacyparkourcompat.change.v1_8;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.BlockCollisionShape;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FenceBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/** Restores the 1.8 collision arm when an End Portal Frame borders a fence. */
@MovementChange(emulates = ParkourVersion.V1_8)
public final class FencePortalFrameConnection implements BlockCollisionShape {
    public static final List<String> BLOCK_IDS = List.of(
        "minecraft:oak_fence",
        "minecraft:spruce_fence",
        "minecraft:birch_fence",
        "minecraft:jungle_fence",
        "minecraft:acacia_fence",
        "minecraft:dark_oak_fence",
        "minecraft:nether_brick_fence"
    );

    private static final VoxelShape NORTH_ARM = Shapes.box(0.375, 0.0, 0.0, 0.625, 1.5, 0.625);
    private static final VoxelShape EAST_ARM = Shapes.box(0.375, 0.0, 0.375, 1.0, 1.5, 0.625);
    private static final VoxelShape SOUTH_ARM = Shapes.box(0.375, 0.0, 0.375, 0.625, 1.5, 1.0);
    private static final VoxelShape WEST_ARM = Shapes.box(0.0, 0.0, 0.375, 0.625, 1.5, 0.625);

    private final String blockId;

    public FencePortalFrameConnection(String blockId) {
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
        boolean northFrame = level.getBlockState(pos.north()).is(Blocks.END_PORTAL_FRAME);
        boolean eastFrame = level.getBlockState(pos.east()).is(Blocks.END_PORTAL_FRAME);
        boolean southFrame = level.getBlockState(pos.south()).is(Blocks.END_PORTAL_FRAME);
        boolean westFrame = level.getBlockState(pos.west()).is(Blocks.END_PORTAL_FRAME);
        boolean addNorth = northFrame && !state.getValue(FenceBlock.NORTH);
        boolean addEast = eastFrame && !state.getValue(FenceBlock.EAST);
        boolean addSouth = southFrame && !state.getValue(FenceBlock.SOUTH);
        boolean addWest = westFrame && !state.getValue(FenceBlock.WEST);
        if (!addNorth && !addEast && !addSouth && !addWest) {
            return Optional.empty();
        }

        VoxelShape shape = Shapes.box(0.375, 0.0, 0.375, 0.625, 1.5, 0.625);
        if (state.getValue(FenceBlock.NORTH) || addNorth) {
            shape = Shapes.or(shape, NORTH_ARM);
        }
        if (state.getValue(FenceBlock.EAST) || addEast) {
            shape = Shapes.or(shape, EAST_ARM);
        }
        if (state.getValue(FenceBlock.SOUTH) || addSouth) {
            shape = Shapes.or(shape, SOUTH_ARM);
        }
        if (state.getValue(FenceBlock.WEST) || addWest) {
            shape = Shapes.or(shape, WEST_ARM);
        }
        return Optional.of(shape);
    }
}

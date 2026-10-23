package me.wolfii.legacyparkourcompat.change.v1_12;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.block.BlockCollisionShapeHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.block.LegacyFenceWallNeighborConnectionHook;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FenceBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.List;
import java.util.Optional;

/**
 * Restores the 1.12 fence arms beside frosted ice and TNT in the player collision shape.
 * In Minecraft 1.12.2 (Ornithe Feather), FenceBlock.shouldConnectTo and
 * FenceBlock.addCollisions accept these solid-face neighbors and include their directional arms.
 * In Minecraft 1.13 (Ornithe Feather), FenceBlock.shouldConnectTo excludes both blocks and
 * PaneBlock.getCollisionShape uses the resulting connection properties. The existing
 * 1.8.9 portal-frame provider remains a separate BlockCollisionShapeHook change; the resolver
 * keeps it for earlier profiles and selects this provider at the 1.12 boundary.
 */
@MovementChange(emulates = ParkourVersion.V1_12)
public final class FenceLegacySolidConnections implements BlockCollisionShapeHook {
    private static final List<String> FENCE_IDS = List.of(
        "minecraft:oak_fence",
        "minecraft:spruce_fence",
        "minecraft:birch_fence",
        "minecraft:jungle_fence",
        "minecraft:acacia_fence",
        "minecraft:dark_oak_fence",
        "minecraft:nether_brick_fence"
    );

    private static final VoxelShape CENTER = Block.box(6, 0, 6, 10, 24, 10);
    private static final VoxelShape NORTH = Block.box(6, 0, 0, 10, 24, 6);
    private static final VoxelShape EAST = Block.box(10, 0, 6, 16, 24, 10);
    private static final VoxelShape SOUTH = Block.box(6, 0, 10, 10, 24, 16);
    private static final VoxelShape WEST = Block.box(0, 0, 6, 6, 24, 10);

    private final String blockId;

    private FenceLegacySolidConnections(String blockId) {
        this.blockId = blockId;
    }

    @MovementChange.Factory
    public static List<FenceLegacySolidConnections> variants() {
        return FENCE_IDS.stream().map(FenceLegacySolidConnections::new).toList();
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
        return Optional.empty();
    }

    @Override
    public Optional<VoxelShape> collisionShape(
        BlockState state,
        BlockGetter level,
        BlockPos pos,
        CollisionContext context,
        int restoredConnectionMask
    ) {
        if (!(state.getBlock() instanceof FenceBlock) || restoredConnectionMask == 0) {
            return Optional.empty();
        }

        VoxelShape shape = CENTER;
        if (state.getValue(FenceBlock.NORTH)
            || (restoredConnectionMask & LegacyFenceWallNeighborConnectionHook.NORTH_MASK) != 0) {
            shape = Shapes.or(shape, NORTH);
        }
        if (state.getValue(FenceBlock.EAST)
            || (restoredConnectionMask & LegacyFenceWallNeighborConnectionHook.EAST_MASK) != 0) {
            shape = Shapes.or(shape, EAST);
        }
        if (state.getValue(FenceBlock.SOUTH)
            || (restoredConnectionMask & LegacyFenceWallNeighborConnectionHook.SOUTH_MASK) != 0) {
            shape = Shapes.or(shape, SOUTH);
        }
        if (state.getValue(FenceBlock.WEST)
            || (restoredConnectionMask & LegacyFenceWallNeighborConnectionHook.WEST_MASK) != 0) {
            shape = Shapes.or(shape, WEST);
        }
        return Optional.of(shape);
    }
}

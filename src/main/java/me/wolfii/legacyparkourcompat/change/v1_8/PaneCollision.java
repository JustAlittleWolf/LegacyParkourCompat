package me.wolfii.legacyparkourcompat.change.v1_8;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.BlockCollisionShape;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.Optional;

/**
 * 1.8 pane/bar collision: an unconnected pane is a cross, and connected
 * internal segments stop at 0.5. 1.9 uses a 2×2 post with 1-pixel-longer arms.
 */
@MovementChange(emulates = ParkourVersion.V1_8)
public final class PaneCollision implements BlockCollisionShape {
    private static final VoxelShape WEST_HALF = Shapes.box(0.0F, 0.0F, 0.4375F, 0.5F, 1.0F, 0.5625F);
    private static final VoxelShape EAST_HALF = Shapes.box(0.5F, 0.0F, 0.4375F, 1.0F, 1.0F, 0.5625F);
    private static final VoxelShape EAST_WEST = Shapes.box(0.0F, 0.0F, 0.4375F, 1.0F, 1.0F, 0.5625F);
    private static final VoxelShape NORTH_HALF = Shapes.box(0.4375F, 0.0F, 0.0F, 0.5625F, 1.0F, 0.5F);
    private static final VoxelShape SOUTH_HALF = Shapes.box(0.4375F, 0.0F, 0.5F, 0.5625F, 1.0F, 1.0F);
    private static final VoxelShape NORTH_SOUTH = Shapes.box(0.4375F, 0.0F, 0.0F, 0.5625F, 1.0F, 1.0F);

    private final String blockId;

    public PaneCollision(String blockId) {
        this.blockId = blockId;
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
        boolean north = state.getValue(BlockStateProperties.NORTH);
        boolean south = state.getValue(BlockStateProperties.SOUTH);
        boolean west = state.getValue(BlockStateProperties.WEST);
        boolean east = state.getValue(BlockStateProperties.EAST);
        VoxelShape shape = Shapes.empty();
        if ((!west || !east) && (west || east || north || south)) {
            if (west) {
                shape = Shapes.or(shape, WEST_HALF);
            } else if (east) {
                shape = Shapes.or(shape, EAST_HALF);
            }
        } else {
            shape = Shapes.or(shape, EAST_WEST);
        }
        if ((!north || !south) && (west || east || north || south)) {
            if (north) {
                shape = Shapes.or(shape, NORTH_HALF);
            } else if (south) {
                shape = Shapes.or(shape, SOUTH_HALF);
            }
        } else {
            shape = Shapes.or(shape, NORTH_SOUTH);
        }
        return Optional.of(shape);
    }
}

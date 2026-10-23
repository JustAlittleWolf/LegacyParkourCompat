package me.wolfii.legacyparkourcompat.change.v1_10_1;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.piston.MovingPistonCollisionShapeHook;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.piston.PistonBaseBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.Optional;

/**
 * 1.10.2, ornithe-feather build.2: MovingBlock.getCollisionShape dispatches to
 * MovingBlockEntity.getShape(WorldView, BlockPos), which encloses the moved
 * state's outline at current and last progress. PistonBaseBlock.doEvent's
 * type-1 source stores an unextended normal/sticky base; getShape is a full cube.
 * This assembly is shared by the checked 1.9 through 1.10.2 releases.
 *
 * 1.11, ornithe-feather build.2: MovingBlock.addCollisions instead delegates to
 * MovingBlockEntity.addCollisions, adding a fixed extended base and the head's
 * separate collision boxes at current progress. The old getShape union still
 * exists but is no longer used by ordinary collision collection.
 *
 * 26.2, unobfuscated: MovingPistonBlock.getCollisionShape delegates to
 * PistonMovingBlockEntity.getCollisionShape, retaining that separate assembly.
 * Only the accepted retracting normal/sticky source case is replaced here.
 */
@MovementChange(emulates = ParkourVersion.V1_10_1)
public final class RetractingPistonCollisionShape implements MovingPistonCollisionShapeHook {
    @Override
    public Optional<VoxelShape> collisionShape(BlockState movedState, Direction direction,
                                              boolean extending, boolean source,
                                              float progress, float previousProgress) {
        if (extending || !source
            || !(movedState.is(Blocks.PISTON) || movedState.is(Blocks.STICKY_PISTON))
            || movedState.getValue(PistonBaseBlock.EXTENDED)) {
            return Optional.empty();
        }

        // getAmountExtended and each direction multiplication remain float
        // operations before widening to the double arguments of Box.moved.
        float currentOffset = 1.0F - progress;
        float previousOffset = 1.0F - previousProgress;
        AABB cube = new AABB(0.0, 0.0, 0.0, 1.0, 1.0, 1.0);
        AABB current = cube.move(currentOffset * direction.getStepX(),
            currentOffset * direction.getStepY(), currentOffset * direction.getStepZ());
        AABB previous = cube.move(previousOffset * direction.getStepX(),
            previousOffset * direction.getStepY(), previousOffset * direction.getStepZ());
        return Optional.of(Shapes.create(current.minmax(previous)));
    }
}

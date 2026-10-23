package me.wolfii.legacyparkourcompat.change.v1_12;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.block.BlockCollisionShapeHook;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.Optional;

/**
 * Ornithe Feather 1.8.9 BrewingStandBlock.addCollisions (lines 64-74) and the
 * 1.9.4, 1.10.2, 1.11.2, and 1.12.2 COLLISION_SHAPE / SHAPE providers (lines
 * 36-37, emitted in that order by addCollisions at lines 70-73) use a
 * 2/16-by-2/16 post 14/16 high and a full-footprint plate 2/16 high. In
 * Ornithe Feather 1.13 and 1.13.2, BrewingStandBlock.SHAPE (line 33) and
 * getShape (lines 56-57) retain the post but inset the plate by 1/16 on every
 * side. The old components are combined here for the modern player-context
 * shape query. This preserves their occupied geometry; the combined voxel
 * union does not establish identical clipping across internal component
 * planes to the old separately emitted boxes.
 */
@MovementChange(emulates = ParkourVersion.V1_12)
public final class BrewingStandCollisionShape implements BlockCollisionShapeHook {
    private static final VoxelShape SHAPE = Shapes.or(
        Block.box(7.0, 0.0, 7.0, 9.0, 14.0, 9.0),
        Block.box(0.0, 0.0, 0.0, 16.0, 2.0, 16.0)
    );

    @Override
    public String blockId() {
        return "minecraft:brewing_stand";
    }

    @Override
    public Optional<VoxelShape> collisionShape(
        BlockState state, BlockGetter level, BlockPos pos, CollisionContext context
    ) {
        return Optional.of(SHAPE);
    }
}

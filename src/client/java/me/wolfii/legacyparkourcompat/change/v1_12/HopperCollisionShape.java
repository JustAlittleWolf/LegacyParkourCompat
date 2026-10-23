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
 * Restores the 1.12 hopper collision volume for player movement.
 *
 * <p>Ornithe Feather 1.12.2 {@code net.minecraft.block.HopperBlock#addCollisions}
 * appends a 10/16-high bottom slab followed by west, east, north and south
 * 2/16-wide full-height strips. In Ornithe Feather 1.13,
 * {@code net.minecraft.block.HopperBlock#getShape} selects a facing-dependent
 * component union with {@code Hopper.INSIDE} subtracted; 1.13.2 retains that
 * geometry. Its downward outlet is inset from the old slab, while a horizontal
 * outlet lies within that slab, so the difference is the removed lower interior
 * and the replacement of the old full-height strips with a top rim and outlet.
 * The union returned here preserves the old occupied volume but does not claim
 * parity for internal clipping planes created by the old separate boxes.
 * Earlier profile geometry is not established by this comparison, so the
 * separate 1.11.2 native-fallback fence preserves their native result.
 */
@MovementChange(emulates = ParkourVersion.V1_12)
public final class HopperCollisionShape implements BlockCollisionShapeHook {
    private static final VoxelShape OLD_COLLISION_SHAPE = Shapes.or(
        Block.box(0.0, 0.0, 0.0, 16.0, 10.0, 16.0),
        Block.box(0.0, 0.0, 0.0, 2.0, 16.0, 16.0),
        Block.box(14.0, 0.0, 0.0, 16.0, 16.0, 16.0),
        Block.box(0.0, 0.0, 0.0, 16.0, 16.0, 2.0),
        Block.box(0.0, 0.0, 14.0, 16.0, 16.0, 16.0)
    );

    @Override
    public String blockId() {
        return "minecraft:hopper";
    }

    @Override
    public Optional<VoxelShape> collisionShape(
        BlockState state, BlockGetter level, BlockPos pos, CollisionContext context
    ) {
        return Optional.of(OLD_COLLISION_SHAPE);
    }
}

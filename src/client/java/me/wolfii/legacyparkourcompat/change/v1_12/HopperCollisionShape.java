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
 * <p>The source-blind Ornithe Feather comparison finds the same ordered
 * 10/16-high bottom slab and west, east, north and south 2/16-wide full-height
 * strips in 1.8.9, 1.9.4, 1.10.2, 1.11.2 and 1.12.2
 * {@code net.minecraft.block.HopperBlock#addCollisions}. The 1.8.9
 * {@code HopperBlock#addCollisions} mutates its bounds for those five boxes and
 * restores the full cube after the additions. The inspected
 * {@code Entity#move} collision consumer reaches these boxes in each release.
 * In Ornithe Feather 1.13,
 * {@code net.minecraft.block.HopperBlock#getShape} selects a facing-dependent
 * component union with {@code Hopper.INSIDE} subtracted; 1.13.2 retains that
 * geometry. Its downward outlet is inset from the old slab, while a horizontal
 * outlet lies within that slab. The newer union removes lower interior volume
 * and replaces the old full-height strips, but also adds a full-footprint solid
 * band from 10/16 to 11/16 because the inside subtraction starts at 11/16.
 * Its funnel from 4/16 to 10/16 remains solid.
 * The union returned here preserves the old occupied volume but does not claim
 * parity for internal clipping planes created by the old separate boxes. The
 * 1.8.0 through 1.8.8 geometry remains uninspected and uses the separate 1.8
 * native-fallback fence.
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

package me.wolfii.legacyparkourcompat.change.v1_13;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.change.BlockChanges;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.MovementChangeRegistry;
import me.wolfii.legacyparkourcompat.mechanic.hook.BlockCollisionShape;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.BedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.Optional;

/** Through 1.13, beds had a solid 9/16-high footprint rather than legs. */
@MovementChange(emulates = ParkourVersion.V1_13)
public final class SolidBedCollision implements BlockCollisionShape {
    private static final VoxelShape SHAPE = Shapes.box(0.0, 0.0, 0.0, 1.0, 0.5625, 1.0);
    private final String blockId;

    public SolidBedCollision(String blockId) {
        this.blockId = blockId;
    }

    public static void register(MovementChangeRegistry registry) {
        BlockChanges.registerEach(registry, block -> block instanceof BedBlock, SolidBedCollision::new);
    }

    @Override
    public String blockId() {
        return this.blockId;
    }

    @Override
    public Optional<VoxelShape> collisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return Optional.of(SHAPE);
    }
}

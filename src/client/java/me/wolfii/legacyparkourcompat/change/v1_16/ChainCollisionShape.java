package me.wolfii.legacyparkourcompat.change.v1_16;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.block.BlockCollisionShapeHook;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.Optional;

/**
 * Restores the vertical chain column for the 1.16 and 1.16.1 profiles.
 * Mojmap ChainBlock#SHAPE and #getShape use a centered 3/16 by full-height by 3/16 box
 * in 1.16.0/.1; ChainBlock adds AXIS-dependent horizontal and vertical shapes in 1.16.2.
 */
@MovementChange(emulates = ParkourVersion.V1_16)
public final class ChainCollisionShape implements BlockCollisionShapeHook {
    private static final VoxelShape VERTICAL_COLUMN = Shapes.box(
        6.5 / 16.0, 0.0, 6.5 / 16.0,
        9.5 / 16.0, 1.0, 9.5 / 16.0
    );

    public ChainCollisionShape() {
    }

    @Override
    public String blockId() {
        return "minecraft:chain";
    }

    @Override
    public boolean supports(ParkourVersion selected) {
        return selected == ParkourVersion.V1_16;
    }

    @Override
    public Optional<VoxelShape> collisionShape(
        BlockState state, BlockGetter level, BlockPos pos, CollisionContext context
    ) {
        return Optional.of(VERTICAL_COLUMN);
    }
}

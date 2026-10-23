package me.wolfii.legacyparkourcompat.mechanic.hook.block;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

/** Historical wall collision height, resolved independently from horizontal wall geometry. */
@MechanicType("block.wall.collision-height")
public interface WallCollisionHeightHook extends VersionedMechanic {
    String blockId();

    @Override
    default String variant() {
        return this.blockId();
    }

    double maxY(BlockState state);

    default VoxelShape applyHeight(VoxelShape shape, BlockState state) {
        return Shapes.join(shape, Shapes.box(0.0, 0.0, 0.0, 1.0, this.maxY(state), 1.0), BooleanOp.AND);
    }
}

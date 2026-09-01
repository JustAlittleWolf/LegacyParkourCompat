package me.wolfii.legacyparkourcompat.change.v1_10_1;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.BlockCollisionShape;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.Optional;

/**
 * Farmland was a full cube through 1.10. 1.10.1 lowered it to 15 pixels
 * ({@code 0.9375}).
 */
@MovementChange(emulates = ParkourVersion.V1_10)
public final class FullFarmland implements BlockCollisionShape {
    private static final VoxelShape SHAPE = Shapes.block();

    @Override
    public String blockId() {
        return "minecraft:farmland";
    }

    @Override
    public Optional<VoxelShape> collisionShape(
        BlockState state,
        BlockGetter level,
        BlockPos pos,
        CollisionContext context
    ) {
        return Optional.of(SHAPE);
    }
}

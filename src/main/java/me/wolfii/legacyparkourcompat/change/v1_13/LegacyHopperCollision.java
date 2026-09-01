package me.wolfii.legacyparkourcompat.change.v1_13;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.MovementChangeRegistry;
import me.wolfii.legacyparkourcompat.mechanic.hook.BlockCollisionShape;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.Optional;

/**
 * Pre-1.13 hoppers: 0.125 walls and a 0.625 inner floor, no modelled spout.
 * 1.13 raised the floor to 0.6875 and added the underside.
 */
@MovementChange(emulates = ParkourVersion.V1_12)
public final class LegacyHopperCollision implements BlockCollisionShape {
    private static final VoxelShape SHAPE = Shapes.or(
        Shapes.box(0.0, 0.0, 0.0, 1.0, 0.625, 1.0),
        Shapes.box(0.0, 0.0, 0.0, 0.125, 1.0, 1.0),
        Shapes.box(0.875, 0.0, 0.0, 1.0, 1.0, 1.0),
        Shapes.box(0.0, 0.0, 0.0, 1.0, 1.0, 0.125),
        Shapes.box(0.0, 0.0, 0.875, 1.0, 1.0, 1.0)
    );

    @Override
    public String blockId() {
        return "minecraft:hopper";
    }

    public static void register(MovementChangeRegistry registry) {
        registry.register(new LegacyHopperCollision());
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

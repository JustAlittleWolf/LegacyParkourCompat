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
 * Pre-1.13 brewing stand: full 1×1 base 0.125 tall plus a 0.125-wide rod.
 * 1.13 shrunk the base to 0.875×0.875.
 */
@MovementChange(emulates = ParkourVersion.V1_12)
public final class LegacyBrewingStandCollision implements BlockCollisionShape {
    private static final VoxelShape SHAPE = Shapes.or(
        Shapes.box(0.0, 0.0, 0.0, 1.0, 0.125, 1.0),
        Shapes.box(0.4375, 0.0, 0.4375, 0.5625, 0.875, 0.5625)
    );

    @Override
    public String blockId() {
        return "minecraft:brewing_stand";
    }

    public static void register(MovementChangeRegistry registry) {
        registry.register(new LegacyBrewingStandCollision());
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

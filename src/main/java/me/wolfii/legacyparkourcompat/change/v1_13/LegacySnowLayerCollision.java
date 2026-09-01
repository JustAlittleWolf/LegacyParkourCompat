package me.wolfii.legacyparkourcompat.change.v1_13;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.MovementChangeRegistry;
import me.wolfii.legacyparkourcompat.mechanic.hook.BlockCollisionShape;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.SnowLayerBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.Optional;

/**
 * 1.12.2 {@code SnowLayerBlock#LAYERS_TO_SHAPE}: collision height is
 * {@code layers * 0.125}, so one layer is 0.125 tall. 1.13 made a single layer
 * use the 0-height slot (intangible).
 */
@MovementChange(emulates = ParkourVersion.V1_12)
public final class LegacySnowLayerCollision implements BlockCollisionShape {
    private static final VoxelShape[] SHAPES = {
        Shapes.empty(),
        Shapes.box(0.0, 0.0, 0.0, 1.0, 0.125, 1.0),
        Shapes.box(0.0, 0.0, 0.0, 1.0, 0.25, 1.0),
        Shapes.box(0.0, 0.0, 0.0, 1.0, 0.375, 1.0),
        Shapes.box(0.0, 0.0, 0.0, 1.0, 0.5, 1.0),
        Shapes.box(0.0, 0.0, 0.0, 1.0, 0.625, 1.0),
        Shapes.box(0.0, 0.0, 0.0, 1.0, 0.75, 1.0),
        Shapes.box(0.0, 0.0, 0.0, 1.0, 0.875, 1.0),
        Shapes.block()
    };

    @Override
    public String blockId() {
        return "minecraft:snow";
    }

    public static void register(MovementChangeRegistry registry) {
        registry.register(new LegacySnowLayerCollision());
    }

    @Override
    public Optional<VoxelShape> collisionShape(
        BlockState state,
        BlockGetter level,
        BlockPos pos,
        CollisionContext context
    ) {
        return Optional.of(SHAPES[state.getValue(SnowLayerBlock.LAYERS)]);
    }
}

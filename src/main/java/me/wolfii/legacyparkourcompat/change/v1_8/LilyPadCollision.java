package me.wolfii.legacyparkourcompat.change.v1_8;

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
 * 1.8 lily pads are a full-width 0.015625-tall slab. 1.9 inset them by 1 pixel
 * and raised them to 0.09375.
 */
@MovementChange(emulates = ParkourVersion.V1_8)
public final class LilyPadCollision implements BlockCollisionShape {
    private static final VoxelShape SHAPE = Shapes.box(0.0F, 0.0F, 0.0F, 1.0F, 0.015625F, 1.0F);

    @Override
    public String blockId() {
        return "minecraft:lily_pad";
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

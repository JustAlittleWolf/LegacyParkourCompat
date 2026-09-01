package me.wolfii.legacyparkourcompat.change.v1_8;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.BlockCollisionShape;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.piston.PistonHeadBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.Optional;

/**
 * 1.8 piston-head arms are the wrong width, do not protrude into the next
 * block, and the west-facing arm uses a bugged box. 1.9 matched the model.
 */
@MovementChange(emulates = ParkourVersion.V1_8)
public final class PistonHeadCollision implements BlockCollisionShape {
    @Override
    public String blockId() {
        return "minecraft:piston_head";
    }

    @Override
    public Optional<VoxelShape> collisionShape(
        BlockState state,
        BlockGetter level,
        BlockPos pos,
        CollisionContext context
    ) {
        Direction facing = state.getValue(PistonHeadBlock.FACING);
        return Optional.of(Shapes.or(head(facing), arm(facing)));
    }

    private static VoxelShape head(Direction facing) {
        return switch (facing) {
            case DOWN -> Shapes.box(0.0F, 0.0F, 0.0F, 1.0F, 0.25F, 1.0F);
            case UP -> Shapes.box(0.0F, 0.75F, 0.0F, 1.0F, 1.0F, 1.0F);
            case NORTH -> Shapes.box(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 0.25F);
            case SOUTH -> Shapes.box(0.0F, 0.0F, 0.75F, 1.0F, 1.0F, 1.0F);
            case WEST -> Shapes.box(0.0F, 0.0F, 0.0F, 0.25F, 1.0F, 1.0F);
            case EAST -> Shapes.box(0.75F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
        };
    }

    private static VoxelShape arm(Direction facing) {
        return switch (facing) {
            case DOWN -> Shapes.box(0.375F, 0.25F, 0.375F, 0.625F, 1.0F, 0.625F);
            case UP -> Shapes.box(0.375F, 0.0F, 0.375F, 0.625F, 0.75F, 0.625F);
            case NORTH -> Shapes.box(0.25F, 0.375F, 0.25F, 0.75F, 0.625F, 1.0F);
            case SOUTH -> Shapes.box(0.25F, 0.375F, 0.0F, 0.75F, 0.625F, 0.75F);
            case WEST -> Shapes.box(0.375F, 0.25F, 0.25F, 0.625F, 0.75F, 1.0F);
            case EAST -> Shapes.box(0.0F, 0.375F, 0.25F, 0.75F, 0.625F, 0.75F);
        };
    }
}

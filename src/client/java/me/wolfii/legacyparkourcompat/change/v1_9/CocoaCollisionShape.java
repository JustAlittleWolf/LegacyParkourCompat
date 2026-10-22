package me.wolfii.legacyparkourcompat.change.v1_9;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.block.BlockCollisionShapeHook;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.CocoaBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.Optional;

/**
 * In 1.9 Ornithe Feather, {@code net.minecraft.block.CocoaBlock}'s directional
 * shape tables and {@code getShape} repeat the age-1 box at age 2; 1.9.4 keeps
 * the same tables. In 1.8.9, {@code CocoaBlock.updateShape} instead derives a
 * larger box from age. In 26.2 unobfuscated, the age-indexed shape table again
 * makes age 2 larger. Restore the 1.9 age-1 box only for mature cocoa in the
 * 1.9 profile group; later profiles retain their native geometry.
 */
@MovementChange(emulates = ParkourVersion.V1_9)
public final class CocoaCollisionShape implements BlockCollisionShapeHook {
    public CocoaCollisionShape() {
    }

    @Override
    public String blockId() {
        return "minecraft:cocoa";
    }

    @Override
    public Optional<VoxelShape> collisionShape(
        BlockState state, BlockGetter level, BlockPos pos, CollisionContext context
    ) {
        if (state.getValue(CocoaBlock.AGE) != 2) {
            return Optional.empty();
        }

        Direction facing = state.getValue(CocoaBlock.FACING);
        VoxelShape ageOneShape = switch (facing) {
            case NORTH -> Shapes.box(0.3125, 0.3125, 0.0625, 0.6875, 0.75, 0.4375);
            case SOUTH -> Shapes.box(0.3125, 0.3125, 0.5625, 0.6875, 0.75, 0.9375);
            case WEST -> Shapes.box(0.0625, 0.3125, 0.3125, 0.4375, 0.75, 0.6875);
            case EAST -> Shapes.box(0.5625, 0.3125, 0.3125, 0.9375, 0.75, 0.6875);
            default -> throw new IllegalStateException("Cocoa facing is not horizontal: " + facing);
        };
        return Optional.of(ageOneShape);
    }
}

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
 * In 1.9 and 1.9.4 Ornithe Feather, {@code net.minecraft.block.CocoaBlock}'s
 * directional shape tables and {@code getShape} repeat the age-1 box at age 2.
 * In 1.10, 1.10.1 and 1.10.2 Feather Gen2 build 2, the identical
 * {@code CocoaBlock.EAST_SHAPE/WEST_SHAPE/NORTH_SHAPE/SOUTH_SHAPE} tables and
 * {@code getShape} retain that regression; {@code Block.getCollisionShape}
 * delegates to the state's shape. In 1.11, 1.11.1 and 1.11.2 with the same
 * mappings and members, age 2 instead uses the larger, lower box.
 *
 * <p>In 1.8.9 Ornithe Feather, {@code CocoaBlock.updateShape} derives the larger
 * box from age; the separate 1.8 change preserves that earlier geometry.
 * In 26.2 unobfuscated, {@code net.minecraft.world.level.block.CocoaBlock.SHAPES}
 * and {@code getShape} retain the larger box. Restore only mature cocoa's
 * collision box from 1.9 through 1.10.2; 1.11 and later retain native geometry.
 */
@MovementChange(emulates = ParkourVersion.V1_10_1)
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

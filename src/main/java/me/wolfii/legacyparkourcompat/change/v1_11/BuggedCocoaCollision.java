package me.wolfii.legacyparkourcompat.change.v1_11;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.BlockCollisionShape;
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
 * 1.9–1.10 cocoa (MC-94274): the age-2 AABB is a copy of age 1. 1.8 keeps
 * {@link me.wolfii.legacyparkourcompat.change.v1_8.CocoaCollision}; 1.11
 * restored the larger box. Boxes are 1.9.4 {@code CocoaBlock} {@code EAST},
 * {@code WEST}, {@code NORTH}, {@code SOUTH} arrays.
 */
@MovementChange(emulates = ParkourVersion.V1_10)
public final class BuggedCocoaCollision implements BlockCollisionShape {
    private static final VoxelShape[] EAST = {
        Shapes.box(0.6875, 0.4375, 0.375, 0.9375, 0.75, 0.625),
        Shapes.box(0.5625, 0.3125, 0.3125, 0.9375, 0.75, 0.6875),
        Shapes.box(0.5625, 0.3125, 0.3125, 0.9375, 0.75, 0.6875)
    };
    private static final VoxelShape[] WEST = {
        Shapes.box(0.0625, 0.4375, 0.375, 0.3125, 0.75, 0.625),
        Shapes.box(0.0625, 0.3125, 0.3125, 0.4375, 0.75, 0.6875),
        Shapes.box(0.0625, 0.3125, 0.3125, 0.4375, 0.75, 0.6875)
    };
    private static final VoxelShape[] NORTH = {
        Shapes.box(0.375, 0.4375, 0.0625, 0.625, 0.75, 0.3125),
        Shapes.box(0.3125, 0.3125, 0.0625, 0.6875, 0.75, 0.4375),
        Shapes.box(0.3125, 0.3125, 0.0625, 0.6875, 0.75, 0.4375)
    };
    private static final VoxelShape[] SOUTH = {
        Shapes.box(0.375, 0.4375, 0.6875, 0.625, 0.75, 0.9375),
        Shapes.box(0.3125, 0.3125, 0.5625, 0.6875, 0.75, 0.9375),
        Shapes.box(0.3125, 0.3125, 0.5625, 0.6875, 0.75, 0.9375)
    };

    @Override
    public String blockId() {
        return "minecraft:cocoa";
    }

    @Override
    public Optional<VoxelShape> collisionShape(
        BlockState state,
        BlockGetter level,
        BlockPos pos,
        CollisionContext context
    ) {
        int age = state.getValue(CocoaBlock.AGE);
        Direction facing = state.getValue(CocoaBlock.FACING);
        VoxelShape[] boxes = switch (facing) {
            case SOUTH -> SOUTH;
            case WEST -> WEST;
            case EAST -> EAST;
            default -> NORTH;
        };
        return Optional.of(boxes[age]);
    }
}

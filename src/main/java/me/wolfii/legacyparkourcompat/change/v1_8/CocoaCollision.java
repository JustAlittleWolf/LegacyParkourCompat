package me.wolfii.legacyparkourcompat.change.v1_8;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.MovementChangeRegistry;
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
 * 1.8 cocoa collision from {@code CocoaBlock#setBoundingBox}:
 * {@code j = 4 + age * 2}, {@code k = 5 + age * 2}. 1.9 copied the age-1 box
 * into the age-2 slot (MC-94274); 1.11 restored this formula.
 */
@MovementChange(emulates = ParkourVersion.V1_8)
public final class CocoaCollision implements BlockCollisionShape {
    @Override
    public String blockId() {
        return "minecraft:cocoa";
    }

    public static void register(MovementChangeRegistry registry) {
        registry.register(new CocoaCollision());
    }

    @Override
    public Optional<VoxelShape> collisionShape(
        BlockState state,
        BlockGetter level,
        BlockPos pos,
        CollisionContext context
    ) {
        return Optional.of(shape(state.getValue(CocoaBlock.AGE), state.getValue(CocoaBlock.FACING)));
    }

    private static VoxelShape shape(int age, Direction facing) {
        int j = 4 + age * 2;
        int k = 5 + age * 2;
        float f = j / 2.0F;
        return switch (facing) {
            case SOUTH -> Shapes.box(
                (8.0F - f) / 16.0F,
                (12.0F - k) / 16.0F,
                (15.0F - j) / 16.0F,
                (8.0F + f) / 16.0F,
                0.75F,
                0.9375F
            );
            case NORTH -> Shapes.box(
                (8.0F - f) / 16.0F,
                (12.0F - k) / 16.0F,
                0.0625F,
                (8.0F + f) / 16.0F,
                0.75F,
                (1.0F + j) / 16.0F
            );
            case WEST -> Shapes.box(
                0.0625F,
                (12.0F - k) / 16.0F,
                (8.0F - f) / 16.0F,
                (1.0F + j) / 16.0F,
                0.75F,
                (8.0F + f) / 16.0F
            );
            default -> Shapes.box(
                (15.0F - j) / 16.0F,
                (12.0F - k) / 16.0F,
                (8.0F - f) / 16.0F,
                0.9375F,
                0.75F,
                (8.0F + f) / 16.0F
            );
        };
    }
}

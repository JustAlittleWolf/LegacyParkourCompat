package me.wolfii.legacyparkourcompat.change.v1_8;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.block.BlockCollisionShapeHook;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.CocoaBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.Optional;

/**
 * In 1.8.9 Ornithe Feather, {@code net.minecraft.block.CocoaBlock.updateShape}
 * derives {@code j = 4 + age * 2} and {@code k = 5 + age * 2};
 * {@code getCollisionShape} uses that shape. At age 2 its four oriented boxes
 * match 26.2 unobfuscated {@code net.minecraft.world.level.block.CocoaBlock.getShape}
 * and its age-indexed shape table. This entry keeps the 1.8 profile on that
 * pre-1.9 geometry when the later age-2 regression is selected for 1.9.
 */
@MovementChange(emulates = ParkourVersion.V1_8)
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

        VoxelShape ageTwoShape = switch (state.getValue(CocoaBlock.FACING)) {
            case NORTH -> Shapes.box(0.25, 0.1875, 0.0625, 0.75, 0.75, 0.5625);
            case SOUTH -> Shapes.box(0.25, 0.1875, 0.4375, 0.75, 0.75, 0.9375);
            case WEST -> Shapes.box(0.0625, 0.1875, 0.25, 0.5625, 0.75, 0.75);
            case EAST -> Shapes.box(0.4375, 0.1875, 0.25, 0.9375, 0.75, 0.75);
            default -> throw new IllegalStateException("Cocoa facing is not horizontal: " + state.getValue(CocoaBlock.FACING));
        };
        return Optional.of(ageTwoShape);
    }
}

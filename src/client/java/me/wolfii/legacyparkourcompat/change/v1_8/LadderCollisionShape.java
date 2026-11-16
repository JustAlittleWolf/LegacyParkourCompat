package me.wolfii.legacyparkourcompat.change.v1_8;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.block.BlockCollisionShapeHook;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LadderBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.List;
import java.util.Optional;

/**
 * Ornithe Feather 1.8 LadderBlock#getCollisionShape calls updateShape, whose
 * facing boxes are 0.125F thick. In Ornithe Feather 1.9,
 * LadderBlock#getShape returns 0.1875-thick facing boxes and
 * Block#getCollisionShape returns that selected state shape.
 */
@MovementChange(emulates = ParkourVersion.V1_8)
public final class LadderCollisionShape implements BlockCollisionShapeHook {
    private static final String LADDER = "minecraft:ladder";
    private static final double THICKNESS = 0.125D;

    private LadderCollisionShape() {
    }

    @Override
    public String blockId() {
        return LADDER;
    }

    @Override
    public Optional<VoxelShape> collisionShape(BlockState state, BlockGetter level, BlockPos pos,
                                               CollisionContext context) {
        BlockState worldState = level.getBlockState(pos);
        if (worldState.getBlock() != Blocks.LADDER) {
            return Optional.empty();
        }
        return Optional.of(shape(worldState.getValue(LadderBlock.FACING)));
    }

    private static VoxelShape shape(Direction facing) {
        return switch (facing) {
            case NORTH -> Shapes.box(0.0D, 0.0D, 1.0D - THICKNESS, 1.0D, 1.0D, 1.0D);
            case SOUTH -> Shapes.box(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, THICKNESS);
            case WEST -> Shapes.box(1.0D - THICKNESS, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
            case EAST -> Shapes.box(0.0D, 0.0D, 0.0D, THICKNESS, 1.0D, 1.0D);
            default -> throw new IllegalStateException("Unexpected ladder facing: " + facing);
        };
    }

    @MovementChange.Factory
    public static List<LadderCollisionShape> variants() {
        return List.of(new LadderCollisionShape());
    }
}

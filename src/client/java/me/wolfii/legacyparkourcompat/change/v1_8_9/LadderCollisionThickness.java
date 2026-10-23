package me.wolfii.legacyparkourcompat.change.v1_8_9;

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
 * 1.8.9 (Ornithe Feather) LadderBlock#getCollisionShape and #updateShape use
 * a 0.125F facing thickness; 1.9 through 1.9.4 (Ornithe Feather)
 * LadderBlock#getShape use 0.1875. Native 26.2 (unobfuscated)
 * LadderBlock#getShape retains 0.1875. The existing keyed
 * BlockCollisionShapeHook dispatch keeps this player-context-only.
 */
@MovementChange(emulates = ParkourVersion.V1_8_9)
public final class LadderCollisionThickness implements BlockCollisionShapeHook {
    private static final String LADDER = "minecraft:ladder";

    private LadderCollisionThickness() {
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
        double thickness = 0.125;
        return switch (facing) {
            case NORTH -> Shapes.box(0.0, 0.0, 1.0 - thickness, 1.0, 1.0, 1.0);
            case SOUTH -> Shapes.box(0.0, 0.0, 0.0, 1.0, 1.0, thickness);
            case WEST -> Shapes.box(1.0 - thickness, 0.0, 0.0, 1.0, 1.0, 1.0);
            case EAST -> Shapes.box(0.0, 0.0, 0.0, thickness, 1.0, 1.0);
            default -> throw new IllegalStateException("Unexpected ladder facing: " + facing);
        };
    }

    @MovementChange.Factory
    public static List<LadderCollisionThickness> variants() {
        return List.of(new LadderCollisionThickness());
    }
}

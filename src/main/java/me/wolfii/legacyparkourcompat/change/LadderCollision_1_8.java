package me.wolfii.legacyparkourcompat.change;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.BlockCollisionShape;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LadderBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.Optional;

/**
 * 1.8 ladder collision is 2 pixels ({@code 0.125}); 1.9 widened it to 3 pixels.
 */
@MovementChange(emulates = ParkourVersion.V1_8)
public final class LadderCollision_1_8 implements BlockCollisionShape {
    private static final VoxelShape EAST = Block.box(0.0, 0.0, 0.0, 2.0, 16.0, 16.0);
    private static final VoxelShape WEST = Block.box(14.0, 0.0, 0.0, 16.0, 16.0, 16.0);
    private static final VoxelShape SOUTH = Block.box(0.0, 0.0, 0.0, 16.0, 16.0, 2.0);
    private static final VoxelShape NORTH = Block.box(0.0, 0.0, 14.0, 16.0, 16.0, 16.0);

    @Override
    public String blockId() {
        return "minecraft:ladder";
    }

    @Override
    public Optional<VoxelShape> collisionShape(
            BlockState state,
            BlockGetter level,
            BlockPos pos,
            CollisionContext context
    ) {
        return Optional.of(switch (state.getValue(LadderBlock.FACING)) {
            case NORTH -> NORTH;
            case SOUTH -> SOUTH;
            case WEST -> WEST;
            default -> EAST;
        });
    }
}

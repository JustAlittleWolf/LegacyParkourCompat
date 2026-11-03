package me.wolfii.legacyparkourcompat.change.v1_8_9;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.block.BlockCollisionShapeHook;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.Optional;

/**
 * Ornithe Feather 1.8.9 {@code net/minecraft/block/LilyPadBlock#getCollisionShape}
 * uses block bounds [0, 0, 0]–[1, 1/64, 1]. Ornithe Feather 1.9.4
 * {@code net/minecraft/block/LilyPadBlock#getShape} returns SHAPE with bounds
 * [1/16, 0, 1/16]–[15/16, 3/32, 15/16]. This restores only player collision;
 * the block's outline shape and boat collision behavior remain vanilla.
 */
@MovementChange(emulates = ParkourVersion.V1_8_9)
public final class LilyPadCollisionShape implements BlockCollisionShapeHook {
    private static final VoxelShape SHAPE = Shapes.box(0.0, 0.0, 0.0, 1.0, 1.0 / 64.0, 1.0);

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

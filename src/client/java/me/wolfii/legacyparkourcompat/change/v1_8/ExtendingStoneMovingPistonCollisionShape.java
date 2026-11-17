package me.wolfii.legacyparkourcompat.change.v1_8;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.CollisionAxisOrderContext;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.piston.MovingPistonCollisionShapeHook;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.Optional;

/**
 * 1.8 through 1.8.9, Ornithe Feather Gen2 build 2:
 * MovingBlock.getCollisionShape trims the destination-owned Stone box using
 * MovingBlockEntity.getProgress(0.0F). In 1.9, MovingBlock.getCollisionShape
 * delegates to MovingBlockEntity.getShape, which unions translated boxes.
 * In 26.2 (unobfuscated), PistonMovingBlockEntity.tick copies progress to
 * progressO before advancing by 0.5F, and getCollisionShape translates the
 * full Stone shape using current progress.
 */
@MovementChange(emulates = ParkourVersion.V1_8_9)
public final class ExtendingStoneMovingPistonCollisionShape implements MovingPistonCollisionShapeHook {
    @Override
    public Optional<VoxelShape> collisionShape(BlockState movedState, Direction direction,
                                               boolean extending, boolean source,
                                               float progress, float previousProgress) {
        if (!extending || source || !movedState.is(Blocks.STONE)
            || progress != 1.0F || previousProgress != 0.5F) {
            // The zero-thickness old plane and terminal cube keep the native route.
            return Optional.empty();
        }
        var movement = CollisionAxisOrderContext.movementQuery();
        if (movement == null || movement.moverType() != MoverType.SELF || movement.entity().isPassenger()) {
            return Optional.empty();
        }

        float trim = 1.0F - previousProgress;
        double minX = 0.0;
        double minY = 0.0;
        double minZ = 0.0;
        double maxX = 1.0;
        double maxY = 1.0;
        double maxZ = 1.0;
        switch (direction) {
            case EAST -> maxX -= trim;
            case WEST -> minX += trim;
            case UP -> maxY -= trim;
            case DOWN -> minY += trim;
            case SOUTH -> maxZ -= trim;
            case NORTH -> minZ += trim;
        }
        return Optional.of(Shapes.box(minX, minY, minZ, maxX, maxY, maxZ));
    }
}

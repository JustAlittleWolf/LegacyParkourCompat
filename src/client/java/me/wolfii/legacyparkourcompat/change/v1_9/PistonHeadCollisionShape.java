package me.wolfii.legacyparkourcompat.change.v1_9;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.PistonHeadPlate;
import me.wolfii.legacyparkourcompat.mechanic.hook.block.BlockCollisionShapeHook;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.level.block.piston.PistonHeadBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.List;
import java.util.Optional;

/**
 * 1.8.9, ornithe-feather: net.minecraft.block.PistonHeadBlock.addCollisions and
 * setArmShapeForCollisions, plus net.minecraft.block.Block.addCollisions. 1.9, ornithe-feather:
 * PistonHeadBlock.addCollisions and getArmShape, plus Block.addCollision.
 * 1.9.1 through 1.9.4/ornithe-feather retain these head and arm bounds.
 */
@MovementChange(emulates = ParkourVersion.V1_9)
public final class PistonHeadCollisionShape implements BlockCollisionShapeHook {
    @Override
    public String blockId() {
        return "minecraft:piston_head";
    }

    @Override
    public Optional<List<VoxelShape>> collisionShapes(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        Direction facing = state.getValue(PistonHeadBlock.FACING);
        AABB head = PistonHeadPlate.head(facing);
        AABB arm = arm(facing);
        return Optional.of(List.of(Shapes.create(head), Shapes.create(arm)));
    }

    @Override
    public Optional<VoxelShape> collisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        Direction facing = state.getValue(PistonHeadBlock.FACING);
        return Optional.of(Shapes.or(Shapes.create(PistonHeadPlate.head(facing)), Shapes.create(arm(facing))));
    }

    private static AABB arm(Direction facing) {
        // 1.9 PistonHeadBlock.getArmShape(...) is independent of SHORT and includes
        // protrusion in the direction opposite-facing named arms, including EAST X -0.25.
        return switch (facing) {
            case DOWN -> box(0.375, 0.25, 0.375, 0.625, 1.25, 0.625);
            case UP -> box(0.375, -0.25, 0.375, 0.625, 0.75, 0.625);
            case NORTH -> box(0.375, 0.375, 0.25, 0.625, 0.625, 1.25);
            case SOUTH -> box(0.375, 0.375, -0.25, 0.625, 0.625, 0.75);
            case WEST -> box(0.25, 0.375, 0.375, 1.25, 0.625, 0.625);
            case EAST -> box(-0.25, 0.375, 0.375, 0.75, 0.625, 0.625);
        };
    }

    private static AABB box(double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
        return new AABB(minX, minY, minZ, maxX, maxY, maxZ);
    }
}

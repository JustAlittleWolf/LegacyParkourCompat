package me.wolfii.legacyparkourcompat.change.v1_8;

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
 */
@MovementChange(emulates = ParkourVersion.V1_8_9)
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
        // 1.8.9 Block.java addCollisions(...) uses the mutable block bounds as written here.
        // In particular the EAST arm starts at local X 0.0, unlike the 1.9 protruding arm.
        return switch (facing) {
            case DOWN -> box(0.375, 0.25, 0.375, 0.625, 1.0, 0.625);
            case UP -> box(0.375, 0.0, 0.375, 0.625, 0.75, 0.625);
            case NORTH -> box(0.25, 0.375, 0.25, 0.75, 0.625, 1.0);
            case SOUTH -> box(0.25, 0.375, 0.0, 0.75, 0.625, 0.75);
            case WEST -> box(0.375, 0.25, 0.25, 0.625, 0.75, 1.0);
            case EAST -> box(0.0, 0.375, 0.25, 0.75, 0.625, 0.75);
        };
    }

    private static AABB box(double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
        return new AABB(minX, minY, minZ, maxX, maxY, maxZ);
    }
}

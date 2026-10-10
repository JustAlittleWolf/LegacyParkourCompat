package me.wolfii.legacyparkourcompat.change.v1_15_2;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.BlockCollisionShape;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.piston.PistonBaseBlock;
import net.minecraft.world.level.block.piston.PistonHeadBlock;
import net.minecraft.world.level.block.piston.PistonMovingBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.Optional;

/** Reproduces the short-head collision shape for a retracting source piston. */
@MovementChange(emulates = ParkourVersion.V1_15_2)
public final class RetractingPistonHeadCollisionShape implements BlockCollisionShape {
    @Override
    public String blockId() {
        return "minecraft:moving_piston";
    }

    @Override
    public Optional<VoxelShape> collisionShape(
        BlockState state,
        BlockGetter level,
        BlockPos pos,
        CollisionContext context
    ) {
        return Optional.empty();
    }

    @Override
    public Optional<VoxelShape> collisionShape(
        BlockState state,
        BlockGetter level,
        BlockPos pos,
        CollisionContext context,
        VoxelShape vanillaShape
    ) {
        if (!(level.getBlockEntity(pos) instanceof PistonMovingBlockEntity piston)
            || piston.isExtending()
            || !piston.isSourcePiston()
            || !(piston.getMovedState().getBlock() instanceof PistonBaseBlock)) {
            return Optional.empty();
        }

        BlockState extendedBaseState = piston.getMovedState().setValue(PistonBaseBlock.EXTENDED, true);
        VoxelShape baseShape = extendedBaseState.getCollisionShape(level, pos);

        // The native moving-piston query returns this exact base shape from its
        // matching-NOCLIP branch, which the historical head change must bypass.
        if (vanillaShape == baseShape) {
            return Optional.empty();
        }

        float progress = piston.getProgress(1.0F);
        BlockState headState = Blocks.PISTON_HEAD.defaultBlockState()
            .setValue(PistonHeadBlock.FACING, piston.getDirection())
            .setValue(PistonHeadBlock.SHORT, piston.isExtending() != 1.0F - progress < 4.0F);

        Direction direction = piston.getDirection();
        float extendedProgress = 1.0F - progress;
        double dx = direction.getStepX() * extendedProgress;
        double dy = direction.getStepY() * extendedProgress;
        double dz = direction.getStepZ() * extendedProgress;
        VoxelShape headShape = headState.getCollisionShape(level, pos).move(dx, dy, dz);
        return Optional.of(Shapes.or(baseShape, headShape));
    }
}

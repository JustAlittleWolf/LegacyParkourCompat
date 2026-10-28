package me.wolfii.legacyparkourcompat.change.v1_15_2;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.MovementRuntime;
import me.wolfii.legacyparkourcompat.mechanic.hook.movingpiston.ShortArmThresholdHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.block.BlockCollisionShapeHook;
import me.wolfii.legacyparkourcompat.mixin.accessor.PistonMovingBlockEntityAccessor;
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

/**
 * 1.15.2/mojmap net/minecraft/world/level/block/piston/PistonMovingBlockEntity.java,
 * getCollisionShape: retracting source-head SHORT threshold 4.0F.
 * 1.16/mojmap same file/member: threshold becomes 0.25F.
 * Retains the common raw-progress/NOCLIP gate and float offset operation order.
 *
 * Source evidence (paths are relative to decompiled_minecraft/):
 * 1.15.2/mojmap/net/minecraft/world/level/block/piston/PistonMovingBlockEntity.java:
 *   getCollisionShape.
 * 1.16.5/mojmap/net/minecraft/world/level/block/piston/PistonMovingBlockEntity.java:
 *   getCollisionShape.
 * Source-content correspondence and compilation do not establish tick-level runtime parity.
 */
@MovementChange(emulates = ParkourVersion.V1_15_2)
public final class RetractingPistonHeadCollisionShape implements BlockCollisionShapeHook {
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
        if (!(level.getBlockEntity(pos) instanceof PistonMovingBlockEntity piston)
            || piston.isExtending()
            || !piston.isSourcePiston()
            || !(piston.getMovedState().getBlock() instanceof PistonBaseBlock)) {
            return Optional.empty();
        }

        float progress = ((PistonMovingBlockEntityAccessor) piston).legacyparkourcompat$progress();
        if (progress < 1.0
            && PistonMovingBlockEntityAccessor.legacyparkourcompat$noClip().get() == piston.getMovementDirection()) {
            return Optional.empty();
        }

        BlockState extendedBaseState = piston.getMovedState().setValue(PistonBaseBlock.EXTENDED, true);
        VoxelShape baseShape = extendedBaseState.getCollisionShape(level, pos);
        BlockState headState = Blocks.PISTON_HEAD.defaultBlockState()
            .setValue(PistonHeadBlock.FACING, piston.getDirection())
            .setValue(PistonHeadBlock.SHORT, piston.isExtending() != MovementRuntime.find(
                ShortArmThresholdHook.class, MovementRuntime.playerFrom(context))
                .map(hook -> hook.isShort(progress, 1.0F - progress < 4.0F))
                .orElseGet(() -> 1.0F - progress < 4.0F));

        Direction direction = piston.getDirection();
        float extendedProgress = 1.0F - progress;
        double dx = direction.getStepX() * extendedProgress;
        double dy = direction.getStepY() * extendedProgress;
        double dz = direction.getStepZ() * extendedProgress;
        VoxelShape headShape = headState.getCollisionShape(level, pos).move(dx, dy, dz);
        return Optional.of(Shapes.or(baseShape, headShape));
    }
}

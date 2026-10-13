package me.wolfii.legacyparkourcompat.change.v1_15_2;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
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
 * getCollisionShape, lines 333-360: retracting source-head SHORT threshold 4.0F.
 * 1.16/mojmap same file/member, lines 319-346: threshold becomes 0.25F.
 * Retains the common raw-progress/NOCLIP gate and float offset operation order.
 *
 * Source evidence (paths are relative to decompiled_minecraft/):
 * 1.15.2/mojmap/net/minecraft/world/level/block/piston/PistonMovingBlockEntity.java:
 *   getCollisionShape; lines [[333, 361]].
 *   Source SHA-256: 43ef9a249446cac093eb1f7555bd55c4db5416474fa1668772f0f1257672e57a.
 * 1.16.5/mojmap/net/minecraft/world/level/block/piston/PistonMovingBlockEntity.java:
 *   getCollisionShape; lines [[335, 363]].
 *   Source SHA-256: 75d58043d5a9951f9b505a42b7656a5f01028633d971ba948167a9a95729a8b5.
 * Bounded source decisions:
 *   Decision 4a5eae32b5819418dbd59eb4a763ee24ea1f2c52:development/research/archive/legacy-workflows/source-campaign-2026-10-07/independent-review-F-S5-PISTON-HEAD-THRESHOLD-2026-10-08.md; ACCEPT, bounded source snapshot.
 *   Reviewed snapshot 58e56ee59d7cc4015d499e3e2a37ec1d45049d56:workflows/source-campaign-2026-10-07/1.15.2--1.16.5/findings/F-S5-PISTON-HEAD-THRESHOLD.md.
 *   Review 005d5338f9a944f71651b8010ac90f73e1cf9d2c; ACCEPT, first stable boundary1.16.
 *   Reviewed snapshot 4a5eae32b5819418dbd59eb4a763ee24ea1f2c52:development/research/archive/branch-integration-2026-10-13/cf52bc40ba9591b53ca6684f4a6e1b86a0ccd77ffa918ca6235f3697828508f7.md.
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

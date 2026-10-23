package me.wolfii.legacyparkourcompat.change.v1_21_6;

import it.unimi.dsi.fastutil.longs.LongSet;
import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.blockcallback.BlockCallbackContactHook;
import net.minecraft.CrashReport;
import net.minecraft.CrashReportCategory;
import net.minecraft.ReportedException;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.InsideBlockEffectApplier;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * 1.21.6/1.21.7/1.21.8 Mojmap Entity.checkInsideBlocks(Vec3,Vec3,...): alive,
 * state/air, shared dedup, block predicate/callback, then fluid predicate/callback.
 * 26.2 unobfuscated Entity.checkInsideBlocks instead evaluates both predicates
 * before dedup and adds an isPrecise argument to BlockState.entityInside.
 * Preserve the native precision operand: the independent bubble-column
 * eligibility hook restores its old response. Only BubbleColumnBlock consumes
 * this operand in 26.2. Keep contact sequencing independently resolved from
 * the record budget, segment iterator, axis ordering and swept math.
 */
@MovementChange(emulates = ParkourVersion.V1_21_6)
public final class BlockCallbackContact implements BlockCallbackContactHook {
    @Override
    public Optional<Boolean> visit(Entity entity, BlockPos pos, int step, boolean precise,
                         InsideBlockEffectApplier.StepBasedCollector collector, LongSet visited,
                         Consumer<BlockState> onInsideBlock, Predicate<List<AABB>> sweptIntersection) {
        if (!entity.isAlive()) return Optional.of(false);
        BlockState state = entity.level().getBlockState(pos);
        if (state.isAir()) return Optional.of(true);
        if (!visited.add(pos.asLong())) return Optional.of(true);
        VoxelShape shape = state.getEntityInsideCollisionShape(entity.level(), pos, entity);
        boolean insideBlock = shape == Shapes.block()
            || sweptIntersection.test(shape.move(new Vec3(pos)).toAabbs());
        if (insideBlock) {
            try {
                collector.advanceStep(step);
                state.entityInside(entity.level(), pos, entity, collector, precise);
                onInsideBlock.accept(state);
            } catch (Throwable throwable) {
                CrashReport report = CrashReport.forThrowable(throwable, "Colliding entity with block");
                CrashReportCategory blockCategory = report.addCategory("Block being collided with");
                CrashReportCategory.populateBlockDetails(blockCategory, entity.level(), pos, state);
                CrashReportCategory entityCategory = report.addCategory("Entity being checked for collision");
                entity.fillCrashReportCategory(entityCategory);
                throw new ReportedException(report);
            }
        }
        AABB fluidBox = state.getFluidState().getAABB(entity.level(), pos);
        if (fluidBox != null && sweptIntersection.test(List.of(fluidBox))) {
            collector.advanceStep(step);
            state.getFluidState().entityInside(entity.level(), pos, entity, collector);
        }
        return Optional.of(true);
    }
}

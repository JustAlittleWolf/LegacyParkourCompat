package me.wolfii.legacyparkourcompat.change.v1_21_6;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.blockcallback.BlockCallbackContactSequence;
import me.wolfii.legacyparkourcompat.mechanic.hook.blockcallback.BlockCallbackContactHook;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.InsideBlockEffectApplier;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Predicate;
import it.unimi.dsi.fastutil.longs.LongSet;

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
        return BlockCallbackContactSequence.visit(entity, pos, step, precise, collector, visited,
            onInsideBlock, sweptIntersection);
    }
}

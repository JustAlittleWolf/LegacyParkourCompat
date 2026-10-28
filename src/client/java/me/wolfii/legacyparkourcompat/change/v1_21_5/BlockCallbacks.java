package me.wolfii.legacyparkourcompat.change.v1_21_5;

import it.unimi.dsi.fastutil.longs.LongSet;
import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.blockcallback.BlockCallbackDda;
import me.wolfii.legacyparkourcompat.mechanic.hook.blockcallback.BlockCallbackContactSequence;
import me.wolfii.legacyparkourcompat.mechanic.hook.blockcallback.BlockCallbackBudgetHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.blockcallback.BlockCallbackContactHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.blockcallback.BlockCallbackTraversalHook;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.InsideBlockEffectApplier;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * Mojmap 1.21.5 Entity.move builds axis-ordered resolved segments, and
 * checkInsideBlocks consumes them with a shared visited set and staged effect
 * collector. This provider selects the source-audited traversal and contact
 * sequence; EntityMixin stores the per-move nested batches and the batch-drain
 * mixin preserves their order. Source: 1.21.5/mojmap/net/minecraft/world/entity/Entity.java,
 * move, addMovementThisTick and checkInsideBlocks.
 */
@MovementChange(emulates = ParkourVersion.V1_21_5)
public final class BlockCallbacks implements BlockCallbackTraversalHook,
    BlockCallbackBudgetHook, BlockCallbackContactHook {
    @Override
    public boolean appliesTo(Entity entity) {
        return entity instanceof LocalPlayer && entity.level().isClientSide()
            && !entity.isRemoved() && !entity.noPhysics;
    }

    @Override
    public Optional<Boolean> visitSegment(Vec3 from, Vec3 to, AABB target, BlockGetter.BlockStepVisitor visitor) {
        return BlockCallbackDda.visitSegment(from, to, target, visitor);
    }

    @Override
    public OptionalInt segmentDebit() {
        return OptionalInt.of(0);
    }

    @Override
    public int segmentLimit(Entity entity, int nativeLimit) {
        return this.appliesTo(entity) ? Integer.MAX_VALUE : nativeLimit;
    }

    @Override
    public Optional<Boolean> visit(Entity entity, BlockPos pos, int step, boolean precise,
                                   InsideBlockEffectApplier.StepBasedCollector collector, LongSet visited,
                                   Consumer<BlockState> onInsideBlock, Predicate<List<AABB>> sweptIntersection) {
        return BlockCallbackContactSequence.visit(entity, pos, step, precise, collector, visited,
            onInsideBlock, sweptIntersection);
    }
}

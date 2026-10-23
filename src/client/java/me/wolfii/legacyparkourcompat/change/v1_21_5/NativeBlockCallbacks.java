package me.wolfii.legacyparkourcompat.change.v1_21_5;

import it.unimi.dsi.fastutil.longs.LongSet;
import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.blockcallback.BlockCallbackBudgetHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.blockcallback.BlockCallbackContactHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.blockcallback.BlockCallbackTraversalHook;
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
 * Mojmap 1.21.5 Entity.move/addMovementThisTick immediately split component
 * records with different position arithmetic and batching; 1.21.6-1.21.8
 * Entity.checkInsideBlocks reconstructs them later with 100-record compression.
 * The shared old BlockGetter cap alone does not establish the complete 1.21.5
 * record recipe against unobfuscated 26.2 Entity.move/checkInsideBlocks.
 * Retain native operations through this boundary as an explicit gap. Earlier
 * final-box replay remains independently resolved and takes precedence.
 */
@MovementChange(emulates = ParkourVersion.V1_21_5)
public final class NativeBlockCallbacks implements BlockCallbackTraversalHook,
    BlockCallbackBudgetHook, BlockCallbackContactHook {
    @Override
    public Optional<Boolean> visitSegment(Vec3 from, Vec3 to, AABB target, BlockGetter.BlockStepVisitor visitor) {
        return Optional.empty();
    }

    @Override
    public OptionalInt segmentDebit() {
        return OptionalInt.empty();
    }

    @Override
    public Optional<Boolean> visit(Entity entity, BlockPos pos, int step, boolean precise,
                                   InsideBlockEffectApplier.StepBasedCollector collector, LongSet visited,
                                   Consumer<BlockState> onInsideBlock, Predicate<List<AABB>> sweptIntersection) {
        return Optional.empty();
    }
}

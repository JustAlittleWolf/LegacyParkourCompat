package me.wolfii.legacyparkourcompat.change.v1_21_2;

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

/** Keeps callback segment, contact, and budget operations native through 1.21.3. */
@MovementChange(emulates = ParkourVersion.V1_21_2)
public final class NativeBlockCallbacks implements BlockCallbackTraversalHook, BlockCallbackBudgetHook, BlockCallbackContactHook {
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

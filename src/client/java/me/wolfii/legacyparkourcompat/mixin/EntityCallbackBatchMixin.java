package me.wolfii.legacyparkourcompat.mixin;

import it.unimi.dsi.fastutil.longs.LongSet;
import me.wolfii.legacyparkourcompat.mechanic.MovementRuntime;
import me.wolfii.legacyparkourcompat.mechanic.hook.blockcallback.MovementCallbackRecordHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.blockcallback.MovementCallbackSegmentsAccess;
import me.wolfii.legacyparkourcompat.mixin.accessor.EntityInvoker;
import me.wolfii.legacyparkourcompat.mixin.accessor.EntityVisitedBlocksAccessor;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.InsideBlockEffectApplier;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.List;

/** Replaces only the 1.21.5 movement-record drain with its ordered batch list. */
@Mixin(Entity.class)
abstract class EntityCallbackBatchMixin {
    @Inject(method = "checkInsideBlocks(Ljava/util/List;Lnet/minecraft/world/entity/InsideBlockEffectApplier$StepBasedCollector;)V",
        at = @At("HEAD"), cancellable = true)
    private void legacyparkourcompat$drainHistoricalCallbackBatches(
        List<?> nativeMovements,
        InsideBlockEffectApplier.StepBasedCollector collector,
        CallbackInfo callbackInfo
    ) {
        Entity entity = (Entity)(Object)this;
        if (entity.isRemoved() || entity.noPhysics) return;
        boolean historicalBatches = MovementRuntime.find(MovementCallbackRecordHook.class, entity)
            .map(hook -> hook.captureAxisSegments(entity))
            .orElse(false);
        if (!historicalBatches) return;

        MovementCallbackSegmentsAccess access = (MovementCallbackSegmentsAccess)entity;
        if (access.legacyparkourcompat$isExplicitCallbackScan()) return;

        List<MovementCallbackSegmentsAccess.Batch> batches = access.legacyparkourcompat$takeMovementCallbackBatches();
        if (batches.isEmpty()) return;

        List<MovementCallbackSegmentsAccess.Segment> flattened = new ArrayList<>();
        for (MovementCallbackSegmentsAccess.Batch batch : batches) {
            flattened.addAll(batch.segments());
        }
        if (flattened.isEmpty()) return;

        Vec3 currentPosition = entity.position();
        Vec3 lastEndpoint = flattened.getLast().to();
        if (lastEndpoint.distanceToSqr(currentPosition) > 9.9999994E-11F) {
            flattened.add(new MovementCallbackSegmentsAccess.Segment(lastEndpoint, currentPosition));
        }

        LongSet visited = ((EntityVisitedBlocksAccessor)entity).legacyparkourcompat$visitedBlocks();
        EntityInvoker invoker = (EntityInvoker)entity;
        for (MovementCallbackSegmentsAccess.Segment segment : flattened) {
            invoker.legacyparkourcompat$invokeCheckInsideBlocks(
                segment.from(), segment.to(), collector, visited, 16);
        }
        visited.clear();
        callbackInfo.cancel();
    }
}

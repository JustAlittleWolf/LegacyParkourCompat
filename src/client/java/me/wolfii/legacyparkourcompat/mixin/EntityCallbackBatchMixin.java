package me.wolfii.legacyparkourcompat.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import it.unimi.dsi.fastutil.longs.LongSet;
import me.wolfii.legacyparkourcompat.mechanic.MovementRuntime;
import me.wolfii.legacyparkourcompat.mechanic.hook.blockcallback.MovementCallbackSegmentsAccess;
import me.wolfii.legacyparkourcompat.mechanic.hook.blockcallback.MovementCallbackSegmentsAccess.Segment;
import me.wolfii.legacyparkourcompat.mechanic.hook.blockcallback.MovementCallbackSegmentsAccess.Snapshot;
import me.wolfii.legacyparkourcompat.mixin.accessor.EntityInvoker;
import me.wolfii.legacyparkourcompat.mixin.accessor.EntityVisitedBlocksAccessor;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.InsideBlockEffectApplier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

/** Replaces only the 1.21.5 movement-record drain with its ordered batch list. */
@Mixin(Entity.class)
abstract class EntityCallbackBatchMixin {
    @Unique private Snapshot legacyparkourcompat$callbackSnapshot;

    @WrapOperation(method = "applyEffectsFromBlocks()V", at = @At(value = "INVOKE",
        target = "Lnet/minecraft/world/entity/Entity;applyEffectsFromBlocks(Ljava/util/List;)V"))
    private void legacyparkourcompat$prepareCallbackBatches(Entity entity, List<?> nativeMovements,
                                                            Operation<Void> original) {
        if (!(entity instanceof LocalPlayer) || !MovementRuntime.appliesTo(entity)) {
            original.call(entity, nativeMovements);
            return;
        }
        // 1.21.5 Mojmap Entity.applyEffectsFromBlocks() drains even when the list
        // overload skips effects, and finalizes endpoints before stepOn can run.
        // Only the resolved axis-segment provider enqueues these historical batches.
        var batches = ((MovementCallbackSegmentsAccess)entity).legacyparkourcompat$takeMovementCallbackBatches();
        Snapshot previous = this.legacyparkourcompat$callbackSnapshot;
        this.legacyparkourcompat$callbackSnapshot = batches.isEmpty() ? null
            : Snapshot.prepare(nativeMovements, batches, entity.oldPosition(), entity.position());
        try {
            original.call(entity, nativeMovements);
        } finally {
            this.legacyparkourcompat$callbackSnapshot = previous;
        }
    }

    @Inject(method = "checkInsideBlocks(Ljava/util/List;Lnet/minecraft/world/entity/InsideBlockEffectApplier$StepBasedCollector;)V",
        at = @At("HEAD"), cancellable = true)
    private void legacyparkourcompat$visitCallbackBatches(List<?> nativeMovements,
        InsideBlockEffectApplier.StepBasedCollector collector, CallbackInfo callbackInfo) {
        Snapshot snapshot = this.legacyparkourcompat$callbackSnapshot;
        Entity entity = (Entity)(Object)this;
        if (snapshot == null || snapshot.nativeMovements() != nativeMovements || entity.isRemoved() || entity.noPhysics) return;
        LongSet visited = ((EntityVisitedBlocksAccessor)entity).legacyparkourcompat$visitedBlocks();
        EntityInvoker invoker = (EntityInvoker)entity;
        for (Segment segment : snapshot.segments()) {
            invoker.legacyparkourcompat$invokeCheckInsideBlocks(
                segment.from(), segment.to(), collector, visited, 16);
        }
        visited.clear();
        callbackInfo.cancel();
    }
}

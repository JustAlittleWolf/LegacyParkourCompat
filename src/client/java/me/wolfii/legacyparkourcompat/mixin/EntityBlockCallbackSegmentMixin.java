package me.wolfii.legacyparkourcompat.mixin;

import it.unimi.dsi.fastutil.longs.LongSet;
import me.wolfii.legacyparkourcompat.mechanic.MovementRuntime;
import me.wolfii.legacyparkourcompat.mechanic.hook.blockcallback.BlockCallbackBudgetHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.blockcallback.BlockCallbackContactHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.blockcallback.BlockCallbackTraversalHook;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.InsideBlockEffectApplier;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/** Keeps the native record loop, effect collector, cleanup and application order. */
@Mixin(Entity.class)
abstract class EntityBlockCallbackSegmentMixin {
    @Shadow protected abstract AABB makeBoundingBox(Vec3 position);
    @Shadow protected abstract void onInsideBlock(BlockState state);

    @ModifyVariable(
        method = "checkInsideBlocks(Lnet/minecraft/world/phys/Vec3;Lnet/minecraft/world/phys/Vec3;Lnet/minecraft/world/entity/InsideBlockEffectApplier$StepBasedCollector;Lit/unimi/dsi/fastutil/longs/LongSet;I)I",
        at = @At("HEAD"), argsOnly = true
    )
    private int legacyParkourCompat$historicalSegmentLimit(int nativeLimit) {
        Entity entity = (Entity)(Object)this;
        return MovementRuntime.find(BlockCallbackBudgetHook.class, entity)
            .map(hook -> hook.segmentLimit(entity, nativeLimit))
            .orElse(nativeLimit);
    }

    @Inject(method = "checkInsideBlocks(Lnet/minecraft/world/phys/Vec3;Lnet/minecraft/world/phys/Vec3;Lnet/minecraft/world/entity/InsideBlockEffectApplier$StepBasedCollector;Lit/unimi/dsi/fastutil/longs/LongSet;I)I",
        at = @At("HEAD"), cancellable = true)
    private void legacyParkourCompat$historicalSegment(Vec3 from, Vec3 to,
        InsideBlockEffectApplier.StepBasedCollector collector, LongSet visited, int remainingBudget,
        CallbackInfoReturnable<Integer> cir) {
        Entity entity = (Entity)(Object)this;
        MovementRuntime.find(BlockCallbackTraversalHook.class, entity)
            .filter(iterator -> iterator.appliesTo(entity))
            .ifPresent(iterator -> {
            AABB target = this.makeBoundingBox(to).deflate(1.0E-5F);
            boolean movedFar = from.distanceToSqr(to) > Mth.square(0.9999900000002526);
            var result = iterator.visitSegment(from, to, target, (pos, step) ->
                MovementRuntime.find(BlockCallbackContactHook.class, entity)
                    .flatMap(contact -> contact.visit(entity, pos, step, movedFar || target.intersects(pos),
                        collector, visited, this::onInsideBlock,
                        shapes -> entity.collidedWithShapeMovingFrom(from, to, shapes)))
                    .orElseThrow(() -> new IllegalStateException("Historical callback iterator requires contact sequencing")));
            if (result.isPresent()) {
                // A stopped segment still permits later components, as in the old list loop.
                int debit = MovementRuntime.find(BlockCallbackBudgetHook.class, entity)
                    .map(BlockCallbackBudgetHook::segmentDebit)
                    .orElseThrow(() -> new IllegalStateException("Historical callback iterator requires record accounting"))
                    .orElseThrow(() -> new IllegalStateException("Historical callback iterator requires a historical debit"));
                cir.setReturnValue(debit);
            }
        });
    }
}

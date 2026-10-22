package me.wolfii.legacyparkourcompat.mixin;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementRuntime;
import me.wolfii.legacyparkourcompat.mechanic.hook.entity.SupportingBlockPositionHook;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
abstract class EntitySupportingBlockPositionMixin {
    @Inject(method = "getBlockPosBelowThatAffectsMyMovement", at = @At("RETURN"), cancellable = true)
    private void legacyparkourcompat$useHistoricalSupportingBlockPosition(CallbackInfoReturnable<BlockPos> cir) {
        Entity entity = (Entity)(Object)this;
        if (MovementRuntime.profile(entity).target().olderThan(ParkourVersion.V1_15)) {
            return;
        }
        MovementRuntime.find(SupportingBlockPositionHook.class, entity).ifPresent(hook ->
            cir.setReturnValue(hook.getPosition(entity, cir::getReturnValue))
        );
    }
}

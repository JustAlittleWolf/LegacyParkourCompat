package me.wolfii.legacyparkourcompat.mixin;

import me.wolfii.legacyparkourcompat.mechanic.MovementRuntime;
import me.wolfii.legacyparkourcompat.mechanic.hook.fluid.EyeFluidCacheHook;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.material.Fluid;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.HashSet;
import java.util.Set;

@Mixin(Entity.class)
abstract class EntityEyeFluidCacheMixin {
    @Unique
    private final Set<TagKey<Fluid>> legacyparkourcompat$eyeFluidTags = new HashSet<>();

    @Inject(
        method = "baseTick()V",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/entity/Entity;updateFluidInteraction()Z",
            shift = At.Shift.AFTER
        )
    )
    private void legacyparkourcompat$refreshEyeFluidCache(CallbackInfo callbackInfo) {
        Entity entity = (Entity) (Object) this;
        // 1.21.11 Mojmap Entity#baseTick reads wasEyeInWater before Entity#updateFluidOnEyes;
        // 26.1 unobfuscated Entity#baseTick reads the cache before Entity#updateFluidInteraction.
        // Refreshing after this call retains that previous-cache timing without replacing body trackers.
        // MovementRuntime.find gates changes to enabled client-side players; absence preserves native behavior.
        MovementRuntime.find(EyeFluidCacheHook.class, entity)
            .filter(EyeFluidCacheHook::overridesVanillaCache)
            .ifPresent(hook -> hook.refresh(entity, this.legacyparkourcompat$eyeFluidTags));
    }

    @Inject(method = "isEyeInFluid(Lnet/minecraft/tags/TagKey;)Z", at = @At("HEAD"), cancellable = true)
    private void legacyparkourcompat$readHistoricalEyeFluidCache(
        TagKey<Fluid> fluid,
        CallbackInfoReturnable<Boolean> callbackInfo
    ) {
        Entity entity = (Entity) (Object) this;
        // MovementRuntime.find gates changes to enabled client-side players; absence preserves native behavior.
        MovementRuntime.find(EyeFluidCacheHook.class, entity)
            .filter(EyeFluidCacheHook::overridesVanillaCache)
            .ifPresent(hook -> callbackInfo.setReturnValue(this.legacyparkourcompat$eyeFluidTags.contains(fluid)));
    }
}

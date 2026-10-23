package me.wolfii.legacyparkourcompat.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import me.wolfii.legacyparkourcompat.mechanic.MovementRuntime;
import me.wolfii.legacyparkourcompat.mechanic.hook.fishing.FishingRetrievalImpulseHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.fishing.FishingRetrievalTargetHook;
import net.minecraft.client.Minecraft;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.FishingHook;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FishingHook.class)
abstract class FishingRetrievalMixin {
    @Shadow @Final private static EntityDataAccessor<Integer> DATA_HOOKED_ENTITY;
    @Unique private @Nullable Entity legacyparkourcompat$responseTarget;

    @Inject(method = "onSyncedDataUpdated", at = @At("HEAD"))
    private void legacyparkourcompat$metadata(EntityDataAccessor<?> accessor, CallbackInfo ci) {
        FishingHook hook = (FishingHook) (Object) this;
        if (hook.level().isClientSide() && DATA_HOOKED_ENTITY.equals(accessor)) {
            MovementRuntime.find(FishingRetrievalTargetHook.class, Minecraft.getInstance().player).ifPresent(change ->
                legacyparkourcompat$responseTarget = change.afterMetadata(legacyparkourcompat$responseTarget,
                    hook.getEntityData().get(DATA_HOOKED_ENTITY)));
        }
    }

    @Inject(method = "tick", at = @At(value = "INVOKE",
        target = "Lnet/minecraft/world/entity/projectile/Projectile;tick()V", shift = At.Shift.AFTER))
    private void legacyparkourcompat$resolveTarget(CallbackInfo ci) {
        FishingHook hook = (FishingHook) (Object) this;
        if (hook.level().isClientSide()) {
            MovementRuntime.find(FishingRetrievalTargetHook.class, Minecraft.getInstance().player).ifPresent(change ->
                legacyparkourcompat$responseTarget = change.afterTick(legacyparkourcompat$responseTarget,
                    hook.getEntityData().get(DATA_HOOKED_ENTITY), hook.level()));
        }
    }

    @Redirect(method = "handleEntityEvent", at = @At(value = "FIELD",
        target = "Lnet/minecraft/world/entity/projectile/FishingHook;hookedIn:Lnet/minecraft/world/entity/Entity;"))
    private @Nullable Entity legacyparkourcompat$responseTarget(FishingHook hook) {
        Entity nativeTarget = hook.getHookedIn();
        if (!hook.level().isClientSide()) {
            return nativeTarget;
        }
        var change = MovementRuntime.find(FishingRetrievalTargetHook.class, Minecraft.getInstance().player);
        return change.isPresent()
            ? change.get().eventTarget(legacyparkourcompat$responseTarget, nativeTarget) : nativeTarget;
    }

    @ModifyExpressionValue(method = "pullEntity", at = @At(value = "INVOKE",
        target = "Lnet/minecraft/world/phys/Vec3;scale(D)Lnet/minecraft/world/phys/Vec3;"))
    private Vec3 legacyparkourcompat$impulse(Vec3 nativeImpulse, Entity entity) {
        FishingHook hook = (FishingHook) (Object) this;
        if (!(entity instanceof Player player) || !player.isLocalPlayer()) {
            return nativeImpulse;
        }
        Entity owner = hook.getOwner();
        if (owner == null) {
            throw new IllegalStateException("Fishing retrieval impulse reached without its guarded owner");
        }
        return MovementRuntime.find(FishingRetrievalImpulseHook.class, entity)
            .map(change -> change.impulse(owner.getX() - hook.getX(), owner.getY() - hook.getY(),
                owner.getZ() - hook.getZ(), nativeImpulse)).orElse(nativeImpulse);
    }

}

package me.wolfii.legacyparkourcompat.mixin.network;

import me.wolfii.legacyparkourcompat.mechanic.hook.network.ClientReceivedInterpolationHook;
import me.wolfii.legacyparkourcompat.mechanic.MovementRuntime;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.InterpolationHandler;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class LivingEntityPacketInterpolationMixin {
    @Unique private static final ThreadLocal<Entity> LEGACY_TICK_ENTITY = new ThreadLocal<>();

    @Redirect(
        method = "aiStep",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;isInterpolating()Z")
    )
    private boolean legacyparkourcompat$includeQueuedPacketTarget(Entity entity) {
        LEGACY_TICK_ENTITY.remove();
        if (entity instanceof LocalPlayer) {
            var hook = MovementRuntime.find(ClientReceivedInterpolationHook.class, entity).orElse(null);
            if (hook != null && hook.handlesLegacyPackets(entity) && hook.pending(entity)) {
                LEGACY_TICK_ENTITY.set(entity);
                return true;
            }
        }
        return entity.isInterpolating();
    }

    @Redirect(
        method = "aiStep",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/InterpolationHandler;interpolate()V")
    )
    private void legacyparkourcompat$advancePacketTarget(InterpolationHandler handler) {
        Entity entity = LEGACY_TICK_ENTITY.get();
        if (entity instanceof LocalPlayer) {
            LEGACY_TICK_ENTITY.remove();
            var hook = MovementRuntime.find(ClientReceivedInterpolationHook.class, entity).orElse(null);
            if (hook != null && hook.handlesLegacyPackets(entity) && hook.pending(entity)) {
                hook.advance(entity);
                return;
            }
        }
        handler.interpolate();
    }

    @Inject(method = "aiStep", at = @At("TAIL"))
    private void legacyparkourcompat$clearTickTarget(CallbackInfo callbackInfo) {
        LEGACY_TICK_ENTITY.remove();
    }
}

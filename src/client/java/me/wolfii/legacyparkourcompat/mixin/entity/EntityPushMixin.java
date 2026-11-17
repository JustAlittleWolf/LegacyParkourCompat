package me.wolfii.legacyparkourcompat.mixin.entity;

import me.wolfii.legacyparkourcompat.mechanic.MovementRuntime;
import me.wolfii.legacyparkourcompat.mechanic.hook.entity.EntityPushHook;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Entity.class)
abstract class EntityPushMixin {
    @Inject(method = "push(Lnet/minecraft/world/entity/Entity;)V", at = @At("HEAD"), cancellable = true)
    private void legacyparkourcompat$suppressHistoricalInboundPush(Entity source, CallbackInfo ci) {
        Entity receiver = (Entity) (Object) this;
        MovementRuntime.find(EntityPushHook.class, receiver)
            .filter(hook -> hook.suppressInboundPush(receiver, source))
            .ifPresent(hook -> ci.cancel());
    }
}

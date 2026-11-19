package me.wolfii.legacyparkourcompat.mixin.entity;

import me.wolfii.legacyparkourcompat.mechanic.MovementRuntime;
import me.wolfii.legacyparkourcompat.mechanic.hook.entity.EntityPushHook;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Entity.class)
abstract class EntityPushMixin {
    // 26.2 Entity.push(Entity) pushes the receiver first and the source second.
    @Redirect(
        method = "push(Lnet/minecraft/world/entity/Entity;)V",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;push(DDD)V", ordinal = 0)
    )
    private void legacyparkourcompat$applyInboundPush(Entity receiver, double x, double y, double z, Entity source) {
        boolean suppress = MovementRuntime.find(EntityPushHook.class, receiver)
            .map(hook -> hook.suppressInboundPush(receiver, source)).orElse(false);
        if (!suppress) {
            receiver.push(x, y, z);
        }
    }
}

package me.wolfii.legacyparkourcompat.mixin.entity;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import me.wolfii.legacyparkourcompat.mechanic.EntityCollisionPushContext;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Entity.class)
abstract class EntityPushMixin {
    @WrapOperation(
        method = "push(Lnet/minecraft/world/entity/Entity;)V",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;push(DDD)V"),
        require = 2
    )
    private void legacyparkourcompat$applyCollisionPush(
        Entity recipient, double x, double y, double z, Operation<Void> original,
        @Local(argsOnly = true, index = 1) Entity pusher
    ) {
        Entity receiver = (Entity) (Object) this;
        if (!EntityCollisionPushContext.suppressVelocityWrite(receiver, pusher, recipient)) {
            original.call(recipient, x, y, z);
        }
    }
}
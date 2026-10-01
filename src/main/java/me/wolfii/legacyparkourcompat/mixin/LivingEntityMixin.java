package me.wolfii.legacyparkourcompat.mixin;

import me.wolfii.legacyparkourcompat.mechanic.MovementRuntime;
import me.wolfii.legacyparkourcompat.mechanic.hook.GlideFallDistanceBehavior;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {
    @Inject(method = "travelFallFlying(Lnet/minecraft/world/phys/Vec3;)V", at = @At("HEAD"))
    private void legacyparkourcompat$fallFlyingFallDistance(Vec3 input, CallbackInfo ci) {
        if ((Object) this instanceof Player player) {
            MovementRuntime.find(GlideFallDistanceBehavior.class, player)
                .ifPresent(behavior -> behavior.beforeFallFlyingTravel(player));
        }
    }
}

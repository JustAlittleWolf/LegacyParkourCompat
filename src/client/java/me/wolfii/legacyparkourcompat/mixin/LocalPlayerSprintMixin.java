package me.wolfii.legacyparkourcompat.mixin;

import me.wolfii.legacyparkourcompat.mechanic.MovementRuntime;
import me.wolfii.legacyparkourcompat.mechanic.hook.SprintDurationBehavior;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
abstract class LocalPlayerSprintMixin {
    @Inject(method = "setSprinting", at = @At("TAIL"))
    private void legacyParkourCompat$setLocalSprinting(boolean sprinting, CallbackInfo ci) {
        LivingEntity entity = (LivingEntity) (Object) this;
        if (entity instanceof LocalPlayer player) {
            MovementRuntime.find(SprintDurationBehavior.class, player)
                .ifPresent(behavior -> behavior.onSetSprinting(player, sprinting));
        }
    }
}

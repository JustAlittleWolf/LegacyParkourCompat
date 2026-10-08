package me.wolfii.legacyparkourcompat.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementRuntime;
import me.wolfii.legacyparkourcompat.mechanic.hook.FallFlyingSavedStateBehavior;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.storage.ValueOutput;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(LivingEntity.class)
abstract class ServerLivingEntityMixin {
    @WrapOperation(
        method = "addAdditionalSaveData(Lnet/minecraft/world/level/storage/ValueOutput;)V",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/level/storage/ValueOutput;putBoolean(Ljava/lang/String;Z)V",
            ordinal = 0
        )
    )
    private void legacyparkourcompat$saveFallFlyingState(
        ValueOutput output,
        String key,
        boolean value,
        Operation<Void> original
    ) {
        if ("FallFlying".equals(key) && (Object) this instanceof ServerPlayer player) {
            ParkourVersion selected = MovementRuntime.profile(player).target();
            boolean persist = MovementRuntime.find(FallFlyingSavedStateBehavior.class, player)
                .map(behavior -> behavior.shouldPersistFallFlyingState(player, selected))
                .orElse(true);
            if (!persist) {
                return;
            }
        }
        original.call(output, key, value);
    }
}

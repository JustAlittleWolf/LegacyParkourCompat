package me.wolfii.legacyparkourcompat.mixin;

import me.wolfii.legacyparkourcompat.mechanic.MovementRuntime;
import me.wolfii.legacyparkourcompat.mechanic.hook.FallFlyingSavedStateBehavior;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.storage.ValueInput;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayer.class)
abstract class ServerPlayerMixin {
    @Inject(
        method = "readAdditionalSaveData(Lnet/minecraft/world/level/storage/ValueInput;)V",
        at = @At("TAIL")
    )
    private void legacyparkourcompat$loadFallFlyingState(ValueInput input, CallbackInfo ci) {
        ServerPlayer player = (ServerPlayer) (Object) this;
        MovementRuntime.find(FallFlyingSavedStateBehavior.class, player)
            .ifPresent(behavior -> behavior.afterFallFlyingStateLoaded(
                player,
                MovementRuntime.profile(player).target()
            ));
    }
}

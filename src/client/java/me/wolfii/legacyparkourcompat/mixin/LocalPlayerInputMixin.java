package me.wolfii.legacyparkourcompat.mixin;

import me.wolfii.legacyparkourcompat.mechanic.MovementRuntime;
import me.wolfii.legacyparkourcompat.mechanic.hook.SneakInputSlowdownBehavior;
import net.minecraft.client.player.LocalPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(LocalPlayer.class)
public abstract class LocalPlayerInputMixin {
    @Redirect(
        method = "modifyInput(Lnet/minecraft/world/phys/Vec2;)Lnet/minecraft/world/phys/Vec2;",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/LocalPlayer;isMovingSlowly()Z")
    )
    private boolean legacyparkourcompat$sneakSlowdownGate(LocalPlayer player) {
        boolean vanilla = player.isMovingSlowly();
        return MovementRuntime.find(SneakInputSlowdownBehavior.class, player)
            .map(behavior -> behavior.shouldSlowDown(player))
            .orElse(vanilla);
    }
}

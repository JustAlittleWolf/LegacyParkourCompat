package me.wolfii.legacyparkourcompat.mixin;

import me.wolfii.legacyparkourcompat.mechanic.MovementRuntime;
import me.wolfii.legacyparkourcompat.mechanic.hook.ClientInputBehavior;
import me.wolfii.legacyparkourcompat.mechanic.hook.SneakInputSlowdownBehavior;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.phys.Vec2;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

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

    @Inject(
        method = "modifyInput(Lnet/minecraft/world/phys/Vec2;)Lnet/minecraft/world/phys/Vec2;",
        at = @At("RETURN")
    )
    private void legacyparkourcompat$modifyInput(Vec2 raw, CallbackInfoReturnable<Vec2> callback) {
        LocalPlayer player = (LocalPlayer)(Object)this;
        MovementRuntime.find(ClientInputBehavior.class, player).ifPresent(behavior -> {
            boolean usingItem = player.isUsingItem() && !player.isPassenger();
            boolean movingSlowly = player.isMovingSlowly();
            float sneakingSpeed = (float)player.getAttributeValue(Attributes.SNEAKING_SPEED);
            boolean flyingSneak = player.getAbilities().flying && player.isShiftKeyDown();
            callback.setReturnValue(behavior.modify(
                raw,
                callback.getReturnValue(),
                usingItem,
                movingSlowly,
                sneakingSpeed,
                flyingSneak
            ));
        });
    }
}

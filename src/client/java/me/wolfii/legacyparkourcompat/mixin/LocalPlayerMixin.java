package me.wolfii.legacyparkourcompat.mixin;

import me.wolfii.legacyparkourcompat.mechanic.MovementRuntime;
import me.wolfii.legacyparkourcompat.mechanic.hook.ClientInputBehavior;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.phys.Vec2;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LocalPlayer.class)
abstract class LocalPlayerMixin {
    @Inject(method = "modifyInput", at = @At("RETURN"), cancellable = true)
    private void legacyParkourCompat$modifyInput(Vec2 raw, CallbackInfoReturnable<Vec2> cir) {
        LocalPlayer player = (LocalPlayer) (Object) this;
        MovementRuntime.find(ClientInputBehavior.class, player).ifPresent(behavior -> cir.setReturnValue(
            behavior.modify(
                raw,
                cir.getReturnValue(),
                player.isUsingItem(),
                player.isMovingSlowly(),
                (float) player.getAttributeValue(Attributes.SNEAKING_SPEED),
                player.getAbilities().flying && player.isMovingSlowly()
            )
        ));
    }

}

package me.wolfii.legacyparkourcompat.mixin;

import me.wolfii.legacyparkourcompat.mechanic.MovementRuntime;
import me.wolfii.legacyparkourcompat.mechanic.hook.SneakEdgeProbeBehavior;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Player.class)
abstract class PlayerEdgeBackoffMixin {
    @Inject(method = "canFallAtLeast(DDD)Z", at = @At("HEAD"), cancellable = true)
    private void legacy$historicalSupportProbe(
        double deltaX,
        double deltaZ,
        double minHeight,
        CallbackInfoReturnable<Boolean> callback
    ) {
        Player player = (Player) (Object) this;
        MovementRuntime.find(SneakEdgeProbeBehavior.class, player)
            .filter(behavior -> behavior.appliesTo(player))
            .ifPresent(behavior -> callback.setReturnValue(
                behavior.canFallAtLeast(player, deltaX, deltaZ, minHeight)));
    }
}

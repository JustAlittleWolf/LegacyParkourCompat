package me.wolfii.legacyparkourcompat.mixin.client;

import me.wolfii.legacyparkourcompat.mechanic.MovementRuntime;
import me.wolfii.legacyparkourcompat.mechanic.hook.KeyboardDiagonalInputBehavior;
import net.minecraft.client.player.KeyboardInput;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.player.Input;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec2;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LocalPlayer.class)
abstract class LocalPlayerInputMixin {
    @Inject(
        method = "modifyInput(Lnet/minecraft/world/phys/Vec2;)Lnet/minecraft/world/phys/Vec2;",
        at = @At("RETURN"),
        cancellable = true
    )
    private void legacy$diagonalInputPrecision(Vec2 raw, CallbackInfoReturnable<Vec2> callback) {
        LocalPlayer player = (LocalPlayer) (Object) this;
        Input keys = player.input.keyPresses;
        float left = keys.left() == keys.right() ? 0.0F : keys.left() ? 1.0F : -1.0F;
        float forward = keys.forward() == keys.backward() ? 0.0F : keys.forward() ? 1.0F : -1.0F;
        Vec2 rawImpulses = new Vec2(left, forward);
        boolean keyboard = player.input instanceof KeyboardInput;
        boolean diagonal = left != 0.0F && forward != 0.0F;
        boolean usingItem = player.isUsingItem() && !player.isPassenger();
        Vec2 vanilla = callback.getReturnValue();
        MovementRuntime.find(KeyboardDiagonalInputBehavior.class, player)
            .ifPresent(behavior -> callback.setReturnValue(behavior.diagonalInput(
                (Player) player,
                rawImpulses,
                vanilla,
                keyboard,
                diagonal,
                player.isMovingSlowly(),
                usingItem
            )));
    }
}

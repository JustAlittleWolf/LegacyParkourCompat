package me.wolfii.legacyparkourcompat.mixin.client;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import me.wolfii.legacyparkourcompat.mechanic.MovementRuntime;
import me.wolfii.legacyparkourcompat.mechanic.hook.ClientMoveInputBehavior;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.ClientInput;
import net.minecraft.client.player.KeyboardInput;
import net.minecraft.world.phys.Vec2;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ClientInput.class)
public abstract class ClientInputMixin {
    @ModifyReturnValue(method = "getMoveVector", at = @At("RETURN"))
    private Vec2 lpc$moveVector(Vec2 vanilla) {
        ClientInput self = (ClientInput) (Object) this;
        if (!(self instanceof KeyboardInput)) {
            return vanilla;
        }
        var player = Minecraft.getInstance().player;
        if (!MovementRuntime.appliesTo(player)) {
            return vanilla;
        }
        return MovementRuntime.find(ClientMoveInputBehavior.class, player)
            .map(behavior -> behavior.moveVector(self.keyPresses, vanilla))
            .orElse(vanilla);
    }
}

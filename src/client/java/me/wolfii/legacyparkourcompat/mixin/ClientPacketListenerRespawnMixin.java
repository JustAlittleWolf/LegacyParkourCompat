package me.wolfii.legacyparkourcompat.mixin;

import me.wolfii.legacyparkourcompat.mechanic.MovementRuntime;
import me.wolfii.legacyparkourcompat.mechanic.hook.player.RespawnMovementAttributeHook;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientCommonPacketListenerImpl;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.Connection;
import net.minecraft.client.multiplayer.CommonListenerCookie;
import net.minecraft.network.protocol.game.ClientboundRespawnPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/** Dispatches the profile-specific movement attribute response after client respawn copying. */
@Mixin(ClientPacketListener.class)
abstract class ClientPacketListenerRespawnMixin extends ClientCommonPacketListenerImpl {
    protected ClientPacketListenerRespawnMixin(Minecraft minecraft, Connection connection, CommonListenerCookie cookie) {
        super(minecraft, connection, cookie);
    }

    @Inject(
        method = "handleRespawn",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/entity/ai/attributes/AttributeMap;assignAllValues(Lnet/minecraft/world/entity/ai/attributes/AttributeMap;)V",
            shift = At.Shift.AFTER
        )
    )
    private void legacyparkourcompat$afterAssignAllAttributes(ClientboundRespawnPacket packet, CallbackInfo ci) {
        legacyparkourcompat$afterAttributeCopy();
    }

    @Inject(
        method = "handleRespawn",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/entity/ai/attributes/AttributeMap;assignBaseValues(Lnet/minecraft/world/entity/ai/attributes/AttributeMap;)V",
            shift = At.Shift.AFTER
        )
    )
    private void legacyparkourcompat$afterAssignBaseAttributes(ClientboundRespawnPacket packet, CallbackInfo ci) {
        legacyparkourcompat$afterAttributeCopy();
    }

    private void legacyparkourcompat$afterAttributeCopy() {
        LocalPlayer replacementPlayer = this.minecraft.player;
        if (replacementPlayer == null) {
            throw new IllegalStateException("Client respawn completed attribute copying without a replacement player");
        }
        MovementRuntime.find(RespawnMovementAttributeHook.class, replacementPlayer)
            .ifPresent(hook -> hook.afterRespawn(replacementPlayer));
    }
}

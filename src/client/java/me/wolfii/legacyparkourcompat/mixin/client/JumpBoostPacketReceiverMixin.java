package me.wolfii.legacyparkourcompat.mixin.client;

import me.wolfii.legacyparkourcompat.mechanic.MovementRuntime;
import me.wolfii.legacyparkourcompat.mechanic.hook.jumpboost.JumpBoostEffectReceiverHook;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.protocol.game.ClientboundRemoveMobEffectPacket;
import net.minecraft.network.protocol.game.ClientboundUpdateMobEffectPacket;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/** Captures only the local player's Jump Boost packet events after vanilla handles them. */
@Mixin(ClientPacketListener.class)
abstract class JumpBoostPacketReceiverMixin {
    @Shadow
    private ClientLevel level;

    @Inject(method = "handleUpdateMobEffect", at = @At("TAIL"))
    private void legacyparkourcompat$receiveJumpBoostUpdate(
        ClientboundUpdateMobEffectPacket packet, CallbackInfo callbackInfo
    ) {
        if (!packet.getEffect().equals(MobEffects.JUMP_BOOST)) {
            return;
        }
        Entity entity = this.level.getEntity(packet.getEntityId());
        if (entity instanceof Player player && player == Minecraft.getInstance().player) {
            MovementRuntime.find(JumpBoostEffectReceiverHook.class, player).ifPresent(receiver ->
                receiver.receiveUpdate(player, packet.getEffectAmplifier(), packet.getEffectDurationTicks())
            );
        }
    }

    @Inject(method = "handleRemoveMobEffect", at = @At("TAIL"))
    private void legacyparkourcompat$receiveJumpBoostRemoval(
        ClientboundRemoveMobEffectPacket packet, CallbackInfo callbackInfo
    ) {
        if (!packet.effect().equals(MobEffects.JUMP_BOOST)) {
            return;
        }
        Entity entity = packet.getEntity(this.level);
        if (entity instanceof Player player && player == Minecraft.getInstance().player) {
            MovementRuntime.find(JumpBoostEffectReceiverHook.class, player).ifPresent(receiver ->
                receiver.receiveRemoval(player)
            );
        }
    }
}

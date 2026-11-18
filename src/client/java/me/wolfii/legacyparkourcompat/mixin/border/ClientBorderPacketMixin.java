package me.wolfii.legacyparkourcompat.mixin.border;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import me.wolfii.legacyparkourcompat.mechanic.ClientBorderLerpState;
import me.wolfii.legacyparkourcompat.mechanic.MovementRuntime;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.protocol.game.ClientboundInitializeBorderPacket;
import net.minecraft.network.protocol.game.ClientboundLoginPacket;
import net.minecraft.network.protocol.game.ClientboundRespawnPacket;
import net.minecraft.network.protocol.game.ClientboundSetBorderLerpSizePacket;
import net.minecraft.world.level.border.WorldBorder;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/** Captures client-only packet timing without mutating vanilla border state. */
@Mixin(ClientPacketListener.class)
abstract class ClientBorderPacketMixin {
    @Shadow private ClientLevel level;

    private static final String THREAD_GUARD =
        "Lnet/minecraft/network/protocol/PacketUtils;ensureRunningOnSameThread(" +
            "Lnet/minecraft/network/protocol/Packet;Lnet/minecraft/network/PacketListener;" +
            "Lnet/minecraft/network/PacketProcessor;)V";

    @Inject(method = "handleInitializeBorder", at = @At(value = "INVOKE", target = THREAD_GUARD, shift = At.Shift.AFTER))
    private void legacyparkourcompat$captureInitialBorder(ClientboundInitializeBorderPacket packet, CallbackInfo ci) {
        MovementRuntime.clearClientBorderContraction();
        capture(this.level.getWorldBorder(), packet.getOldSize(), packet.getNewSize(), packet.getLerpTime());
    }

    @Inject(method = "handleSetBorderLerpSize", at = @At(value = "INVOKE", target = THREAD_GUARD, shift = At.Shift.AFTER))
    private void legacyparkourcompat$captureBorderLerp(ClientboundSetBorderLerpSizePacket packet, CallbackInfo ci) {
        capture(this.level.getWorldBorder(), packet.getOldSize(), packet.getNewSize(), packet.getLerpTime());
    }

    @WrapOperation(method = "handleSetBorderSize(Lnet/minecraft/network/protocol/game/ClientboundSetBorderSizePacket;)V",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/border/WorldBorder;setSize(D)V"))
    private void legacyparkourcompat$setStaticBorder(WorldBorder border, double size, Operation<Void> original) {
        ClientBorderLerpState.clear(border);
        MovementRuntime.setClientBorderSize(this.level, border, size, () -> original.call(border, size));
    }

    @Inject(method = "handleLogin", at = @At(value = "INVOKE", target = THREAD_GUARD, shift = At.Shift.AFTER))
    private void legacyparkourcompat$clearLoginBorderState(ClientboundLoginPacket packet, CallbackInfo ci) {
        ClientBorderLerpState.clearAll();
        MovementRuntime.clearClientBorderContraction();
    }

    @Inject(method = "handleRespawn", at = @At(value = "INVOKE", target = THREAD_GUARD, shift = At.Shift.AFTER))
    private void legacyparkourcompat$clearRespawnBorderState(ClientboundRespawnPacket packet, CallbackInfo ci) {
        ClientBorderLerpState.clearAll();
        MovementRuntime.clearClientBorderContraction();
    }

    private static void capture(WorldBorder border, double from, double to, long durationTicks) {
        ClientBorderLerpState.begin(border, from, to, durationTicks);
    }
}

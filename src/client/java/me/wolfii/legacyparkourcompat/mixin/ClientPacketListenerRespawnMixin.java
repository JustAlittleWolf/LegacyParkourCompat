package me.wolfii.legacyparkourcompat.mixin;

import me.wolfii.legacyparkourcompat.mechanic.MovementRuntime;
import me.wolfii.legacyparkourcompat.mechanic.hook.player.RespawnMovementAttributeHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.player.RespawnMovementFlagsHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.player.RespawnJumpStrengthBaseHook;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientCommonPacketListenerImpl;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.Connection;
import net.minecraft.client.multiplayer.CommonListenerCookie;
import net.minecraft.network.protocol.game.ClientboundRespawnPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.OptionalDouble;

/** Dispatches the profile-specific movement attribute response after client respawn copying. */
@Mixin(ClientPacketListener.class)
abstract class ClientPacketListenerRespawnMixin extends ClientCommonPacketListenerImpl {
    @Unique
    private LocalPlayer legacyparkourcompat$respawningPlayer;

    @Unique
    private byte legacyparkourcompat$oldSharedFlags;

    protected ClientPacketListenerRespawnMixin(Minecraft minecraft, Connection connection, CommonListenerCookie cookie) {
        super(minecraft, connection, cookie);
    }

    @Inject(
        method = "handleRespawn",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/network/protocol/PacketUtils;ensureRunningOnSameThread(Lnet/minecraft/network/protocol/Packet;Lnet/minecraft/network/PacketListener;Lnet/minecraft/network/PacketProcessor;)V",
            shift = At.Shift.AFTER
        )
    )
    private void legacyparkourcompat$captureOldMovementFlags(ClientboundRespawnPacket packet, CallbackInfo ci) {
        if (packet.shouldKeep((byte) 2)) {
            this.legacyparkourcompat$respawningPlayer = null;
            return;
        }
        LocalPlayer oldPlayer = this.minecraft.player;
        this.legacyparkourcompat$respawningPlayer = oldPlayer;
        if (oldPlayer != null) {
            this.legacyparkourcompat$oldSharedFlags = legacyparkourcompat$sharedFlags(oldPlayer);
        }
    }

    @Inject(method = "handleRespawn", at = @At("TAIL"))
    private void legacyparkourcompat$restoreOldMovementFlags(ClientboundRespawnPacket packet, CallbackInfo ci) {
        LocalPlayer oldPlayer = this.legacyparkourcompat$respawningPlayer;
        this.legacyparkourcompat$respawningPlayer = null;
        if (oldPlayer == null || packet.shouldKeep((byte) 2)) {
            return;
        }

        LocalPlayer replacementPlayer = this.minecraft.player;
        if (replacementPlayer == null || replacementPlayer == oldPlayer) {
            throw new IllegalStateException("Client respawn did not hand off to a replacement player");
        }
        MovementRuntime.find(RespawnMovementFlagsHook.class, replacementPlayer).ifPresent(hook -> {
            byte currentFlags = legacyparkourcompat$sharedFlags(replacementPlayer);
            byte restoredFlags = hook.restore(this.legacyparkourcompat$oldSharedFlags, currentFlags);
            if (restoredFlags != currentFlags) {
                EntityDataAccessor<Byte> accessor = EntitySharedFlagsAccessor.legacyparkourcompat$getSharedFlagsAccessor();
                SynchedEntityData entityData = replacementPlayer.getEntityData();
                entityData.set(accessor, restoredFlags);
            }
        });
    }

    @Unique
    private static byte legacyparkourcompat$sharedFlags(Entity entity) {
        return entity.getEntityData().get(EntitySharedFlagsAccessor.legacyparkourcompat$getSharedFlagsAccessor());
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
        if (packet.shouldKeep((byte) 1)) {
            return;
        }

        LocalPlayer replacementPlayer = this.minecraft.player;
        if (replacementPlayer == null) {
            throw new IllegalStateException("Client respawn completed base attribute copying without a replacement player");
        }
        MovementRuntime.find(RespawnJumpStrengthBaseHook.class, replacementPlayer)
            .map(RespawnJumpStrengthBaseHook::noKeepBaseOverride)
            .ifPresent(baseOverride -> {
                if (baseOverride.isEmpty()) {
                    return;
                }
                AttributeInstance jumpStrength = replacementPlayer.getAttribute(Attributes.JUMP_STRENGTH);
                if (jumpStrength == null) {
                    throw new IllegalStateException("Replacement player has no jump-strength attribute");
                }
                jumpStrength.setBaseValue(baseOverride.getAsDouble());
            });
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

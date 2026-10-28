package me.wolfii.legacyparkourcompat.change.v1_20_2;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.player.LevitationAmplifierHook;

/**
 * Restores the signed amplifier consumed by the 1.20.2–1.20.4 levitation movement response.
 *
 * <p>Official Mojang mappings, exact sources: Minecraft 1.20.2, 1.20.3 and 1.20.4
 * {@code net.minecraft.network.protocol.game.ClientboundUpdateMobEffectPacket(FriendlyByteBuf)},
 * {@code getEffectAmplifier()}, {@code net.minecraft.client.multiplayer.ClientPacketListener.handleUpdateMobEffect},
 * {@code net.minecraft.world.effect.MobEffectInstance.<init>}, and
 * {@code net.minecraft.world.entity.LivingEntity.travel}; Minecraft 1.20.5 and 1.20.6
 * {@code net.minecraft.network.protocol.game.ClientboundUpdateMobEffectPacket(RegistryFriendlyByteBuf)},
 * {@code getEffectAmplifier()},
 * {@code ClientPacketListener.handleUpdateMobEffect}, {@code MobEffectInstance.<init>}, and
 * {@code LivingEntity.travel}. The earlier packet reads and returns a signed byte which is
 * stored unchanged, while 1.20.5 transports a VarInt and clamps the instance amplifier to 0..255.
 * The movement read here reproduces only the earlier signed interpretation; packet and stored
 * client effect state stay vanilla.
 */
@MovementChange(emulates = ParkourVersion.V1_20_2)
public final class LevitationAmplifier implements LevitationAmplifierHook {
    @Override
    public int movementAmplifier(int receivedAmplifier) {
        return (byte) receivedAmplifier;
    }
}

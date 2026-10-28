package me.wolfii.legacyparkourcompat.change.v1_20_2;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.jump.JumpBoostAmplifierHook;

/**
 * Mojmap 1.20, 1.20.1, 1.20.2, 1.20.3, and 1.20.4:
 * ClientboundUpdateMobEffectPacket#getEffectAmplifier exposes the decoded byte
 * as signed, ClientPacketListener#handleUpdateMobEffect stores that value through
 * the MobEffectInstance constructor, and LivingEntity#getJumpBoostPower consumes it through
 * MobEffectInstance#getAmplifier. Mojmap 1.20.5 and 1.20.6 change
 * ClientboundUpdateMobEffectPacket#getEffectAmplifier to int and clamp in
 * the MobEffectInstance constructor, so this interpretation ends at that boundary. The
 * native float expression and its operation order remain in
 * LivingEntity#getJumpBoostPower.
 */
@MovementChange(emulates = ParkourVersion.V1_20_2)
public final class SignedByteJumpBoostAmplifier implements JumpBoostAmplifierHook {
    @Override
    public int interpretAmplifier(int nativeAmplifier) {
        return (byte) nativeAmplifier;
    }
}

package me.wolfii.legacyparkourcompat.change.v1_19_4;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.jump.JumpBoostAmplifierHook;

/**
 * Native lower resolution fence outside the accepted 1.20+ source range.
 * Mojmap 1.20, 1.20.1, and 1.20.2 use the signed packet byte through
 * ClientboundUpdateMobEffectPacket#getEffectAmplifier and
 * ClientPacketListener#handleUpdateMobEffect before LivingEntity#getJumpBoostPower.
 */
@MovementChange(emulates = ParkourVersion.V1_19_4)
public final class NativeJumpBoostAmplifier implements JumpBoostAmplifierHook {
    @Override
    public int interpretAmplifier(int nativeAmplifier) {
        return nativeAmplifier;
    }
}

package me.wolfii.legacyparkourcompat.change.v1_20_5;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.jump.JumpBoostAmplifierHook;

/**
 * Mojmap 1.20.5 and 1.20.6 use an int from
 * ClientboundUpdateMobEffectPacket#getEffectAmplifier, then clamp it in the
 * MobEffectInstance constructor before LivingEntity#getJumpBoostPower reads it.
 */
@MovementChange(emulates = ParkourVersion.V1_20_5)
public final class NativeJumpBoostAmplifier implements JumpBoostAmplifierHook {
    @Override
    public int interpretAmplifier(int nativeAmplifier) {
        return nativeAmplifier;
    }
}

package me.wolfii.legacyparkourcompat.mechanic.hook.jump;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;

/** Resolves the amplifier value consumed by LivingEntity#getJumpBoostPower. */
@MechanicType("jump.boost_amplifier")
public interface JumpBoostAmplifierHook extends VersionedMechanic {
    int interpretAmplifier(int nativeAmplifier);
}

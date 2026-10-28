package me.wolfii.legacyparkourcompat.change.v1_20;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.jump.JumpBoostAmplifierHook;

/** Native lower fence for this bounded finding; V1_20 is outside its accepted range. */
@MovementChange(emulates = ParkourVersion.V1_20)
public final class NativeJumpBoostAmplifier implements JumpBoostAmplifierHook {
    @Override
    public int interpretAmplifier(int nativeAmplifier) {
        return nativeAmplifier;
    }
}

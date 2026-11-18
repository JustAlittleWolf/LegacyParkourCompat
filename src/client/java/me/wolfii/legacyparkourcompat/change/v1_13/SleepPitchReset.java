package me.wolfii.legacyparkourcompat.change.v1_13;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.state.SleepPitchResetHook;

/**
 * Ornithe Feather 1.8 through 1.13.2 LivingEntity#tick has no sleeping
 * pitch reset. Ornithe Feather 1.14 LivingEntity#tick calls mobTick, which
 * performs travel, then writes pitch = 0.0F when sleeping. In 26.2
 * unobfuscated, LivingEntity#tick makes the corresponding setXRot call.
 */
@MovementChange(emulates = ParkourVersion.V1_13_1)
public final class SleepPitchReset implements SleepPitchResetHook {
    @Override
    public boolean resetsPitch() {
        return false;
    }
}

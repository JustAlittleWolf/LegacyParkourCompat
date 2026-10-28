package me.wolfii.legacyparkourcompat.change.v1_13;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.state.SleepPitchResetHook;

/**
 * Ornithe Feather 1.13.2 LivingEntity.tick does not reset sleeping pitch
 * after mobTick/travel. Ornithe Feather 1.14 adds if (isSleeping()) pitch=0F;
 * 1.14.4 retains it, as does 26.2 unobfuscated LivingEntity.tick via setXRot.
 * Suppress only that local post-travel write; sleep entry and received state
 * remain vanilla. Earlier profile applicability remains unbound.
 */
@MovementChange(emulates = ParkourVersion.V1_13_1)
public final class SleepPitchReset implements SleepPitchResetHook {
    @Override
    public boolean resetsPitch(ParkourVersion selected) {
        return selected.olderThan(ParkourVersion.V1_13);
    }
}

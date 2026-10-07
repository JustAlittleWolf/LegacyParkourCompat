package me.wolfii.legacyparkourcompat.change.v1_8;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.FastRegenerationBehavior;
import me.wolfii.legacyparkourcompat.mechanic.hook.RegenerationExhaustionBehavior;

@MovementChange(emulates = ParkourVersion.V1_8)
public final class NaturalRegeneration implements FastRegenerationBehavior, RegenerationExhaustionBehavior {
    @Override
    public boolean isHurtForFastRegeneration(boolean vanilla) {
        return false;
    }

    @Override
    public float slowRegenerationExhaustion(float vanilla) {
        return 3.0F;
    }
}

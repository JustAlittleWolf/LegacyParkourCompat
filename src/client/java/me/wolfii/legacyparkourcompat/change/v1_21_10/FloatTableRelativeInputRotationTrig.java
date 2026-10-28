package me.wolfii.legacyparkourcompat.change.v1_21_10;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.change.common.LegacyTrig;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.relativeinputrotation.RelativeInputRotationTrigHook;

/**
 * 1.21.10 (Mojang official mappings), Mth.java lines 32-36 and 48-54 use
 * float table indexing, called by Entity#getInputVector (Entity.java lines
 * 1595-1604) with float radians. 1.21.11 (Mojang official mappings), Mth.java
 * lines 33-37 and 50-56 use double indexing, and Entity#getInputVector
 * (Entity.java lines 1613-1622) widens its float radians at the call. Reuse the
 * shared float table for the older operation; yaw -45F selects cosine index
 * 8192 here but index 8191 in 1.21.11.
 */
@MovementChange(emulates = ParkourVersion.V1_21_10)
public final class FloatTableRelativeInputRotationTrig implements RelativeInputRotationTrigHook {
    @Override
    public float sin(float radians) {
        return LegacyTrig.sin(radians);
    }

    @Override
    public float cos(float radians) {
        return LegacyTrig.cos(radians);
    }
}

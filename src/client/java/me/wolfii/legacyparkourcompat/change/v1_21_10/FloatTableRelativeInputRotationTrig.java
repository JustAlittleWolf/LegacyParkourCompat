package me.wolfii.legacyparkourcompat.change.v1_21_10;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.change.common.LegacyTrig;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.relativeinputrotation.RelativeInputRotationTrigHook;

/**
 * In 1.16.2 (Mojmap), Mth.java lines 12-15 and 25-30 define the float-table
 * lookup used by Entity#getInputVector (Entity.java lines 1080-1089). The same
 * float overload and call are present in 1.21.10 (Mth.java lines 32-36 and
 * 48-53; Entity.java lines 1595-1604), with 1.21.9 independently checked to
 * use the same lookup (Mth.java lines 32-35 and 48-53; Entity.java lines
 * 1590-1598). In 1.21.11 (Mojmap), Mth.java lines 34-38 and 50-56 use double
 * indexing, and Entity#getInputVector (Entity.java lines 1613-1622) widens its
 * float radians at the call. Reuse the shared float table for the older
 * operation; yaw -45F selects cosine index 8192 here but index 8191 in 1.21.11.
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

package me.wolfii.legacyparkourcompat.change.v1_16;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.relativeinputrotation.RelativeInputRotationTrigHook;
import net.minecraft.util.Mth;

/**
 * Native barrier for profiles before the accepted float-table range. The
 * Mojmap Mth.sin(float)/cos(float) lookup and Entity#getInputVector call are
 * present in the compared 1.16.2 sources (Mth.java lines 12-15 and 25-30;
 * Entity.java lines 1080-1089) and 1.21.10 sources (Mth.java lines 32-36 and
 * 48-53; Entity.java lines 1595-1604). In 1.21.11, Mth changes to the
 * double-indexed lookup (Mth.java lines 34-38 and 50-56), while
 * Entity#getInputVector keeps the float angle expression and widens it at the
 * call (Entity.java lines 1613-1622). This barrier leaves profiles before
 * 1.16.2 on the native lookup; V1_16 covers 1.16 and 1.16.1, and the next
 * profile group V1_16_2 begins at the first source-confirmed release.
 */
@MovementChange(emulates = ParkourVersion.V1_16)
public final class NativeRelativeInputRotationTrig implements RelativeInputRotationTrigHook {
    @Override
    public float sin(float radians) {
        return Mth.sin((double) radians);
    }

    @Override
    public float cos(float radians) {
        return Mth.cos((double) radians);
    }
}

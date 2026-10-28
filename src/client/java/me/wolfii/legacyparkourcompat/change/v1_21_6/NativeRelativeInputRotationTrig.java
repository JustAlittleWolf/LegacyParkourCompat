package me.wolfii.legacyparkourcompat.change.v1_21_6;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.relativeinputrotation.RelativeInputRotationTrigHook;
import net.minecraft.util.Mth;

/**
 * Native barrier for profiles before the accepted float-table boundary. The
 * exact 1.21.9 and 1.21.10 Mojang-mapped Mth sources use float arguments and
 * table indexing (1.21.9 Mth.java lines 32-36 and 48-54; 1.21.10 Mth.java
 * lines 32-36 and 48-54), while 1.21.11 changes these to double (Mth.java
 * lines 33-37 and 50-56). Their Entity#getInputVector calls are at Entity.java
 * lines 1590-1599, 1595-1604 and 1613-1622, respectively. This explicit earlier
 * boundary prevents the 1.21.10 finding from propagating into older profiles
 * whose relative-input trig has not been source-confirmed here.
 */
@MovementChange(emulates = ParkourVersion.V1_21_6)
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

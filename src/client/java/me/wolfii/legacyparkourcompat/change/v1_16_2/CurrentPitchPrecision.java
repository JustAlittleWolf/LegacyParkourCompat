package me.wolfii.legacyparkourcompat.change.v1_16_2;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.entity.turn.CurrentPitchHook;
import net.minecraft.util.Mth;

/**
 * Minecraft 1.13 (Ornithe Feather) Entity.updateLocalPlayerCamera(double,double)
 * multiplies the cursor pitch input by double 0.15, adds it to the current
 * float pitch in double precision, narrows once, and clamps. The same current
 * pitch sequence remains in 1.16.5 (Mojmap) Entity.turn(double,double),
 * Entity.java:340-352. At 1.17 (Mojmap) Entity.turn(double,double),
 * Entity.java:377-388, the input is narrowed before multiplication by 0.15F
 * and the first setter changes the precision. The selected V1_16_2 group ends
 * at 1.16.5; later profiles retain their separately resolved behavior.
 */
@MovementChange(emulates = ParkourVersion.V1_16_2)
public final class CurrentPitchPrecision implements CurrentPitchHook {
    @Override
    public float transformFirstPitchArgument(float currentPitch, double pitchInput, float vanillaArgument) {
        if (!Float.isFinite(currentPitch) || !Double.isFinite(pitchInput)) {
            return vanillaArgument;
        }
        double pitchDelta = pitchInput * 0.15D;
        float historicalPitch = (float) (currentPitch + pitchDelta);
        return Mth.clamp(historicalPitch, -90.0F, 90.0F);
    }
}

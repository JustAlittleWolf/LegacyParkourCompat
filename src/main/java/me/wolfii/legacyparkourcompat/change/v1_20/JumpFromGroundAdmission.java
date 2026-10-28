package me.wolfii.legacyparkourcompat.change.v1_20;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.player.JumpFromGroundAdmissionHook;

/**
 * Mojmap LivingEntity#jumpFromGround in 1.20.2, 1.20.3 and 1.20.4 writes the
 * computed jump power without an admission guard, while 1.20.5 and 1.20.6 add
 * {@code !(jumpPower <= 1.0E-5F)}. This V1_20 lower fence keeps the target's
 * native threshold outside the accepted 1.20.2–1.20.4 interval.
 */
@MovementChange(emulates = ParkourVersion.V1_20)
public final class JumpFromGroundAdmission implements JumpFromGroundAdmissionHook {
    @Override
    public float admissionThreshold(float nativeThreshold) {
        return nativeThreshold;
    }
}

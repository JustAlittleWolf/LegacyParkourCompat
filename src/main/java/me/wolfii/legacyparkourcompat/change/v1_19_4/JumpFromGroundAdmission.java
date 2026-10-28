package me.wolfii.legacyparkourcompat.change.v1_19_4;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.player.JumpFromGroundAdmissionHook;

/**
 * Mojmap LivingEntity#jumpFromGround in 1.20, 1.20.1, 1.20.2, 1.20.3, and
 * 1.20.4 writes computed jump power without an admission guard. Mojmap 1.20.5
 * and 1.20.6 add {@code !(jumpPower <= 1.0E-5F)}. This V1_19_4 identity fence
 * preserves profiles before the accepted 1.20–1.20.4 unconditional interval.
 */
@MovementChange(emulates = ParkourVersion.V1_19_4)
public final class JumpFromGroundAdmission implements JumpFromGroundAdmissionHook {
    @Override
    public float admissionThreshold(float nativeThreshold) {
        return nativeThreshold;
    }
}

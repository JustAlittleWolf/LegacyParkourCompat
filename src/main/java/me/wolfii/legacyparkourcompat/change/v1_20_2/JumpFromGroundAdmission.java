package me.wolfii.legacyparkourcompat.change.v1_20_2;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.player.JumpFromGroundAdmissionHook;

/**
 * Mojmap 1.20.2, 1.20.3 and 1.20.4 LivingEntity.jumpFromGround write computed
 * jump power unconditionally. Mojmap 1.20.5 and 1.20.6 add
 * {@code !(jumpPower <= 1.0E-5F)} around the response. NaN makes that comparison
 * false for every float, including NaN, so the negated predicate admits all
 * powers as the earlier body did. The native power and response body stay intact.
 */
@MovementChange(emulates = ParkourVersion.V1_20_2)
public final class JumpFromGroundAdmission implements JumpFromGroundAdmissionHook {
    @Override
    public float admissionThreshold(float nativeThreshold) {
        return Float.NaN;
    }
}

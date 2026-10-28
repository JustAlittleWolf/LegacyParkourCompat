package me.wolfii.legacyparkourcompat.change.v1_21_10;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.change.common.LegacyTrig;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.VanillaFn;
import me.wolfii.legacyparkourcompat.mechanic.hook.player.SprintJumpTrigLookupHook;

/**
 * Restores the float-indexed table used for the sprint impulse in 1.21.9 and
 * 1.21.10. These versions compute a float yaw angle and use float Mth lookup;
 * the current target uses double lookup indexing after that angle is widened.
 *
 * <p>Minecraft 1.21.10 (Mojmap), LivingEntity#jumpFromGround and Mth#sin(float)/
 * cos(float), use the float table and float scale/index arithmetic. The same
 * jump-site call and lookup are present in 1.21.9 (Mojmap),
 * LivingEntity#jumpFromGround and Mth#sin(float)/cos(float). In 1.21.11
 * (Mojmap), Mth#sin(double)/cos(double) switch to double scale and long
 * truncation; the same lookup is present in 26.2 (unobfuscated), Mth and
 * LivingEntity#jumpFromGround. The LegacyTrig helper shares the product's
 * existing float table without changing the separate impulse multiplication.
 */
@MovementChange(emulates = ParkourVersion.V1_21_10)
public final class FloatTableSprintJumpTrigLookup implements SprintJumpTrigLookupHook {
    @Override
    public float lookup(double radians, boolean cosine, VanillaFn<Float> vanilla) {
        float angle = (float) radians;
        return cosine ? LegacyTrig.cos(angle) : LegacyTrig.sin(angle);
    }
}

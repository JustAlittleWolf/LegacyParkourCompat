package me.wolfii.legacyparkourcompat.change.v1_21_10;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.change.common.LegacyTrig;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.VanillaFn;
import me.wolfii.legacyparkourcompat.mechanic.hook.player.SprintJumpTrigLookupHook;

/**
 * Restores the float-indexed table used for the sprint impulse from 1.20.5
 * through 1.21.10. These versions compute a float yaw angle and use float Mth
 * lookup; the current target uses double lookup indexing after that angle is
 * widened.
 *
 * <p>The sprint gate, float yaw expression, and trig calls are text-identical
 * in the Mojmap sources for 1.20.5, 1.20.6, and 1.21 through 1.21.10:
 * LivingEntity#jumpFromGround uses Mth#sin(float)/cos(float), and Mth initializes
 * its float table with the same scale and index arithmetic. In 1.21.11 (Mojmap),
 * Mth#sin(double)/cos(double) switch to double scale and long truncation; the
 * same lookup is present in 26.2 (unobfuscated), Mth and
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

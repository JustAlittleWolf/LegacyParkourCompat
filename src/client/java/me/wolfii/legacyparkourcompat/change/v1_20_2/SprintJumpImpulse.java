package me.wolfii.legacyparkourcompat.change.v1_20_2;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.change.common.LegacyTrig;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.jump.SprintJumpImpulseHook;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;

/**
 * Historical sprint-jump yaw and impulse arithmetic. Minecraft 1.19.4, 1.20.2,
 * 1.20.3 and 1.20.4 (Mojmap), net.minecraft.world.entity.LivingEntity#jumpFromGround,
 * multiply the float sine/cosine terms by 0.2F before widening each rounded
 * product to double. Minecraft 1.20.5 and 1.20.6 (Mojmap), same class/member,
 * instead multiply by double 0.2; Minecraft 26.2 (unobfuscated), same
 * class/member, retains those double products.
 *
 * Minecraft 1.20.4 (Mojmap), net.minecraft.util.Mth#SIN initialization, #sin
 * and #cos, supplies the float table/index arithmetic preserved by LegacyTrig.
 * Minecraft 26.2 (unobfuscated), same class/members, uses different double
 * table/index arithmetic, so its trig results cannot supply the old products.
 * The float yaw expression is preserved in all the compared jumpFromGround
 * members. This reuses the existing historical helper and earlier coverage;
 * V1_20_2 includes 1.20.3 and 1.20.4, immediately before the 1.20.5 cutover.
 *
 * Only the impulse argument is replaced after native sprint/jump eligibility.
 * The newer low-jump-power gate is independent of this precision change;
 * ordinary default jump power passes it. Source correspondence does not
 * establish tick-level runtime parity.
 */
@MovementChange(emulates = ParkourVersion.V1_20_2)
public final class SprintJumpImpulse implements SprintJumpImpulseHook {
    @Override
    public Vec3 impulse(LivingEntity entity, Vec3 vanilla) {
        float angle = entity.getYRot() * (float)(Math.PI / 180.0);
        return new Vec3(-LegacyTrig.sin(angle) * 0.2F, 0.0, LegacyTrig.cos(angle) * 0.2F);
    }
}

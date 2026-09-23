package me.wolfii.legacyparkourcompat.change.v1_14;

import me.wolfii.legacyparkourcompat.mechanic.VanillaCall;
import me.wolfii.legacyparkourcompat.mechanic.hook.JumpBehavior;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;

/** Shared 1.14–1.16 float jump assembly and direct Y assignment. */
public abstract class FloatJumpThrough116 implements JumpBehavior {
    protected abstract float basePower(LivingEntity entity);

    @Override
    public final float jumpPower(LivingEntity entity, float vanilla) {
        return basePower(entity);
    }

    @Override
    public final void jumpFromGround(LivingEntity entity, VanillaCall vanilla) {
        float power = basePower(entity);
        if (entity.hasEffect(MobEffects.JUMP_BOOST)) {
            power += 0.1F * (entity.getEffect(MobEffects.JUMP_BOOST).getAmplifier() + 1);
        }

        Vec3 movement = entity.getDeltaMovement();
        entity.setDeltaMovement(movement.x, power, movement.z);
        if (entity.isSprinting()) {
            float angle = entity.getYRot() * (float) (Math.PI / 180.0);
            entity.setDeltaMovement(entity.getDeltaMovement().add(
                -Mth.sin(angle) * 0.2F, 0.0, Mth.cos(angle) * 0.2F
            ));
        }
        entity.needsSync = true;
    }
}

package me.wolfii.legacyparkourcompat.change.v1_17;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.VanillaCall;
import me.wolfii.legacyparkourcompat.mechanic.hook.JumpBehavior;
import me.wolfii.legacyparkourcompat.mixin.access.EntityInvoker;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;

/** 1.17–1.19 add Jump Boost to double Y and retain the float sprint impulse. */
@MovementChange(emulates = ParkourVersion.V1_19_4)
public final class DoubleBoostJump implements JumpBehavior {
    @Override
    public float jumpPower(LivingEntity entity, float vanilla) {
        return 0.42F * ((EntityInvoker) entity).lpc$getBlockJumpFactor();
    }

    @Override
    public void jumpFromGround(LivingEntity entity, VanillaCall vanilla) {
        double power = jumpPower(entity, 0.0F);
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

package me.wolfii.legacyparkourcompat.change.v1_8;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.VanillaCall;
import me.wolfii.legacyparkourcompat.mechanic.hook.JumpBehavior;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.phys.Vec3;

/** Restores the direct vertical jump assignment and float sprint impulse through 1.13. */
@MovementChange(emulates = ParkourVersion.V1_13)
public final class LegacySprintJump implements JumpBehavior {
    @Override
    public void jumpFromGround(LivingEntity entity, VanillaCall vanilla) {
        double jumpVelocity = 0.42F;
        if (entity.hasEffect(MobEffects.JUMP_BOOST)) {
            jumpVelocity += (entity.getEffect(MobEffects.JUMP_BOOST).getAmplifier() + 1) * 0.1F;
        }
        Vec3 movement = entity.getDeltaMovement();
        if (!entity.isSprinting()) {
            entity.setDeltaMovement(movement.x, jumpVelocity, movement.z);
            entity.needsSync = true;
            return;
        }
        float radians = entity.getYRot() * (float) (Math.PI / 180.0);
        entity.setDeltaMovement(
            movement.x - LegacyGroundAcceleration.sin(radians) * 0.2F,
            jumpVelocity,
            movement.z + LegacyGroundAcceleration.cos(radians) * 0.2F
        );
        entity.needsSync = true;
    }

    @Override
    public float jumpPower(LivingEntity entity, float vanilla) {
        return 0.42F;
    }
}

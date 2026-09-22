package me.wolfii.legacyparkourcompat.change.v1_20;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.VanillaCall;
import me.wolfii.legacyparkourcompat.mechanic.hook.JumpBehavior;
import me.wolfii.legacyparkourcompat.mixin.access.EntityInvoker;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;

/** 1.20.1–1.20.4 assemble float jump power and overwrite Y unconditionally. */
@MovementChange(emulates = ParkourVersion.V1_20)
public final class FloatJumpThrough1204 implements JumpBehavior {
    @Override
    public float jumpPower(LivingEntity entity, float vanilla) {
        return 0.42F * ((EntityInvoker) entity).lpc$getBlockJumpFactor() + entity.getJumpBoostPower();
    }

    @Override
    public void jumpFromGround(LivingEntity entity, VanillaCall vanilla) {
        Vec3 movement = entity.getDeltaMovement();
        entity.setDeltaMovement(movement.x, jumpPower(entity, 0.0F), movement.z);
        if (entity.isSprinting()) {
            float angle = entity.getYRot() * (float) (Math.PI / 180.0);
            entity.setDeltaMovement(entity.getDeltaMovement().add(
                -Mth.sin(angle) * 0.2F, 0.0, Mth.cos(angle) * 0.2F
            ));
        }
        entity.needsSync = true;
    }
}

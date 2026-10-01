package me.wolfii.legacyparkourcompat.change;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.VanillaCall;
import me.wolfii.legacyparkourcompat.mechanic.hook.JumpBehavior;
import me.wolfii.legacyparkourcompat.mixin.EntityJumpFactorAccess;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;

@MovementChange(emulates = ParkourVersion.V1_19_4)
public final class JumpBoostPrecision_1_19_4 implements JumpBehavior {
    @Override
    public void jumpFromGround(LivingEntity entity, VanillaCall vanilla) {
        float blockJumpFactor = ((EntityJumpFactorAccess)(Object)entity).legacyparkourcompat$getBlockJumpFactor();
        double jumpPower = 0.42F * blockJumpFactor + entity.getJumpBoostPower();
        Vec3 movement = entity.getDeltaMovement();
        entity.setDeltaMovement(movement.x, jumpPower, movement.z);
        if (entity.isSprinting()) {
            float angle = entity.getYRot() * (float)(Math.PI / 180.0);
            entity.setDeltaMovement(entity.getDeltaMovement().add(-Mth.sin(angle) * 0.2F, 0.0, Mth.cos(angle) * 0.2F));
        }
        entity.needsSync = true;
    }
}

package me.wolfii.legacyparkourcompat.change.v1_21;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.VanillaCall;
import me.wolfii.legacyparkourcompat.mechanic.hook.JumpBehavior;
import me.wolfii.legacyparkourcompat.mixin.access.LivingEntityInvoker;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;

/** Through 1.21.1, jump power replaced Y even if current Y was higher. */
@MovementChange(emulates = ParkourVersion.V1_21)
public final class DirectJumpThrough1211 implements JumpBehavior {
    @Override
    public void jumpFromGround(LivingEntity entity, VanillaCall vanilla) {
        float power = ((LivingEntityInvoker) entity).lpc$getJumpPower();
        if (power <= 1.0E-5F) {
            return;
        }
        Vec3 movement = entity.getDeltaMovement();
        entity.setDeltaMovement(movement.x, power, movement.z);
        if (entity.isSprinting()) {
            float angle = entity.getYRot() * (float) (Math.PI / 180.0);
            entity.setDeltaMovement(entity.getDeltaMovement().add(
                -Mth.sin(angle) * 0.2, 0.0, Mth.cos(angle) * 0.2
            ));
        }
        entity.needsSync = true;
    }
}

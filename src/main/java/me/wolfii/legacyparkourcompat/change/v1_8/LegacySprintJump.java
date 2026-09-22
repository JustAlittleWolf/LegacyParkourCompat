package me.wolfii.legacyparkourcompat.change.v1_8;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.VanillaCall;
import me.wolfii.legacyparkourcompat.mechanic.hook.JumpBehavior;
import me.wolfii.legacyparkourcompat.mixin.access.LivingEntityInvoker;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;

/** Restores the float-indexed sprint-jump impulse used through 1.13. */
@MovementChange(emulates = ParkourVersion.V1_13)
public final class LegacySprintJump implements JumpBehavior {
    @Override
    public void jumpFromGround(LivingEntity entity, VanillaCall vanilla) {
        if (!entity.isSprinting()) {
            vanilla.run();
            return;
        }
        float jumpPower = ((LivingEntityInvoker) entity).lpc$getJumpPower();
        Vec3 movement = entity.getDeltaMovement();
        float radians = entity.getYRot() * (float) (Math.PI / 180.0);
        entity.setDeltaMovement(
            movement.x - LegacyGroundAcceleration.sin(radians) * 0.2F,
            jumpPower,
            movement.z + LegacyGroundAcceleration.cos(radians) * 0.2F
        );
        entity.needsSync = true;
    }
}

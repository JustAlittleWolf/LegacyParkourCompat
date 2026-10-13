package me.wolfii.legacyparkourcompat.change.v1_20_2;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.change.common.LegacyTrig;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.jump.SprintJumpImpulseHook;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;

/**
 * Retains float yaw/trig multiplication by 0.2F before widening the impulse into
 * Vec3. The grouped 1.20.2/1.20.3/1.20.4 profile and earlier profiles share this
 * operation; the next group starts at the changed 1.20.5 double multiplication.
 *
 * Source evidence (paths are relative to decompiled_minecraft/):
 * 1.19.4/mojmap/net/minecraft/world/entity/LivingEntity.java:
 *   jumpFromGround uses the same float yaw and 0.2F impulse.
 * 1.20.5/mojmap/net/minecraft/world/entity/LivingEntity.java:
 *   jumpFromGround multiplies widened trig values by double 0.2.
 * 1.20.4/mojmap/net/minecraft/util/Mth.java:
 *   sin.
 * 1.20.4/mojmap/net/minecraft/world/entity/Entity.java:
 *   addDeltaMovement.
 * 1.20.4/mojmap/net/minecraft/world/entity/LivingEntity.java:
 *   jumpFromGround.
 *   getJumpPower.
 * 1.20.4/mojmap/net/minecraft/world/phys/Vec3.java:
 *   add.
 * 1.20.6/mojmap/net/minecraft/util/Mth.java:
 *   sin.
 * 1.20.6/mojmap/net/minecraft/world/entity/Entity.java:
 *   addDeltaMovement.
 * 1.20.6/mojmap/net/minecraft/world/entity/LivingEntity.java:
 *   jumpFromGround.
 * 1.20.6/mojmap/net/minecraft/world/phys/Vec3.java:
 *   add.
 * Source-content correspondence and compilation do not establish tick-level runtime parity.
 */
@MovementChange(emulates = ParkourVersion.V1_20_2)
public final class SprintJumpImpulse implements SprintJumpImpulseHook {
    @Override
    public Vec3 impulse(LivingEntity entity, Vec3 vanilla) {
        float angle = entity.getYRot() * (float)(Math.PI / 180.0);
        return new Vec3(-LegacyTrig.sin(angle) * 0.2F, 0.0, LegacyTrig.cos(angle) * 0.2F);
    }
}

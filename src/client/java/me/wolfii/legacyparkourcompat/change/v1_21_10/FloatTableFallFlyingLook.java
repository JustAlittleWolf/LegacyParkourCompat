package me.wolfii.legacyparkourcompat.change.v1_21_10;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.change.common.LegacyTrig;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.travel.FallFlyingLookHook;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.phys.Vec3;

/** Restores the classic Elytra float lookup through 1.21.10. */
@MovementChange(emulates = ParkourVersion.V1_21_10)
public final class FloatTableFallFlyingLook implements FallFlyingLookHook {
    private static final float DEG_TO_RAD = (float)(Math.PI / 180.0);

    /**
     * The 1.13 Entity.calculateViewVector(float,float) source computes float
     * radians as pitch * (float)(PI / 180) and -yaw * (float)(PI / 180), then
     * calls cos(yaw), sin(yaw), cos(pitch), sin(pitch) and returns
     * (sinYaw * cosPitch, -sinPitch, cosYaw * cosPitch). The accepted source
     * comparisons confirm that formula and float lookup in 1.13 through 1.21.10.
     * The canonical caller is the fall-flying travel/update path's getLookAngle;
     * the lift-force sample also reads that same vector, so both use this hook.
     * In 1.21.11 the source changes the lookup API and index arithmetic to double;
     * this 1.21.10 provider is consequently ineligible for that selected profile.
     * The 1.11.2 head-yaw and 1.12.2 older-form providers remain version-local.
     *
     * Source paths are relative to decompiled_minecraft/: 1.13/ornithe-feather/
     * net/minecraft/entity/Entity.java (getRotationVector); 1.13.2 uses the same
     * Entity, LivingEntity and MathHelper source bytes; 1.14.4/mojmap and each
     * checked Mojmap release through 1.21.10 retain the same expression and
     * float lookup. 1.21.11/mojmap/net/minecraft/util/Mth.java has the double
     * lookup boundary.
     */
    @Override
    public Vec3 lookVector(LivingEntity entity, ParkourVersion selected, Vec3 vanilla) {
        if (!(entity instanceof Player player)
            || !player.getItemBySlot(EquipmentSlot.CHEST).is(Items.ELYTRA)) {
            return vanilla;
        }

        float pitchRadians = entity.getXRot() * DEG_TO_RAD;
        float yawRadians = -entity.getYRot() * DEG_TO_RAD;
        float cosineYaw = LegacyTrig.cos(yawRadians);
        float sineYaw = LegacyTrig.sin(yawRadians);
        float cosinePitch = LegacyTrig.cos(pitchRadians);
        float sinePitch = LegacyTrig.sin(pitchRadians);
        return new Vec3(sineYaw * cosinePitch, -sinePitch, cosineYaw * cosinePitch);
    }
}

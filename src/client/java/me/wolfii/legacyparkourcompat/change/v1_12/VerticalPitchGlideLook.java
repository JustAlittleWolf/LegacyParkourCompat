package me.wolfii.legacyparkourcompat.change.v1_12;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.travel.FallFlyingLookHook;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.phys.Vec3;

/**
 * Restores the historical nonzero Elytra look direction when modern lookup
 * rounding erases both horizontal components for a near-vertical negative pitch.
 *
 * <p>For selected 1.12–1.12.2, the accepted 1.12-to-1.12.1 and
 * 1.12.1-to-1.12.2 comparisons plus the 1.12.2-to-26.2 endpoint establish
 * that the old float lookup retains a nonzero horizontal factor across the
 * 720 finite negative pitch floats where 26.2's double lookup produces exact
 * zero horizontal components. In 1.12.2,
 * Ornithe Feather {@code Entity.getRotationVector(float,float)} and
 * {@code Entity.getLookVector()} use {@code MathHelper.sin(float)} and
 * {@code MathHelper.cos(float)}; {@code LivingEntity.moveRelative(float,float,float)}
 * then consumes the positive horizontal magnitude during active dry Elytra
 * movement. In 26.2, unobfuscated {@code Entity.getLookAngle()} calls
 * {@code Entity.calculateViewVector(float,float)}, whose result is consumed by
 * {@code LivingEntity.updateFallFlyingMovement(Vec3)}. The provider registered
 * at 1.11.2 supplies the existing resolved operation for earlier selected
 * profiles and is unchanged here; this 1.12 provider handles 1.12–1.12.2.
 *
 * <p>The exact 1.9-to-1.12.2 alias comparisons are archived alongside those
 * proofs. Earlier selected profiles already resolve to the existing
 * {@code V1_11_2} computed-vector provider; this change does not modify that
 * provider or assert additional near-vertical behavior for those aliases.
 * Source paths are relative to {@code decompiled_minecraft/}:
 * {@code 1.12.2/ornithe-feather/net/minecraft/entity/Entity.java},
 * {@code net/minecraft/util/math/MathHelper.java}, and
 * {@code net/minecraft/entity/living/LivingEntity.java}; and
 * {@code 26.2/unobfuscated/net/minecraft/world/entity/Entity.java} and
 * {@code net/minecraft/world/entity/LivingEntity.java}. Exact alias reports,
 * endpoint verdict and provenance resolution are archived under
 * {@code development/research/source-evidence/20261118/block-c-near-vertical-look/}.
 * The adjacent 1.12.2-to-1.13 denominator witness is archived under
 * {@code development/research/source-evidence/20261118/block-c-middle-late-player-operation-closures/}.
 * These sources establish the bounded operation, not full movement or runtime parity.
 */
@MovementChange(emulates = ParkourVersion.V1_12)
public final class VerticalPitchGlideLook implements FallFlyingLookHook {
    private static final float[] LEGACY_SINE_TABLE = new float[65536];

    static {
        for (int i = 0; i < LEGACY_SINE_TABLE.length; i++) {
            LEGACY_SINE_TABLE[i] = (float)Math.sin(i * Math.PI * 2.0 / 65536.0);
        }
    }

    @Override
    public Vec3 lookVector(LivingEntity entity, ParkourVersion selected, Vec3 vanilla) {
        if (selected.olderThan(ParkourVersion.V1_9)) {
            return vanilla;
        }

        float pitch = entity.getXRot();
        float yaw = entity.getYRot();

        // Keep the original exact-vertical behavior for every existing caller.
        if (pitch == -90.0F) {
            return historicalLookVector(pitch, yaw);
        }

        if (!(entity instanceof Player player)
            || !player.level().isClientSide()
            || Minecraft.getInstance().player != player
            || !player.isFallFlying()
            || player.isInWater()
            || player.isInLava()
            || !player.getItemBySlot(EquipmentSlot.CHEST).is(Items.ELYTRA)
            || !Float.isFinite(pitch)
            || pitch < -90.0F
            || pitch >= 0.0F
            || !Float.isFinite(yaw)
            || vanilla == null
            || !Double.isFinite(vanilla.x)
            || !Double.isFinite(vanilla.y)
            || !Double.isFinite(vanilla.z)
            || vanilla.x != 0.0
            || vanilla.z != 0.0) {
            return vanilla;
        }

        return historicalLookVector(pitch, yaw);
    }

    private static Vec3 historicalLookVector(float pitch, float yaw) {
        float f = legacyCos(-yaw * (float)(Math.PI / 180.0) - (float)Math.PI);
        float g = legacySin(-yaw * (float)(Math.PI / 180.0) - (float)Math.PI);
        float h = -legacyCos(-pitch * (float)(Math.PI / 180.0));
        float i = legacySin(-pitch * (float)(Math.PI / 180.0));
        return new Vec3(g * h, i, f * h);
    }

    private static float legacySin(float value) {
        return LEGACY_SINE_TABLE[(int)(value * 10430.378F) & 65535];
    }

    private static float legacyCos(float value) {
        return LEGACY_SINE_TABLE[(int)(value * 10430.378F + 16384.0F) & 65535];
    }
}

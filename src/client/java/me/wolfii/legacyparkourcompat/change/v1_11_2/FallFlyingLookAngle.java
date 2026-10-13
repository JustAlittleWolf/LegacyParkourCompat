package me.wolfii.legacyparkourcompat.change.v1_11_2;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.travel.FallFlyingLookHook;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;

/**
 * Source evidence (paths are relative to decompiled_minecraft/):
 * 1.11.2/ornithe-feather/net/minecraft/entity/Entity.java:
 *   public Vec3d getLookVector().
 * 1.11.2/ornithe-feather/net/minecraft/entity/living/LivingEntity.java:
 *   public Vec3d getLookVector().
 *   public Vec3d getRotationVector(float tickDelta).
 * 1.12.2/ornithe-feather/net/minecraft/entity/Entity.java:
 *   public Vec3d getLookVector().
 * 1.12.2/ornithe-feather/net/minecraft/entity/living/LivingEntity.java:
 *   public Vec3d getRotationVector(float tickDelta).
 * No exact independently accepted source binding is established for this operation.
 * Remaining source/applicability gates:
 *   Later applicability gate: exact introduction of legacy-run-MOVE-001-fall-flying-look-yaw is unknown within (1.11.2, 1.12.2]
 *   Exact independent source verdict/snapshot identity for MOVE-001-fall-flying-look-yaw
 * Source-content correspondence and compilation do not establish tick-level runtime parity.
 */
@MovementChange(emulates = ParkourVersion.V1_11_2)
public final class FallFlyingLookAngle implements FallFlyingLookHook {
    // Preserve the 1.11.2 MathHelper lookup-table indexing; current Mth trig uses different double math.
    private static final float[] SINE_TABLE = createSineTable();

    @Override
    public Vec3 lookVector(LivingEntity entity, ParkourVersion selected, Vec3 vanilla) {
        if (selected.olderThan(ParkourVersion.V1_9)) {
            return vanilla;
        }

        float scale = (float)(Math.PI / 180.0);
        float yaw = -entity.getYHeadRot() * scale - (float)Math.PI;
        float pitch = -entity.getXRot() * scale;
        float horizontalCos = legacyCos(yaw);
        float horizontalSin = legacySin(yaw);
        float verticalCos = -legacyCos(pitch);
        float verticalSin = legacySin(pitch);
        return new Vec3(horizontalSin * verticalCos, verticalSin, horizontalCos * verticalCos);
    }

    private static float[] createSineTable() {
        float[] table = new float[65536];
        for (int i = 0; i < table.length; i++) {
            table[i] = (float)Math.sin(i * Math.PI * 2.0 / 65536.0);
        }
        return table;
    }

    private static float legacySin(float value) {
        return SINE_TABLE[(int)(value * 10430.378F) & 65535];
    }

    private static float legacyCos(float value) {
        return SINE_TABLE[(int)(value * 10430.378F + 16384.0F) & 65535];
    }
}

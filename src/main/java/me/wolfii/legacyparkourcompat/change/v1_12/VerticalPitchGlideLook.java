package me.wolfii.legacyparkourcompat.change.v1_12;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.travel.FallFlyingLookHook;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;

/**
 * Lookup-table rounding of the Elytra look vector at a vertical pitch.
 *
 * Source evidence (paths are relative to decompiled_minecraft/):
 * 1.12.2/ornithe-feather/net/minecraft/entity/Entity.java:
 *   public Vec3d getRotationVector(float tickDelta) {; lines 1229-1237.
 *   protected final Vec3d getRotationVector(float pitch, float yaw) {; lines 1239-1245.
 *   Source SHA-256: 80f091bf32166c88cf8bbd31caf72d84fa16224410733c7d2a0f00563f294a0a.
 * 1.12.2/ornithe-feather/net/minecraft/entity/living/LivingEntity.java:
 *   public void moveRelative(float forwards, float sideways, float upwards) {; lines 1425-1599.
 *   Source SHA-256: 190e9ac551538e015d9e4d6c42856e5ba32b593131cf6d93895e7b29533f1ee6.
 * 1.13.2/ornithe-feather/net/minecraft/entity/Entity.java:
 *   public final Vec3d getRotationVector(float tickDelta) {; lines 1239-1241.
 *   protected final Vec3d getRotationVector(float pitch, float yaw) {; lines 1251-1259.
 *   Source SHA-256: 1d6ec8b80f74635401745c2c027bf36555c85348ca5693764f2668363b17d269.
 * 1.13.2/ornithe-feather/net/minecraft/entity/living/LivingEntity.java:
 *   public void moveRelative(float forwards, float sideways, float upwards) {; lines 1478-1669.
 *   Source SHA-256: bb691358c9a43c9f46e85575bf4d0a4ad671d0eb912502acc3a6a3f625e42f1c.
 * No exact independently accepted source binding is established for this operation.
 * Remaining source/applicability gates:
 *   Later applicability gate: exact introduction of campaign-F-ELYTRA-LOOK-GUARD is unknown within (1.12.2, 1.13.2]
 *   Exact independent source verdict/snapshot identity for F-ELYTRA-LOOK-GUARD
 *   Exact independent source verdict/snapshot identity for glide-vertical-gaze
 * Source-content correspondence and compilation do not establish tick-level runtime parity.
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

        if (entity.getXRot() != -90.0F) {
            return vanilla;
        }

        float pitch = entity.getXRot();
        float yaw = entity.getYRot();
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

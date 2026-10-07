package me.wolfii.legacyparkourcompat.change.v1_11_2;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.FallFlyingLookBehavior;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;

@MovementChange(emulates = ParkourVersion.V1_11_2)
public final class FallFlyingLookAngle implements FallFlyingLookBehavior {
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

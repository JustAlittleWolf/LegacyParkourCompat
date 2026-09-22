package me.wolfii.legacyparkourcompat.change.v1_13;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.change.HistoricalTrig;
import me.wolfii.legacyparkourcompat.mechanic.MovementChangeRegistry;
import me.wolfii.legacyparkourcompat.mechanic.hook.InputVectorBehavior;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;

/**
 * Through 1.13, input is normalized, scaled, and rotated as floats before
 * adding it to double velocity. The 1.8 yaw expression has a separate float
 * rounding step that changed in 1.9.
 */
public final class LegacyInputVector implements InputVectorBehavior {
    private final boolean oldYawConversion;

    private LegacyInputVector(boolean oldYawConversion) {
        this.oldYawConversion = oldYawConversion;
    }

    public static void register(MovementChangeRegistry registry) {
        registry.register(InputVectorBehavior.class, ParkourVersion.V1_13, new LegacyInputVector(false));
        registry.register(InputVectorBehavior.class, ParkourVersion.V1_8, new LegacyInputVector(true));
    }

    @Override
    public Vec3 inputVector(Vec3 input, float speed, float yaw) {
        float sideways = (float) input.x;
        float vertical = (float) input.y;
        float forward = (float) input.z;
        float magnitude = sideways * sideways + vertical * vertical + forward * forward;
        if (magnitude < 1.0E-4F) {
            return Vec3.ZERO;
        }
        magnitude = Mth.sqrt(magnitude);
        if (magnitude < 1.0F) {
            magnitude = 1.0F;
        }
        magnitude = speed / magnitude;
        sideways *= magnitude;
        vertical *= magnitude;
        forward *= magnitude;
        float angle = oldYawConversion ? yaw * (float) Math.PI / 180.0F : yaw * (float) (Math.PI / 180.0);
        // 1.8–1.13 use float indexing into the 65536-entry sine table.
        float sin = HistoricalTrig.sin(angle);
        float cos = HistoricalTrig.cos(angle);
        return new Vec3(sideways * cos - forward * sin, vertical, forward * cos + sideways * sin);
    }
}

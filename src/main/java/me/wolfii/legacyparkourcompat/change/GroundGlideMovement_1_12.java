package me.wolfii.legacyparkourcompat.change;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.FallFlyingLookBehavior;
import me.wolfii.legacyparkourcompat.mechanic.hook.GroundSpeedBehavior;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;

/** One-ULP ground acceleration and the vertical-pitch Elytra lookup quirk. */
@MovementChange(emulates = ParkourVersion.V1_12)
public final class GroundGlideMovement_1_12 implements GroundSpeedBehavior, FallFlyingLookBehavior {
    private static final float[] LEGACY_SINE_TABLE = new float[65536];

    static {
        for (int i = 0; i < LEGACY_SINE_TABLE.length; i++) {
            LEGACY_SINE_TABLE[i] = (float)Math.sin(i * Math.PI * 2.0 / 65536.0);
        }
    }

    @Override
    public float speed(LivingEntity entity, float blockFriction, float vanilla) {
        if (!entity.onGround()) {
            return vanilla;
        }
        float friction = blockFriction * 0.91F;
        float acceleration = 0.16277136F / (friction * friction * friction);
        return entity.getSpeed() * acceleration;
    }

    @Override
    public Vec3 lookVector(LivingEntity entity, Vec3 vanilla) {
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

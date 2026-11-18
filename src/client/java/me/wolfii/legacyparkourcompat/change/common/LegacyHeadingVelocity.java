package me.wolfii.legacyparkourcompat.change.common;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

/** Two-axis float heading arithmetic shared by the exact 1.8 release providers. */
public final class LegacyHeadingVelocity {
    private static final float[] SINE_TABLE = new float[65536];

    static {
        for (int i = 0; i < SINE_TABLE.length; i++) {
            SINE_TABLE[i] = (float) Math.sin(i * Math.PI * 2.0 / 65536.0);
        }
    }

    private LegacyHeadingVelocity() {}

    public static void apply(Player player, float speed, Vec3 input) {
        float sideways = (float) input.x;
        float forwards = (float) input.z;
        float magnitude = sideways * sideways + forwards * forwards;
        if (magnitude < 1.0E-4F) {
            return;
        }
        magnitude = (float) Math.sqrt(magnitude);
        if (magnitude < 1.0F) {
            magnitude = 1.0F;
        }
        magnitude = speed / magnitude;
        sideways *= magnitude;
        forwards *= magnitude;
        float angle = player.getYRot() * (float) Math.PI / 180.0F;
        float sin = SINE_TABLE[(int) (angle * 10430.378F) & 65535];
        float cos = SINE_TABLE[(int) (angle * 10430.378F + 16384.0F) & 65535];
        Vec3 movement = player.getDeltaMovement();
        player.setDeltaMovement(
            movement.x + (sideways * cos - forwards * sin),
            movement.y,
            movement.z + (forwards * cos + sideways * sin)
        );
    }
}

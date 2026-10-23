package me.wolfii.legacyparkourcompat.change.v1_8_9;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.OrdinaryPlayerContext;
import me.wolfii.legacyparkourcompat.mechanic.hook.movement.HeadingVelocityHook;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

/**
 * Minecraft 1.8.9, Ornithe Feather Gen2 build 2,
 * net.minecraft.entity.Entity.updateVelocity(float,float,float), and
 * net.minecraft.util.math.MathHelper.sin(float), cos(float), sqrt(float) and
 * its sine-table initializer.
 * Minecraft 1.9.4, Ornithe Feather Gen2 build 2, Entity.updateVelocity changes the yaw
 * multiplication to float-cast (PI / 180). The remaining float normalization,
 * scalar multiplication, trig lookup and X/Z operation order are retained.
 * The 1.8.9 exact-profile boundary keeps modern sleeping, swimming,
 * fall-flying, spin, passenger and three-axis movement on their native path.
 */
@MovementChange(emulates = ParkourVersion.V1_8_9)
public final class HeadingVelocity implements HeadingVelocityHook {
    private static final float[] SINE_TABLE = new float[65536];

    static {
        for (int i = 0; i < SINE_TABLE.length; i++) {
            SINE_TABLE[i] = (float) Math.sin(i * Math.PI * 2.0 / 65536.0);
        }
    }

    @Override
    public boolean apply(Player player, float speed, Vec3 input) {
        if (!OrdinaryPlayerContext.matches(player) || input.y != 0.0) {
            return false;
        }
        float sideways = (float) input.x;
        float forwards = (float) input.z;
        float magnitude = sideways * sideways + forwards * forwards;
        if (magnitude < 1.0E-4F) {
            return true;
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
        return true;
    }
}

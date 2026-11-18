package me.wolfii.legacyparkourcompat.change.v1_21_10;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.change.common.LegacyTrig;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.travel.SwimmingPitchHook;
import net.minecraft.world.entity.player.Player;

/**
 * Reproduces the float-table look-Y consumed by the active-swimming
 * correction in {@code Player.travel}. Across the independently accepted
 * adjacent comparisons from 1.17.1 through 1.21.10, this ordinary local
 * player's gate, correction order, and float-backed view-vector Y remain the
 * same. The earlier source trace in Minecraft 1.13 Ornithe Feather Gen2
 * ({@code net.ornithemc:feather-gen2:1.13+build.2}) follows
 * {@code PlayerEntity#moveRelative} through the old rotation-vector and
 * {@code MathHelper#sin(float)} lookup. The 1.14.4 and 1.17.1 Mojmap
 * {@code net.minecraft.world.entity.player.Player#travel(Vec3)} paths show
 * this correction consuming {@code Entity#getLookAngle().y} before inherited
 * travel, using the float pitch-radian / sine-table value widened to double.
 * In 1.21.10 Mojmap,
 * {@code net.minecraft.world.entity.player.Player#travel(Vec3)} reads
 * {@code net.minecraft.world.entity.Entity#getLookAngle().y};
 * {@code Entity#calculateViewVector(float,float)} rounds pitch radians to
 * float and supplies Y using {@code net.minecraft.util.Mth#sin(float)}. That
 * method indexes the 65,536-entry float table with the float product and
 * truncating int cast.
 *
 * <p>The exact 1.21.10→1.21.11 Mojmap boundary leaves the
 * {@code Player#travel(Vec3)} correction itself unchanged, but changes
 * {@code net.minecraft.util.Mth}: 1.21.11 initializes the table with the
 * double divisor {@code 10430.378350470453} and indexes through the double
 * product, long truncation, and long mask in {@code Mth#sin(double)}. The
 * float radians passed from {@code Entity#calculateViewVector(float,float)}
 * are widened for that call. The accepted lawful pitch witness selects
 * adjacent table entries across this boundary. Exact comparisons then retain
 * the new lookup procedure through 26.2. This
 * provider is annotated at {@code V1_21_10}; selected 1.21.11 and later
 * profiles therefore keep native behavior. The existing travel redirect
 * still replaces only look-Y, preserves native X/Z, and retains the original
 * gates and operation order.
 */
@MovementChange(emulates = ParkourVersion.V1_21_10)
public final class SwimmingPitch implements SwimmingPitchHook {
    @Override
    public double lookY(Player player, double vanilla) {
        float pitch = player.getXRot() * (float)(Math.PI / 180.0);
        return -LegacyTrig.sin(pitch);
    }
}

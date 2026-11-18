package me.wolfii.legacyparkourcompat.change.v1_17_1;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.change.common.LegacyTrig;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.travel.SwimmingPitchHook;
import net.minecraft.world.entity.player.Player;

/**
 * Reproduces the float-table look-Y used by the active-swimming correction in
 * {@code Player.travel}. In 1.14.4 Mojmap,
 * {@code net.minecraft.world.entity.player.Player#travel(Vec3)} reads
 * {@code Entity#getLookAngle().y}; {@code Entity#getLookAngle()} calls
 * {@code Entity#calculateViewVector(float,float)}, which forms float pitch
 * radians and obtains Y from {@code Mth#sin(float)} before the correction
 * widens it to double. In 1.17.1 Mojmap, the same
 * {@code Player#travel(Vec3)} correction retains its swim/non-passenger gate,
 * float-backed look-Y, threshold, ordered conditions, and Y update before
 * {@code super.travel}. The exact 1.14.4→1.15, 1.15→1.15.1,
 * 1.15.1→1.15.2, 1.15.2→1.16, 1.16→1.16.1, 1.16.1→1.16.2,
 * 1.16.2→1.16.3, 1.16.3→1.16.4, 1.16.4→1.16.5, 1.16.5→1.17, and
 * 1.17→1.17.1 comparisons accept this operation's continuity, including
 * documented source-identity corrections.
 *
 * <p>In 26.2 (unobfuscated), {@code Player#travel(Vec3)} obtains the same
 * look-Y through {@code Entity#calculateViewVector} and {@code Mth#sin(double)};
 * the accepted 1.14.0 endpoint witness at pitch {@code 84.375F} produces a
 * different table index. This provider is selected only through 1.17.1;
 * later profiles retain native fallback. The existing travel redirect
 * replaces only look-Y, preserves native X/Z, and keeps the original gates and
 * operation order.
 */
@MovementChange(emulates = ParkourVersion.V1_17_1)
public final class SwimmingPitch implements SwimmingPitchHook {
    @Override
    public double lookY(Player player, double vanilla) {
        float pitch = player.getXRot() * (float)(Math.PI / 180.0);
        return -LegacyTrig.sin(pitch);
    }
}

package me.wolfii.legacyparkourcompat.change.v1_14_2;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.change.common.LegacyTrig;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.travel.SwimmingPitchHook;
import net.minecraft.world.entity.player.Player;

/**
 * Reproduces the float-table look-Y used by the active-swimming vertical
 * adjustment from 1.13 through 1.14.4. In 1.13 (Ornithe Feather Gen2),
 * {@code PlayerEntity#moveRelative} reads {@code Entity#getLookVector().y};
 * {@code Entity#getRotationVector(float,float)} forms float pitch radians and
 * {@code MathHelper#sin(float)} supplies the indexed float Y value. The exact
 * 1.13.2–1.14.0 comparison keeps this operation unchanged. In 1.14.3 (Yarn),
 * {@code PlayerEntity#travel} reads {@code Entity#getRotationVector().y}; in
 * 1.14.4 (Mojmap), {@code Player#travel(Vec3)} reads
 * {@code Entity#getLookAngle().y}. Both retain the float sine-table result
 * before the swim adjustment widens it to double. 1.14.0–1.14.4 edge audits
 * bind the unchanged operation across every supported profile alias.
 *
 * <p>In 26.2 (unobfuscated), {@code Player#travel(Vec3)} keeps the same-shaped
 * vertical writer but obtains Y through {@code Entity#calculateViewVector} and
 * {@code Mth#sin(double)}; the accepted 1.14.0 endpoint witness at pitch
 * {@code 84.375F} produces a different table index. This change replaces only
 * the look-Y value at the existing travel read. The native Y argument is
 * otherwise returned unchanged, and the existing redirect preserves native
 * X/Z and the travel branch's original gates and operation order.
 */
@MovementChange(emulates = ParkourVersion.V1_14_2)
public final class SwimmingPitch implements SwimmingPitchHook {
    @Override
    public double lookY(Player player, double vanilla) {
        float pitch = player.getXRot() * (float)(Math.PI / 180.0);
        return -LegacyTrig.sin(pitch);
    }
}

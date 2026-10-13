package me.wolfii.legacyparkourcompat.change.v1_13;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.change.common.LegacyTrig;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.travel.SwimmingPitchHook;
import net.minecraft.world.entity.player.Player;

/**
 * Float-quantized look-vector Y used by the 1.13 swimming pitch correction.
 *
 * Source evidence (paths are relative to decompiled_minecraft/):
 * 1.12.2/ornithe-feather/net/minecraft/entity/living/player/PlayerEntity.java:
 *   public void moveRelative(float forwards, float sideways, float upwards).
 * 1.13.2/ornithe-feather/net/minecraft/entity/living/player/PlayerEntity.java:
 *   public void moveRelative(float forwards, float sideways, float upwards).
 * No exact independently accepted source binding is established for this operation.
 * Remaining source/applicability gates:
 *   Later applicability gate: exact introduction of campaign-F-SWIM-LOOK is unknown within (1.12.2, 1.13.2]
 *   Exact independent source verdict/snapshot identity for F-SWIM-LOOK
 *   Exact independent source verdict/snapshot identity for swim-look-vertical-control
 * Source-content correspondence and compilation do not establish tick-level runtime parity.
 */
@MovementChange(emulates = ParkourVersion.V1_13)
public final class SwimmingPitch implements SwimmingPitchHook {
    @Override
    public double lookY(Player player, double vanilla) {
        float pitch = player.getXRot() * (float)(Math.PI / 180.0);
        return -LegacyTrig.sin(pitch);
    }
}

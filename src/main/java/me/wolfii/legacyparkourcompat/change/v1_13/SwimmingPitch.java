package me.wolfii.legacyparkourcompat.change.v1_13;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.change.common.LegacyTrig;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.SwimmingPitchBehavior;
import net.minecraft.world.entity.player.Player;

/** Float-quantized look-vector Y used by the 1.13 swimming pitch correction. */
@MovementChange(emulates = ParkourVersion.V1_13)
public final class SwimmingPitch implements SwimmingPitchBehavior {
    @Override
    public double lookY(Player player, double vanilla) {
        float pitch = player.getXRot() * (float)(Math.PI / 180.0);
        return -LegacyTrig.sin(pitch);
    }
}

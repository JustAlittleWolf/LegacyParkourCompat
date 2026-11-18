package me.wolfii.legacyparkourcompat.change.v1_12;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.travel.SwimmingPitchHook;
import net.minecraft.world.entity.player.Player;

/**
 * Native fence: 1.12.2 (Feather Gen2) {@code PlayerEntity#moveRelative} has
 * no swimming pitch adjustment; it first appears in the 1.13 counterpart.
 */
@MovementChange(emulates = ParkourVersion.V1_12)
public final class NativeSwimmingPitch implements SwimmingPitchHook {
    @Override
    public double lookY(Player player, double vanilla) {
        return vanilla;
    }
}

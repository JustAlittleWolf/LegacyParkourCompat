package me.wolfii.legacyparkourcompat.change;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.VanillaCall;
import me.wolfii.legacyparkourcompat.mechanic.hook.SwimmingBehavior;
import net.minecraft.world.entity.player.Player;

/**
 * Sprint-swimming (1.13+) did not exist. Keep the swimming flag off.
 */
@MovementChange(emulates = ParkourVersion.V1_12)
public final class NoSwimming_1_12 implements SwimmingBehavior {
    @Override
    public void updateSwimming(Player player, VanillaCall vanilla) {
        player.setSwimming(false);
    }
}

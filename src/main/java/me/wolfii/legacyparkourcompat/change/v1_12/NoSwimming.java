package me.wolfii.legacyparkourcompat.change.v1_12;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.VanillaCall;
import me.wolfii.legacyparkourcompat.mechanic.hook.SwimmingBehavior;
import net.minecraft.world.entity.player.Player;

/**
 * Sprint-swimming (1.13+) did not exist. Keep the swimming flag off.
 */
// TODO: swimming is not restored perfectly, sprinting underwater should be impossible? Or not as fast at least
@MovementChange(emulates = ParkourVersion.V1_12)
public final class NoSwimming implements SwimmingBehavior {
    @Override
    public void updateSwimming(Player player, VanillaCall vanilla) {
        player.setSwimming(false);
    }
}

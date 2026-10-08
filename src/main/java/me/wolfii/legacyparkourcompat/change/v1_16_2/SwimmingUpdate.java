package me.wolfii.legacyparkourcompat.change.v1_16_2;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.SwimmingUpdateBehavior;
import net.minecraft.world.entity.player.Player;

/** Preserves the 1.16.5 swimming entry predicate without the later block-fluid gate. */
@MovementChange(emulates = ParkourVersion.V1_16_2)
public final class SwimmingUpdate implements SwimmingUpdateBehavior {
    @Override
    public boolean isSwimmingAfterUpdate(Player player, ParkourVersion selected) {
        if (selected.olderThan(ParkourVersion.V1_13)) {
            return false;
        }

        if (player.isSwimming()) {
            return player.isSprinting() && player.isInWater() && !player.isPassenger();
        }
        return player.isSprinting() && player.isUnderWater() && !player.isPassenger();
    }
}

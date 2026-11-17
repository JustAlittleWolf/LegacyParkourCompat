package me.wolfii.legacyparkourcompat.change.v1_19;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.input.SprintDoubleTapGroundHook;
import net.minecraft.world.entity.player.Player;

/**
 * Mojmap 1.18.2 and 1.19.2 net.minecraft.client.player.LocalPlayer.aiStep use
 * onGround() || isUnderWater() before the double-tap timer and sprint write;
 * 1.19.3 net.minecraft.client.player.LocalPlayer.aiStep adds the mounted-vehicle
 * ground term. The inspected ground-gate coverage starts at 1.18.2, so earlier
 * selections retain the current native gate.
 */
@MovementChange(emulates = ParkourVersion.V1_19)
public final class DoubleTapSprintGround implements SprintDoubleTapGroundHook {
    @Override
    public boolean admits(ParkourVersion selected, Player player) {
        return selected.olderThan(ParkourVersion.V1_18_2)
            || player.onGround() || player.isUnderWater();
    }
}

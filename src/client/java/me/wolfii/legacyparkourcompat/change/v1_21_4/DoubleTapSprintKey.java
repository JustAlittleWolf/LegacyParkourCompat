package me.wolfii.legacyparkourcompat.change.v1_21_4;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.input.DoubleTapSprintKeyHook;

/**
 * 1.21.4 Mojmap LocalPlayer.aiStep arms the double-tap timer only when it has
 * expired and the sprint key is up. 1.21.5 Mojmap LocalPlayer.aiStep tests the
 * timer alone and handles the sprint key in the following branch.
 *
 * The older check is also present in 1.12.2 and 1.13 Feather
 * LocalClientPlayerEntity.mobTick. In 1.13 it can start on an eligible grounded
 * forward press in shallow water even though the subsequent key branch rejects
 * shallow water. The timer keeps its post-decrement value on this route.
 */
@MovementChange(emulates = ParkourVersion.V1_21_4)
public final class DoubleTapSprintKey implements DoubleTapSprintKeyHook {
    @Override
    public boolean startsWithExpiredTimer(boolean sprintKeyDown) {
        return sprintKeyDown;
    }
}

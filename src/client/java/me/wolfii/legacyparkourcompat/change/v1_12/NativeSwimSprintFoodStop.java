package me.wolfii.legacyparkourcompat.change.v1_12;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.sprint.SwimSprintFoodStopHook;
import net.minecraft.client.player.LocalPlayer;

/**
 * Native fence before swimming sprint retention existed. The 1.12.2 client
 * has no swimming sprint-retention branch; the operation first appears in
 * 1.13. Preserve native stop eligibility at the pre-feature boundary.
 */
@MovementChange(emulates = ParkourVersion.V1_12)
public final class NativeSwimSprintFoodStop implements SwimSprintFoodStopHook {
    @Override
    public boolean canSprintForSwimStop(LocalPlayer player, boolean vanillaCanSprint) {
        return vanillaCanSprint;
    }
}

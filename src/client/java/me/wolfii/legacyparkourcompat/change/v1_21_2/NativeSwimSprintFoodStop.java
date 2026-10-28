package me.wolfii.legacyparkourcompat.change.v1_21_2;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.sprint.SwimSprintFoodStopHook;
import net.minecraft.client.player.LocalPlayer;

/**
 * Identity anchor that confines the currently accepted swim-food emulation to
 * the 1.21.4 profile while earlier stop-condition boundaries remain unclosed.
 */
@MovementChange(emulates = ParkourVersion.V1_21_2)
public final class NativeSwimSprintFoodStop implements SwimSprintFoodStopHook {
    @Override
    public boolean canSprintForSwimStop(LocalPlayer player, boolean vanillaCanSprint) {
        return vanillaCanSprint;
    }
}

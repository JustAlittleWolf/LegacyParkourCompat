package me.wolfii.legacyparkourcompat.mechanic.hook.sprint;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.client.player.LocalPlayer;

/** Resolves the insufficient-food stop reason for an already-swimming sprint. */
@MechanicType("player.sprint.swim_food_stop")
public interface SwimSprintFoodStopHook extends VersionedMechanic {
    /** Returns the native eligibility result with only the historical food exemption restored. */
    boolean canSprintForSwimStop(LocalPlayer player, boolean vanillaCanSprint);
}

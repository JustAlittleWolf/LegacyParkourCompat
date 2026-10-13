package me.wolfii.legacyparkourcompat.mechanic.hook.sprint;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.client.player.LocalPlayer;

/** The fall-flight sprint response immediately after tutorial input. */
@MechanicType("player.sprint.early_fall_flying_stop")
public interface EarlyFallFlyingSprintStopHook extends VersionedMechanic {
    void afterTutorialInput(LocalPlayer player);
}

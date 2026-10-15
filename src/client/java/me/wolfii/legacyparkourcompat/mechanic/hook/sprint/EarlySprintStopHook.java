package me.wolfii.legacyparkourcompat.mechanic.hook.sprint;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.client.player.LocalPlayer;

/** Early active-sprint stop immediately after tutorial input. */
@MechanicType("player.sprint.early_stop")
public interface EarlySprintStopHook extends VersionedMechanic {
    void afterTutorialInput(LocalPlayer player);
}

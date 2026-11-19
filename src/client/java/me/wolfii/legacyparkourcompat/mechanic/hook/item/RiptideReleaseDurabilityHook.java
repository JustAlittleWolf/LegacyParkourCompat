package me.wolfii.legacyparkourcompat.mechanic.hook.item;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;

@MechanicType("player.riptide.release_durability")
public interface RiptideReleaseDurabilityHook extends VersionedMechanic {
    boolean rejectsRelease(float strength, boolean vanillaResult);
}

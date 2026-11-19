package me.wolfii.legacyparkourcompat.mechanic.hook.item;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;

@MechanicType("player.riptide.release_trigonometry")
public interface RiptideReleaseTrigHook extends VersionedMechanic {
    float sin(float radians);

    float cos(float radians);
}

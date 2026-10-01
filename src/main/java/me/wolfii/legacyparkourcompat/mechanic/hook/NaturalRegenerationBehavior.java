package me.wolfii.legacyparkourcompat.mechanic.hook;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;

/** Player movement's hunger gates affected by natural regeneration. */
@MechanicType("player.natural_regeneration")
public interface NaturalRegenerationBehavior extends VersionedMechanic {
    boolean isHurtForFastRegeneration(boolean vanilla);

    float slowRegenerationExhaustion(float vanilla);
}

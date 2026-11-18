package me.wolfii.legacyparkourcompat.mechanic.hook.entity.turn;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.world.entity.Entity;

/** Resolves the historical transform of the first pitch setter in ordinary Entity.turn. */
@MechanicType("entity.turn.pitch")
public interface EntityTurnPitchHook extends VersionedMechanic {
    float transformFirstPitchArgument(Entity entity, float vanillaArgument);
}

package me.wolfii.legacyparkourcompat.mechanic.hook;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.world.entity.Entity;

/**
 * Historical step height ({@code Entity#maxUpStep}).
 */
@MechanicType("player.step_height")
public interface StepHeightBehavior extends VersionedMechanic {
    float stepHeight(Entity entity, float vanilla);
}

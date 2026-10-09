package me.wolfii.legacyparkourcompat.mechanic.hook;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.material.FluidState;

/** Selects fluid states considered by an entity's fluid-interaction scan. */
@FunctionalInterface
@MechanicType("entity.fluid.interaction_fluid")
public interface FluidInteractionFluidBehavior extends VersionedMechanic {
    FluidState fluidState(Entity entity, FluidState vanilla);
}

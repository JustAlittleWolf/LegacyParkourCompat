package me.wolfii.legacyparkourcompat.mechanic.hook;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.AABB;
import org.jspecify.annotations.Nullable;

/** Selects the box used to query an entity's fluid interactions. */
@FunctionalInterface
@MechanicType("entity.fluid.interaction_box")
public interface FluidInteractionBoxBehavior extends VersionedMechanic {
    @Nullable AABB interactionBox(Entity entity, @Nullable AABB vanilla);
}

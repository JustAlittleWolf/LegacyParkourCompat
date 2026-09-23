package me.wolfii.legacyparkourcompat.mechanic.hook;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VanillaCall;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;

/** Updates position and bounding box from the resolved move. */
@MechanicType("entity.movement_position")
public interface MovementPositionBehavior extends VersionedMechanic {
    void updatePosition(Entity entity, Vec3 resolvedMovement, VanillaCall vanilla);
}

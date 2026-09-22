package me.wolfii.legacyparkourcompat.mechanic.hook;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.world.phys.Vec3;

/** The movement-input normalization and yaw rotation used by Entity.moveRelative. */
@MechanicType("player.travel.input_vector")
public interface InputVectorBehavior extends VersionedMechanic {
    Vec3 inputVector(Vec3 input, float speed, float yaw);
}

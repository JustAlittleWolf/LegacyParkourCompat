package me.wolfii.legacyparkourcompat.mechanic.hook.travel;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VanillaFn;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.world.phys.Vec3;

/** Converts relative input to the acceleration added by Entity.moveRelative. */
@MechanicType("player.travel.input_vector")
public interface InputVectorHook extends VersionedMechanic {
    Vec3 inputVector(Vec3 input, float speed, float yaw, VanillaFn<Vec3> vanilla);
}

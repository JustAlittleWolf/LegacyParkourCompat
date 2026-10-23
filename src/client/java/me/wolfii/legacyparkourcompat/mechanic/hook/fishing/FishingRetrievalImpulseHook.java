package me.wolfii.legacyparkourcompat.mechanic.hook.fishing;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.world.phys.Vec3;

@MechanicType("fishing.retrieval_impulse")
public interface FishingRetrievalImpulseHook extends VersionedMechanic {
    default Vec3 impulse(double dx, double dy, double dz, Vec3 nativeImpulse) {
        return nativeImpulse;
    }
}

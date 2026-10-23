package me.wolfii.legacyparkourcompat.mechanic.hook.travel;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;

/** Resolves the post-move vertical impulse for ordinary air travel. */
@MechanicType("travel.climb_impulse")
public interface ClimbImpulseHook extends VersionedMechanic {
    /**
     * Resolved trapdoor branch for the active profile, used after the Change confirms its legacy state gate.
     */
    Vec3 afterMove(LivingEntity entity, Vec3 nativeMovement, boolean trapdoorClimbing);
}

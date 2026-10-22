package me.wolfii.legacyparkourcompat.mechanic.hook.travel;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;

/** Resolves the post-move vertical impulse for ordinary air travel. */
@MechanicType
public interface ClimbImpulseHook extends VersionedMechanic {
    Vec3 afterMove(LivingEntity entity);
}

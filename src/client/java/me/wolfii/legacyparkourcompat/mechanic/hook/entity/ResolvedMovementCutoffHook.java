package me.wolfii.legacyparkourcompat.mechanic.hook.entity;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.world.entity.MoverType;

@MechanicType("entity.resolved-movement-cutoff")
public interface ResolvedMovementCutoffHook extends VersionedMechanic {
    double squaredCutoff(MoverType moverType, double vanillaCutoff);
}

package me.wolfii.legacyparkourcompat.mechanic.hook;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

/** Chooses the historical player position when leaving a vehicle. */
@FunctionalInterface
@MechanicType("player.dismount_position")
public interface DismountPositionBehavior extends VersionedMechanic {
    /** Returns {@code true} when this hook positioned the passenger. */
    boolean dismount(LivingEntity passenger, Entity vehicle);
}

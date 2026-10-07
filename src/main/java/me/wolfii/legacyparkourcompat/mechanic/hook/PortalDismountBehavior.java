package me.wolfii.legacyparkourcompat.mechanic.hook;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

/** Repositions a player dismounting a vehicle inside a portal. */
@FunctionalInterface
@me.wolfii.legacyparkourcompat.mechanic.MechanicType("player.dismount.portal")
public interface PortalDismountBehavior extends VersionedMechanic {
    boolean dismountFromPortal(LivingEntity passenger, Entity vehicle, ParkourVersion selected);
}

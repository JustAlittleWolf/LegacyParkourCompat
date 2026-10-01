package me.wolfii.legacyparkourcompat.mechanic.hook;

import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

@me.wolfii.legacyparkourcompat.mechanic.MechanicType("player.dismount.portal")
public interface PortalDismountBehavior extends VersionedMechanic {
    void dismountFromPortal(LivingEntity passenger, Entity vehicle);
}

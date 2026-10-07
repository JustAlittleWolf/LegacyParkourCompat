package me.wolfii.legacyparkourcompat.mechanic.hook;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.world.entity.LivingEntity;

/** Historical powder-snow state used by the post-move climb boost. */
@FunctionalInterface
@MechanicType("player.climb.powder_snow")
public interface PowderSnowClimbBehavior extends VersionedMechanic {
    boolean wasInPowderSnowForBoost(LivingEntity entity, ParkourVersion selected, boolean vanilla);
}

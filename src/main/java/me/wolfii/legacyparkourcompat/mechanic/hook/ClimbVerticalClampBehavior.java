package me.wolfii.legacyparkourcompat.mechanic.hook;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;

/** Historical vertical precision after ladder and vine velocity clamping. */
@MechanicType("player.climb.vertical_clamp")
public interface ClimbVerticalClampBehavior extends VersionedMechanic {
    Vec3 clampVertical(LivingEntity entity, Vec3 movement);
}

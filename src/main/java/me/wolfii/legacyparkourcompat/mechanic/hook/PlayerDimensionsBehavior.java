package me.wolfii.legacyparkourcompat.mechanic.hook;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;

/**
 * Historical pose bounding-box size ({@code LivingEntity#getDimensions(Pose)}).
 * 1.9–1.13 sneaking is 1.65m; 1.14 lowered crouch to 1.5m.
 */
@MechanicType("player.dimensions")
public interface PlayerDimensionsBehavior extends VersionedMechanic {
    EntityDimensions dimensions(Player player, Pose pose, EntityDimensions vanilla);
}

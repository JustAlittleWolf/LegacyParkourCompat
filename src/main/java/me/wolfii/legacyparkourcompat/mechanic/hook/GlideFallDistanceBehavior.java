package me.wolfii.legacyparkourcompat.mechanic.hook;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.world.entity.player.Player;

/** Historical fall-distance update at the start of fall-flying travel. */
@MechanicType("player.fall_flying.fall_distance")
public interface GlideFallDistanceBehavior extends VersionedMechanic {
    void beforeFallFlyingTravel(Player player);
    default boolean appliesToVersion(me.wolfii.legacyparkourcompat.api.ParkourVersion selected) {
        return selected.newerThanOrEqual(me.wolfii.legacyparkourcompat.api.ParkourVersion.V1_9);
    }
}

package me.wolfii.legacyparkourcompat.mechanic.hook;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

/** Narrow historical water travel coefficients and vertical adjustment. */
@MechanicType("player.travel.water")
public interface WaterTravelBehavior extends VersionedMechanic {
    default float sprintSlowdown(Player player, float vanilla) {
        return vanilla;
    }

    default Vec3 gravityAdjustedMovement(
        Player player,
        double baseGravity,
        boolean falling,
        Vec3 movement,
        Vec3 vanilla
    ) {
        return vanilla;
    }
}

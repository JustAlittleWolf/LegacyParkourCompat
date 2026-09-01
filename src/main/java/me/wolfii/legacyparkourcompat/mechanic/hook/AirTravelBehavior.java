package me.wolfii.legacyparkourcompat.mechanic.hook;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VanillaCall;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

/**
 * Historical airborne travel ({@code LivingEntity#travelInAir}).
 */
@MechanicType("player.travel.air")
public interface AirTravelBehavior extends VersionedMechanic {
    void travelInAir(Player player, Vec3 input, VanillaCall vanilla);
}

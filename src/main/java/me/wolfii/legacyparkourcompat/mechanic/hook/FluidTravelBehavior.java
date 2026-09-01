package me.wolfii.legacyparkourcompat.mechanic.hook;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VanillaCall;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

/**
 * Historical water / lava travel ({@code LivingEntity#travelInFluid}).
 */
@MechanicType("player.travel.fluid")
public interface FluidTravelBehavior extends VersionedMechanic {
    void travelInFluid(Player player, Vec3 input, VanillaCall vanilla);
}

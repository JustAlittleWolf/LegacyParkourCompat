package me.wolfii.legacyparkourcompat.mechanic.hook;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VanillaCall;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

/**
 * Historical elytra travel. Versions before elytra typically cancel vanilla flying.
 */
@MechanicType("player.travel.elytra")
public interface ElytraTravelBehavior extends VersionedMechanic {
    void travelFallFlying(Player player, Vec3 input, VanillaCall vanilla);
}

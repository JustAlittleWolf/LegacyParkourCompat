package me.wolfii.legacyparkourcompat.mechanic.hook.fluid;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import me.wolfii.legacyparkourcompat.mechanic.VanillaFn;
import net.minecraft.world.entity.player.Player;

/** Climbable admission for the post-water-movement upward impulse. */
@MechanicType("player.water.climb_impulse")
public interface WaterClimbImpulseHook extends VersionedMechanic {
    boolean onClimbable(Player player, ParkourVersion selected, VanillaFn<Boolean> nativeCheck);
}

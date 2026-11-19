package me.wolfii.legacyparkourcompat.mechanic.hook.input;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.world.phys.Vec3;

import java.util.Optional;

/** Replaces the velocity write after the water-sneak descent gates have admitted it. */
@FunctionalInterface
@MechanicType("player.water.sneak_impulse")
public interface WaterSneakImpulseHook extends VersionedMechanic {
    Optional<Vec3> applyImpulse(Vec3 movement);
}

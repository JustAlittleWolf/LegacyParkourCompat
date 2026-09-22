package me.wolfii.legacyparkourcompat.mechanic.hook;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.world.phys.Vec3;

/** Sprint-jump horizontal impulse before the modern double multiplication. */
@MechanicType("player.jump.sprint_impulse")
public interface SprintJumpImpulseBehavior extends VersionedMechanic {
    Vec3 impulse(float yaw, Vec3 vanilla);
}

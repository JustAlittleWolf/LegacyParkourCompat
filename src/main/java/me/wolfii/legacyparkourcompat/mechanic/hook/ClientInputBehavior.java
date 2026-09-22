package me.wolfii.legacyparkourcompat.mechanic.hook;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.world.phys.Vec2;

/** Versioned preparation of local-player horizontal input. */
@MechanicType("player.input.horizontal")
public interface ClientInputBehavior extends VersionedMechanic {
    Vec2 modify(Vec2 raw, Vec2 vanilla, boolean usingItem, boolean movingSlowly, float sneakingSpeed, boolean flyingSneak);
}

package me.wolfii.legacyparkourcompat.mechanic.hook;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.world.phys.Vec2;

/** Historical input operation applied before the vanilla input transformations. */
@MechanicType("player.input.horizontal")
public interface ClientInputBehavior extends VersionedMechanic {
    Vec2 modify(Vec2 input, boolean usingItem, boolean movingSlowly, float sneakingSpeed, boolean flyingSneak);
}

package me.wolfii.legacyparkourcompat.mechanic.hook.explosion;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.world.phys.Vec3;

import java.util.Optional;
import java.util.function.Consumer;

/** Controls the local player's explosion knockback response after packet decoding. */
@MechanicType("player.explosion.knockback")
public interface ExplosionKnockbackHook extends VersionedMechanic {
    void apply(Optional<Vec3> knockback, Consumer<Vec3> addDeltaMovement);
}

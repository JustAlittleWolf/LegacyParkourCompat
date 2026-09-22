package me.wolfii.legacyparkourcompat.mechanic.hook;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.world.entity.player.Input;
import net.minecraft.world.phys.Vec2;

/** Keyboard movement vector before modern diagonal normalization. */
@MechanicType("player.input.keyboard_vector")
public interface ClientMoveInputBehavior extends VersionedMechanic {
    Vec2 moveVector(Input keys, Vec2 vanilla);
}

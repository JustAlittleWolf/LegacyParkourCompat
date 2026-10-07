package me.wolfii.legacyparkourcompat.mechanic.hook;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec2;

/** Historical float input scaling before Entity's double-precision normalization. */
@FunctionalInterface
@MechanicType("player.input.keyboard_diagonal")
public interface KeyboardDiagonalInputBehavior extends VersionedMechanic {
    Vec2 diagonalInput(
        Player player,
        Vec2 rawImpulses,
        Vec2 vanilla,
        boolean keyboard,
        boolean diagonal,
        boolean movingSlowly,
        boolean usingItem
    );
}

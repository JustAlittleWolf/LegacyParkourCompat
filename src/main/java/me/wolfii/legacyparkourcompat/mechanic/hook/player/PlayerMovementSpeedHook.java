package me.wolfii.legacyparkourcompat.mechanic.hook.player;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.resources.Identifier;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/** Historical override for the float movement speed read by a player. */
@MechanicType("player.movement_speed")
public interface PlayerMovementSpeedHook extends VersionedMechanic {
    /**
     * Raw movement-speed inputs captured once by the client runtime. Providers
     * must reconstruct any historical category order they need and retain
     * native behavior when the supplied inputs cannot support that order.
     */
    Optional<Float> movementSpeed(Snapshot snapshot);

    record Snapshot(double baseValue, List<Modifier> modifiers, History history) {
        public Snapshot {
            modifiers = List.copyOf(modifiers);
        }
    }

    /** One current attribute modifier without depending on a game API type. */
    record Modifier(Identifier identifier, double amount, int operation) {
    }

    /** Bounded observation of modifier IDs seen since attribute construction. */
    record History(Set<Identifier> identifiers, boolean complete, boolean totalOnly) {
        public History {
            identifiers = Set.copyOf(identifiers);
        }
    }
}

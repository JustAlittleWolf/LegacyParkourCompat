package me.wolfii.legacyparkourcompat.mechanic.hook.jumpboost;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.world.entity.player.Player;

import java.util.Optional;
import java.util.OptionalInt;

/** Historical client packet receiver state used only for the player's Jump Boost movement read. */
@MechanicType("player.jump_boost_effect_receiver")
public interface JumpBoostEffectReceiverHook extends VersionedMechanic {
    void receiveUpdate(Player player, int amplifier, int duration);

    void receiveRemoval(Player player);

    void tick(Player player);

    OptionalInt amplifier(Player player);

    /** Empty leaves the native presence predicate in control for unobserved or native sessions. */
    Optional<Boolean> hasJumpBoost(Player player);
}

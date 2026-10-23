package me.wolfii.legacyparkourcompat.mechanic.hook.player;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.resources.Identifier;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

/** Historical override for the float movement speed read by a player. */
@MechanicType("player.movement_speed")
public interface PlayerMovementSpeedHook extends VersionedMechanic {
    /**
     * The caller has observed this attribute since construction, and every
     * observed modifier used ADD_MULTIPLIED_TOTAL. The identifier set includes
     * removed modifiers; unsupported history must retain native behavior.
     */
    Optional<Float> movementSpeed(double baseValue, Collection<AttributeModifier> modifiers, Set<Identifier> observedIdentifiers);
}

package me.wolfii.legacyparkourcompat.change.v1_20_2;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.player.PlayerMovementSpeedHook;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.resources.Identifier;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

/** Keeps native modifier iteration for versions before the proven identifier change. */
@MovementChange(emulates = ParkourVersion.V1_20_2)
public final class NativePlayerMovementSpeed implements PlayerMovementSpeedHook {
    @Override
    public Optional<Float> movementSpeed(double baseValue, Collection<AttributeModifier> modifiers, Set<Identifier> observedIdentifiers) {
        return Optional.empty();
    }
}

package me.wolfii.legacyparkourcompat.mechanic.hook;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;

/**
 * Historical {@code Block#getBounceRestitution} for one block.
 */
@MechanicType("block.bounce")
public interface BlockBounceBehavior extends VersionedMechanic {
    String blockId();

    @Override
    default String variant() {
        return this.blockId();
    }

    float bounceRestitution(float vanilla);
}

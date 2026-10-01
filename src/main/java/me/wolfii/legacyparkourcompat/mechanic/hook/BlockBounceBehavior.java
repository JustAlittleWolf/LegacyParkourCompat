package me.wolfii.legacyparkourcompat.mechanic.hook;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.world.entity.Entity;

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

    float bounceRestitution(float vanilla, Entity entity);
}

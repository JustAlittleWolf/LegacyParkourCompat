package me.wolfii.legacyparkourcompat.mechanic.hook;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VanillaCall;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.BlockGetter;

/**
 * Historical block response after vertical collision, keyed by the landing block.
 * The general Entity movement hook dispatches this because modern movement no
 * longer calls Block#updateEntityMovementAfterFallOn directly.
 */
@MechanicType("block.landing")
public interface BlockLandingBehavior extends VersionedMechanic {
    String blockId();

    @Override
    default String variant() {
        return this.blockId();
    }

    void updateMovementAfterFallOn(BlockGetter level, Entity entity, java.util.Optional<BlockBounceBehavior> bounce, VanillaCall vanilla);
}

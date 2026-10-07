package me.wolfii.legacyparkourcompat.mechanic.hook;

import java.util.Optional;
import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VanillaCall;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

/**
 * Historical entity-wide velocity response after collision. Block-specific
 * vertical landing behavior is resolved separately through {@link BlockLandingBehavior}.
 */
@FunctionalInterface
@MechanicType("entity.restitution")
public interface CollisionRestitutionBehavior extends VersionedMechanic {
    void restituteAfterCollisions(
        Entity entity,
        BlockState effectState,
        boolean xCollision,
        boolean zCollision,
        boolean yCollision,
        Vec3 movement,
        Optional<BlockLandingBehavior> blockLanding,
        Optional<BlockBounceBehavior> bounce,
        VanillaCall vanilla
    );
}

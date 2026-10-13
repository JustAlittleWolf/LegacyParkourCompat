package me.wolfii.legacyparkourcompat.mechanic.hook.collision;

import me.wolfii.legacyparkourcompat.mechanic.hook.block.BlockBounceHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.block.BlockLandingHook;

import java.util.Optional;
import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VanillaCall;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

/**
 * Historical entity-wide velocity response after collision. Block-specific
 * vertical landing behavior is resolved separately through {@link BlockLandingHook}.
 */
@FunctionalInterface
@MechanicType("entity.restitution")
public interface CollisionRestitutionHook extends VersionedMechanic {
    void restituteAfterCollisions(
        Entity entity,
        BlockState effectState,
        boolean xCollision,
        boolean zCollision,
        boolean yCollision,
        Vec3 movement,
        Optional<BlockLandingHook> blockLanding,
        Optional<BlockBounceHook> bounce,
        VanillaCall vanilla
    );
}

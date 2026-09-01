package me.wolfii.legacyparkourcompat.mechanic.hook;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VanillaCall;
import me.wolfii.legacyparkourcompat.mechanic.VanillaFn;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

/**
 * Historical post-collision velocity restitution (slime/bed bounce in 26.2+ lives here).
 */
@MechanicType("entity.restitution")
public interface CollisionRestitutionBehavior extends VersionedMechanic {
    void restituteAfterCollisions(
            Entity entity,
            BlockState effectState,
            boolean xCollision,
            boolean zCollision,
            Vec3 movement,
            VanillaCall vanilla
    );

    default double blockBounciness(Entity entity, Block onBlock, VanillaFn<Double> vanilla) {
        return vanilla.get();
    }
}

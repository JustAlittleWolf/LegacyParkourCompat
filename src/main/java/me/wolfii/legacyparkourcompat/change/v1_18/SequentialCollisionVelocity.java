package me.wolfii.legacyparkourcompat.change.v1_18;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.change.v1_21_11.LegacyBlockRestitution;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.VanillaCall;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

/** Through 1.18.1, the Z collision reset restored X from the pre-collision snapshot. */
@MovementChange(emulates = ParkourVersion.V1_18)
public final class SequentialCollisionVelocity extends LegacyBlockRestitution {
    @Override
    public void restituteAfterCollisions(
        Entity entity,
        BlockState effectState,
        boolean xCollision,
        boolean zCollision,
        Vec3 movement,
        VanillaCall vanilla
    ) {
        double previousX = entity.getDeltaMovement().x;
        super.restituteAfterCollisions(entity, effectState, xCollision, zCollision, movement, vanilla);
        if (xCollision && zCollision) {
            Vec3 velocity = entity.getDeltaMovement();
            entity.setDeltaMovement(previousX, velocity.y, velocity.z);
        }
    }
}

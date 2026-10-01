package me.wolfii.legacyparkourcompat.change.v26_1;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.VanillaCall;
import me.wolfii.legacyparkourcompat.mechanic.hook.BlockLandingBehavior;
import me.wolfii.legacyparkourcompat.mechanic.hook.CollisionRestitutionBehavior;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

import java.util.Optional;

@MovementChange(emulates = ParkourVersion.V26_1)
public final class CollisionRestitution26_1 implements CollisionRestitutionBehavior {
    @Override
    public void restituteAfterCollisions(
        Entity entity,
        BlockState effectState,
        boolean xCollision,
        boolean zCollision,
        Vec3 movement,
        Optional<BlockLandingBehavior> blockLanding,
        VanillaCall vanilla
    ) {
        Vec3 velocity = entity.getDeltaMovement();
        double x = xCollision ? 0.0 : velocity.x;
        double z = zCollision ? 0.0 : velocity.z;
        entity.setDeltaMovement(x, velocity.y, z);

        if (entity.verticalCollision) {
            VanillaCall landing = () -> entity.setDeltaMovement(entity.getDeltaMovement().multiply(1.0, 0.0, 1.0));
            blockLanding.ifPresentOrElse(
                behavior -> behavior.updateMovementAfterFallOn(entity.level(), entity, landing),
                landing::run
            );
        }
    }
}

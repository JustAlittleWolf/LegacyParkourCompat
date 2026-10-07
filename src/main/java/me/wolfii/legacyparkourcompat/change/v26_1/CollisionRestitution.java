package me.wolfii.legacyparkourcompat.change.v26_1;

import java.util.Optional;
import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.VanillaCall;
import me.wolfii.legacyparkourcompat.mechanic.hook.BlockBounceBehavior;
import me.wolfii.legacyparkourcompat.mechanic.hook.BlockLandingBehavior;
import me.wolfii.legacyparkourcompat.mechanic.hook.CollisionRestitutionBehavior;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

@MovementChange(emulates = ParkourVersion.V26_1)
public final class CollisionRestitution implements CollisionRestitutionBehavior {
    @Override
    public void restituteAfterCollisions(
        Entity entity,
        BlockState effectState,
        boolean xCollision,
        boolean zCollision,
        boolean yCollision,
        Vec3 movement,
        Optional<BlockLandingBehavior> blockLanding,
        Optional<BlockBounceBehavior> bounce,
        VanillaCall vanilla
    ) {
        Vec3 velocity = entity.getDeltaMovement();
        double x = xCollision ? 0.0 : velocity.x;
        double z = zCollision ? 0.0 : velocity.z;
        entity.setDeltaMovement(x, velocity.y, z);

        if (yCollision) {
            VanillaCall landing = () -> entity.setDeltaMovement(entity.getDeltaMovement().multiply(1.0, 0.0, 1.0));
            blockLanding.ifPresentOrElse(
                behavior -> behavior.updateMovementAfterFallOn(entity.level(), entity, bounce, landing),
                landing::run
            );
        }
    }
}

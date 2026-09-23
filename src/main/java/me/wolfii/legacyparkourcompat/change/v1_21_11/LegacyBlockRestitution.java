package me.wolfii.legacyparkourcompat.change.v1_21_11;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.MovementRuntime;
import me.wolfii.legacyparkourcompat.mechanic.VanillaCall;
import me.wolfii.legacyparkourcompat.mechanic.hook.CollisionRestitutionBehavior;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.BedBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

/** Bed and slime bounce through 26.1 used direct vertical velocity reflection. */
@MovementChange(emulates = ParkourVersion.V26_1)
public final class LegacyBlockRestitution implements CollisionRestitutionBehavior {
    @Override
    public void restituteAfterCollisions(
        Entity entity,
        BlockState effectState,
        boolean xCollision,
        boolean zCollision,
        Vec3 movement,
        VanillaCall vanilla
    ) {
        boolean bed = effectState.getBlock() instanceof BedBlock;
        boolean slime = effectState.is(Blocks.SLIME_BLOCK);
        if (!entity.verticalCollision || (!bed && !slime)) {
            vanilla.run();
            return;
        }

        Vec3 velocity = entity.getDeltaMovement();
        double y = velocity.y;
        boolean noBedBounce = bed
            && MovementRuntime.profile(entity).target().olderThanOrEqual(ParkourVersion.V1_11_2);
        if (entity.isSuppressingBounce() || noBedBounce) {
            y = 0.0;
        } else if (y < 0.0) {
            y = slime ? -y : -y * 0.66F;
        }
        entity.setDeltaMovement(xCollision ? 0.0 : velocity.x, y, zCollision ? 0.0 : velocity.z);
    }
}

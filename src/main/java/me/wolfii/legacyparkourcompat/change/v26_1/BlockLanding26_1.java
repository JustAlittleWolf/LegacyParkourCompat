package me.wolfii.legacyparkourcompat.change.v26_1;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.VanillaCall;
import me.wolfii.legacyparkourcompat.mechanic.hook.BlockLandingBehavior;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.phys.Vec3;

@MovementChange(emulates = ParkourVersion.V26_1)
final class BlockLanding26_1 implements BlockLandingBehavior {
    private final String blockId;
    private final boolean slime;

    BlockLanding26_1(String blockId, boolean slime) {
        this.blockId = blockId;
        this.slime = slime;
    }

    @Override
    public String blockId() {
        return this.blockId;
    }

    @Override
    public void updateMovementAfterFallOn(BlockGetter level, Entity entity, VanillaCall vanilla) {
        if (entity.isSuppressingBounce()) {
            vanilla.run();
            return;
        }

        Vec3 movement = entity.getDeltaMovement();
        if (movement.y < 0.0) {
            double factor = entity instanceof LivingEntity ? 1.0 : 0.8;
            double bouncedY = this.slime
                ? -movement.y * factor
                : -movement.y * 0.66F * factor;
            entity.setDeltaMovement(movement.x, bouncedY, movement.z);
        }
    }
}

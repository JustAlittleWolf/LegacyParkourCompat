package me.wolfii.legacyparkourcompat.change.v1_15_2;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.VanillaFn;
import me.wolfii.legacyparkourcompat.mechanic.hook.GroundFrictionBlockBehavior;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;

@MovementChange(emulates = ParkourVersion.V1_15_2)
public final class GroundFrictionSamplePoint implements GroundFrictionBlockBehavior {
    @Override
    public BlockPos getBlockPosBelowThatAffectsMyMovement(Entity entity, VanillaFn<BlockPos> vanilla) {
        return BlockPos.containing(
            entity.getX(),
            entity.getBoundingBox().minY - 0.5000001,
            entity.getZ()
        );
    }
}

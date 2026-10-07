package me.wolfii.legacyparkourcompat.change.v1_14;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.VanillaFn;
import me.wolfii.legacyparkourcompat.mechanic.hook.GroundFrictionBlockBehavior;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;

@MovementChange(emulates = ParkourVersion.V1_14)
public final class GroundFrictionSupportCellChange implements GroundFrictionBlockBehavior {
    @Override
    public BlockPos getBlockPosBelowThatAffectsMyMovement(Entity entity, VanillaFn<BlockPos> vanilla) {
        return BlockPos.containing(
            entity.getX(),
            entity.getBoundingBox().minY - 1.0,
            entity.getZ()
        );
    }
}

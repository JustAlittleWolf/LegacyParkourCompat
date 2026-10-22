package me.wolfii.legacyparkourcompat.change.v1_10_1;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.player.SleepPositionHook;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.BedBlock;
import net.minecraft.world.phys.Vec3;

/**
 * 1.8.9 and 1.10.2 (Ornithe Feather, PlayerEntity.trySleep) use switch-selected
 * float offsets, retaining a 0.5F perpendicular coordinate. Later releases
 * replace this expression with a facing-vector formula.
 */
@MovementChange(emulates = ParkourVersion.V1_10_1)
public final class SleepPosition implements SleepPositionHook {
    @Override
    public Vec3 position(LivingEntity entity, BlockPos bedPosition) {
        float xOffset = 0.5F;
        float zOffset = 0.5F;
        if (entity.level().hasChunkAt(bedPosition)) {
            Direction facing = entity.level().getBlockState(bedPosition).getValue(BedBlock.FACING);
            switch (facing) {
                case NORTH -> zOffset = 0.1F;
                case SOUTH -> zOffset = 0.9F;
                case WEST -> xOffset = 0.1F;
                case EAST -> xOffset = 0.9F;
                default -> throw new IllegalStateException("Unexpected bed facing: " + facing);
            }
        }
        return new Vec3(
            bedPosition.getX() + xOffset,
            bedPosition.getY() + 0.6875F,
            bedPosition.getZ() + zOffset
        );
    }
}

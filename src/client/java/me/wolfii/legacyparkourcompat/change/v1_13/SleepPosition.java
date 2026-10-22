package me.wolfii.legacyparkourcompat.change.v1_13;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.player.SleepPositionHook;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.BedBlock;
import net.minecraft.world.phys.Vec3;

/**
 * 1.11 and 1.13.2 (Ornithe Feather, PlayerEntity.trySleep) use the loaded-bed
 * float formula; 1.14 and 1.14.4 (Ornithe Feather Gen2 build 2,
 * LivingEntity.startSleeping) center x/z unconditionally.
 */
@MovementChange(emulates = ParkourVersion.V1_13)
public final class SleepPosition implements SleepPositionHook {
    @Override
    public Vec3 position(LivingEntity entity, BlockPos bedPosition) {
        float x = bedPosition.getX();
        float y = bedPosition.getY() + 0.6875F;
        float z = bedPosition.getZ();
        Direction facing = entity.level().getBlockState(bedPosition).getValue(BedBlock.FACING);
        if (entity.level().hasChunkAt(bedPosition)) {
            x += 0.5F + facing.getStepX() * 0.4F;
            z += 0.5F + facing.getStepZ() * 0.4F;
        } else {
            x += 0.5F;
            z += 0.5F;
        }
        return new Vec3(x, y, z);
    }
}

package me.wolfii.legacyparkourcompat.change.v1_14;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.change.v1_16_2.BaseTickSoulSandSpeed;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

/** Before 1.15, every soul sand cell intersecting the player applies .4. */
@MovementChange(emulates = ParkourVersion.V1_14)
public final class OverlappingSoulSandSpeed extends BaseTickSoulSandSpeed {
    @Override
    public void baseTick(Entity entity) {
        AABB box = entity.getBoundingBox();
        BlockPos min = BlockPos.containing(box.minX + 0.001, box.minY + 0.001, box.minZ + 0.001);
        BlockPos max = BlockPos.containing(box.maxX - 0.001, box.maxY - 0.001, box.maxZ - 0.001);
        if (!entity.level().hasChunksAt(min, max)) {
            return;
        }
        BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos();
        for (int x = min.getX(); x <= max.getX(); x++) {
            for (int y = min.getY(); y <= max.getY(); y++) {
                for (int z = min.getZ(); z <= max.getZ(); z++) {
                    if (entity.level().getBlockState(pos.set(x, y, z)).is(Blocks.SOUL_SAND)) {
                        Vec3 velocity = entity.getDeltaMovement();
                        entity.setDeltaMovement(velocity.multiply(0.4, 1.0, 0.4));
                    }
                }
            }
        }
    }
}

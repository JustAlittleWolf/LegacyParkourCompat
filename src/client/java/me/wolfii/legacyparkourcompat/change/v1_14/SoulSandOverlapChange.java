package me.wolfii.legacyparkourcompat.change.v1_14;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.collision.AfterCollisionHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.block.BlockSpeedFactorHook;
import me.wolfii.legacyparkourcompat.mixin.accessor.EntityInvoker;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

/**
 * The per-overlapping-cell X/Z multiplication by 0.4 persists through 1.14.4.
 * 1.14 ornithe-feather and 1.14.1, 1.14.2, 1.14.3 yarn
 * SoulSandBlock#onEntityCollision retain that writer. The 1.14.2 alias boundary
 * does not change either overlap response or suppression of the newer support
 * speed factor. 1.15 mojmap SoulsandBlock removes the overlap writer and
 * Entity#getBlockSpeedFactor supplies the newer response.
 *
 * Source evidence (paths are relative to decompiled_minecraft/):
 * 1.14.4/mojmap/net/minecraft/world/entity/Entity.java:
 *   move calls slime stepOn before checkInsideBlocks, whose soul-sand callback
 *   applies one X/Z multiplication per overlapping cell.
 * 1.14.4/mojmap/net/minecraft/world/level/block/SoulsandBlock.java:
 *   entityInside.
 * 1.15.2/mojmap/net/minecraft/world/entity/Entity.java:
 *   move, getBlockSpeedFactor.
 * 1.15.2/mojmap/net/minecraft/world/level/block/SoulsandBlock.java:
 *   class/shape initialization.
 * 26.2/mojmap/net/minecraft/world/entity/Entity.java:
 *   applyEffectsFromBlocks invokes its list overload outside move; that overload
 *   deduplicates visited blocks before stepOn.
 * The product dispatches the historical slime stepOn at the per-move callback
 * site before this overlap scan, matching the 1.14.4 callback order without
 * relying on the later deduplicated applyEffectsFromBlocks collector.
 * Source-content correspondence and compilation do not establish tick-level runtime parity.
 */
@MovementChange(emulates = ParkourVersion.V1_14_2)
public final class SoulSandOverlapChange implements BlockSpeedFactorHook, AfterCollisionHook {
    @Override
    public float movementSpeedFactor(Entity entity, float vanilla) {
        BlockState state = entity.level().getBlockState(entity.blockPosition());
        if (state.is(Blocks.SOUL_SAND)) {
            return 1.0F;
        }
        if (state.is(Blocks.WATER) || state.is(Blocks.BUBBLE_COLUMN) || state.getBlock().getSpeedFactor() != 1.0F) {
            return vanilla;
        }

        BlockPos below = ((EntityInvoker)entity).legacyparkourcompat$invokeGetBlockPosBelowThatAffectsMyMovement();
        return entity.level().getBlockState(below).is(Blocks.SOUL_SAND) ? 1.0F : vanilla;
    }

    @Override
    public void afterCollision(Entity entity) {
        AABB box = entity.getBoundingBox();
        int minX = Mth.floor(box.minX + 0.001);
        int minY = Mth.floor(box.minY + 0.001);
        int minZ = Mth.floor(box.minZ + 0.001);
        int maxX = Mth.floor(box.maxX - 0.001);
        int maxY = Mth.floor(box.maxY - 0.001);
        int maxZ = Mth.floor(box.maxZ - 0.001);
        BlockPos min = new BlockPos(minX, minY, minZ);
        BlockPos max = new BlockPos(maxX, maxY, maxZ);
        if (!entity.level().hasChunksAt(min, max)) {
            return;
        }

        BlockPos.MutableBlockPos position = new BlockPos.MutableBlockPos();
        for (int x = minX; x <= maxX; x++) {
            for (int y = minY; y <= maxY; y++) {
                for (int z = minZ; z <= maxZ; z++) {
                    position.set(x, y, z);
                    if (entity.level().getBlockState(position).is(Blocks.SOUL_SAND)) {
                        Vec3 movement = entity.getDeltaMovement();
                        entity.setDeltaMovement(movement.multiply(0.4, 1.0, 0.4));
                    }
                }
            }
        }
    }
}

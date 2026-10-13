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
 * Source evidence (paths are relative to decompiled_minecraft/):
 * 1.14.4/mojmap/net/minecraft/world/entity/Entity.java:
 *   move; lines [[533, 550]].
 *   checkInsideBlocks; lines [[810, 831]].
 *   Source SHA-256: 31c6be42d165102d3e6dc4295fdfef6e8971fc3aea13f938a5b3a33b91d524a7.
 * 1.14.4/mojmap/net/minecraft/world/level/block/SoulsandBlock.java:
 *   entityInside; lines [[28, 30]].
 *   Source SHA-256: 3f2ce73aae23fb7e13cf001123343c6c750f00684b5331a5597ae33b011ef4e4.
 * 1.15.2/mojmap/net/minecraft/world/entity/Entity.java:
 *   move, getBlockSpeedFactor; lines [[533, 544], [590, 598]].
 *   Source SHA-256: 191b3ad3e7348c9bac1e703fff896706d23a751bf15162aacf676f5f97c0a10e.
 * 1.15.2/mojmap/net/minecraft/world/level/block/SoulsandBlock.java:
 *   class/shape initialization; lines [[16, 34]].
 *   Source SHA-256: 355f0cc25e78dce76cbc9c89d8bd8ff1956d4b53780cba8e3fb5c23f485802cf.
 * No exact independently accepted source binding is established for this operation.
 * Remaining source/applicability gates:
 *   Original digest binding for 1.14.4/mojmap/net/minecraft/world/entity/Entity.java is absent/different; canonical digest 31c6be42d165102d3e6dc4295fdfef6e8971fc3aea13f938a5b3a33b91d524a7 was inspected.
 * Source-content correspondence and compilation do not establish tick-level runtime parity.
 */
@MovementChange(emulates = ParkourVersion.V1_14)
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

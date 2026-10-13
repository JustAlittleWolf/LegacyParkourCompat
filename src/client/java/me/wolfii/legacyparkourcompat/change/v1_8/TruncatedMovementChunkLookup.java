package me.wolfii.legacyparkourcompat.change.v1_8;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.VanillaFn;
import me.wolfii.legacyparkourcompat.mechanic.hook.travel.MovementChunkLookupHook;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;

/**
 * Source evidence (paths are relative to decompiled_minecraft/):
 * 1.8.9/ornithe-feather/net/minecraft/entity/living/LivingEntity.java:
 *   public void moveRelative(float sideways, float forwards).
 * 1.9.4/ornithe-feather/net/minecraft/entity/living/LivingEntity.java:
 *   public void moveRelative(float sideways, float forwards).
 *   public void moveRelative(float sideways, float forwards).
 * No exact independently accepted source binding is established for this operation.
 * Remaining source/applicability gates:
 *   Later applicability gate: exact introduction of campaign-TICK-10-client-unloaded-chunk-coordinate-sampling is unknown within (1.8.9, 1.9.4]
 *   Exact independent source verdict/snapshot identity for TICK-10-client-unloaded-chunk-coordinate-sampling
 *   Exact independent source verdict/snapshot identity for MD-03-negative-zero-chunk-lookup
 * Source-content correspondence and compilation do not establish tick-level runtime parity.
 */
@MovementChange(emulates = ParkourVersion.V1_8)
public final class TruncatedMovementChunkLookup implements MovementChunkLookupHook {
    @Override
    public boolean hasChunkAt(LivingEntity entity, Level level, BlockPos vanillaPosition, VanillaFn<Boolean> vanilla) {
        if (!level.isClientSide()) {
            return vanilla.get();
        }
        return level.hasChunkAt(new BlockPos((int) entity.getX(), 0, (int) entity.getZ()));
    }
}

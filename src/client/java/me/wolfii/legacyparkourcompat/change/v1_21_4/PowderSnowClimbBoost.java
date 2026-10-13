package me.wolfii.legacyparkourcompat.change.v1_21_4;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.travel.PowderSnowClimbHook;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.Blocks;

/**
 * Source evidence (paths are relative to decompiled_minecraft/):
 * 1.20.6/mojmap/net/minecraft/world/entity/Entity.java:
 *   getInBlockState.
 * 1.20.6/mojmap/net/minecraft/world/entity/LivingEntity.java:
 *   handleRelativeFrictionAndCalculateMovement.
 * 1.20.6/mojmap/net/minecraft/world/level/block/PowderSnowBlock.java:
 *   canEntityWalkOnPowderSnow.
 * 1.21.11/mojmap/net/minecraft/world/entity/Entity.java:
 *   getInBlockState.
 * 1.21.11/mojmap/net/minecraft/world/entity/LivingEntity.java:
 *   handleRelativeFrictionAndCalculateMovement.
 * 1.21.11/mojmap/net/minecraft/world/level/block/PowderSnowBlock.java:
 *   canEntityWalkOnPowderSnow.
 * 1.21.4/mojmap/net/minecraft/world/entity/Entity.java:
 *   getInBlockState.
 *   baseTick.
 * 1.21.4/mojmap/net/minecraft/world/entity/LivingEntity.java:
 *   handleRelativeFrictionAndCalculateMovement.
 * 1.21.4/mojmap/net/minecraft/world/level/block/PowderSnowBlock.java:
 *   entityInside.
 * 1.21.5/mojmap/net/minecraft/world/entity/Entity.java:
 *   baseTick.
 * 1.21.5/mojmap/net/minecraft/world/entity/LivingEntity.java:
 *   handleRelativeFrictionAndCalculateMovement.
 * Remaining source/applicability gates:
 *   Standalone independent source-identity correction review for F11 is absent.
 * Source-content correspondence and compilation do not establish tick-level runtime parity.
 */
@MovementChange(emulates = ParkourVersion.V1_21_4)
public final class PowderSnowClimbBoost implements PowderSnowClimbHook {
    @Override
    public boolean wasInPowderSnowForBoost(LivingEntity entity, me.wolfii.legacyparkourcompat.api.ParkourVersion selected, boolean vanilla) {
        if (selected.olderThan(ParkourVersion.V1_17)) return vanilla;
        return entity.getInBlockState().is(Blocks.POWDER_SNOW);
    }
}

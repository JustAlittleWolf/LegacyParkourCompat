package me.wolfii.legacyparkourcompat.change.v1_21_4;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.travel.PowderSnowClimbHook;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.Blocks;

/**
 * Source evidence (paths are relative to decompiled_minecraft/):
 * 1.20.6/mojmap/net/minecraft/world/entity/Entity.java:
 *   getInBlockState; lines 3169-3175.
 *   Source SHA-256: 71cd6b9f6c002684154dce11d3745e8714d82f13c8e1b3aa56743930131c18f3.
 * 1.20.6/mojmap/net/minecraft/world/entity/LivingEntity.java:
 *   handleRelativeFrictionAndCalculateMovement; lines 2268-2282.
 *   Source SHA-256: c66ec8dc3b1856e490e5834a46185589030d9fbc411e3e2ce64c73203cd753b2.
 * 1.20.6/mojmap/net/minecraft/world/level/block/PowderSnowBlock.java:
 *   canEntityWalkOnPowderSnow; lines 127-133.
 *   Source SHA-256: 7a6d6ad2e6d6d719344de4bb4f4e4973387aa88e61757988afea25b46c176ad3.
 * 1.21.11/mojmap/net/minecraft/world/entity/Entity.java:
 *   getInBlockState; lines 3627-3633.
 *   Source SHA-256: 32314478c6036fa9f3f3cc409c61c622eefc5a282d60e1011a1a33cc29cf18a3.
 * 1.21.11/mojmap/net/minecraft/world/entity/LivingEntity.java:
 *   handleRelativeFrictionAndCalculateMovement; lines 2533-2544.
 *   Source SHA-256: 19ed2d565858c401c69a06750b054a633a20ea864cab3a753b5c767f0bdd60e8.
 * 1.21.11/mojmap/net/minecraft/world/level/block/PowderSnowBlock.java:
 *   canEntityWalkOnPowderSnow; lines 137-143.
 *   Source SHA-256: 84e3f4c9063c6be560b400686afa2bcd6761585c9c56566d023ad8fe47048940.
 * 1.21.4/mojmap/net/minecraft/world/entity/Entity.java:
 *   getInBlockState; lines 3360-3365.
 *   baseTick; lines 451-452.
 *   Source SHA-256: 05f18ef2ec0413fc010230407c812a11553eb5123b68d21b5d7b2c0c175698ad.
 * 1.21.4/mojmap/net/minecraft/world/entity/LivingEntity.java:
 *   handleRelativeFrictionAndCalculateMovement; lines 2362-2372.
 *   Source SHA-256: e62ce650af0a5ac97e4d0a2ba7cad4ba68a8d098525609f8b5b6976a8ab0ae30.
 * 1.21.4/mojmap/net/minecraft/world/level/block/PowderSnowBlock.java:
 *   entityInside; lines 60-82.
 *   Source SHA-256: a018411167f5d8b60b131c3f6387dcdd256b736ef64126bdcf33fd4457151654.
 * 1.21.5/mojmap/net/minecraft/world/entity/Entity.java:
 *   baseTick; lines 459-460.
 *   Source SHA-256: fc177733e9cc4b2d5cf2562e5529d0f4e0b9c3690f081fbd58aa9119eb173dc4.
 * 1.21.5/mojmap/net/minecraft/world/entity/LivingEntity.java:
 *   handleRelativeFrictionAndCalculateMovement; lines 2394-2404.
 *   Source SHA-256: a8aed863d4fdc515c751dd2878a8bb9c13179228cb8dbb50edf1d19cd5404271.
 * Bounded source decisions:
 *   Decision 4a5eae32b5819418dbd59eb4a763ee24ea1f2c52:development/research/reconciliation/branch-doc-review-late.md; reported ACCEPT, digest-only corrected revision.
 * Remaining source/applicability gates:
 *   Standalone independent digest-correction review artifact identity for F11 is absent.
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

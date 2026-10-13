package me.wolfii.legacyparkourcompat.change.v1_15_2;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.VanillaFn;
import me.wolfii.legacyparkourcompat.mechanic.hook.block.GroundFrictionBlockHook;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;

/**
 * Source evidence (paths are relative to decompiled_minecraft/):
 * 1.14.4/mojmap/net/minecraft/world/entity/LivingEntity.java:
 *   travel.
 * 1.15.2/mojmap/net/minecraft/world/entity/Entity.java:
 *   getBlockPosBelowThatAffectsMyMovement.
 * 1.15.2/mojmap/net/minecraft/world/entity/LivingEntity.java:
 *   travel.
 * 1.19.4/mojmap/net/minecraft/world/entity/Entity.java:
 *   move.
 * 1.20.1/mojmap/net/minecraft/world/entity/Entity.java:
 *   move.
 *   setOnGround.
 * 1.20.1/mojmap/net/minecraft/world/entity/player/Player.java:
 *   tick.
 *   tick.
 * 1.20.1/mojmap/net/minecraft/world/level/CollisionGetter.java:
 *   findSupportingBlock.
 * Source-content correspondence and compilation do not establish tick-level runtime parity.
 */
// The same operation previously had identical catalog entries for 1.15.2 and 1.19.4.
// Its last applicable profile is 1.19.4; the resolver also selects it for earlier
// profiles above the separate 1.14 delta, without a second registration.
@MovementChange(emulates = ParkourVersion.V1_19_4)
public final class GroundFrictionSamplePoint implements GroundFrictionBlockHook {
    @Override
    public BlockPos getBlockPosBelowThatAffectsMyMovement(Entity entity, VanillaFn<BlockPos> vanilla) {
        return BlockPos.containing(
            entity.getX(),
            entity.getBoundingBox().minY - 0.5000001,
            entity.getZ()
        );
    }
}

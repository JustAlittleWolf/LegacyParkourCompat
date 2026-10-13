package me.wolfii.legacyparkourcompat.change.v1_20;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.state.EffectFallDistanceResetHook;
import net.minecraft.world.entity.LivingEntity;

/**
 * Source evidence (paths are relative to decompiled_minecraft/):
 * 1.19.4/mojmap/net/minecraft/world/entity/LivingEntity.java:
 *   travel.
 *   travel.
 * 1.19.4/mojmap/net/minecraft/world/entity/player/Player.java:
 *   maybeBackOffFromEdge.
 * 1.20.1/mojmap/net/minecraft/world/entity/LivingEntity.java:
 *   aiStep.
 * 1.20.1/mojmap/net/minecraft/world/entity/player/Player.java:
 *   maybeBackOffFromEdge.
 * 1.20.6/mojmap/net/minecraft/world/entity/LivingEntity.java:
 *   aiStep.
 * 1.21.4/mojmap/net/minecraft/world/entity/Entity.java:
 *   checkFallDamage.
 * 1.21.4/mojmap/net/minecraft/world/entity/player/Player.java:
 *   isStayingOnGroundSurface.
 *   maybeBackOffFromEdge.
 *   isAboveGround.
 * 1.21.5/mojmap/net/minecraft/world/entity/Entity.java:
 *   checkFallDamage.
 * 1.21.5/mojmap/net/minecraft/world/entity/player/Player.java:
 *   isStayingOnGroundSurface.
 *   maybeBackOffFromEdge.
 *   isAboveGround.
 * No exact independently accepted source binding is established for this operation.
 * Source-content correspondence and compilation do not establish tick-level runtime parity.
 */
@MovementChange(emulates = ParkourVersion.V1_20)
public final class EffectFallDistanceReset implements EffectFallDistanceResetHook {
    @Override
    public boolean resetBeforeTravel(LivingEntity entity, boolean vanilla) {
        return false;
    }
}

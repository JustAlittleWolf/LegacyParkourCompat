package me.wolfii.legacyparkourcompat.change.v1_20;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.state.EffectFallDistanceResetHook;
import net.minecraft.world.entity.LivingEntity;

/**
 * Source evidence (paths are relative to decompiled_minecraft/):
 * 1.19.4/mojmap/net/minecraft/world/entity/LivingEntity.java:
 *   travel; lines 1983-1991.
 *   travel; lines 1983-1989.
 *   Source SHA-256: c8d91af61f87aaa1666de79696d7cd9d4a8212d05f873bdaf28d7bb2926bf165.
 * 1.19.4/mojmap/net/minecraft/world/entity/player/Player.java:
 *   maybeBackOffFromEdge; lines 1040-1093.
 *   Source SHA-256: 5e4436afccb361156f8184e7a5cfd91d5b12dcfe8f5937b4ac23dd3edda737a2.
 * 1.20.1/mojmap/net/minecraft/world/entity/LivingEntity.java:
 *   aiStep; lines 2562-2567.
 *   Source SHA-256: decf8cd70d194098ad51d0c82e5a0087e0687881e0a2dc66456f2ba6ea76676f.
 * 1.20.1/mojmap/net/minecraft/world/entity/player/Player.java:
 *   maybeBackOffFromEdge; lines 1043-1094.
 *   Source SHA-256: 873d82c6f5471d06812929e471d970fd973bfbe21638e4e348a6fcde45637dac.
 * 1.20.6/mojmap/net/minecraft/world/entity/LivingEntity.java:
 *   aiStep; lines 2674-2683.
 *   Source SHA-256: c66ec8dc3b1856e490e5834a46185589030d9fbc411e3e2ce64c73203cd753b2.
 * 1.21.4/mojmap/net/minecraft/world/entity/Entity.java:
 *   checkFallDamage; lines 1237-1252.
 *   Source SHA-256: 05f18ef2ec0413fc010230407c812a11553eb5123b68d21b5d7b2c0c175698ad.
 * 1.21.4/mojmap/net/minecraft/world/entity/player/Player.java:
 *   isStayingOnGroundSurface; lines 348-349.
 *   maybeBackOffFromEdge; lines 1069-1117.
 *   isAboveGround; lines 1120-1122.
 *   Source SHA-256: c45f41b9784ce50a88a498e84a13edcef0a23e175233d726ed7f90940f19ebc1.
 * 1.21.5/mojmap/net/minecraft/world/entity/Entity.java:
 *   checkFallDamage; lines 1274-1292.
 *   Source SHA-256: fc177733e9cc4b2d5cf2562e5529d0f4e0b9c3690f081fbd58aa9119eb173dc4.
 * 1.21.5/mojmap/net/minecraft/world/entity/player/Player.java:
 *   isStayingOnGroundSurface; lines 363-364.
 *   maybeBackOffFromEdge; lines 1026-1075.
 *   isAboveGround; lines 1077-1079.
 *   Source SHA-256: 8fc187f33999db9dfc49251e95e92a17645a50adab16ca0f93f4948209f62036.
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

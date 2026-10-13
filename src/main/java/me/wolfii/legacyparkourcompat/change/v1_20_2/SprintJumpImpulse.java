package me.wolfii.legacyparkourcompat.change.v1_20_2;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.change.common.LegacyTrig;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.jump.SprintJumpImpulseHook;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;

/**
 * Source evidence (paths are relative to decompiled_minecraft/):
 * 1.20.4/mojmap/net/minecraft/util/Mth.java:
 *   sin; lines 47-53.
 *   Source SHA-256: f211066a32f5fd2ff3605d0db66f79d7811d7c90ea205caf6b5ce957c0652075.
 * 1.20.4/mojmap/net/minecraft/world/entity/Entity.java:
 *   addDeltaMovement; lines 3174-3176.
 *   Source SHA-256: 07383522bff169938136638ef8c3244ca511b56ca4266913524f99f9821331b9.
 * 1.20.4/mojmap/net/minecraft/world/entity/LivingEntity.java:
 *   jumpFromGround; lines 2008-2017.
 *   getJumpPower; lines 2000-2002.
 *   Source SHA-256: f7bc53db24c1798f19f9bd6f6356c86d5e560e9c8e8aac60decaf15cc785e07d.
 * 1.20.4/mojmap/net/minecraft/world/phys/Vec3.java:
 *   add; lines 88-90.
 *   Source SHA-256: dc05337c94e64d5c137138ef98b7f10768a420af23bffb0812b68a8320c7c813.
 * 1.20.6/mojmap/net/minecraft/util/Mth.java:
 *   sin; lines 48-54.
 *   Source SHA-256: f1b2d62f18b4b20ffad5843f40d34d8b5f9c40eee3e106dfe275859a24cfb18d.
 * 1.20.6/mojmap/net/minecraft/world/entity/Entity.java:
 *   addDeltaMovement; lines 3189-3191.
 *   Source SHA-256: 71cd6b9f6c002684154dce11d3745e8714d82f13c8e1b3aa56743930131c18f3.
 * 1.20.6/mojmap/net/minecraft/world/entity/LivingEntity.java:
 *   jumpFromGround; lines 2069-2082.
 *   Source SHA-256: c66ec8dc3b1856e490e5834a46185589030d9fbc411e3e2ce64c73203cd753b2.
 * 1.20.6/mojmap/net/minecraft/world/phys/Vec3.java:
 *   add; lines 88-90.
 *   Source SHA-256: dc05337c94e64d5c137138ef98b7f10768a420af23bffb0812b68a8320c7c813.
 * Bounded source decisions:
 *   Review 581b5bd2cb44981c4889eaf05ea739134e332b87; ACCEPT, finding snapshot only.
 *   Reviewed snapshot b168d90c05e813d6dff2ccd97090dc1602d9d418; SHA-256 9e073eb434e750f6babac4cd6881b2cf38b1e4aad37523db0f62657b82071315.
 * Source-content correspondence and compilation do not establish tick-level runtime parity.
 */
@MovementChange(emulates = ParkourVersion.V1_20_2)
public final class SprintJumpImpulse implements SprintJumpImpulseHook {
    @Override
    public Vec3 impulse(LivingEntity entity, Vec3 vanilla) {
        float angle = entity.getYRot() * (float)(Math.PI / 180.0);
        return new Vec3(-LegacyTrig.sin(angle) * 0.2F, 0.0, LegacyTrig.cos(angle) * 0.2F);
    }
}

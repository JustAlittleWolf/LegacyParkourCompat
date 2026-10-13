package me.wolfii.legacyparkourcompat.change.v1_20_2;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.travel.GravityHook;
import net.minecraft.world.entity.Entity;

/**
 * Source evidence (paths are relative to decompiled_minecraft/):
 * 1.19.4/mojmap/net/minecraft/world/entity/LivingEntity.java:
 *   travel; lines 1983-1994.
 *   Source SHA-256: c8d91af61f87aaa1666de79696d7cd9d4a8212d05f873bdaf28d7bb2926bf165.
 * 1.20.4/mojmap/net/minecraft/world/entity/LivingEntity.java:
 *   travel; lines 2035-2166.
 *   aiStep; lines 2601-2603.
 *   isFallFlying; lines 3086-3088.
 *   getFluidFallingAdjustedMovement; lines 2215-2231.
 *   Source SHA-256: f7bc53db24c1798f19f9bd6f6356c86d5e560e9c8e8aac60decaf15cc785e07d.
 * 1.20.4/mojmap/net/minecraft/world/entity/player/Player.java:
 *   travel; lines 1451-1475.
 *   tryToStartFallFlying; lines 1506-1516.
 *   Source SHA-256: 218da60bc4f8c9279f56eb87e2cf2c0af79562fbe3d818a408429c656efb95dd.
 * 1.20.6/mojmap/net/minecraft/world/entity/Entity.java:
 *   getDefaultGravity; lines 1101-1107.
 *   getDefaultGravity; lines 1101-1107.
 *   Source SHA-256: 71cd6b9f6c002684154dce11d3745e8714d82f13c8e1b3aa56743930131c18f3.
 * 1.20.6/mojmap/net/minecraft/world/entity/LivingEntity.java:
 *   travel; lines 2104-2112.
 *   getDefaultGravity; lines 2100-2102.
 *   createLivingAttributes; lines 292-308.
 *   getFluidFallingAdjustedMovement; lines 2282-2295.
 *   travel; lines 2104-2233.
 *   getDefaultGravity; lines 2100-2102.
 *   createLivingAttributes; lines 292-305.
 *   Source SHA-256: c66ec8dc3b1856e490e5834a46185589030d9fbc411e3e2ce64c73203cd753b2.
 * 1.20.6/mojmap/net/minecraft/world/entity/ai/attributes/Attributes.java:
 *   class declaration / field initialization; lines 42-44.
 *   class declaration / field initialization; lines 42-44.
 *   Source SHA-256: 7e30d87c7a58b14d0052d2f9f7319d997b49ae7d025579cd762d28e845b2e82e.
 * 1.20.6/mojmap/net/minecraft/world/entity/player/Player.java:
 *   createAttributes; lines 221-230.
 *   class declaration / field initialization; lines 137.
 *   tryToStartFallFlying; lines 1534-1544.
 *   createAttributes; lines 221-230.
 *   Source SHA-256: 785d93ccc94e1f912e545b2b0c355edeb352b44ee8e83a69364daec35266dbe2.
 * Bounded source decisions:
 *   Review d6664c08d015b6d301b5498302d9f64f32ebec92; ACCEPT, corrected source snapshot only.
 *   Reviewed snapshot 13cf725ab0ac943f78c2ec7e5851a473022edfb4; SHA-256 3354ad673e73ec35247699184eb26fcf82519f4dd2cf6963164d71c35add0a2f.
 * Remaining source/applicability gates:
 *   Archived-to-canonical source identity for these checked secondary citations: 1.20.6/mojmap/net/minecraft/world/entity/Entity.java. Neither a matching archived full-file hash nor an archived matching canonical source-manifest digest was resolved for them.
 *   Eligible vanilla 1.20.4 Player producer for noGravity in fall-flight; non-default newer generic GRAVITY attribute is excluded and source acceptance does not establish producer applicability.
 * Source-content correspondence and compilation do not establish tick-level runtime parity.
 */
@MovementChange(emulates = ParkourVersion.V1_20_2)
public final class AttributeGravity implements GravityHook {
    @Override
    public double gravity(Entity entity, double vanilla) {
        return entity.isNoGravity() ? 0.0 : 0.08;
    }
}

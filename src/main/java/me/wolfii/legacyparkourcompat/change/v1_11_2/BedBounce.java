package me.wolfii.legacyparkourcompat.change.v1_11_2;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.block.BlockBounceHook;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

/**
 * Source evidence (paths are relative to decompiled_minecraft/):
 * 1.11.2/ornithe-feather/net/minecraft/block/Block.java:
 *   public void beforeCollision(World world, Entity entity) {; lines 624-626.
 *   Source SHA-256: ab873436b24487ab0ee8055bf7a478349c7f7978398aea8e387796a6c820996a.
 * 1.12.2/ornithe-feather/net/minecraft/block/BedBlock.java:
 *   public void beforeCollision(World world, Entity entity) {; lines 141-150.
 *   Source SHA-256: 48c127b2df425cb8d7ff60a284207de10a8408e90ee18a8449522a004a0b4369.
 * 1.12.2/ornithe-feather/net/minecraft/block/Block.java:
 *   public void beforeCollision(World world, Entity entity) {; lines 643-645.
 *   Source SHA-256: e4a90eca411e7b0e14f5018f7385ec29891f6cdf1a1e917fa648b5712f9b33a1.
 * No exact independently accepted source binding is established for this operation.
 * Remaining source/applicability gates:
 *   Later applicability gate: exact introduction of legacy-run-MOVE-002-bed-bounce is unknown within (1.11.2, 1.12.2]
 *   Exact independent source verdict/snapshot identity for MOVE-002-bed-bounce
 * Source-content correspondence and compilation do not establish tick-level runtime parity.
 */
@MovementChange(emulates = ParkourVersion.V1_11_2)
public final class BedBounce implements BlockBounceHook {
    @Override
    public String blockId() {
        return "minecraft:red_bed";
    }

    @Override
    public float bounceRestitution(float vanilla, Entity entity) {
        return entity instanceof Player ? 0.0F : vanilla;
    }
}

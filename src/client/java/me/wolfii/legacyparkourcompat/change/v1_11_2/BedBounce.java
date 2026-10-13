package me.wolfii.legacyparkourcompat.change.v1_11_2;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.block.BlockBounceHook;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

/**
 * Source evidence (paths are relative to decompiled_minecraft/):
 * 1.11.2/ornithe-feather/net/minecraft/block/Block.java:
 *   public void beforeCollision(World world, Entity entity).
 * 1.12.2/ornithe-feather/net/minecraft/block/BedBlock.java:
 *   public void beforeCollision(World world, Entity entity).
 * 1.12.2/ornithe-feather/net/minecraft/block/Block.java:
 *   public void beforeCollision(World world, Entity entity).
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

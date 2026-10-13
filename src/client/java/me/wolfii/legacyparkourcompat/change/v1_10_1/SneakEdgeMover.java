package me.wolfii.legacyparkourcompat.change.v1_10_1;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.collision.SneakEdgeMoverHook;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.player.Player;

/**
 * Source evidence (paths are relative to decompiled_minecraft/):
 * 1.10.2/ornithe-feather/net/minecraft/block/entity/MovingBlockEntity.java:
 *   private void moveEntities().
 * 1.10.2/ornithe-feather/net/minecraft/entity/Entity.java:
 *   public void move(double dx, double dy, double dz).
 * 1.11.2/ornithe-feather/net/minecraft/block/entity/MovingBlockEntity.java:
 *   private void moveEntities(float progress).
 * 1.11.2/ornithe-feather/net/minecraft/entity/Entity.java:
 *   public void move(MoverType moverType, double x, double y, double z).
 * No exact independently accepted source binding is established for this operation.
 * Remaining source/applicability gates:
 *   Later applicability gate: exact introduction of campaign-F-03-piston-sneak-edge-bypass is unknown within (1.10.2, 1.11.2]
 *   Exact independent source verdict/snapshot identity for F-03-piston-sneak-edge-bypass
 *   Exact independent source verdict/snapshot identity for F002
 * Source-content correspondence and compilation do not establish tick-level runtime parity.
 */
@MovementChange(emulates = ParkourVersion.V1_10_1)
public final class SneakEdgeMover implements SneakEdgeMoverHook {
    @Override
    public MoverType edgeMoverType(Player player, MoverType vanilla) {
        return vanilla == MoverType.PISTON ? MoverType.SELF : vanilla;
    }
}

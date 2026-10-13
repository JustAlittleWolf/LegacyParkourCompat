package me.wolfii.legacyparkourcompat.change.v1_10_1;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.collision.SneakEdgeMoverHook;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.player.Player;

/**
 * Source evidence (paths are relative to decompiled_minecraft/):
 * 1.10.2/ornithe-feather/net/minecraft/block/entity/MovingBlockEntity.java:
 *   private void moveEntities() {; lines 91-150.
 *   Source SHA-256: 720e1305c494a3a316b21beef823158c435826f50383778a5bd00ad0e4ff6ac1.
 * 1.10.2/ornithe-feather/net/minecraft/entity/Entity.java:
 *   public void move(double dx, double dy, double dz) {; lines 446-725.
 *   Source SHA-256: 05da145effa19a6ef7934cc276e89226373b67c12f4ce89a8ce2183f29039f77.
 * 1.11.2/ornithe-feather/net/minecraft/block/entity/MovingBlockEntity.java:
 *   private void moveEntities(float progress) {; lines 116-169.
 *   Source SHA-256: a55ee14227fe6ac0932283cba0414e946ba8e2637f543b3a3fe13d4e45b7b16e.
 * 1.11.2/ornithe-feather/net/minecraft/entity/Entity.java:
 *   public void move(MoverType moverType, double x, double y, double z) {; lines 459-790.
 *   Source SHA-256: ce8104a17ce783df639cf9726e7b1cd0936563eaa7ba308603335bbe05d49440.
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

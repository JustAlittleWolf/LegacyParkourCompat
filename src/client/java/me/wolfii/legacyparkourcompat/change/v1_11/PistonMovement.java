package me.wolfii.legacyparkourcompat.change.v1_11;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.collision.PistonMovementHook;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

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
 *   Later applicability gate: exact introduction of campaign-F-02-piston-movement-cap is unknown within (1.10.2, 1.11.2]
 *   Exact independent source verdict/snapshot identity for F-02-piston-movement-cap
 *   Exact independent source verdict/snapshot identity for F003
 * Source-content correspondence and compilation do not establish tick-level runtime parity.
 */
@MovementChange(emulates = ParkourVersion.V1_11)
public final class PistonMovement implements PistonMovementHook {
    @Override
    public Vec3 limitPistonMovement(Player player, Vec3 vanillaMovement) {
        return vanillaMovement;
    }
}

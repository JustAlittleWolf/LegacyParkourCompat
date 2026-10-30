package me.wolfii.legacyparkourcompat.change.v1_10_1;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.change.common.SneakEdgeBackoff;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.collision.SneakEdgeCollisionQueryHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.collision.SneakEdgeHook;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.Nullable;

import java.util.Optional;

/**
 * Source evidence (paths are relative to decompiled_minecraft/):
 * 1.10.2/ornithe-feather/net/minecraft/entity/Entity.java:
 *   move(double, double, double) gates the X, Z, then diagonal support
 *   backoff on onGround, isSneaking and PlayerEntity, probing at -1.0 Y.
 * 1.11/ornithe-feather/net/minecraft/entity/Entity.java:
 *   move(MoverType, double, double, double) adds SELF/PLAYER to that gate and
 *   changes the probe offset to -stepHeight.
 * The accepted source comparison bounds the former gate and offset through
 * 1.10.2; the latter begins at 1.11.
 * The independently resolved mover policy translates era-existing external moves
 * to SELF before this operation. Untranslated external movers belong to features
 * newer than the selected profile and retain native handling.
 * Source-content correspondence and compilation do not establish tick-level runtime parity.
 */
@MovementChange(emulates = ParkourVersion.V1_10_1)
public final class SneakEdge implements SneakEdgeHook {
    @Override
    public Optional<Vec3> maybeBackOffFromEdge(
        Player player,
        Vec3 delta,
        MoverType moverType,
        boolean stayingOnGroundSurface,
        float probeDistance,
        @Nullable SneakEdgeCollisionQueryHook collisionQuery
    ) {
        if (moverType != MoverType.SELF && moverType != MoverType.PLAYER) {
            return Optional.empty();
        }
        if (!player.onGround() || !player.isShiftKeyDown()) {
            return Optional.of(delta);
        }

        return Optional.of(SneakEdgeBackoff.apply(player, delta, probeDistance, collisionQuery));
    }
}

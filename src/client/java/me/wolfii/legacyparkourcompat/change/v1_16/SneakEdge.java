package me.wolfii.legacyparkourcompat.change.v1_16;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.change.common.SneakEdgeBackoff;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.collision.SneakEdgeHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.collision.SneakEdgeCollisionQueryHook;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.Nullable;

import java.util.Optional;

/**
 * Mojmap 1.16 and 1.16.1, {@code Player#maybeBackOffFromEdge}, require
 * {@code onGround} for sneak restraint. Mojmap 1.16.2, the same member
 * and {@code Player#isAboveGround}, exclude flying players and also allow
 * restraint during a short fall when the support probe finds a collision.
 */
@MovementChange(emulates = ParkourVersion.V1_16)
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
        if ((moverType != MoverType.SELF && moverType != MoverType.PLAYER)
            || !player.onGround()
            || !stayingOnGroundSurface) {
            return Optional.of(delta);
        }

        return Optional.of(SneakEdgeBackoff.apply(player, delta, probeDistance, collisionQuery));
    }
}

package me.wolfii.legacyparkourcompat.change.v1_18_2;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.change.common.SneakEdgeBackoff;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.SneakEdgeBehavior;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

@MovementChange(emulates = ParkourVersion.V1_18_2)
public final class SneakEdge implements SneakEdgeBehavior {
    @Override
    public Vec3 maybeBackOffFromEdge(
        Player player,
        Vec3 delta,
        MoverType moverType,
        boolean stayingOnGroundSurface,
        float probeDistance
    ) {
        if (player.getAbilities().flying
            || (moverType != MoverType.SELF && moverType != MoverType.PLAYER)
            || !stayingOnGroundSurface
            || !isAboveGround(player, probeDistance)) {
            return delta;
        }

        return SneakEdgeBackoff.apply(player, delta, probeDistance);
    }

    private static boolean isAboveGround(Player player, float probeDistance) {
        return player.onGround()
            || player.fallDistance < probeDistance
                && !player.level().noCollision(
                    player,
                    player.getBoundingBox().move(0.0D, player.fallDistance - probeDistance, 0.0D)
                );
    }
}

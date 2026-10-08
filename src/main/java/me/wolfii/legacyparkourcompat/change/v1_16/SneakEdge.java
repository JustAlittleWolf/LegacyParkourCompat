package me.wolfii.legacyparkourcompat.change.v1_16;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.change.common.SneakEdgeBackoff;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.SneakEdgeBehavior;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

@MovementChange(emulates = ParkourVersion.V1_16)
public final class SneakEdge implements SneakEdgeBehavior {
    @Override
    public Vec3 maybeBackOffFromEdge(
        Player player,
        Vec3 delta,
        MoverType moverType,
        boolean stayingOnGroundSurface,
        float probeDistance
    ) {
        if ((moverType != MoverType.SELF && moverType != MoverType.PLAYER)
            || !player.onGround()
            || !stayingOnGroundSurface) {
            return delta;
        }

        return SneakEdgeBackoff.apply(player, delta, probeDistance);
    }
}

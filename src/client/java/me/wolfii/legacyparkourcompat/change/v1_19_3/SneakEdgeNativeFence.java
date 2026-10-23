package me.wolfii.legacyparkourcompat.change.v1_19_3;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.collision.SneakEdgeCollisionQueryHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.collision.SneakEdgeHook;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.Nullable;

import java.util.Optional;

/** Leaves edge handling native before the verified 1.19.4 behavior. */
@MovementChange(emulates = ParkourVersion.V1_19_3)
public final class SneakEdgeNativeFence implements SneakEdgeHook {
    @Override
    public Optional<Vec3> maybeBackOffFromEdge(
        Player player,
        Vec3 delta,
        MoverType moverType,
        boolean stayingOnGroundSurface,
        float probeDistance,
        @Nullable SneakEdgeCollisionQueryHook collisionQuery
    ) {
        return Optional.empty();
    }
}

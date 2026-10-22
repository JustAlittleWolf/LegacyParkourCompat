package me.wolfii.legacyparkourcompat.change.v1_11_2;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.collision.SneakEdgeDistanceHook;
import net.minecraft.world.entity.player.Player;

/**
 * Source evidence (paths are relative to decompiled_minecraft/):
 * 1.11/ornithe-feather/net/minecraft/entity/Entity.java:
 *   move(MoverType, double, double, double) probes at -stepHeight.
 * 1.11.2/ornithe-feather/net/minecraft/entity/Entity.java:
 *   move(MoverType, double, double, double) retains the stepHeight offset.
 * 1.11/ornithe-feather/net/minecraft/entity/living/LivingEntity.java and
 * 1.11.2/ornithe-feather/net/minecraft/entity/living/LivingEntity.java:
 *   constructors initialize stepHeight to 0.6F.
 * The accepted comparison establishes the 1.11.0 through 1.11.2 boundary.
 * Source-content correspondence and compilation do not establish tick-level runtime parity.
 */
@MovementChange(emulates = ParkourVersion.V1_11_2)
public final class SneakEdgeDistance implements SneakEdgeDistanceHook {
    @Override
    public float edgeFallDistance(Player player, float vanillaMaxUpStep) {
        return 0.6F;
    }
}

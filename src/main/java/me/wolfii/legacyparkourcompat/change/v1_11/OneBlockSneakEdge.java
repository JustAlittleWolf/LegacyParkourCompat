package me.wolfii.legacyparkourcompat.change.v1_11;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.SneakEdgeDistanceBehavior;
import net.minecraft.world.entity.player.Player;

/**
 * Through 1.10.2, sneaking prevented a 1-block drop. 1.11 reduced that to 0.6b
 * ({@code maxUpStep}).
 */
@MovementChange(emulates = ParkourVersion.V1_10_1)
public final class OneBlockSneakEdge implements SneakEdgeDistanceBehavior {
    @Override
    public float edgeFallDistance(Player player, float vanillaMaxUpStep) {
        return 1.0F;
    }
}

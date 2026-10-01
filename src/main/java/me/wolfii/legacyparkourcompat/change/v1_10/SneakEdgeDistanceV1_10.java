package me.wolfii.legacyparkourcompat.change.v1_10;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.SneakEdgeDistanceBehavior;
import net.minecraft.world.entity.player.Player;

@MovementChange(emulates = ParkourVersion.V1_10_1)
public final class SneakEdgeDistanceV1_10 implements SneakEdgeDistanceBehavior {
    @Override
    public float edgeFallDistance(Player player, float vanillaMaxUpStep) {
        return 1.0F;
    }
}

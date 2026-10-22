package me.wolfii.legacyparkourcompat.change.v1_10_1;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.collision.SneakEdgeDistanceHook;
import net.minecraft.world.entity.player.Player;

/**
 * Source evidence (paths are relative to decompiled_minecraft/):
 * 1.10.2/ornithe-feather/net/minecraft/entity/Entity.java:
 *   move(double, double, double) uses a fixed -1.0 Y offset for the grounded
 *   sneaking-player support probes.
 * 1.11/ornithe-feather/net/minecraft/entity/Entity.java:
 *   move(MoverType, double, double, double) uses -stepHeight for those probes.
 * The independently accepted source comparison bounds this fixed offset to
 * 1.8.9 through 1.10.2; the next inspected release, 1.11, uses stepHeight.
 * Source-content correspondence and compilation do not establish tick-level runtime parity.
 */
@MovementChange(emulates = ParkourVersion.V1_10_1)
public final class SneakEdgeDistance implements SneakEdgeDistanceHook {
    @Override
    public float edgeFallDistance(Player player, float vanillaMaxUpStep) {
        return 1.0F;
    }
}

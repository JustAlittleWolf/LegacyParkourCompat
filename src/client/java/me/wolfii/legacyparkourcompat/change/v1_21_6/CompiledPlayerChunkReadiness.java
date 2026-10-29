package me.wolfii.legacyparkourcompat.change.v1_21_6;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.player.PlayerChunkReadinessHook;

/**
 * Reproduces the manager predicate used by 1.21.4–1.21.8.
 *
 * <p>Exact Mojmap source: 1.21.4 and 1.21.8
 * {@code net.minecraft.client.multiplayer.LevelLoadStatusManager.tick()}
 * and {@code net.minecraft.client.renderer.LevelRenderer.isSectionCompiled(BlockPos)}.
 * The source's short-circuit order is outside-build-height, compiled section,
 * spectator, then dead player. It has no tracker timeout and no visibility gate.
 */
@MovementChange(emulates = ParkourVersion.V1_21_6)
public final class CompiledPlayerChunkReadiness implements PlayerChunkReadinessHook {
    @Override
    public boolean isReady(Probe probe) {
        return probe.outsideBuildHeight()
            || probe.playerSectionCompiled()
            || probe.spectator()
            || !probe.alive();
    }
}

package me.wolfii.legacyparkourcompat.change.v1_21_10;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.player.PlayerChunkReadinessHook;

/**
 * Reproduces the tracker predicate used by 1.21.9–1.21.10.
 *
 * <p>Exact Mojmap source: 1.21.9 and 1.21.10
 * {@code net.minecraft.client.multiplayer.LevelLoadTracker.WaitingForPlayerChunk.isReady()}
 * and {@code net.minecraft.client.renderer.LevelRenderer.isSectionCompiled(BlockPos)}.
 * The strict millisecond deadline check is first; then build-height, spectator,
 * and alive bypasses precede the player's compiled-section query. There is no
 * visibility threshold in this range.
 */
@MovementChange(emulates = ParkourVersion.V1_21_10)
public final class TimedCompiledPlayerChunkReadiness implements PlayerChunkReadinessHook {
    @Override
    public boolean isReady(Probe probe) {
        if (probe.currentTimeMillis() > probe.timeoutAfterMillis()) {
            return true;
        }
        return probe.outsideBuildHeight()
            || probe.spectator()
            || !probe.alive()
            || probe.playerSectionCompiled();
    }
}

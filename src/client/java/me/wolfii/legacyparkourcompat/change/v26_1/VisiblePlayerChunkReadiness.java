package me.wolfii.legacyparkourcompat.change.v26_1;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.player.PlayerChunkReadinessHook;

/**
 * Reproduces synchronous readiness through 26.1.2; 26.2's callback stays native.
 *
 * <p>Exact Mojmap source: 1.21.11
 * {@code net.minecraft.client.multiplayer.LevelLoadTracker.WaitingForPlayerChunk.isReady()}
 * and {@code net.minecraft.client.renderer.LevelRenderer.isSectionCompiledAndVisible(BlockPos)}.
 * Exact published unobfuscated source: 26.1.2
 * {@code net.minecraft.client.multiplayer.LevelLoadTracker.WaitingForPlayerChunk.isReady()}
 * and {@code net.minecraft.client.renderer.LevelRenderer.isSectionCompiledAndVisible(BlockPos)}.
 * The timeout warning and player eligibility bypasses remain; the ordinary
 * player-position query additionally requires the renderer's evaluated visibility
 * to be at least {@code 0.3F}. No provider emulates 26.2's asynchronous
 * camera-render-state callback.
 */
@MovementChange(emulates = ParkourVersion.V26_1)
public final class VisiblePlayerChunkReadiness implements PlayerChunkReadinessHook {
    @Override
    public boolean isReady(Probe probe) {
        if (probe.currentTimeMillis() > probe.timeoutAfterMillis()) {
            probe.logTimeoutWarning();
            return true;
        }
        return probe.outsideBuildHeight()
            || probe.spectator()
            || !probe.alive()
            || probe.playerSectionCompiledAndVisible();
    }
}

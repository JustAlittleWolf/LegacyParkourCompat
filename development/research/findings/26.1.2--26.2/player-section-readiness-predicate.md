# Player section readiness predicate

Exact source: 26.1.2 published unobfuscated `net.minecraft.client.multiplayer.LevelLoadTracker.WaitingForPlayerChunk.isReady`, `ClientPacketListener.tick/notifyPlayerLoaded`, and `net.minecraft.client.renderer.LevelRenderer.isSectionCompiledAndVisible`; 26.2 published unobfuscated members of the same names, plus `LevelLoadTracker.getPlayerCompiledSectionCallback`, `renderer.extract.LevelExtractor.extract`, and `LevelRenderer.render`.

In 26.1.2 the tracker synchronously checks the local player's block position. After its strict 30-second timeout check, outside-build-height, spectator, or dead players are ready; otherwise the player's section must be compiled and have evaluated visibility at least `0.3F`.

In 26.2 the ordinary tracker branch reads an atomic flag. A callback sets that flag after the renderer checks the camera render-state block position's compiled and visible section. The tracker additionally accepts when the current main camera is outside build height. These are distinct from the player's section predicate. The listener advances readiness before notifying the local player; `LocalPlayer.tick` uses the resulting loaded state to admit local simulation and movement packet work.

The accepted earlier-source chain establishes three historical variants:

- 1.21.4–1.21.8 Mojmap: `LevelLoadStatusManager.tick` checks outside build height, compiled player section, spectator, then death, in that short-circuit order. No tracker timeout or visibility threshold.
- 1.21.9–1.21.10 Mojmap: `LevelLoadTracker.WaitingForPlayerChunk.isReady` checks the strict 30-second timeout, then player eligibility before the compiled-only player-section query.
- 1.21.11 Mojmap and 26.1–26.1.2 published unobfuscated: retain the timeout and eligibility bypasses and require the player's section to be compiled and visible.

The 1.21.3 manager already has the compiled-only predicate, but has no later loaded-state local simulation gate. The implementation preserves the pre-existing native path through that profile. It does not emulate renderer production, scheduling, the camera, packet arrival, or server readiness. The separate historical 60-local-tick fallback remains an independently resolved operation.

Independent exact-source reports and audits are preserved in `development/research/source-evidence/20261029/player-chunk-readiness/`. Their mapping-root embedded manifests bind 1.21.6, 1.21.7 and 1.21.9 despite absent newer ready markers. Runtime timing and trajectories were not tested; compilation does not prove movement parity.

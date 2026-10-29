# Supplement 01: earlier 1.21 client player-section readiness

This supplement is additive. It does not alter the bytes of the existing readiness report.

**Bound original report:** `report.md`, SHA-256 `ff75dfeed8bc8a50a0771c7fdfb00ee68d6a2b33cc47e95828cddfb58c83faa1` (recomputed before this supplement; matches `report.sha256`).

## Result

**1.21.4 is not the earliest checked exact release with the direct compiled-section player readiness predicate. It is already present in 1.21.3.** In `LevelLoadStatusManager.tick()`, the `WAITING_FOR_PLAYER_CHUNK` branch reads `player.blockPosition()` and calls `levelRenderer.isSectionCompiled(pos)`; the same exact class bytes are present in ready Mojmap roots 1.21.3, 1.21.4, 1.21.5, and 1.21.8.

Keep this separate from the local simulation gate. In 1.21.3, the manager's ready predicate is passed to `ReceivingLevelScreen` and ticked by `ClientPacketListener`; the inspected `LocalPlayer.tick()` has no `hasClientLoaded()` guard and the inspected Player source has no `clientLoaded` state. In 1.21.4, the byte-identical section-readiness manager is additionally consumed by `ClientPacketListener.tick()` to send `ServerboundPlayerLoadedPacket` and set `Player.clientLoaded`; `LocalPlayer.tick()` then gates its ordinary body on `hasClientLoaded()` (with the 60-tick fallback). Thus the separate loaded-state/simulation-gate introduction is not the introduction of the compiled-section readiness predicate.

This establishes that the predicate exists by 1.21.3 and therefore refutes 1.21.4 as its earliest point. It does not locate the predicate's first-ever introduction before 1.21.3.

The requested 1.21.6 and 1.21.9 directories are **not ready source endpoints** in the canonical tree. Both lack a mapping-root ready marker and source manifest; 1.21.6 has only a generated `.decompile-provenance.json` with no exact-version/status/manifest identity fields, and 1.21.9 additionally has an in-progress `.mojmap.staging-7509591801405925509` directory. I make no source behavior claim for either version. Exact ready 1.21.4, .5, and .8 evidence closes the verified intervals around these unavailable endpoints without treating the missing snapshots as equivalent to either neighbor.

## Identity and hash-first pruning

Canonical read-only roots are `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\<version>\mojmap`. Each ready release below has an exact matching `versionId` and `versionMetadataId`; the computed source-manifest SHA-256 equals its ready marker. Cited Java-file hashes were recomputed and match the corresponding source-manifest path rows.

| Release | Ready state / file count | Source-manifest SHA-256 | Client SHA-256 in ready marker |
|---|---:|---|---|
| 1.21.3 | ready / 5,655 | `d673bb5464853e3a2a92ed9b1ffe1789d4e62c097bb884f67c336fcbb3b178ce` | `25190c556bc56d11070638ccab3b3c6efd76bb9684c92a2680a4de01c3c7138b` |
| 1.21.4 | ready / 5,744 | `f90b61197928632e061ea877955a19055c92ae6f357c2daf1bc646172c6f51f0` | `c17c450c6e72cc51297daa57ce38f800aa01cf022b743daa21a0512d326d894e` |
| 1.21.5 | ready / 5,921 | `365cc2d22446ceba0e36f46aa1c95ce4cc31514dbd657505680fb660f3adefd9` | `522672ad20b460c02c2e39b6c5035ef6a849af28eb11ab2ac7eb293d395a8c11` |
| 1.21.8 | ready / 6,105 | `8ffb76cea647a2ba4fe58e000678f751bea2c40ea6358bae492e962ed1d9d008` | `ea74e9c5e92d01f95f3d39196ddb734e19a077a58b4c433c34109487d600276f` |

1.21.3, .4, and .5 provenance sidecars all identify the exact Mojmap batch `1.21.1,1.21.3,1.21.4,1.21.5`; each ready marker independently identifies its own requested release. 1.21.8's sidecar identifies the exact Mojmap batch `1.21.8,1.21.10,1.21.11`. These are Mojmap trees, not mapping aliases.

Before opening the shared predicate body, I hashed `LevelLoadStatusManager.java` in all four ready roots. Every tree has the same manifest-verified digest, `2d871f1c73e8391e3fe049552702210566d49857d7d02d20b781f3148d75dbec`; I then read that common body once. `ClientPacketListener.java`, `LocalPlayer.java`, and `LevelRenderer.java` do not share whole-file hashes across these versions, so I checked their bounded call sites/method bodies separately. Their per-release hashes also match their respective source manifests:

| Release | `ClientPacketListener.java` | `LocalPlayer.java` | `LevelRenderer.java` |
|---|---|---|---|
| 1.21.3 | `169e1edaf666ebeb4ad736323565e28935a247748fecb67333dd5db53f24fb8a` | `fbd40f1f47adfa66dda9b15188e5dce82af3e8e8d7c3dd0543e602a354ad3fe0` | `ac55ec0da56f69a02edd8ad9298fe6abc9fb6da2f1f46de0fea6a31859035b3b` |
| 1.21.4 | `eef170dd22b5711e7d9527601592093a0456590f192b54fefef170b5791165b4` | `145686ebdc7f0d12a64070309073665eb8695b7e09911a723e86113a77d04611` | `2b0f0d330771d72b7b5f75f1a33e80d4496ac1260fc0000a9b8ed71f0148c297` |
| 1.21.5 | `284f34159a1baf30b6b540751ad2fa62344c179d5ed6a7bbcb5c888665779617` | `f1fcfed4a938732361e7ad951f93e9b73b02320ee56f0b219e2b3c7acdbfa2ef` | `d683c3f23fa6ef5cf0981d5c8a3e28448f4c757c22cb5bbafb5f36fedfc10595` |
| 1.21.8 | `a66556678a93c63c0ceb8953f25261b82bf3bb1971048fbc15b177c6dffea8cd` | `53f2a71a886b9c71853afe36f2df857f80a9bf1cd6ec4ca8604ccd7e238d89ee` | `581979a2ef91d93bd5d8cc28079ddeeeb76c76a419c3d5424eb94dbca00a5e63` |

`Player.java` hashes in 1.21.3 and 1.21.4 are respectively `a803203e92aa4729d5f5c9b16085b6a43ce51d9907d309eb96736e9c7c1340de` and `c45f41b9784ce50a88a498e84a13edcef0a23e175233d726ed7f90940f19ebc1`; each matches its source manifest. These two files were checked to distinguish the later player-loaded state from the earlier manager predicate.

## Predicate and caller chain

### Common predicate: 1.21.3, .4, .5, and .8

The byte-identical `LevelLoadStatusManager.java` (`:7–46`) stores `LocalPlayer`, `ClientLevel`, and `LevelRenderer`, and begins in `WAITING_FOR_SERVER`. Its `loadingPacketsReceived()` transition changes that state to `WAITING_FOR_PLAYER_CHUNK` (`:36–40`). In `tick()`, that state reads `BlockPos pos = player.blockPosition()` and marks `LEVEL_READY` when **outside build height OR `levelRenderer.isSectionCompiled(pos)` OR spectator OR not alive** (`:19–29`). For an alive, non-spectator player within build height, readiness therefore directly depends on the compiled predicate for the player's current block position. This is a synchronous player-position section poll, not a camera-position query and not a visibility callback.

The exact corresponding renderer operation is `LevelRenderer.isSectionCompiled(BlockPos)`:

- 1.21.3 `LevelRenderer.java:1302–1305`: look up `viewArea.getRenderSectionAt(pos)`; accept when non-null and `compiled.get() != CompiledSection.UNCOMPILED`.
- 1.21.4 `LevelRenderer.java:1277–1280`: same operation and condition.
- 1.21.5 `LevelRenderer.java:1276–1279`: same operation and condition.
- 1.21.8 `LevelRenderer.java:1245–1248`: same operation, represented by `sectionMesh.get() != CompiledSectionMesh.UNCOMPILED`.

None of these methods applies the later `getVisibility(...) >= 0.3F` threshold. The exact source call graph reaches the common predicate as follows:

- In 1.21.3, `ClientPacketListener.handleGameEvent` forwards `LEVEL_CHUNKS_LOAD_START` to `levelLoadStatusManager.loadingPacketsReceived()` (`ClientPacketListener.java:1448–1449`). `startWaitingForNewLevel(...)` creates the manager with the local player, level, and renderer and passes `levelLoadStatusManager::levelReady` to `ReceivingLevelScreen` (`:1453–1455`). `ClientPacketListener.tick()` calls `levelLoadStatusManager.tick()` (`:2452–2454`). This is the manager/screen readiness path; the listener tick does not set a player-loaded flag in 1.21.3.
- In 1.21.4 the event, manager construction, and screen callback call sites are byte-level analogous (`ClientPacketListener.java:1448–1455`). Its listener tick polls the same manager, then, when `levelReady()` and `!minecraft.player.hasClientLoaded()`, sends `ServerboundPlayerLoadedPacket` and calls `setClientLoaded(true)` (`:2458–2463`).
- 1.21.5 preserves the same structure at `ClientPacketListener.java:1464–1471` and `:2482–2487`; 1.21.8 at `:1528–1535` and `:2589–2594`. The predicate implementation is already pruned to the same manager bytes, while caller locations are verified separately because the listener files differ.

### Separate local simulation gate

The exact 1.21.3 `LocalPlayer.tick()` calls `super.tick()` and proceeds into local input/movement packet handling without a `hasClientLoaded()` branch (`LocalPlayer.java:188–212`). Its Player source has no `clientLoaded` or timeout fields/methods. In 1.21.4, `LocalPlayer.tick()` first calls `tickClientLoadTimeout()` and enters its ordinary body only inside `if (hasClientLoaded())` (`LocalPlayer.java:191–217`). `Player` introduces `clientLoaded` and a 60-tick timeout; `hasClientLoaded()` is `clientLoaded || clientLoadedTimeoutTimer <= 0` (`Player.java:167–168,2096–2109`). The identical `LevelLoadStatusManager` predicate existed before this simulation gate.

## Comparison to the bound 1.21.10–26.2 report

The original report hash above is the evidence binding for its exact-source comparison. Its 1.21.10 method is `LevelLoadTracker.WaitingForPlayerChunk.isReady()` (`LevelLoadTracker.java:112–127`): it too reads `player.blockPosition()` and, on the ordinary in-height/alive/non-spectator path, calls `LevelRenderer.isSectionCompiled(pos)`. The state object and timeout structure changed from the older `LevelLoadStatusManager`, but the section query remains the direct player-position/compiled-only form at 1.21.10. The bound report records the exact source manifest and per-file identity for that endpoint.

By 1.21.11, the same ordinary direct player-position poll instead calls `isSectionCompiledAndVisible(pos)` and requires the evaluated visibility threshold; the 26.1/26.1.1/26.1.2 roots retain that form. 26.2 then moves the compiled-and-visible query into the renderer callback path and keys callback production from `cameraRenderState.blockPos`, while the tracker consumes an atomic flag; see the bound report's 26.2 method trace. Accordingly, the sequence supported by the ready evidence is:

`1.21.3, 1.21.4, 1.21.5, 1.21.8` (the ready endpoints checked): direct compiled-only predicate at player block position → `1.21.10`: same direct compiled-only player-position predicate in `LevelLoadTracker` → `1.21.11–26.1.2`: direct compiled-and-visible player-position predicate → `26.2`: compiled-and-visible renderer callback predicate at camera render-state position. Exact 1.21.6 and 1.21.9 behavior is unavailable from ready source here.

This supplement establishes early presence and the verified endpoint distinctions only. It does not claim exact introduction between pre-1.21.3 versions, behavior for unready 1.21.6/1.21.9, rendered output, runtime scheduling, packet timing, or movement trajectory. No product/history/wiki work, source generation, build, test, launch, or Git operation was performed.

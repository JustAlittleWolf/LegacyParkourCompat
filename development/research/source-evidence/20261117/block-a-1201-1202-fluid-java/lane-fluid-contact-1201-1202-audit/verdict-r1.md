# Independent verdict r1 — LocalPlayer ordinary water/lava contact, 1.20.1 → 1.20.2

## Bound inputs

- Corrected author claim: `C:/Users/Wolfi/.codex/visualizations/2026/11/17/01a1eb7b-ac27-7712-9259-3041950bc340/lane-fluid-contact-1201-1202/comparison.corrected-r1.md`
- Verified exact claim SHA-256: `74afdce1697062534c51aad9b6c8a9efff5d6b8ae8ff5e6519d6315cbf57b488` (matches assignment).
- Original frozen roster and verdict are preserved. Original roster SHA-256: `b67fe32d009a9002d0683f241d12390f1a61244f21715b3969394e4810949fc7`; original verdict SHA-256: `6ff8e836720be2a4d3394b9708eab132fa076417f43a30ed4631216c12f82c2d`.
- Pre-claim source/provenance addendum: [`source-roster-addendum.md`](source-roster-addendum.md), SHA-256 `cd8c5bf193416dc8bc35764b12a1a8cbe3cc740508aa425ea440b76e1346e75b`.
- Exact source root: `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/<release>/mojmap/`.

## Source provenance verification

For both releases, the version-root ready marker, provenance, source manifest, artifact manifest, and diagnostics raw hashes match the corrected claim and the pre-claim addendum. The ready marker names the correct exact `versionId` and `mojmap` mapping; its source-manifest and artifact-manifest digests equal the raw hashes of those files. The source file hashes recorded in the frozen rosters match their version's source-manifest entries. Provenance binds the Mojmap decompile run and toolchain. No source generation or modification occurred.

## Verdict by assigned slice

### 1. Body admission and current push — no difference in the verified Java operations

The original verdict remains bound to the same unchanged `Entity`, `LivingEntity`, `LocalPlayer`, `FluidState`, and `FluidTags` bytes. The corrected claim's additional frozen Java evidence closes the previously open Player override, chunk preflight, and `FlowingFluid.getFlow` operations:

- `Entity.isPushedByFluid()` returns `true`; `Player.isPushedByFluid()` returns `!this.abilities.flying` in both releases. `LocalPlayer extends AbstractClientPlayer extends Player`, and its constructor takes `ClientLevel`, so a non-flying LocalPlayer uses the Player override and passes the pushed-flow branch. This dispatch chain is unchanged. (Entity.java 2565 / 2603; Player.java 1874–1876 / 1878–1880; AbstractClientPlayer.java 26 / 18; LocalPlayer.java 88, 136 / 88, 135.)
- `LevelReader.hasChunksAt` is byte-identical. Its four-bound overload converts the X/Z bounds to section coordinates and checks the inclusive rectangle. `ClientLevel.hasChunk(int,int)` returns `true` in both releases. Therefore `Entity.touchingUnloadedChunk()` does not reject LocalPlayer sampling because horizontal client chunks are absent. The separate 3-D overload's build-height guard is not on this four-bound call path.
- For scan cells within build height, `Level.getFluidState` reaches `getChunkAt` and then the client chunk source. The `LevelReader` full-chunk defaults pass `load=true`; `ClientChunkCache.getChunk` returns its `EmptyLevelChunk` when a loaded valid chunk is unavailable and loading is requested. `EmptyLevelChunk` returns `Blocks.VOID_AIR.defaultBlockState()` and `Fluids.EMPTY.defaultFluidState()`. Those fallback bodies and the Level forwarding methods are identical across the pair. Out-of-build-height fluid lookups return empty directly in `Level.getFluidState`.
- `FluidState.getFlow` invokes `this.getType().getFlow(level, pos, this)` and is byte-identical. `FlowingFluid.getFlow`, `affectsFlow`, and `isSolidFace` have the same expressions and order in both releases: neighbor-fluid filtering by empty/same-fluid identity, own-height differences, lower-neighbor adjustment below a non-motion-blocking side, falling-flow solid-face handling, and final normalization. `FlowingFluid.java` as a whole differs, but the directly compared flow methods do not.
- The existing entity scan/aggregation and water/lava refresh order remain unchanged as recorded in the original verdict. These paired Java operations show no movement difference.

The corrected claim additionally relies on `WaterFluid.java`, `LavaFluid.java`, and/or fluid registration to bind the concrete ordinary water/lava types to the shared `FlowingFluid` implementation and prove there is no concrete override. Those files were not in the frozen pre-claim roster, so that exact concrete-fluid subtype binding remains an open Java dependency in this independent verdict. The Player/LocalPlayer subclass dispatch above is directly checked and closed.

### 2. Water/lava vertical response and upward collision exit clearance — no difference

Rebound from the original verdict to the unchanged paired `LivingEntity`/`Entity` file hashes, with provenance and manifest entries now verified. The same controlled-local-instance gates, water and lava vertical math, fluid-fall adjustment, clearance displacement (`velocityY + 0.6F - currentY + priorY`), `0.3F` success velocity, and `Entity.isFree` conjunction of collision-free and liquid-free probes remain present in the same order. No difference was found in these reviewed operations.

### 3. Depth-cache handoff to held-jump dispatch — no difference

Rebound from the original verdict to the unchanged paired source files. `Entity.baseTick` refreshes fluid depth before the LivingEntity jump path; LocalPlayer's input handoff and LivingEntity's cache-consuming held-jump dispatch retain the same source order and branches. No difference was found.

## Separate unresolved dependency and coverage boundary

Effective resource/data membership of the `minecraft:water` and `minecraft:lava` tags remains unresolved. Java tag declarations and the checked tag gates do not establish versioned tag JSON membership.

This remains bounded pair evidence, not full-pair coverage/freeze, runtime parity, tag applicability acceptance, or implementation acceptance. The concrete WaterFluid/LavaFluid subclass binding noted above is the remaining Java closure gap for slice 1; do not infer it from the FluidTags Java declarations.
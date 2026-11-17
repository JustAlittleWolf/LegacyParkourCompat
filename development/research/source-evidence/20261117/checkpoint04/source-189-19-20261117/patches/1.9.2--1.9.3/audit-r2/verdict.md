# Independent blind source audit r2 — 1.9.2 → 1.9.3

## Verdict: REQUEST CHANGES

This verdict is bound to the immutable source-coverage report `coverage-r2.md` (SHA-256 `C2BC2D53FDF47D36D779FD2A660F7A21050DD7DA8A734EE48778EDC1CB88007A`) and candidate `player-tick-world-limit-r2.md` (SHA-256 `B4B0ED48F380356ABCD47663C1B656A2EEFC39738EE5DE745B54102960456514`), against the exact `ornithe-feather` source manifests: 1.9.2 SHA-256 `9928b467deb35c7e7f891b4fee1627a68feecdf233c2076909fd4834b0380510`; 1.9.3 SHA-256 `43325cdd53ca34b88ee9af8464782b323a948b98c8a556c4761f1e5737b84c3a`.

## Verified

- Both source manifests contain 1,819 rows. Every listed source path exists exactly once, no unlisted Java sources are present, and all source hashes match. The paired inventory is 1,816 shared paths, three removed, three added, with 621 differing same-path hashes. The removed and added path counts and names in the coverage report match the manifests.
- The reported local tick gate is exact: `LocalClientPlayerEntity.tick()` queries `isChunkLoaded(new BlockPos(x, 0.0, z))` before `super.tick()` in both versions. In 1.9.2 `World.isChunkLoaded(BlockPos, boolean)` rejects `!contains(pos)`; in 1.9.3 it directly calls `isChunkLoadedAt`. The one-argument overload passes `true`, and both `ClientWorld.isChunkLoadedAt` bodies return true for `allowEmpty`.
- `PlayerEntity.tick()` sets `noClip = isSpectator()` before inherited movement and clamps x/z to `[-29,999,999, 29,999,999]` after it, in both versions. Thus for an already out-of-range local player, the stated skipped-tick versus admitted-tick/same-tick-clamp observation is supported. The candidate correctly bounds this to a one-tick difference and does not claim continuing movement beyond the limit.
- The reported `LiquidBlock` flow-helper refactor is described as equivalent on a stable same-tick world state, rather than promoted to a separate movement claim. The coverage report identifies and excludes the accepted pane/piston source claims as instructed.

## Open gate

The pair coverage does not disposition another changed world-bound guard that reaches the player collision route. In 1.9.2, `World.getBlockState(BlockPos)` returns air when `contains(pos)` fails, including x/z outside `[-30,000,000, 30,000,000)`. In 1.9.3 it returns air only for out-of-height positions and otherwise reads the chunk. `World.getCollisions` calls `getBlockState` for collision cells when the entity is outside the world border (`!bl2`); `Entity.move` uses that collision query. The newly admitted local tick can therefore reach a player movement path whose block-state lookup semantics also changed. The reports do not establish whether a loaded non-air block state can be observed there, whether the provider always yields an empty chunk, or whether this changes any movement output.

Before pair freeze, compare this paired `getBlockState`/`getCollisions`/`Entity.move` path for an out-of-border non-spectator local player, including the client chunk-provider result, and disposition the changed horizontal-bound guard (and its direct movement-reachable callers). If it is provably movement-neutral, add that evidence to coverage; otherwise report the concrete additional difference and its bounded movement effect. This is an unresolved source-coverage gate, not a claim that a second movement difference has already been demonstrated.

## Exact source anchors

- `decompiled_minecraft/1.9.2/ornithe-feather/net/minecraft/world/World.java`: `isChunkLoaded` lines 176–181; `getBlockState` lines 614–619; `getCollisions` lines 893–930.
- `decompiled_minecraft/1.9.3/ornithe-feather/net/minecraft/world/World.java`: `isChunkLoaded` lines 182–187; `getBlockState` lines 621–626; `getCollisions` lines 899–931.
- `decompiled_minecraft/1.9.2/ornithe-feather/net/minecraft/client/world/ClientWorld.java`: `isChunkLoadedAt` lines 103–105; `decompiled_minecraft/1.9.3/ornithe-feather/net/minecraft/client/world/ClientWorld.java`: lines 104–106.
- `decompiled_minecraft/1.9.2/ornithe-feather/net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java`: `tick` lines 142–156; 1.9.3: lines 143–157.
- `decompiled_minecraft/1.9.2/ornithe-feather/net/minecraft/entity/living/player/PlayerEntity.java`: `tick` lines 169–231; 1.9.3: lines 170–232.

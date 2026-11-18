# Independent audit: lava scan upper boundary, 1.12 → 1.12.2

## Terminal verdict

**FAILED as a hash-bound evidence package; the movement no-difference conclusion is independently supported.** The immutable comparison contains one source-hash transcription error. The source file itself matches the published source manifest, but the table does not identify its actual bytes. Fixing that requires a new comparison revision and a new audit identity.

## Exact identities

- Comparison: `lane-lava-scan-upper-bound-112-1122/comparison.md`, SHA-256 `6ac5b6a735465425f2f497ae9d53f1f62040673f4b396caf9a8fc3f3887a8fa8`; valid UTF-8, LF, no BOM.
- Frozen source roster: `source-roster.txt`, SHA-256 `3809e91baf8fb719b71d86a975d2271cc5c929511d10470609bfbb7fa16105ba` (13 files, frozen before opening the comparison).
- Supplemental dependency: `supplemental-roster.txt`; MathHelper was added after the comparison identified `ceil` as a dependency.
- 1.12 provenance: `.decompile-provenance.json`, SHA-256 `27515e5f4c71e7db2f035a81716507128d77466c75380ad1ef7d140c06401467`; 2,048 source entries and 2,048 source files, all source hashes verified against the manifest.
- 1.12.2 publication sidecars: `ornithe-feather.provenance.json` SHA-256 `747425a26da363d1abc8a2cecd83a74504aeb1f58be93aa53f48c690e35318ce`; `ornithe-feather.ready.json` SHA-256 `b0aeec721e5c0af02b33d9e217cb8272889fa139ded1ecc2a31e42b930138f7a`; `ornithe-feather.sources.sha256` SHA-256 `b8a37ccfccd2aac5f40f5e34fec85873dbdfa93a266043c103e592e4a8c949da`; `artifacts.sha256` SHA-256 `8171a095a5ee74cebbd12b3d76983fc01796c0c23c962076d1e235cd0f80fa8c`. Ready-marker digests match both manifests. All 2,050 listed source files match their hashes.

## Hash blocker

For `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java` (1.12.2), the comparison table states:

`01a58e94d8c6ff98a8e3794227cdc76a5fcdbbad795c70c9cf28854aff9823cc`

Programmatic SHA-256 of the published file and its source-manifest value:

`01a58e94d8c6ff98a8e3794227cdc76a5fcbdbad795c70c9cf28854aff9823cc`

These differ (`...a5fcdbbad...` vs `...a5fcbdbad...`). The other six rows in the comparison's exact-source hash table match programmatic hashes.

## Movement claim and gate audit

The bounded no-difference claim is correct for the examined path. Exact method bodies match for `Entity.isInLava`, `World.containsMaterial`, `World.getBlockState`, `World.getChunk`, `World.isChunkLoaded`, `MathHelper.ceil`, `LivingEntity.mobTick`, `LivingEntity.moveRelative`, `PlayerEntity.moveRelative`, `LocalClientPlayerEntity.tick`, and `ClientWorld.isChunkLoadedAt`. `Box.java`, `Entity.java`, `LivingEntity.java`, `MathHelper.java` are also whole-file identical by SHA-256; `World.java`, `PlayerEntity.java`, and `LocalClientPlayerEntity.java` differ elsewhere, while the compared methods match.

`containsMaterial` uses `[floor(min), ceil(max))` in X/Y/Z nested-loop order and direct material equality. Thus `ceil(maxY)` is exclusive as the loop limit: a block at an integral `maxY` coordinate is not visited; a nonintegral upper edge includes the containing integer cell. The entity query shrinks its shape by `(-0.1F, -0.4F, -0.1F)`. `getBlockState` returns AIR for Y below 0 or at/above 256, then obtains the chunk through `getChunk`/`getChunkAt`; the scan itself has no per-cell `isChunkLoaded` check.

The local tick has an outer reachability gate: `LocalClientPlayerEntity.tick()` calls `super.tick()` only when the chunk containing `BlockPos(x, 0, z)` is loaded. `World.isChunkLoaded` computes chunk coordinates with `>> 4`; `ClientWorld.isChunkLoadedAt` is the client implementation. This gate is unchanged at both endpoints and should be stated in the comparison's reachability coverage. After that gate, `LivingEntity.tick()` dispatches virtual `mobTick()`: local-player/player overrides delegate to the living implementation, whose jump-query path requires jump input and water precedence; its travel `moveRelative` path is additionally gated by local/server movement ownership, water precedence, and player flight. The relevant movement body and operation order are unchanged.

The 1.12.2 local player adds an inventory-screen close check in the portal branch before `super.mobTick()`; this is outside the lava scan and does not change the compared scan or travel method bodies. The comparison's omission of the outer loaded-chunk tick gate is a coverage correction, not a contrary movement finding.

Endpoint evidence only: no 1.12.1 boundary is established. No other movement areas were reviewed.

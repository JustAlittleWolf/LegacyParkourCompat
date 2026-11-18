# Corrected revision audit: lava scan tick and missing-chunk gates

## Terminal verdict

**ACCEPTED for the bounded claim.** The corrected comparison has the assigned SHA-256. Its lava-scan no-difference conclusion remains supported. The outer `LocalClientPlayerEntity.tick()` condition is syntactically present, but it is not an effective loaded-chunk gate: `World.isChunkLoaded(pos)` passes `allowEmpty=true`, and `ClientWorld.isChunkLoadedAt` short-circuits to `true` before asking the chunk source. Missing block chunks are handled separately during the lava scan: the client chunk cache supplies an `EmptyChunk`, whose `getBlockState` returns AIR.

The previous audit's wording that tick proceeds “only when the chunk ... is loaded” was inaccurate. This additive revision clarifies that statement; `audit-verdict.md` and the frozen roster were left unchanged.

## Programmatic identities

- Corrected comparison: `comparison.corrected-r1.md` SHA-256 `479ac237d70c523b4c1ea0320eac3e3e9b758440075ea3e9b9da3625c7e906df` (matches assigned identity; valid UTF-8, LF, no BOM).
- Prior verdict: `audit-verdict.md` SHA-256 `7f7ce72abc9b29b44187b66b3a195da5f06b3f854aa644186f75f2fecd44ea2d`.
- Frozen roster: `source-roster.txt` SHA-256 `3809e91baf8fb719b71d86a975d2271cc5c929511d10470609bfbb7fa16105ba`.
- Supplemental roster: `supplemental-roster.txt` SHA-256 `1e080f2ef5fa6710deccc0b196015fa504571bc84e50313a51796e0bd2e36572`.

## Cited exact-source file SHA-256

| Relative source path | 1.12 | 1.12.2 |
| --- | --- | --- |
| `net/minecraft/world/World.java` | `f7ab7a34d456e720e7ee4f34fbd0eb10f7f96151596c8c615ab2edef05a884d8` | `e9fa9b8d6d31ad57a5b876f5f63a5e3c554a23437decf1845a493daf48233594` |
| `net/minecraft/client/world/ClientWorld.java` | `996286a2b9a14b7396f1ff3501c0a2494af67534930ad5e9a0bbf5e88f12e01f` | `468def1eb1bd0791ce1d8b194ba22fe0fe49962bb3e696fb34b83bf4de7fb72c` |
| `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java` | `d7237b8b9a68525bbfec6af1c234d2aafb1e4aa7d52059331d1d243313d6d7d1` | `01a58e94d8c6ff98a8e3794227cdc76a5fcbdbad795c70c9cf28854aff9823cc` |
| `net/minecraft/client/world/chunk/ClientChunkCache.java` | `235599d5eed2386ce4a5ed282ee893df15d8a79ff3c301f17ff3a6b45796e206` | `235599d5eed2386ce4a5ed282ee893df15d8a79ff3c301f17ff3a6b45796e206` |
| `net/minecraft/world/chunk/EmptyChunk.java` | `ffc3414f16ccb2b1523a7b9b2c9adfc2946c006bdb97b83c3c29f405ee5f8fd0` | `ffc3414f16ccb2b1523a7b9b2c9adfc2946c006bdb97b83c3c29f405ee5f8fd0` |

## Semantic trace

At `LocalClientPlayerEntity.tick()` (1.12 line 164; 1.12.2 line 165), the `if` encloses `super.tick()`. Its predicate calls `World.isChunkLoaded(BlockPos)`, which delegates to `isChunkLoaded(pos, true)`; that overload derives chunk X/Z using `>> 4`. Client `ClientWorld.isChunkLoadedAt` returns `allowEmpty || !getChunkSource().getChunk(...).isEmpty()`. With `true`, Java short-circuits the `||`, so the predicate returns true even when the requested chunk is absent. It does not ask `ClientChunkCache` whether that chunk is loaded. This predicate therefore does not suppress the local entity tick for a missing chunk.

The lava scan's block lookup has separate behavior. `World.getBlockState` returns AIR outside Y `[0, 256)`; otherwise `World.getChunk`/`getChunkAt` calls the client world’s `ChunkSource`. `ClientWorld.createChunkCache()` installs `ClientChunkCache`. Its `getChunk(x,z)` returns a loaded chunk or its `empty` sentinel (`EmptyChunk`). `EmptyChunk.getBlockState` returns `Blocks.AIR.defaultState()`. Thus a lava scan cell in a missing chunk reads as air, and cannot match `Material.LAVA`; this is per-cell fallback during the scan, not a local-player tick gate.

The examined bodies match exactly across endpoints: `World.isChunkLoaded(pos)` SHA-256 `8ab16ea459b9d491e3feaacae7a70050661b9145cbeb1a474a4d81909b5078af`; its overload SHA-256 `ddbb821c5165daefa60871c6b46f85a15efc2d2720aedd46e56443a8ed813898`; `ClientWorld.isChunkLoadedAt` SHA-256 `a944067860e656a3cae543330e1f433642911e6c8f4c99fd455b49fbfc11a949`; local player `tick()` SHA-256 `522084d075dd61a0c53b236f3507f230172463bf1fb02bb9e5bc5b7012bc1590`; `World.getBlockState` SHA-256 `6c16d7941df1587e743d282519a560dc38c1364ae5a33f3c94e4136f3a3fade2`; `World.getChunk` SHA-256 `8ed7170247a27ebe3c0bbee845f465dc751bd985efd7f1dd07bd0d0be97d31d2`; `World.getChunkAt` SHA-256 `56acc21727814912b949ca2f9eecf2d4a5a5692a8b4a085e4c02ff3344926b7f`; `ClientChunkCache.getChunk` SHA-256 `34763a12f4522fae33467d65d802d7f416545d1a984b08380342ddfc5def9d76`; and `EmptyChunk.getBlockState` SHA-256 `a608eba14378d045787f64c9bb2f456b69c2ee0e55eae3d47b3e77c88699ab4e`.

No difference was found in these gate or missing-chunk semantics from 1.12 to 1.12.2. This remains endpoint-only evidence.

# Raw-bytecode water contact supplement: 1.8.0 and 1.8.1

## Scope and method

This read-only supplement checks the exact Mojang 1.8 and 1.8.1 client class files for the bounded water body-contact scan, the scan's vertical and chunk admission, and its client-cache fallback. It uses the raw client JARs directly with `javap -p -c -s`; it does not use the cached `client-ornithe-feather.jar` outputs. No class was executed. No source was generated, no decompiler/Gradle/build/test/runtime was launched, and no cache or canonical source was modified.

The inspected operations are `Entity.checkWaterCollisions`, `World.applyLiquidDrag`, `LiquidBlock.getHeightLoss`, `World`'s per-position containment and area-loaded gates, `ClientWorld`'s chunk-cache construction, `ClientChunkCache`'s `ChunkSource` methods, and `EmptyChunk`'s block-state result. The comparison baseline is the published, provenance-bound Feather source closure for 1.8.2–1.8.9. This report does not claim full-pair coverage or runtime parity.

## Exact raw input and mapping identity

The cache pins, version JSON and raw client JAR SHA-256 values were rechecked locally. Each `downloads.client.sha1` in the exact version JSON agrees with the client object ID; the local cache pin files also hash-match the JSON and JAR. The Feather metadata pins resolve to build `.2`, and the named mapping files below match the Tiny payload hashes reported for their mergedv2 artifacts.

| Release | Version JSON SHA-256 | Client object SHA-1 / local JAR SHA-256 | Feather Gen2 mergedv2 SHA-256 | Tiny `official / intermediary / named` SHA-256 |
|---|---|---|---|---|
| 1.8 | `8be392d3e8b3e61500b60eb141de56519299069c9169081483ea5aae9fa9d593` | `d722504db9de2b47f46cc592b8528446272ae648` / `ccccd0a92f76fbb24279691daad9b67f85d80471a37e59e0da76d7754982dfe8` | `3c020d1588b4aab403f5df077ca91b6e3776da2823d3b55202f4616f0d5ae5fe` | `0db2fa38dc74a0981164e53fc73152291cea5153e705b5d06d001ede01b682e8` |
| 1.8.1 | `8c6a1660665a2b1551b628f819552ed5ba0327cbb8045fb266f3fdc3e98db149` | `6edd2a3e3d76a7602c52f319f87cf11f7720b43b` / `66a749ad95adcb929439155c0341309e89e437444867e108dcb36fd16493b0bd` | `dac40cbd25c4d76d99d4263fd9754ab0195efd658756b6828fd06f0c4a8002f7` | `f26e3fea2c580cf9eb2d5c47cd32a071f72177ba5e2c208c39196e669c8a383d` |

The exact Feather metadata pin hashes are `83ac5ccbc65ec6496a6b4a54413ee22082f60bc99c74ea7b20f68b97acc0be7b` (1.8) and `40754754c7133b6e01354a49e8c8ba6be2b16bac02adc881057831b6b543b7c9` (1.8.1). Both metadata lists select `1.8[.1]+build.2`. Both Tiny payloads declare namespaces `official`, `intermediary`, `named` in that order.

The exact Tiny class entries bind each raw official class in each client JAR to the same intermediary class ID and named class:

| Named class | 1.8 raw official / intermediary ID | 1.8.1 raw official / intermediary ID |
|---|---|---|
| `World` | `aqu` / `C_64041439` | `aqr` / `C_64041439` |
| `Entity` | `wv` / `C_42232651` | `wx` / `C_42232651` |
| `LiquidBlock` | `axl` / `C_13709197` | `axj` / `C_13709197` |
| `ClientWorld` | `cen` / `C_34399650` | `cem` / `C_34399650` |
| `ClientChunkCache` | `cel` / `C_30731159` | `cek` / `C_30731159` |
| `ChunkSource` | `bfe` / `C_44627882` | `bfc` / `C_44627882` |
| `EmptyChunk` | `bfg` / `C_93318205` | `bfe` / `C_93318205` |

Tiny also maps `Entity.checkWaterCollisions()Z`, `World.applyLiquidDrag(AABB,Material,Entity)Z`, `LiquidBlock.getHeightLoss(I)F`, and `World.isAreaLoaded(IIIIII,boolean)Z` to the raw descriptors inspected below. The `ChunkSource.hasChunk(II)Z` interface mapping identifies the `ClientChunkCache` implementation with that descriptor; its exact body returns `true` in both raw client classes.

## Raw bytecode observations

**Contact query.** In both raw `Entity` classes, `checkWaterCollisions()Z` builds the query from the entity box, moves it down by the float constant `0.4000000059604645`, contracts all three axes by `0.001`, and calls the water `World.applyLiquidDrag` overload with the entity. A positive return resets fall distance, sets the in-water field and clears the liquid-related counter in the same order; a negative return clears the in-water field. The method bodies have the same relevant constants, call order and state writes in 1.8 and 1.8.1.

**Scan bounds and result.** Raw `World.applyLiquidDrag(AABB,Material,Entity)Z` in both releases calculates each minimum as `floor(min)` and each exclusive maximum as `floor(max + 1.0)`, then calls the private area-loaded method with those six bounds and `allowEmpty=true`. Its three loops are half-open (`cell < exclusiveMax`). For each visited cell it checks material equality, computes the liquid top as `(cellY + 1)` converted to float minus `LiquidBlock.getHeightLoss(level)`, and accepts contact when the integer exclusive maximum-Y bound is at least that surface. Flow accumulation/current writes follow the accepted-cell test. 1.8 allocates a new block position in the inner loop; 1.8.1 reuses a mutable position. This allocation change is noted; the inspected bounds, gate, surface comparison and contact result condition match.

`LiquidBlock.getHeightLoss(I)F` in both releases changes levels `>= 8` to zero and returns `(level + 1) / 9.0F`. Thus the level-8-and-up liquid-height behavior in this operation is explicit in both raw inputs.

**Vertical range and individual out-of-height reads.** The private `World.isAreaLoaded(IIIIII,boolean)Z` rejects when `maxY < 0` or `minY >= 256`, then shifts each horizontal endpoint right by four and iterates the inclusive chunk-coordinate rectangle. It passes the caller's `allowEmpty` boolean to the chunk-loaded predicate. In `applyLiquidDrag`, that value is `true`.

The cell read used by the scan first calls the world's per-position containment check. In both raw worlds this requires `0 <= y < 256` (as well as the horizontal world border); a failing position returns the block state's `Blocks.AIR` default instead of reading a chunk. Consequently, an area overlapping the valid vertical span can still scan its bounded cells, but individual cells below zero or at/above 256 cannot contribute water. The separate area gate rejects a scan wholly outside the vertical span.

**Client chunk admission and missing chunks.** The raw `ClientWorld` chunk-source factory constructs its `ClientChunkCache`. `ClientChunkCache` implements the mapped `ChunkSource.hasChunk(II)Z` method with an unconditional `true`. Its `(II)Chunk` lookup returns the cache's shared `EmptyChunk` when the keyed chunk is absent and returns the cached chunk otherwise. In both raw `EmptyChunk` classes, `getBlockState(BlockPos)` returns the static `Blocks.AIR` block-state value. With `allowEmpty=true`, `World.isAreaLoaded` accepts the client coordinates through `hasChunk`; the subsequent state read through an absent chunk is air. This matches the client-side empty-cache path described by the accepted source audit; it is not a real loaded-chunk requirement.

## Comparison with published source evidence

The immutable canonical audit `.native-source/20261028/liquid-scan-18-boundary/REPORT.md` has SHA-256 `8713c683ab2550b831de03d1dcea024fdc63277c43c81ecebc96d4114fc3ba9c`. It independently binds the relevant `World`, `Entity`, `LiquidBlock`, `ClientWorld` and `ClientChunkCache` source files across exact published Feather 1.8.2–1.8.9 trees to their provenance/source-manifest identities. Those source files are byte-identical across that range. It records the same `floor(max + 1.0)` bounds, vertical admission (`maxY >= 0 && minY < 256`), water-height rule and client empty-cache behavior.

The older cached-bytecode audit `.native-source/20261028/liquid-scan-180-181-cached-audit/REPORT.md` has SHA-256 `1eefff2c059a2b92cc71283b573d544e19790f4b63931a2f06d3a595edb722d7`; its observations came from mapped output JARs that lacked output provenance and are not relied upon here. This raw supplement independently binds its observations to the exact raw client JARs and pinned Tiny maps.

## Bounded conclusion

The exact 1.8.0 and 1.8.1 raw client classes, mapped through the pinned Feather Gen2 build `.2` Tiny inputs, establish the specified water body-contact scan, floor-plus-one bounds, surface/level calculation, vertical gates and client empty-chunk path. For these bounded operations, 1.8.0 and 1.8.1 agree with each other and with the independently provenance-bound 1.8.2–1.8.9 source closure. This closes the earlier identity/applicability gap for the body-contact and area/chunk-admission slice across the named releases; it does not establish every intervening release, close other fluid behaviors, or claim runtime parity.

No source generation, cache/canonical source writes, network lookup, class execution, decompiler, Gradle, build, test, game or runtime was used.

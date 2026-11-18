# Independent audit revision 1: dry oak fence in falling-water flow

**Verdict: ACCEPT, bounded.** The author's conclusion that this cue and the local-player water-current path have no 1.8.2→1.8.4 behavior change is supported by the independent comparison recorded in `terminal.md`. This verdict covers only the named slices; it does not establish pair-wide tick/inventory coverage. This revision repairs only the evidence encoding reference; no source re-review was performed.

## Immutable inputs and encoding recovery

- Original roster: `roster.json`, SHA-256 `388f728b1d336fae8c33bee7b865deb543bd22a91c3b9fa097abc145fe639859`. The original bytes are preserved unchanged. It is UTF-8 without BOM and contains 432 CRLF pairs.
- Normalized roster: `roster.lf.json`, SHA-256 `1d46a5f0f270367d6c1d2ffab96301c2673dbb67aa9e43cd79b2139c2d59cd3c`. Produced by replacing each CRLF pair with LF only. It is UTF-8 without BOM, has zero CRLF pairs, parses as JSON, and has the same parsed value and top-level keys as the original.
- Original independent verdict: `terminal.md`, SHA-256 `8c1eadd8fc7e520a3d43705beb60f03f1d8caa3a34597b5828a9bbd9cac56ee8`.
- Author input: `lane-falling-water-fence-182-184/terminal.md`, SHA-256 `bcda12239923a09615c52dce5ed1cb06dca0d94168c89b9d2ebb4d99f0efce23`.
- Exact source roots: `decompiled_minecraft/1.8.2/ornithe-feather/`, `1.8.3/ornithe-feather/`, and `1.8.4/ornithe-feather/` in the primary local project.
- Provenance manifest SHA-256 values: 1.8.2 `8ad11ea36f2dda39ae30d57daa53316a8c0889a2f3c0c84ee5d79b9b21c25472`; 1.8.3 `7da5f772d7ed1433cb3da23861723ebaec7cb714ce49c4d1a5c30499fec3afed`; 1.8.4 `b2207bf9ab2a7561bf73ac92a63cc67760ccc6fd9e2378ff1e10710ecbb03a00`.
- The manifests identify Feather Gen2 `1.8.2+build.2`, `1.8.3+build.2`, and `1.8.4+build.2` merged-v2 inputs. Client SHA-256 values are respectively `5cc5b24312f3d182bf06e7f1a4da3a05ce94be6c861d5c9995c86e6d8811fbe9`, `4da43d06c8c1605a9a3ba1bf22893245cf1b108eb5c8fed859012252370c2c06`, and `d68a3aa5271abece4ce2b7048f6caf4f58071476344d9a52fe9ab1410c5d458b`; Feather artifact hashes are `8235d85a8471d541f2bad73c2a60c6132a9432fe804b98369982fc8de847f37b`, `e0ad2917dcc3a91586fb6f9b1bde78e55f1ece908572672885395e0d045dd9a2`, and `8ee5582ac3f6581e85ac27de5c9df9427a4bf67d0652531cf2e94eac4dde9b8c`.
- `roster.lf.json` preserves the original roster's exact source paths and per-release SHA-256 values. The listed files matched their provenance source entries and were byte-identical across all three releases.

## Per-slice verdict

**Dry ordinary oak fence cue: ACCEPT.** `Block.java` registers ordinary `fence` as a `FenceBlock` with `Material.WOOD`. `Material.WOOD` is a base `Material` with flammability; its inherited `blocksMovement()` and `isSolid()` both return true. In `LiquidBlock.getFlow`, the dry-neighbor gradient branch samples water below only when the same-height neighbor's material does not block movement, so the dry fence blocks that sample. Separately, for a liquid cell with raw `LEVEL >= 8`, each horizontal neighbor and its block above are checked with `isFaceSolid`. A dry fence is not water or ice, so `LiquidBlock.isFaceSolid` delegates to `Block.isFaceSolid`, which reads the neighboring material's `isSolid()`; the fence therefore supplies the same-height and one-block-above wall cue. This cue reads material properties, not the fence's collision box or cube/render predicates. The normalized-vector, downward `-6.0`, and final-normalization sequence is identical in all three releases.

**Local-player current application and gates: ACCEPT.** `LocalClientPlayerEntity.tick` enters its inherited tick path only when the player's chunk is loaded. In that path the inherited base entity tick invokes `checkWaterCollisions` with the player shape grown down by `0.4F` and contracted by `0.001` on all axes. `World.applyLiquidDrag` returns false if its scanned area is not loaded; otherwise it scans floored bounds, selects matching-material cells that pass the liquid-height intersection test, and accumulates `Block.applyMaterialDrag` results. It adds the normalized nonzero sum at scale `0.014` only when `entity.hasLiquidCollision()` is true. `PlayerEntity.hasLiquidCollision()` returns `!abilities.flying`, so flight suppresses this current response. The water-collision boolean independently sets `inWater`, clears fall distance and fire timer; a dry fence alone does not satisfy the liquid query or make the player wet. These gates and operation order match across 1.8.2, 1.8.3, and 1.8.4.

**Patch-step coverage: ACCEPT for these paths only.** The compared source bytes are unchanged at both 1.8.2→1.8.3 and 1.8.3→1.8.4. No finding is supported for this interval and these operations.

## Citation correction

The author's terminal's source-hash table labels the material path `net/minecraft/block/Material.java`. The actual Feather source path is `net/minecraft/block/material/Material.java`; its 1.8.2 SHA-256 is `017713d76afe726ca243ce32cbc35c13d3f0f0e7e5d90d122f81103cc2ca1bd2`, matching the supplied digest. This is a path typo only and does not change the reviewed behavior or verdict.

# Independent audit: lava-presence height/admission supplement

## Verdict

**ACCEPT** for the bounded height-admission, out-of-height block-state, and client chunk-readiness corrections in the source-only supplement. The supplement is accurate for the inspected 1.16.1, 1.16.2, and 26.2 source slices. This verdict does not revise the original lava-presence movement finding, establish runtime parity, or freeze a full release pair.

## Bound inputs

- Reviewed supplement: `.native-source/20261028/lava-presence-client-consumers/height-admission-correction-supplement.md`, SHA-256 `27c29dec0ffd6425419f7d083786e2d7e220279f1f9f6d3e9f3b933847aab545`.
- Bound report: `.native-source/20261028/lava-presence-client-consumers/REPORT.md`, SHA-256 `d3bbd6c106a0051396aca17aa0269e17fc217542a44f07656ada428a99454b05`.
- Bound prior audit: `.native-source/20261028/lava-presence-client-consumers-audit/audit.md`, SHA-256 `02e6604f2c3115247747c43eb06c0fe041fd017a6f4cbc88b10442b4b1466b6e`.

I rebound all three hashes from the workspace before inspecting the canonical source tree at `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/`.

## Independent source checks

The 1.16.1 and 1.16.2 `LevelReader.hasChunksAt(BlockPos, BlockPos)` overloads delegate to the six-coordinate overload. Both six-coordinate bodies use `maxY >= 0 && minY < 256`, then traverse chunk X and Z coordinates inclusively and return false if a `hasChunk` call returns false. The `LevelReader.java` hash is identical between the two snapshots (`d301b992…d29577a`). The gate is vertical overlap admission, not clipping: partial overlap passes this Y gate; a box wholly below or wholly above `[0,256)` fails it.

For the client path, `ClientLevel.hasChunk(int,int)` returns `true` unconditionally in both releases. The horizontal loop therefore does not make `hasChunksAt` a missing-client-chunk readiness barrier for an actual client level. Separately, `LevelReader.getChunk(x,z)` delegates to `FULL` with `loadOrGenerate=true`; both 1.16.x `ClientChunkCache` sources return their `emptyChunk` for a missed request with that flag, and both corresponding `EmptyLevelChunk.getBlockState` implementations return `Blocks.VOID_AIR.defaultBlockState()`. The 1.16.1 and 1.16.2 empty-chunk source hashes match the supplement, including its revised 1.16.2 hash.

`Level.getBlockState` in each old release checks `isOutsideBuildHeight` first and returns `VOID_AIR` for Y below zero or at least 256, before chunk retrieval. The old scan's original coordinate loop is not clipped by the height gate; on a partial-overlap scan, out-of-height reads therefore resolve to void air and cannot invoke a lava block callback. A wholly out-of-height region fails admission before the scan. The bound report establishes that the lava latch has just been cleared before this admission check, so it stays false for that callback phase.

The available 26.2 source is unobfuscated, not Mojmap. Its `LevelReader` uses `y1 >= getMinY() && y0 <= getMaxY()` and then an inclusive horizontal chunk loop. `LevelHeightAccessor` defines `maxY = minY + height - 1` and outside-height as `< minY || > maxY`. `Level.getBlockState` returns `VOID_AIR` outside valid bounds, whose vertical component is this dynamic range. `ClientLevel.hasChunk` again returns true unconditionally. `ClientChunkCache.getChunk(..., loadOrGenerate)` returns `emptyChunk` on a cache miss when the flag is true, and `EmptyLevelChunk.getBlockState` returns `VOID_AIR`. These facts support the supplement's bounded dynamic-height and client-readiness statements.

## Rebound source identities

The three source-manifest hashes in the supplement were recomputed and match the local manifests and ready-marker values: 1.16.1 Mojmap `76fdd121…ab9754`, 1.16.2 Mojmap `10d312ca…952a7a7`, and 26.2 unobfuscated `a7ad74fc…efe894`. The listed selected source-file hashes also match the canonical files, including `ClientChunkCache.java`, both old `EmptyLevelChunk.java` files, 26.2 `LevelChunk.java`, and 26.2 `EmptyLevelChunk.java`.

The provenance qualifications are retained: the 1.16.x records identify one exact batch decompile and Mojmap, while cached binary artifacts are not rehashed here; 26.2 is available as unobfuscated source, its provenance has `mappingArtifacts: null`, and its persisted success evidence is a summary excerpt. No named mapping build is claimed for 26.2.

## Limits

No online mapping lookup, source regeneration, product implementation or Git-history inspection, build, test, or runtime was used. This audit covers only the admission and state-access paths named above. It confirms the source-only correction and does not imply measured movement trajectories or broader pair coverage.

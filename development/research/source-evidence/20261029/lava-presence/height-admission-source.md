# Source correction supplement: lava-presence height and chunk admission

## Bound inputs and source identity

This is a source-only supplement to the accepted client-consumer report. Its input report SHA-256 is `d3bbd6c106a0051396aca17aa0269e17fc217542a44f07656ada428a99454b05`; the independent audit SHA-256 is `02e6604f2c3115247747c43eb06c0fe041fd017a6f4cbc88b10442b4b1466b6e`. Both were rehashed before this review.

The canonical source root is `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/`. Release and namespace identity comes from the local ready markers and provenance files:

- 1.16.1, Mojmap: Vineflower source snapshot; source-manifest SHA-256 `76fdd121070c2012f48cbdf5cf0c78490116afc94117c30b0858eedcd6ab9754`.
- 1.16.2, Mojmap: same exact decompile run as 1.16.1; source-manifest SHA-256 `10d312ca29ee48e25f4727adafb9fcfbd741dcebe81b343d3dcffa071952a7a7`.
- 26.2: the canonical available snapshot is `unobfuscated`, not Mojmap; source-manifest SHA-256 `a7ad74fc712567eb87136afc1eafcfd602b4088e9869f3596331fd34faefe894`.

The selected files were independently hashed in the canonical tree:

| Release / namespace | Source file | SHA-256 |
|---|---|---|
| 1.16.1 / Mojmap | `net/minecraft/world/level/LevelReader.java` | `d301b992d71b05306a3139e69f3f1529190f82404791ec60f9a693188d29577a` |
| 1.16.1 / Mojmap | `net/minecraft/world/level/Level.java` | `5f6be38c6b3e11e1ca00a9067f676151d1288f19d8cd817a3bdb1cc91b8eaa19` |
| 1.16.1 / Mojmap | `net/minecraft/client/multiplayer/ClientLevel.java` | `3bae536422595514893b85bcde09b59459c292c737dd487d69db49d28913da7b` |
| 1.16.1 / Mojmap | `net/minecraft/client/multiplayer/ClientChunkCache.java` | `a87557dae1fc7d01050e2e05154364d3b056d69ac3c596edd323abc6a87e4d54` |
| 1.16.1 / Mojmap | `net/minecraft/world/level/chunk/EmptyLevelChunk.java` | `9731056f120999caf4d98b9d9433e854af9f17ff33a8014bdabda438fd04ea806` |
| 1.16.2 / Mojmap | `net/minecraft/world/level/LevelReader.java` | `d301b992d71b05306a3139e69f3f1529190f82404791ec60f9a693188d29577a` |
| 1.16.2 / Mojmap | `net/minecraft/world/level/Level.java` | `e9a23488863fd43a7d8975efc23f4c7cf4af874554bab5b76eaa2c79f028db80` |
| 1.16.2 / Mojmap | `net/minecraft/client/multiplayer/ClientLevel.java` | `96c80a10623d41f136fa70391b3920cf7f3ef15547923f7d772b78d356f16d93` |
| 1.16.2 / Mojmap | `net/minecraft/client/multiplayer/ClientChunkCache.java` | `a87557dae1fc7d01050e2e05154364d3b056d69ac3c596edd323abc6a87e4d54` |
| 1.16.2 / Mojmap | `net/minecraft/world/level/chunk/EmptyLevelChunk.java` | `0921237b788233436eadb98982a1885febf7f31ce553a029ae8218675f57b0ab` |
| 26.2 / unobfuscated | `net/minecraft/world/level/LevelReader.java` | `8fbfb7cf6d6ff8a19e784198d6bf75a1eae346cd8371d3d23eb3c8af546621a1` |
| 26.2 / unobfuscated | `net/minecraft/world/level/LevelHeightAccessor.java` | `ef1406338d159157652f43d56366925bbfd9ccb89ec068357eff90067b62b6a2` |
| 26.2 / unobfuscated | `net/minecraft/world/level/Level.java` | `1ae4f565ad909f4e90786089b5b4f125e8a7d18e2ef7d8c376172a5787fc5639` |
| 26.2 / unobfuscated | `net/minecraft/client/multiplayer/ClientLevel.java` | `f0255138128f19fb354e1a208f457e7fdb2df986f3103ec91f52bcabfa076631` |
| 26.2 / unobfuscated | `net/minecraft/client/multiplayer/ClientChunkCache.java` | `e36e3ea9cc4b8bf4e312a0b3b460f99bf2445906de964e7f3fbd53a9cef56f1d` |
| 26.2 / unobfuscated | `net/minecraft/world/level/chunk/LevelChunk.java` | `4e93a4279f5fad591348ce27a0bc379973ed495aa7c2e0216c46ec86ee0e1878` |
| 26.2 / unobfuscated | `net/minecraft/world/level/chunk/EmptyLevelChunk.java` | `6e2d9ba27e57ba79683984d8b59f7fb88ac39da10b4127c3762748894a635274` |

## Verified behavior

In 1.16.1 and 1.16.2 Mojmap, `LevelReader.hasChunksAt(minPos,maxPos)` delegates to the six-integer overload. That overload admits the box only when `maxY >= 0 && minY < 256`, then iterates every horizontal chunk column from `minX >> 4` through `maxX >> 4` and `minZ >> 4` through `maxZ >> 4`, inclusive, returning false if any `hasChunk` call is false (`LevelReader.java`, `hasChunksAt(BlockPos,BlockPos)` and `hasChunksAt(int,int,int,int,int,int)`, lines 163–184 in 1.16.1; the same method body at the end of 1.16.2 `LevelReader.java`). This is an overlap test, not a vertical clamp: a partially overlapping box is admitted, while a box wholly above or below `[0,256)` is rejected.

For the client-player path, the horizontal loop is present but not an actual readiness barrier. Both 1.16.1 and 1.16.2 `ClientLevel.hasChunk(int,int)` returns `true` unconditionally (`ClientLevel.java:265`). Thus `hasChunksAt` still performs the horizontal column loop, but this client implementation does not reject a box because a client chunk is absent. `LevelReader.getChunk(x,z)` requests `FULL` with `loadOrGenerate=true`; `ClientChunkCache.getChunk` returns the cached chunk if present and otherwise returns its `emptyChunk` for that request. That fallback chunk also returns `VOID_AIR` from `getBlockState` (`EmptyLevelChunk.java:31`). The scan’s `hasChunksAt` invocation must be described separately from server-side or generic `LevelReader` readiness semantics.

The old scan iterates its original bounding-box-derived block coordinates once admission succeeds, as documented in the bound report (`Entity.checkInsideBlocks`, 1.16.1 `Entity.java:803–828`). It does not clip that iteration to `[0,256)`. However, `Level.getBlockState(pos)` checks `isOutsideBuildHeight(pos)` before retrieving a chunk and returns `Blocks.VOID_AIR.defaultBlockState()` for `y < 0 || y >= 256` (`Level.java:163–168, 408–414`). The precise value is therefore **VOID_AIR**, not `Blocks.AIR`; it is an air state and cannot contribute a lava callback. Consequently, a partially overlapping scan may call `getBlockState` for out-of-height cells, and those reads resolve to void air. A wholly out-of-height box fails admission before the scan; because the report establishes that the lava latch is cleared immediately before the admission check, it remains false for that movement callback phase.

In the available 26.2 unobfuscated source, `LevelReader.hasChunksAt` uses dynamic inclusive height bounds: `y1 >= getMinY() && y0 <= getMaxY()`, then delegates to a horizontal-only overload that loops inclusive chunk coordinates and calls `hasChunk` (`LevelReader.java:188–214`). `LevelHeightAccessor.getMaxY()` is `getMinY() + getHeight() - 1`, and `isOutsideBuildHeight` is `y < getMinY() || y > getMaxY()` (`LevelHeightAccessor.java:9–13, 35–42`). `Level.getBlockState` returns `VOID_AIR` when `isInValidBounds(pos)` is false (`Level.java:366–372`); valid bounds include the dynamic vertical range (`Level.java:176–177`). As on the old client path, `ClientLevel.hasChunk` returns true unconditionally (`ClientLevel.java:518`), while `ClientChunkCache.getChunk` returns its `emptyChunk` when a load-or-generate request misses (`ClientChunkCache.java:69–82`); `EmptyLevelChunk.getBlockState` also returns `VOID_AIR` (`EmptyLevelChunk.java:26–28`). Thus 26.2 uses dynamic Y overlap admission, but its client `hasChunksAt` loop also does not impose an actual loaded-chunk readiness condition.

## Narrow conclusion and limits

The candidate claim is confirmed for 1.16.1: admission requires `maxY >= 0 && minY < 256` before checking horizontal chunk columns. The same admission body is present in 1.16.2. The old public `Level.getBlockState` returns VOID_AIR outside `[0,256)`, not ordinary AIR; therefore partial-overlap reads outside that interval cannot see blocks or lava. Fully out-of-height boxes are not scanned, leaving the just-cleared old lava latch false for that phase. Dynamic `getMinY()/getMaxY()` admission is confirmed in 26.2.

For an actual client player, the horizontal `hasChunk` calls remain in source but `ClientLevel.hasChunk` is unconditionally true in all three inspected snapshots. It is therefore inaccurate to describe the client scan as requiring the corresponding chunks to be loaded for admission. This does not generalize to other `LevelReader` implementations.

The 1.16.1/1.16.2 provenance records identify the exact batch decompile, Mojmap namespace, and ready source manifests; their binary artifacts are not rehashed here. The 26.2 ready identity is an unobfuscated snapshot; its provenance records `mappingArtifacts: null` and a summary-excerpt success log, so no named mapping build can be asserted. No online mapping/decompiler lookup, Gradle invocation, product implementation/history inspection, test, or runtime was used. The conclusion is limited to these admission and state-access paths and does not revise the accepted movement-consumer finding beyond clarifying its client chunk-readiness wording.

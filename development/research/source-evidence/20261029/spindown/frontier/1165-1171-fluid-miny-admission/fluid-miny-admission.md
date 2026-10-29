# 1.16.5 → 1.17.1: client fluid-query admission and dimension minY

## Bounded result

This slice establishes how the local-player fluid-query path is admitted vertically in exact 1.16.5 and 1.17.1 Mojmap sources. It compares query bounds and the entity scan’s preflight only. It does not compare fluid-cell contents, dimension data producers, server-side fluid production, trajectories, or parity.

**Source pair:** Minecraft 1.16.5 → 1.17.1, both `mojmap`, from the canonical read-only tree `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\<version>\mojmap`.

**Provenance parent:** both version manifests identify the same exact run: `1.15.2,1.16.5,1.17.1,1.18.2`, `--mappings=mojmap`, Java `25.0.3+9-LTS`, Vineflower `1.12.0`, created `2026-10-07T14:47:33.0330302Z`. Each `.ready.json` identifies its exact version and source/artifact manifest hashes. The actual selected Java files below match their version’s `mojmap.sources.sha256` entries.

| Version | Client JAR SHA-256 | Source manifest SHA-256 | Artifact manifest SHA-256 | Mojmap mapping text / remapped JAR SHA-256 |
|---|---|---|---|---|
| 1.16.5 | `00b5ebbc33e95ea88c1ab80601599c9827e9aa861d93ccc2cfcbbfd996e86263` | `9499f2611d0e6dde37cb635f1a28a591416382d6bc188b37d5f99e2b2a20823b` | `f9b9812d6995012cefcc1201a63855e5931e8551b508753be6c46d442d8b370c` | `7931ed6d723eceb1d621d05a76e10ddf643bf468c6ecf1c4ecf377bd72cf8b8c` / `7c1a9f30983fa3c777d00116bdf3f3cef97c1c1aaf8294027052f5337fcde940` |
| 1.17.1 | `a49b4a56c5bbe15c9ed9fe53efa9a591f265a1f5ba7d6aa9739a58ea7a92b79d` | `93270d229acfb751bf56daf1e7be26ce3dcff26b29e94aa157de621405a3463b` | `e52c5dbae7d9663190ccc55a4f9b44a8e0615fb1fbbd8280811df43f70ec8aaa` | `2b28ded68f8602aaf2f35ef92dd10ee41ba2cf9723e29570155d60e1723848e9` / `2a2be036174902e447865498741b8c59fa2e090d352d786a8507dccb7c23008c` |

The readiness markers’ source-manifest and artifact-manifest hashes match the corresponding manifest files. Relevant Java source hashes are listed at the end. The provenance JSON has `mappingArtifacts: null`; the artifact manifest records hashes for both `client_mappings.txt` and `client-mojmap.jar`, but those artifacts were not present at the recorded cache path during this review, so their bytes could not be rehashed independently. The decompiled source manifests themselves are intact and their selected Java entries match.

## Source trace and exact gates

### Cell-query admission

- **1.16.5** — `net/minecraft/world/level/Level.java`:158–164 defines static outside-build-height as `y < 0 || y >= 256`. `Level.getFluidState(BlockPos)`:378–385 returns `Fluids.EMPTY.defaultFluidState()` when that predicate is true; otherwise it gets the chunk’s fluid state.
- **1.17.1** — `net/minecraft/world/level/LevelReader.java`:65–75 supplies `getMinBuildHeight()` from `dimensionType().minY()` and `getHeight()` from `dimensionType().height()`. `BlockGetter` extends `LevelHeightAccessor`; `LevelHeightAccessor`:11–12 computes max as `min + height`, and :27–32 treats `y < min` or `y >= max` as outside. `Level.getFluidState(BlockPos)`:387–394 returns empty outside that active interval, otherwise the chunk’s fluid state.

Thus a *single block-position fluid query* uses fixed `[0, 256)` admission in 1.16.5 and active-dimension `[minY, minY + height)` admission in 1.17.1. The upper limit is exclusive in both; only the 1.17.1 lower/upper values are obtained from the active dimension type.

### Entity volume scan admission

- **1.16.5** — `Entity.updateFluidHeightAndDoFluidPushing(Tag<Fluid>, double)`:2641–2651 deflates the entity box by `0.001`, computes floor(min)/ceil(max) coordinates, then returns `false` unless `level.hasChunksAt(i, k, m, j, l, n)` passes. `LevelReader.hasChunksAt(int,int,int,int,int,int)`:168–186 first tests `m >= 0 && j < 256`, where the Entity call places `ceil(box.maxY)` in `m` and `floor(box.minY)` in `j`; it then checks the horizontal chunks. This is a discrete envelope preflight: it passes the vertical part when `ceil(box.maxY) >= 0` and `floor(box.minY) < 256`, not only when the continuous AABB intersects the interval. Passing boxes scan integer cells with `p ∈ [floor(minX), ceil(maxX))`, `q ∈ [floor(minY), ceil(maxY))`, `r ∈ [floor(minZ), ceil(maxZ))`, querying `getFluidState` for each (:2660–2665).
- **1.17.1** — the corresponding method at `Entity.java`:2773–2797 first returns false only when `touchingUnloadedChunk()` is true. That helper at :2840–2846 inflates the entity box by `1.0`, takes horizontal X/Z floor/ceil bounds, and calls the four-argument horizontal `hasChunksAt`; it has no Y check. It then uses the same `0.001` deflated box and the same floor/ceil half-open cell loops, querying `getFluidState` for each cell. Out-of-range cells receive the Level-level empty-state fallback described above.
- `LevelReader.hasChunksAt(int,int,int,int,int,int)` still has a vertical bound in 1.17.1 (`LevelReader.java`:185–187): it admits when `maxY >= getMinBuildHeight()` and `minY < getMaxBuildHeight()`, then checks X/Z chunks. **The 1.17.1 entity fluid method does not call that six-argument overload**; it uses the X/Z-only overload through `touchingUnloadedChunk()`.

Consequently, in 1.16.5 the scan is rejected when `ceil(box.maxY) < 0` or `floor(box.minY) >= 256`; fractional boxes immediately below Y=0 can still pass the coarse preflight, but their sampled negative-Y cells resolve to empty. In 1.17.1, if the horizontal chunk check passes, the scan proceeds without a vertical preflight and each sampled out-of-range cell query resolves to empty. The scan envelope and a queried cell’s build-height gate are distinct operations.

### Other direct Entity fluid queries

- `Entity.updateFluidOnEyes()` samples `BlockPos(getX(), getEyeY() - 0.11111111F, getZ())` and calls `level.getFluidState` in 1.16.5 `Entity.java`:981–1006 and 1.17.1 `Entity.java`:1090–1107. It therefore inherits the same release-specific single-cell admission. Its separate boat-overlap gate controls whether that sample is made.
- `Entity.updateSwimming()` in 1.16.5 `Entity.java`:949–955 only sets swimming from sprinting, cached underwater state, and passenger status. In 1.17.1 `Entity.java`:1056–1064, the non-already-swimming branch adds a final direct `level.getFluidState(blockPosition).is(FluidTags.WATER)` predicate after the sprinting/underwater/non-passenger checks. That single query uses the 1.17.1 active-dimension gate.
- The scan’s remaining fluid tests and calculations (`FluidState.is`, height/flow reads, accumulated height and pushing) are not evidence of which cell fluid data is produced; no fluid cell contents were inspected.

## Local-player route

In both versions `ClientLevel` extends `Level` (1.16.5 `client/multiplayer/ClientLevel.java`:74; 1.17.1:80), and `LocalPlayer` extends `AbstractClientPlayer`, whose chain reaches `Player → LivingEntity → Entity` (the declarations are in the correspondingly named client/entity files). `LocalPlayer.tick()` calls `super.tick()` only when its horizontal chunk gate passes (1.16.5 `client/player/LocalPlayer.java`:184–186; 1.17.1:191–193). The 1.16.5 gate uses `hasChunkAt(BlockPos(x, 0, z))`; the 1.17.1 gate uses `hasChunkAt(x,z)`. Both check the horizontal chunk and do not add a player-Y/build-height predicate.

The inherited tick chain reaches `Player.tick()` → `LivingEntity.tick()` → `Entity.tick()` in both versions. `LivingEntity.tick()` calls `super.tick()` (1.16.5 `LivingEntity.java`:2124–2125; 1.17.1:2218–2219; `Player.tick()` also calls super at 1.16.5 :234 and 1.17.1 :247). At the Entity layer, 1.16.5 `Entity.tick()` calls `updateInWaterStateAndDoFluidPushing()` and `updateFluidOnEyes()` at :380–382; 1.17.1 `Entity.baseTick()` does so at :415–417, reached by `Entity.tick()` at :391–393, then calls `updateSwimming()`. `LocalPlayer.aiStep()` consumes cached `isInWater()` and `isEyeInFluid(WATER)` state (1.16.5 `LocalPlayer.java`:684–737; 1.17.1 :706–759). The query-boundary difference therefore enters the local player’s ordinary inherited client tick path; LocalPlayer itself does not supply a distinct fluid scan implementation.

A nearby but separate 1.17.1 LocalPlayer operation, `resetPos()` at :630–642, loops while its Y is strictly above `getMinBuildHeight()` and strictly below `getMaxBuildHeight()`. It uses `noCollision`, not `getFluidState`, and is not part of the direct fluid-query admission finding.

## Boundaries and open dimensions

- Exact scope is 1.16.5 and 1.17.1 Mojmap endpoint source only. The release where this changed within the interval is not established.
- The method-level 1.17.1 rule is established for any active `DimensionType` supplied to `LevelReader`; this slice does not enumerate runtime dimension-type values or inspect data/registry producers, so it does not assert which vanilla/custom dimensions actually use nonzero `minY` or non-256 height.
- Server producers, dimension configuration, block/fluid contents, and resulting movement trajectory/parity remain unexamined by design.
- Mapping-artifact content is represented by the retained artifact-manifest hashes, but its recorded source artifacts were unavailable for independent rehash; see the provenance note above.

## Selected decompiled Java SHA-256

All listed hashes matched entries in the corresponding `mojmap.sources.sha256` file.

| Version | Relative Java path | SHA-256 |
|---|---|---|
| 1.16.5 | `net/minecraft/world/entity/Entity.java` | `f9a9a073fe3105a0aa53d0f21ec72e59084e8d21a14c1cd3be75703865ee2666` |
| 1.16.5 | `net/minecraft/world/entity/LivingEntity.java` | `b5d8a1a3c80f85d5d545b5a777e9e2a915dc002a7e31b5f5ad12bf7e285d7a88` |
| 1.16.5 | `net/minecraft/world/entity/player/Player.java` | `d2e26589bdb6a20dc914266db06aa48f50811efc792d6e63b3008c9199914960` |
| 1.16.5 | `net/minecraft/client/player/AbstractClientPlayer.java` | `05c36f559ada7061a4a2ee8efaf05a21502945af83a893cb4c849749afb84859` |
| 1.16.5 | `net/minecraft/client/player/LocalPlayer.java` | `6011569e766bb1568609147be9aa14e9c08c51948e3d3a60fd066e848f6a8c2b` |
| 1.16.5 | `net/minecraft/client/multiplayer/ClientLevel.java` | `96c80a10623d41f136fa70391b3920cf7f3ef15547923f7d772b78d356f16d93` |
| 1.16.5 | `net/minecraft/world/level/Level.java` | `e9a23488863fd43a7d8975efc23f4c7cf4af874554bab5b76eaa2c79f028db80` |
| 1.16.5 | `net/minecraft/world/level/LevelReader.java` | `d301b992d71b05306a3139e69f3f1529190f82404791ec60f9a693188d29577a` |
| 1.17.1 | `net/minecraft/world/entity/Entity.java` | `ab28e1fba924771ec048140dfd293ee5a46a7dfe81f71a1a0b1aecc1927232de` |
| 1.17.1 | `net/minecraft/world/entity/LivingEntity.java` | `33fd081aadb2b6fdc9ebf487db6da5b38c54f4b8676572790ee2203690d15e6f` |
| 1.17.1 | `net/minecraft/world/entity/player/Player.java` | `724bb298499dabe489dffd5ce7ec81c9773619a8d2c70044911eae4c9e8ff481` |
| 1.17.1 | `net/minecraft/client/player/AbstractClientPlayer.java` | `d0508c94f43e7e90683b8f46586665bd1e1f0dca2fe25274b852f5e6c0ce05e8` |
| 1.17.1 | `net/minecraft/client/player/LocalPlayer.java` | `c9a91cb6cb57806bc8d22e5bfe2d97daaf21d5d2a48f34a6e2c53164c61c5812` |
| 1.17.1 | `net/minecraft/client/multiplayer/ClientLevel.java` | `fd2b3ebade86250cf1ab05f6086dd48f11b6bcd3a01e67c8a03f5c34f553fbc2` |
| 1.17.1 | `net/minecraft/world/level/Level.java` | `730d3cc1f1c1c7054c102bb32d635d8f5a3657fff8073df863f0848ed1f9a0f8` |
| 1.17.1 | `net/minecraft/world/level/LevelReader.java` | `4d041cdbc702d532c5b9da800c34bd1c23f8d3b554ecbdd89decbb765a6b451a` |
| 1.17.1 | `net/minecraft/world/level/BlockGetter.java` | `ee950170a2c0b28427fc067a7e33abad906c1d379efddb52c694245a5a1e21b6` |
| 1.17.1 | `net/minecraft/world/level/LevelHeightAccessor.java` | `5e0dc5ed7b32ef321a14efc103ad311c6c5066b1555f11fa7aee2d3c73e344b2` |

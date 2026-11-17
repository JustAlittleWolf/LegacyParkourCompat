# 1.20.1–1.20.2 S10 solid-movement source audit, indices 59–87

## Exact evidence identity

- Older source: 1.20.1, Mojmap, Vineflower output under `decompiled_minecraft/1.20.1/mojmap/`; `versionId=1.20.1`, `mapping=mojmap`, `clientSha256=56b71336d2b4fdffd197f56595b0da93e32a946f78f382a299b8f4b92758bb0f`, `sourceManifestSha256=858b56764113e591c60d09bc63e4d90f9c1d67a705645f0c793e8b28f2378465`.
- Newer source: 1.20.2, Mojmap, Vineflower output under `decompiled_minecraft/1.20.2/mojmap/`; `versionId=1.20.2`, `mapping=mojmap`, `clientSha256=fa1a19be56a506426308abbc1cad85f299a7fc6dae4335559351ba0246713fda`, `sourceManifestSha256=e439ff7e1d3f1d31e01be1edd141f94d917a9dc7fa0de5f69904544cbbe62887`.
- Ready-marker manifest digests match these files. For all 29 assigned block source paths, the file exists in each exact source tree and its SHA-256 matches its release's `mojmap.sources.sha256` entry.
- The immutable S10 roster's 116-path unexamined remainder was sorted using ordinal path comparison. This report covers inclusive positions 59–87.

## Ordered coverage

All listed paths are `net/minecraft/world/level/block/<name>.java`. “Byte-identical” means source file SHA-256 equality across the two release trees, so all direct and inherited operations in that file are identical. Changed-file rows summarize the hunk audit; unrelated changed methods were not treated as movement differences.

| # | Path | Result |
|---:|---|---|
| 59 | HangingRootsBlock.java | Byte-identical |
| 60 | HoneyBlock.java | Byte-identical |
| 61 | HopperBlock.java | File differs; only changed logic is powered-state update flag handling (old path passed `2` on placement and `4` on neighbor update; new helper always uses `2`). FACING-to-shape selector, collision shape, interaction shape, state properties and block-entity access are identical. |
| 62 | LadderBlock.java | Byte-identical |
| 63 | LanternBlock.java | Byte-identical |
| 64 | LecternBlock.java | Byte-identical |
| 65 | LightBlock.java | Byte-identical |
| 66 | LiquidBlock.java | File differs only in `pickupBlock` gaining nullable `Player` parameter and parameter renaming. Its collision/support shape operations are unchanged. Pickup is excluded fluid/item interaction. |
| 67 | MangrovePropaguleBlock.java | File differs only in bonemeal target method signature dropping `boolean`; unchanged `return !isHanging(state) || !isFullyGrown(state)`. Shape/support methods unchanged. |
| 68 | MudBlock.java | Byte-identical |
| 69 | MultifaceBlock.java | Byte-identical |
| 70 | MushroomBlock.java | File differs only in bonemeal target method signature dropping `boolean`; unchanged `return true`. Shape/support methods unchanged. |
| 71 | NetherPortalBlock.java | Byte-identical |
| 72 | NetherSproutsBlock.java | Byte-identical |
| 73 | NetherWartBlock.java | Byte-identical |
| 74 | PiglinWallSkullBlock.java | Byte-identical |
| 75 | PipeBlock.java | Byte-identical |
| 76 | PitcherCropBlock.java | File differs in age/growth, support/update and bonemeal logic; getShape and getCollisionShape definitions are unchanged (getShape is relocated in the file). Changed support logic may alter crop persistence/state during ordinary neighbor updates, but the assigned source-only scope finds no changed collision-shape formula or directional support selector. The registered block has `.noCollission()` in both releases, and inherited `BlockBehaviour.getCollisionShape` returns empty for no-collision blocks. Growth/world-state lifecycle excluded from a direct player movement-shape finding. |
| 77 | PointedDripstoneBlock.java | Byte-identical |
| 78 | PotatoBlock.java | Byte-identical |
| 79 | RedStoneWireBlock.java | File differs only in DOWN-neighbor `updateShape`: 1.20.2 removes the wire if `canSurviveOn(level, neighborPos, neighborState)` fails, whereas 1.20.1 returns the existing state. Directional wire outline selectors and outline shape table are unchanged; registered wire is `.noCollission()` in both releases, so this does not change player solid collision. |
| 80 | RedstoneWallTorchBlock.java | Byte-identical |
| 81 | RodBlock.java | Byte-identical |
| 82 | RootsBlock.java | Byte-identical |
| 83 | SaplingBlock.java | File differs only in bonemeal target method signature dropping `boolean`; unchanged `return true`. Shape and support methods unchanged. |
| 84 | SculkSensorBlock.java | Byte-identical |
| 85 | SculkShriekerBlock.java | Byte-identical |
| 86 | SeaPickleBlock.java | File differs only in bonemeal target method signature dropping `boolean`; unchanged `return true`. Collision/support shape and top-face survival selector unchanged. |
| 87 | SeagrassBlock.java | File differs in bonemeal target and `canPlaceLiquid` signatures (plus nullable Player parameter/import); collision/outline shape and support updates unchanged. Fluid placement excluded. |

## Coverage result and limits

No direct player solid-collision or direct support shape/directional-selector difference was found among these 29 paths. The 20 byte-identical files are closed by identity; the nine changed files were inspected for changed geometry, collision dispatch, state/properties feeding geometry, contextual inputs, neighbor updates, and relevant inherited behavior. The shape operations for HopperBlock, PitcherCropBlock, RedStoneWireBlock and SeaPickleBlock were checked despite enclosing files differing. PitcherCropBlock and RedStoneWireBlock have semantic neighbor/state changes, recorded above, but neither changes the direct movement collision shape operation; PitcherCropBlock and redstone wire are no-collision registrations. No finding draft is warranted for this bounded slice.

This is only slice coverage, not complete pair coverage or full-pair freeze. Other S10 roster entries, other movement areas and the remaining 87–116 tail are not covered here. No intermediate release was inspected, so no introduction boundary is established.

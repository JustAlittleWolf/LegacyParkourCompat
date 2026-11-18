# COMPLETED — earlier snow layer-one collision controls

## Result

The 1.12.2 → 1.13 witness remains present in each of the six requested older exact controls. For `LAYERS=1`, the old collision result is an AABB with vertical extent `0..0` at the snow block's bottom. The ordinary block collision collector keeps it when the expanded movement query strictly straddles that plane; descending Y clipping treats the plane as a landing boundary. No earlier geometry or reachability boundary was found among the inspected releases.

A legal witness arrangement is a layer-one snow block directly above a layer-eight snow block. Each `SnowLayerBlock.canBePlaced` accepts the same block beneath at layer count 7 or 8 (1.8.9–1.10.2) or exactly 8 (1.11–1.11.2). The lower layer-eight collision top is at `7/8` block height, while the upper layer-one collision plane is at its block bottom, `1/8` block above that lower top. A descending player can therefore be clipped at the upper plane in these old sources.

This confirms source applicability for these exact releases, not complete tick-trajectory parity or an exact introduction patch.

## Source publication and mapping checks

All trees are `ornithe-feather`; each parent `ornithe-feather.ready.json` reports `ready`, its version and mapping match the selected tree, and its `sourceManifestSha256` equals both the supplied digest and the actual SHA-256 of `ornithe-feather.sources.sha256`. The `SnowLayerBlock.java` path/member is present in that manifest and its file SHA-256 matches the manifest entry.

| Version | Source manifest SHA-256 | `SnowLayerBlock.java` SHA-256 | Mapping artifact identity |
|---|---|---|---|
| 1.8.9 | `9e75f46dc0ed43b6a355bd65db8a92c93a4dfeaecfa92284187c6fe9410d8004` | `d922f1f9a8d6f543959835e8b21e800ecf5cd9c7c60816b9152d873ca132a3ed` | Feather gen2 `1.8.9+build.2` |
| 1.9.4 | `c7b508fe01634887b65919dcd3a900c311a21d9510a1f1ab248d5c17c528ab19` | `3f60db09c66fb07439869254db883eed5da679a17e369e019f95ba97d427b2a5` | Feather gen2 `1.9.4+build.2` |
| 1.10.2 | `91b0f478acb7b6f13463c35b268a30d2806f583402631a56f54ce2eb70d1ec71` | `108450eb72f36a431bbbb49e59d47928de81a6d96f80b21774e57557d02f4881` | Feather gen2 `1.10.2+build.2` |
| 1.11 | `8b3e5da58494f1dba77410960753b0bf646425a1ada491d8242cc71e6c7a59ca` | `82c435d75bd45879fbc8c06950c8aa3fa06a2cd43740a0274eda7894d8b9fcff` | Feather gen2 `1.11+build.2` |
| 1.11.1 | `0d5f4697d4e4ce53b36d0dbfec89d44830eb76753a6643b49e42f0857753066d` | `74bc424529548cdd37abc67cd7bffb5a1ebc7b82589c08862c5ea553da2ad172` | Feather gen2 `1.11.1+build.2` |
| 1.11.2 | `d908c2af76598ad26999563bfef0089f1d3c8c32483eee6b108f0defa2773df0` | `74bc424529548cdd37abc67cd7bffb5a1ebc7b82589c08862c5ea553da2ad172` | Feather gen2 `1.11.2+build.2` |

For 1.8.9, 1.9.4, 1.10.2 and 1.11.2 the provenance manifest names the Feather `mergedv2.jar` and `.tiny` inputs. For 1.11 and 1.11.1, the generation provenance names `mappingOption: feather`; the parent `artifacts.sha256` records the exact `feather-gen2-<version>+build.2-mergedv2.jar` and `.tiny` artifacts. No generation or source tree was modified.

## Geometry and reachability matrix

All relative paths below are under `decompiled_minecraft/<version>/ornithe-feather/`.

| Version | Layer shape and collision evidence | Layer-eight support evidence | Verdict |
|---|---|---|---|
| 1.8.9 | `net/minecraft/block/SnowLayerBlock.java:23-28,40-46,64-70`; collision maxY is `posY + (LAYERS - 1) * 0.125F`, minY is `posY + minY` (zero). | `SnowLayerBlock.java:74-85`: same block with layers `>= 7` is accepted. | Plane and witness reachable. |
| 1.9.4 | `SnowLayerBlock.java:24-35,45-47,61-66`; `SHAPES[LAYERS]` gives layer one a positive `1/8` outline, but collision maxY is `(LAYERS - 1) * 0.125F`, zero for layer one. | `SnowLayerBlock.java:79-91`: same block with layers `>= 7` is accepted. | Plane and witness reachable. |
| 1.10.2 | `SnowLayerBlock.java:24-35,45-47,61-66`; same separated outline/collision behavior. | `SnowLayerBlock.java:79-91`: same block with layers `>= 7` is accepted. | Plane and witness reachable. |
| 1.11 | `SnowLayerBlock.java:24-35,45-47,61-66`; collision uses `i = LAYERS - 1` and `i * 0.125F`, preserving zero maxY for layer one. | `SnowLayerBlock.java:79-91`: same block with layers exactly `8` is accepted. | Plane and witness reachable. |
| 1.11.1 | `SnowLayerBlock.java:24-35,45-47,61-66`; its layer source hash is identical to 1.11.2. | `SnowLayerBlock.java:79-91`: same block with layers exactly `8` is accepted. | Plane and witness reachable. |
| 1.11.2 | `SnowLayerBlock.java:24-35,45-47,61-66`; same as 1.11.1. | `SnowLayerBlock.java:79-91`: same block with layers exactly `8` is accepted. | Plane and witness reachable. |

## Broad phase, collector and descending clip

In every listed version, `Entity.move` obtains block collision shapes using the entity shape expanded by requested movement, then visits returned shapes for vertical clipping:

- 1.8.9: `net/minecraft/entity/Entity.java:439-443`; `net/minecraft/world/World.java:891-922` scans the queried block range and calls `Block.addCollisions`; `net/minecraft/block/Block.java:339-343` appends a non-null shape when `shape.intersects(box)`.
- 1.9.4: `Entity.java:509-514`; `World.java:899-934`; `Block.java:337-346` moves the state collision AABB to world coordinates, then appends it if the expanded query intersects it.
- 1.10.2: `Entity.java:514-519`; `World.java:903-938`; `Block.java:342-351`, same behavior.
- 1.11: `Entity.java:523-529`; `World.java:960-995`; `Block.java:353-362`, same behavior.
- 1.11.1 and 1.11.2: `Entity.java:564-570`; `World.java:1013-1015` dispatches to its block collision collector (private collector starts at `World.java:960`); `Block.java:353-363`, same behavior.

`Box.intersects` uses strict overlap in Y (1.8.9 `Box.java:153-158`; 1.9.4 `199-205`; 1.10.2 `203-209`; 1.11–1.11.2 `245-251`). A zero-height plane is still retained when it lies strictly inside the swept query interval: `query.minY < planeY < query.maxY`. `Box.intersectY` then handles negative requested movement when the player bottom is at or above the plane, setting the vertical displacement to the plane distance (1.8.9 `Box.java:113-127`; 1.9.4 `159-173`; 1.10.2 `163-177`; 1.11–1.11.2 `205-219`).

## Limits and uninspected scope

- This is a bounded source-only control review. No product code, Git history, wiki lanes, runtime, or tests were inspected or changed.
- Exact intermediate patches between the six requested releases and 1.12.2 were not reviewed; do not infer that the geometry stayed constant at every intervening patch or assign a cutover.
- The check establishes the named shape-generation, broad-phase collection, support legality, and descending Y-clip path. It does not cover all movement callers, all world arrangements, or a full tick trajectory.

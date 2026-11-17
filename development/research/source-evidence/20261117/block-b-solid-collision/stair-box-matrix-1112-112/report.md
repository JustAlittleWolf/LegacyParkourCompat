# 1.11.2 → 1.12 StairsBlock collision emission

## Scope and verdict

Bounded comparison of `StairsBlock` collision emission and neighbor-derived `SHAPE`, the ordinary World collector and player movement caller. Excludes face classification, fence/wall/pane behavior, and trajectories. No game/runtime tests were run.

**Verdict:** no difference in stair collision shape selection, neighbor resolution, append/filter behavior, or World collection between exact 1.11.2 and 1.12. The same 40-tuple matrix applies to both. The ordinary caller passes `forceShape=false`, so the stored `SHAPE` is replaced by the neighbor-resolved value before emission; matrix tuples represent resolved `SHAPE` values.

## Exact source identity and integrity

Both source sets use Ornithe Feather mapped source, build.2, decompiled with the pinned Vineflower pipeline.

- **1.11.2:** Feather Gen2 `1.11.2+build.2`; client SHA-256 `be3fff4f2cc005a1310a96389efdeb983d2bcb4b8e747c402acd616ae73d0ba2`; provenance SHA-256 `dcdb65e1361297a584c5b93f581b1b539911911c6cec1a203740baa5379732c6`; ready marker source-manifest SHA-256 `d908c2af76598ad26999563bfef0089f1d3c8c32483eee6b108f0defa2773df0`.
- **1.12:** Feather Gen2 `1.12+build.2`; client SHA-256 `59e84cf6acc3912e1705ab05c722485afb3ec33343eef4756d8c16edfe6411ca`; mapped archive SHA-256 `73ce7d6d0a281db15fa030ae09e836387dae1172fdffcaf3e2d1a4840830b719`; embedded provenance SHA-256 `27515e5f4c71e7db2f035a81716507128d77466c75380ad1ef7d140c06401467`.

Current source hashes matched their recorded digests in both trees:

| Source | 1.11.2 SHA-256 | 1.12 SHA-256 |
|---|---|---|
| `net/minecraft/block/StairsBlock.java` | `5df1782da6eb7acdb9935b3910a0484d6417661bfd6230ecf81df0de6d782627` | `c52a011a759b6f3ef6d45162d3b61f464ceceee5d2d8735de6f6e25a56e55611` |
| `net/minecraft/block/Block.java` | `ab873436b24487ab0ee8055bf7a478349c7f7978398aea8e387796a6c820996a` | `e4a90eca411e7b0e14f5018f7385ec29891f6cdf1a1e917fa648b5712f9b33a1` |
| `net/minecraft/world/World.java` | `27cfaa5ff45c2d88c492fc5dae3ea4a2bb4536fd64bff8498a9a7d0cb82efb58` | `f7ab7a34d456e720e7ee4f34fbd0eb10f7f96151596c8c615ab2edef05a884d8` |
| `net/minecraft/entity/Entity.java` | `ce8104a17ce783df639cf9726e7b1cd0936563eaa7ba308603335bbe05d49440` | `80f091bf32166c88cf8bbd31caf72d84fa16224410733c7d2a0f00563f294a0a` |

Relevant members: `StairsBlock.java` 32–49 (18 static AABBs), 66–89 (emission order), 92–137 (directional box selection), 303–343 in 1.11.2 / 328–368 in 1.12 (resolver and neighbor predicates); `Block.java` 357–364 / 376–383 (translation, intersection and append); `World.java` 960–1013 / 964–1017 (collision collection); `Entity.java` 459–629 / 462–632 (movement queries). `LocalClientPlayerEntity.java` 824–829 / 841–846 forwards to `super.move(...)`.

## Actual neighbor-derived shape

For the center stair facing `F`, define `L = F.counterClockwiseY()`, `R = F.clockwiseY()`, `B = F.opposite()`. The source checks the front neighbor at `pos + F` first; the back neighbor at `pos + B` is considered only if front did not return a shape. A candidate must be a stair with the center's `HALF` and a perpendicular horizontal facing. The side guard passes when the cell queried by `isStairSameHalfSameFacing` is not a stair with the center's facing and half.

| Resolved shape | Neighbor witness from the resolver |
|---|---|
| `STRAIGHT` | Neither front nor back branch qualifies. |
| `OUTER_LEFT` | `pos+F`: same-half stair facing `L`; `pos+R` is not same-facing/same-half stair. |
| `OUTER_RIGHT` | `pos+F`: same-half stair facing `R`; `pos+L` is not same-facing/same-half stair. |
| `INNER_LEFT` | Front branch does not return; `pos+B`: same-half stair facing `L`; `pos+L` is not same-facing/same-half stair. |
| `INNER_RIGHT` | Front branch does not return; `pos+B`: same-half stair facing `R`; `pos+R` is not same-facing/same-half stair. |

Unmentioned cells may be arbitrary as long as they do not satisfy an earlier branch; this is a witness table, not a neighborhood simulator.

## Complete 40-tuple emission matrix

Rows are `(FACING, HALF, resolved SHAPE)`. Box constants are in source append order. The coordinate table gives each candidate's local `(minX,minY,minZ,maxX,maxY,maxZ)` extents; translate by `BlockPos` for world AABBs.

```text
40 resolved-state collision-emission tuples (local unit coordinates; candidate order)
FACING	HALF	RESOLVED_SHAPE	ORDERED_BOX_CONSTANTS
NORTH	BOTTOM	STRAIGHT	BOTTOM_BASE -> BOTTOM_NORTH
NORTH	BOTTOM	INNER_LEFT	BOTTOM_BASE -> BOTTOM_NORTH -> BOTTOM_SOUTH_WEST
NORTH	BOTTOM	INNER_RIGHT	BOTTOM_BASE -> BOTTOM_NORTH -> BOTTOM_SOUTH_EAST
NORTH	BOTTOM	OUTER_LEFT	BOTTOM_BASE -> BOTTOM_NORTH_WEST
NORTH	BOTTOM	OUTER_RIGHT	BOTTOM_BASE -> BOTTOM_NORTH_EAST
NORTH	TOP	STRAIGHT	TOP_BASE -> TOP_NORTH
NORTH	TOP	INNER_LEFT	TOP_BASE -> TOP_NORTH -> TOP_SOUTH_WEST
NORTH	TOP	INNER_RIGHT	TOP_BASE -> TOP_NORTH -> TOP_SOUTH_EAST
NORTH	TOP	OUTER_LEFT	TOP_BASE -> TOP_NORTH_WEST
NORTH	TOP	OUTER_RIGHT	TOP_BASE -> TOP_NORTH_EAST
SOUTH	BOTTOM	STRAIGHT	BOTTOM_BASE -> BOTTOM_SOUTH
SOUTH	BOTTOM	INNER_LEFT	BOTTOM_BASE -> BOTTOM_SOUTH -> BOTTOM_NORTH_EAST
SOUTH	BOTTOM	INNER_RIGHT	BOTTOM_BASE -> BOTTOM_SOUTH -> BOTTOM_NORTH_WEST
SOUTH	BOTTOM	OUTER_LEFT	BOTTOM_BASE -> BOTTOM_SOUTH_EAST
SOUTH	BOTTOM	OUTER_RIGHT	BOTTOM_BASE -> BOTTOM_SOUTH_WEST
SOUTH	TOP	STRAIGHT	TOP_BASE -> TOP_SOUTH
SOUTH	TOP	INNER_LEFT	TOP_BASE -> TOP_SOUTH -> TOP_NORTH_EAST
SOUTH	TOP	INNER_RIGHT	TOP_BASE -> TOP_SOUTH -> TOP_NORTH_WEST
SOUTH	TOP	OUTER_LEFT	TOP_BASE -> TOP_SOUTH_EAST
SOUTH	TOP	OUTER_RIGHT	TOP_BASE -> TOP_SOUTH_WEST
WEST	BOTTOM	STRAIGHT	BOTTOM_BASE -> BOTTOM_WEST
WEST	BOTTOM	INNER_LEFT	BOTTOM_BASE -> BOTTOM_WEST -> BOTTOM_SOUTH_EAST
WEST	BOTTOM	INNER_RIGHT	BOTTOM_BASE -> BOTTOM_WEST -> BOTTOM_NORTH_EAST
WEST	BOTTOM	OUTER_LEFT	BOTTOM_BASE -> BOTTOM_SOUTH_WEST
WEST	BOTTOM	OUTER_RIGHT	BOTTOM_BASE -> BOTTOM_NORTH_WEST
WEST	TOP	STRAIGHT	TOP_BASE -> TOP_WEST
WEST	TOP	INNER_LEFT	TOP_BASE -> TOP_WEST -> TOP_SOUTH_EAST
WEST	TOP	INNER_RIGHT	TOP_BASE -> TOP_WEST -> TOP_NORTH_EAST
WEST	TOP	OUTER_LEFT	TOP_BASE -> TOP_SOUTH_WEST
WEST	TOP	OUTER_RIGHT	TOP_BASE -> TOP_NORTH_WEST
EAST	BOTTOM	STRAIGHT	BOTTOM_BASE -> BOTTOM_EAST
EAST	BOTTOM	INNER_LEFT	BOTTOM_BASE -> BOTTOM_EAST -> BOTTOM_NORTH_WEST
EAST	BOTTOM	INNER_RIGHT	BOTTOM_BASE -> BOTTOM_EAST -> BOTTOM_SOUTH_WEST
EAST	BOTTOM	OUTER_LEFT	BOTTOM_BASE -> BOTTOM_NORTH_EAST
EAST	BOTTOM	OUTER_RIGHT	BOTTOM_BASE -> BOTTOM_SOUTH_EAST
EAST	TOP	STRAIGHT	TOP_BASE -> TOP_EAST
EAST	TOP	INNER_LEFT	TOP_BASE -> TOP_EAST -> TOP_NORTH_WEST
EAST	TOP	INNER_RIGHT	TOP_BASE -> TOP_EAST -> TOP_SOUTH_WEST
EAST	TOP	OUTER_LEFT	TOP_BASE -> TOP_NORTH_EAST
EAST	TOP	OUTER_RIGHT	TOP_BASE -> TOP_SOUTH_EAST

Box extents; tuple order is minX,minY,minZ,maxX,maxY,maxZ
BOTTOM_BASE	0,0,0,1,0.5,1
TOP_BASE	0,0.5,0,1,1,1
BOTTOM_NORTH	0,0.5,0,1,1,0.5
BOTTOM_SOUTH	0,0.5,0.5,1,1,1
BOTTOM_WEST	0,0.5,0,0.5,1,1
BOTTOM_EAST	0.5,0.5,0,1,1,1
TOP_NORTH	0,0,0,1,0.5,0.5
TOP_SOUTH	0,0,0.5,1,0.5,1
TOP_WEST	0,0,0,0.5,0.5,1
TOP_EAST	0.5,0,0,1,0.5,1
BOTTOM_NORTH_WEST	0,0.5,0,0.5,1,0.5
BOTTOM_NORTH_EAST	0.5,0.5,0,1,1,0.5
BOTTOM_SOUTH_WEST	0,0.5,0.5,0.5,1,1
BOTTOM_SOUTH_EAST	0.5,0.5,0.5,1,1,1
TOP_NORTH_WEST	0,0,0,0.5,0.5,0.5
TOP_NORTH_EAST	0.5,0,0,1,0.5,0.5
TOP_SOUTH_WEST	0,0,0.5,0.5,0.5,1
TOP_SOUTH_EAST	0.5,0,0.5,1,0.5,1
```

`Block.addCollision` skips only the `EMPTY_BLOCK_SHAPE` singleton for these non-empty constants, translates by block position, tests `queryBox.intersects(translatedBox)`, and appends the translated box only if the test passes. It processes each candidate in the matrix sequentially, preserving survivor order.

## Ordinary player path and per-operation comparison

`Entity.move(MoverType, x, y, z)` queries `World.getCollisions(this, ...)`; `PlayerEntity` inherits this common implementation. `LocalClientPlayerEntity.move` calls `super.move`, then auto-jump handling. The World list wrapper invokes its collector with `enforceWorldBorder=false`; that collector invokes `blockState.addCollisions(..., entity, false)`. Therefore ordinary player collision queries resolve the stair shape from neighbors.

Extracted declaration-plus-body SHA-256 (raw UTF-8 text) shows identical relevant operations:

| Operation | Extracted-body SHA-256 (both versions) |
|---|---|
| `StairsBlock.addCollisions` | `fee6311038377bfba454b065e055b380e8279c29c8e47234774eec692d3ea3d0` |
| `StairsBlock.getCollisionShapes` | `e22d6c6b55683846b88f2ae0951b12c9d30444a84396b9aa050e5a5adeb5806a` |
| `StairsBlock.getCollisionShapeForInnerStep` | `302436c7ff17826313034efd7314e0e84463e7a7540954bc786918252a700d53` |
| `StairsBlock.getCollisionShapeForOuterStep` | `4819868f88cda5bd2928eb90cff0892fcfc5ef285258a9069888699571e8a1d8` |
| `StairsBlock.resolveVirtualProperties` | `c0537a998d4746312870cdf5a9304586bcf4dbe76505f5cc74cb51e798bc12e2` |
| `StairsBlock.getStairShape` | `1fa1e66ba5b7aa1520397493cea25f9630927f3b12ecff507adbf3ec39343bd4` |
| `StairsBlock.isStairSameHalfSameFacing` | `619d5da11ee7fc1e20064abee252793aeb3fb9ef53508f25dc7589a8d820bf5a` |
| `StairsBlock.isStair` | `a52a28c82e6d17148ccb9d7336a40213dd71ab8efcd31a5816d9866bb0729e44` |
| `Block.addCollision` | `8a7ff325aa717dfa1d633480dec37cdd5e4879a447384b6cdcac219e75b1833d` |
| private `World.getCollisions` collector | `561ea969b971818b172625ddcbb76be6a8088c2a728c40883e2c396ca08d833e` |
| public `World.getCollisions(entity, box)` wrapper | `d99f4617ad87797e493598a05085e6670cc18f8f7cf29e190d7e50a437930191` |
| `LocalClientPlayerEntity.move` forwarding wrapper | `470de74594581ac10848b9e010ad124baa99b6ef2e20c9150989d764a4e0f653` |

`Entity.move` as a whole differs only by an additional air flap-sound branch later in 1.12; it does not change the collision queries or stair emission. Its extracted hashes are 1.11.2 `3eb9b53b913213f99e31dd047118490076f16a52b8a90d1570aa97d288d43b0d` and 1.12 `23844f36d3c547a674e3827928aafadfe0afd0be6825fb267ec7043ea55e66d9`.

## Reproduction

```powershell
python C:\Users\Wolfi\.codex\visualizations\2026\11\17\01a1eb7b-9d43-7363-8cf5-16eb00d97900\source-1112-112-stair-collision-matrix\derive_matrix.py
```

The script reads only the two exact mapped source trees, computes source-body hashes and enumerates the table/extents from the source switch rules. No Gradle, test task, game launch or decompilation is involved.

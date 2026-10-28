# 1.13.0 slab key, state, and shape closure to 26.2

## Evidence identity

- Older source: exact `1.13`, `ornithe-feather`; ready marker says `status=ready`, `versionId=1.13`, mapping `ornithe-feather`, mapping artifacts `feather-gen2-1.13+build.2-mergedv2.jar` and `feather-gen2-1.13+build.2.tiny`. Its source-manifest digest is `0509c2614b0bb0e616e2ebc6382f8eb5d031b5d86b834a4a6ed7fd96b6d68f1a`.
- Newer source: exact `26.2`, `unobfuscated`; ready marker says `status=ready`, `versionId=26.2`, mapping `unobfuscated`. Its source-manifest digest is `a7ad74fc712567eb87136afc1eafcfd602b4088e9869f3596331fd34faefe894`.
- The adjacent source hash manifest records SHA-256 for every decompiled source file used below and each ready/provenance/source-manifest file. The source-file digests were checked against the corresponding decompiler source manifests.

## Complete 1.13.0 slab roster and key mapping

`1.13/ornithe-feather/net/minecraft/block/Block.java:1411-1413,1568-1583` registers these 19 blocks, each with `new SlabBlock(...)`. `Blocks.java:143-161,752-770` exposes the same names as registry lookups. `26.2/unobfuscated/net/minecraft/world/level/block/Blocks.java` registers each corresponding `BlockItemIds` key using `SlabBlock::new`: prismarine keys at `3028-3045`, wood keys at `3201-3248`, and the remaining stone-family keys at `3296-3381`. `BlockItemIds.java:264-280` declares the key strings. The mapping is one-to-one by registry key:

| 1.13.0 registry key | 26.2 registry key | 1.13.0 material/state family |
| --- | --- | --- |
| `oak_slab` | `oak_slab` | wood |
| `spruce_slab` | `spruce_slab` | wood |
| `birch_slab` | `birch_slab` | wood |
| `jungle_slab` | `jungle_slab` | wood |
| `acacia_slab` | `acacia_slab` | wood |
| `dark_oak_slab` | `dark_oak_slab` | wood |
| `stone_slab` | `stone_slab` | stone |
| `sandstone_slab` | `sandstone_slab` | sandstone |
| `petrified_oak_slab` | `petrified_oak_slab` | petrified oak (stone material) |
| `cobblestone_slab` | `cobblestone_slab` | stone |
| `brick_slab` | `brick_slab` | stone |
| `stone_brick_slab` | `stone_brick_slab` | stone |
| `nether_brick_slab` | `nether_brick_slab` | stone |
| `quartz_slab` | `quartz_slab` | stone |
| `red_sandstone_slab` | `red_sandstone_slab` | sandstone |
| `purpur_slab` | `purpur_slab` | stone |
| `prismarine_slab` | `prismarine_slab` | stone |
| `prismarine_brick_slab` | `prismarine_brick_slab` | stone |
| `dark_prismarine_slab` | `dark_prismarine_slab` | stone |

No two keys in this 1.13.0 roster converge to one 26.2 registry block. The keys remain distinct block instances, while every listed key on each release uses that release's common `SlabBlock` implementation and common slab property schema. In particular, `petrified_oak_slab` remains a distinct current key; it does not alias to `oak_slab`. The `Blocks` registration arguments show material, map-color and other block-property differences, but these do not enter the slab shape calculation.

The roster is exhaustive for 1.13.0's vanilla slab blocks: `Blocks.java:143-161` declares exactly these slab registry references, and the matching registry assignments are at `752-770`. Older pre-flattening metadata block IDs are not additional 1.13.0 block keys. The 26.2 block registry also contains slab keys introduced after 1.13.0 (for example smooth, cut, newer wood, and later stone families); those are not members of the 1.13.0 roster and retain current-era feature behavior.

## State schema and defaults

In 1.13.0, `SlabBlock.java:25-26,30-42` uses `HALF` as the Java field name for `Properties.SLAB_TYPE` and declares `WATERLOGGED`. `Properties.java:43,97` names these properties `waterlogged` and `type`. `SlabType.java` has exactly `TOP("top")`, `BOTTOM("bottom")`, and `DOUBLE("double")`. The constructor default at `SlabBlock.java:30-32` is `type=bottom,waterlogged=false`.

In 26.2, `SlabBlock.java:33-34,41-55` uses `TYPE=BlockStateProperties.SLAB_TYPE` plus `WATERLOGGED`; `BlockStateProperties.java:44,135` gives them the same property names, and its `SlabType.java` has the same three serialized values. The constructor default at `SlabBlock.java:44-45` is also `type=bottom,waterlogged=false`.

Thus each mapped key has the same six property combinations: bottom/top/double crossed with `waterlogged=false/true`. The property definitions allow these combinations; placement/liquid methods impose additional rules, but those are not collision-shape rules. Preserve `waterlogged` as a current vanilla property and do not infer equality of the blocks' other behavior from this state-schema match.

## Collision-shape equivalence and provider gates

The 1.13.0 shapes in `SlabBlock.java:27-28,51-60` are constructed as follows. `Block.block` at `Block.java:152` divides all six coordinates by 16:

| `type` | 1.13.0 shape | 26.2 shape | normalized bounds `[minX,minY,minZ]` to `[maxX,maxY,maxZ]` |
| --- | --- | --- | --- |
| `bottom` | `Block.block(0,0,0,16,8,16)` | `Block.column(16,0,8)` | `[0,0,0]` to `[1,0.5,1]` |
| `top` | `Block.block(0,8,0,16,16,16)` | `Block.column(16,8,16)` | `[0,0.5,0]` to `[1,1,1]` |
| `double` | `VoxelShapes.block()` | `Shapes.block()` | `[0,0,0]` to `[1,1,1]` |

The current `Block.column` at `Block.java:177-183` centers the requested 16-wide X/Z column and delegates to `Block.box`; `Block.box` at `160-162` divides coordinates by 16. So the listed bounds equal the older coordinates exactly. The 26.2 `SlabBlock.getShape` at `59-64` ignores its `CollisionContext`, level, and position. The 1.13.0 override at `51-60` likewise selects only the state type.

The collision provider is also compatible for these blocks. Both base implementations return the state's shape when `hasCollision` is true, otherwise an empty shape (`1.13/Block.java:409-410`; `26.2/BlockBehaviour.java:327-328`). `hasCollision` defaults to true in both property builders (`1.13/Block.java:1910`; `26.2/BlockBehaviour.java:985`), and none of the 19 slab registrations applies `noCollision`. Consequently every state listed above has the same collision shape under the default collision query, including both waterlogged values. A generic collision-only collector can use the shared current slab class/state property shape contract; an explicit old-key roster can use the one-to-one table above.

This closes collision geometry only. It does not establish parity for full-block/occlusion/light shapes, face shapes, pathfinding, placement, collision response beyond the provider's returned shape, fluids, drops, material properties, tags, or any other block operation. Keep block states vanilla and preserve 26.2 behavior for later-only slab keys and features. If a collector is reused for any non-collision operation, that operation needs its own source comparison.

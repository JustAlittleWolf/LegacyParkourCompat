# 1.13.2–1.14.4 stained glass pane wall-neighbor predicate

## Scope and source identity

This bounded source-only check covers the 16 stained-glass-pane registry keys that remain marked `UNEXAMINED_FOR_WALL_PREDICATE` in `wall_full_registry_owner_crosswalk/unexamined-wall-projection-keys.tsv`. It compares the adjacent `WallBlock` neighbor-predicate result for all registered pane states and four horizontal wall directions at the exact endpoints. It does not claim player trajectory parity, complete wall-state production, full-pair coverage, or an intermediate release boundary.

- 1.13.2 Ornithe Feather Gen2 build 2 source root: `decompiled_minecraft/1.13.2/ornithe-feather`; ready source-manifest SHA-256 `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211`. The ready marker's `sourceManifestSha256` matches the recomputed `ornithe-feather.sources.sha256` digest.
- 1.14.4 Ornithe Feather Gen2 build 2 source root: `decompiled_minecraft/1.14.4/ornithe-feather`; ready source-manifest SHA-256 `717b468536348557f9dae8cf57edccf80ad16eb65e5bef6c60bdb5ea3c9f75bc`. The ready marker's `sourceManifestSha256` matches the recomputed `ornithe-feather.sources.sha256` digest.
- The existing accepted `glass_pane_crosswalk` concerns the 18-key class/effective collision-provider mapping. It does not evaluate the `WallBlock` neighbor-predicate truth table. This report reuses that provider mapping only to identify the target collision-shape method and independently evaluates the wall predicate for these 16 keys.

## Registered keys and state domain

The keys are `black_stained_glass_pane`, `blue_stained_glass_pane`, `brown_stained_glass_pane`, `cyan_stained_glass_pane`, `gray_stained_glass_pane`, `green_stained_glass_pane`, `light_blue_stained_glass_pane`, `light_gray_stained_glass_pane`, `lime_stained_glass_pane`, `magenta_stained_glass_pane`, `orange_stained_glass_pane`, `pink_stained_glass_pane`, `purple_stained_glass_pane`, `red_stained_glass_pane`, `white_stained_glass_pane`, and `yellow_stained_glass_pane`.

The accepted crosswalk records `StainedGlassPaneBlock` as the registered runtime class on both endpoints for each color key. Each class declares the same four Boolean connection properties (`NORTH`, `EAST`, `SOUTH`, `WEST`) and `WATERLOGGED`; their Cartesian state domain is 32 states per key. The target class extends `IronBarsBlock`; the old class extends `GlassPaneBlock`, which inherits `IronBarsBlock`'s face-shape implementation. Thus the checked domain is 16 keys × 32 registered states × four horizontal faces per endpoint.

## Predicate comparison

In 1.13.2, `WallBlock.getPlacementState` and horizontal `updateShape` call `shouldConnectTo(neighbor, neighbor.getFaceShape(...))`. `shouldConnectTo` admits `MIDDLE_POLE_THICK`, or `MIDDLE_POLE` for a fence gate, or a non-exception `SOLID` face. Old `IronBarsBlock.getFaceShape` returns `MIDDLE_POLE_THIN` for every horizontal face, independent of state, world and position. The old wall predicate therefore returns false for every stained pane key/state/direction.

In 1.14.4, `WallBlock.getPlacementState` and horizontal `updateShape` call `m_71853799(neighbor, neighbor.m_87223014(...), direction)`. That predicate admits a full collision face unless the block is an attachment exception, or a member of `BlockTags.WALLS`, or an eligible fence gate. `BlockState.m_87223014` delegates to `Block.isFaceSolid`; that method requires the collision-shape face to be a full block face. The `PaneBlock` collision shape for these keys is built from a 2/16-wide central post and optional 2/16-wide arms reaching the four horizontal boundaries. For any horizontal face and any assignment of the four connection bits, a boundary arm, when present, covers only a 2/16 strip in the tangent axis; it cannot be a full face. The `WATERLOGGED` bit does not enter the shape index. Thus `m_87223014` is false in all 32 states and four queried horizontal directions.

The other target admission path is also false: none of these pane classes is a `FenceGateBlock`, and the exact 1.14.4 `data/minecraft/tags/blocks/walls.json` payload lists only 14 wall registry keys, none of which is a stained glass pane. The payload SHA-256 is `f20174f51f66d27681fd9d469a77c86cae82012c0d9125e7d246c426b772391c`; the exact raw-client extraction and its independent acceptance are recorded in `wall_resource_membership/extraction-record.md` (SHA-256 `f26f84c7221f667cd84ff4e048d06b0fd3c3aec52779f3fbd0a8f4e2afe64fea`) and `wall_resource_membership_audit/verdict-r2.md` (SHA-256 `6402c6af692b3f198c853d3558b91d4de7c215db793e8847e66e45f08f4d8898`). Consequently, the target wall predicate also returns false for every checked pane key/state/direction.

**Bounded result:** no difference in the horizontal wall-neighbor bit for these 16 stained pane keys across all registered states. The independent source acceptance of this report is pending.

## Limits and next gate

This closes only these stained-pane predicate rows. It does not establish how the four directional pane bits are produced in reachable worlds, parity for other wall-neighbor classes, or any movement outcome from wall state changes. The existing glass-pane provider crosswalk explicitly leaves the broader neighbor-state producer inventory open. Pair coverage and independent full-pair audit remain open.

## Java source SHA-256 ledger

Each hash below was checked against its endpoint source manifest.

| Endpoint | Relative source path | SHA-256 |
|---|---|---|
| 1.13.2 | `net/minecraft/block/WallBlock.java` | `aeb67adf292a3b750e72a7d2fdc13f7b42f8633c6183964d00dfe8d6960e07bf` |
| 1.13.2 | `net/minecraft/block/IronBarsBlock.java` | `df6d956bb2745241e59294056f31513151b976dbd9ceed3f5478fd72b66c68a0` |
| 1.13.2 | `net/minecraft/block/StainedGlassPaneBlock.java` | `494d69648753478aa813a5b20a62b4a8f75b33b38c1d0f70061b9cafafda257d` |
| 1.13.2 | `net/minecraft/block/PaneBlock.java` | `9422e3f2aa9843ecd041f5ced13d05a4c7472d697f7dfd838450e49969e5326c` |
| 1.14.4 | `net/minecraft/block/WallBlock.java` | `033da2cef0973f5d380a81b53ee76e23324fffece72edc74e12fcee0d8764be7` |
| 1.14.4 | `net/minecraft/block/IronBarsBlock.java` | `10ccd6a2135cb05e74cdd4c440da29992dbc60655beedcb5f648f73bbc150541` |
| 1.14.4 | `net/minecraft/block/StainedGlassPaneBlock.java` | `d955bb542a9e9f148ac9c6c299b466a3358fca77c9a3f28330edd614891974c7` |
| 1.14.4 | `net/minecraft/block/PaneBlock.java` | `7150e89268a84113453cd5416ffe25e1ca7d42a39f264f7ae989878a10ee2158` |
| 1.14.4 | `net/minecraft/block/Block.java` | `1f8ec3628f08e196c2793e91ca0b94feacea6bc3939e7fb770f0682a6e66f2c5` |
| 1.14.4 | `net/minecraft/block/state/BlockState.java` | `6c6703c7c2f7203a7507d8a621bc83e1dad613b32089394f5af4fbd8636b165f` |
| 1.14.4 | `net/minecraft/block/Blocks.java` | `9c0323497b31f8a284456e5692898897b92b6f0817d0371fae9a8afb54c638f8` |

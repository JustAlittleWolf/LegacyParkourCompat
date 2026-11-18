# B16 blind source audit: stationary dry walls and panes, 26.1.2 to 26.2

Status: completed bounded independent source audit. Static only; no product, wiki, Git history, tests, runtime, or other lanes consulted.

## Source identity and integrity

- Exact versions/mapping: 26.1.2/unobfuscated and 26.2/unobfuscated.
- 26.1.2 source-manifest SHA-256: `54ae320660ca911a6d20514c965c33eb414fbedd1ad6727bbaa95fe906aa23c0` (matches supplied identity and `unobfuscated.ready.json` claim).
- 26.2 source-manifest SHA-256: `a7ad74fc712567eb87136afc1eafcfd602b4088e9869f3596331fd34faefe894` (matches supplied identity and `unobfuscated.ready.json` claim).
- Direct SHA-256 checks passed against manifest entries for each release's `WallBlock.java`, `IronBarsBlock.java`, `StainedGlassPaneBlock.java`, `CrossCollisionBlock.java`, `BlockBehaviour.java`, and `Blocks.java`.
- Ready manifests identify their version IDs and unobfuscated mappings. Decompilation source trees were read-only.

## Exact verdicts

- Wall: **COMPLETED — no ordinary stationary dry shape or relevant local input difference found.** `WallBlock.makeShapes` retains the cached outline/collision shape construction and values: outline post 16, wall top 14; collision post and wall top 24; 6-wide post and 6-wide directional arms. `getShape` and `getCollisionShape` still select cached shape by state. The only `WallBlock` source delta found is rotation switch syntax/casts, outside this stationary shape slice. Placement/update connection inputs remain the same: face-sturdy neighbor query, wall tag, non-exception sturdy face, bars, and directional gate check. The vertical above-neighbor collision face (`getCollisionShape(...).getFaceShape(DOWN)`) and the same post/arm coverage tests remain unchanged.
- Iron bars: **COMPLETED — no ordinary stationary dry shape or relevant local input difference found.** `IronBarsBlock` source is byte-identical in both releases. It still calls `CrossCollisionBlock(2,16,2,16,16)`, keeps the N/E/S/W mask plus waterlogged state, and attaches via the same face-sturdy, same-class iron bars, or wall-tag predicates. Horizontal updates query the opposite neighbor face; non-horizontal updates delegate.
- Glass pane: **COMPLETED — no ordinary stationary dry shape or relevant local input difference found.** `Blocks.GLASS_PANE` remains registered as `IronBarsBlock` with the same glass properties. `CrossCollisionBlock` shape-generation math and selected local shape/collision behavior are unchanged; its only source delta is rotation syntax/casts.
- Stained glass panes: **COMPLETED — no ordinary stationary dry shape or relevant local input difference found.** `StainedGlassPaneBlock.java` is byte-identical and still inherits `IronBarsBlock`/`CrossCollisionBlock` geometry. Registration changed from sixteen explicit registrations to `ColorCollection.registerBlocks`, with the same factory and properties. The 26.2 collection order is white, orange, magenta, light blue, yellow, lime, pink, gray, light gray, cyan, purple, blue, brown, green, red, black, matching the prior explicit registration order. It selects each pane's `DyeColor`; geometry does not depend on color.

## Shared shape/support dependency check

`BlockBehaviour` shape getters and `BlockStateBase` cached `collisionShape`/face-sturdiness use sites retain the same operations for this slice. The differences observed elsewhere in that large file concern unrelated modern behavior/API members; no change was found in the consulted local collision or ordinary face-support path. Both pane families resolve their cross shapes from the same horizontal connection mask and use the inherited shape cache.

## Bounded coverage limits

Checked paired `WallBlock`, `IronBarsBlock`, `StainedGlassPaneBlock`, `CrossCollisionBlock`, `BlockBehaviour`, and `Blocks` source, plus the 26.2 `ColorCollection` implementation and `BlockItemIds` pane key. Covered ordinary stationary dry instances and legal neighboring air, stone/sturdy, matching wall, iron bars, and pane states as relevant to local neighbor masks and inherited shapes. This is not a full 26.1.2→26.2 tick inventory or pair-coverage freeze. Excluded fences, the transferred block families assigned elsewhere, generic collision solver, fluid/waterlogged behavior, restitution, and other edges.

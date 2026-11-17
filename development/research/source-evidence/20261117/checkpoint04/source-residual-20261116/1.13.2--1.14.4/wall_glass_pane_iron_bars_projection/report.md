# Glass pane and iron bars wall-neighbor predicate: 1.13.2 → 1.14.4

## Exact source identity

Both endpoints are Ornithe Feather Gen2 build 2. The 1.13.2 ready marker binds source manifest SHA-256 `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211`; 1.14.4 binds `717b468536348557f9dae8cf57edccf80ad16eb65e5bef6c60bdb5ea3c9f75bc`. The ready markers and source manifests were checked, and every consulted Java file below matches its endpoint manifest entry and local file bytes.

The exact rows are selected from `wall_full_registry_owner_crosswalk/unexamined-wall-projection-keys.tsv` (SHA-256 `8a79ad3e851470a59976b9120d085ab2ec556d3cd61885205113dbd167712366`): `minecraft:iron_bars` and `minecraft:glass_pane`. The previously accepted `glass_pane_crosswalk/report.md` (SHA-256 `e1150fb5ddc471defc1a67871e4b734012f3e713758d2c32de9261b2227e51ca`) establishes registration/class and effective PaneBlock provider mapping only; it does not review WallBlock neighbor predicates. The distinct stained-glass-pane wall-predicate slice is not included here.

| Endpoint source | SHA-256 |
|---|---|
| 1.13.2 `net/minecraft/block/WallBlock.java` | `aeb67adf292a3b750e72a7d2fdc13f7b42f8633c6183964d00dfe8d6960e07bf` |
| 1.13.2 `net/minecraft/block/Block.java` | `735030e8bb5fc7ead2d6dbcb1b262a36a414b4349daf197456337f460b444cd4` |
| 1.13.2 `net/minecraft/block/IronBarsBlock.java` | `df6d956bb2745241e59294056f31513151b976dbd9ceed3f5478fd72b66c68a0` |
| 1.13.2 `net/minecraft/block/GlassPaneBlock.java` | `7abf507b21b920c76e63e34293e6f40bbdefc95df396eb4847406a6d41df96a0` |
| 1.13.2 `net/minecraft/block/PaneBlock.java` | `9422e3f2aa9843ecd041f5ced13d05a4c7472d697f7dfd838450e49969e5326c` |
| 1.13.2 `net/minecraft/block/state/BlockState.java` | `0496fda381628e90b3b3ff8ae376a5bae24cbd662366f34d70991252fab2fe70` |
| 1.14.4 `net/minecraft/block/WallBlock.java` | `033da2cef0973f5d380a81b53ee76e23324fffece72edc74e12fcee0d8764be7` |
| 1.14.4 `net/minecraft/block/Block.java` | `1f8ec3628f08e196c2793e91ca0b94feacea6bc3939e7fb770f0682a6e66f2c5` |
| 1.14.4 `net/minecraft/block/IronBarsBlock.java` | `10ccd6a2135cb05e74cdd4c440da29992dbc60655beedcb5f648f73bbc150541` |
| 1.14.4 `net/minecraft/block/PaneBlock.java` | `7150e89268a84113453cd5416ffe25e1ca7d42a39f264f7ae989878a10ee2158` |
| 1.14.4 `net/minecraft/block/Blocks.java` | `9c0323497b31f8a284456e5692898897b92b6f0817d0371fae9a8afb54c638f8` |
| 1.14.4 `net/minecraft/block/state/BlockState.java` | `6c6703c7c2f7203a7507d8a621bc83e1dad613b32089394f5af4fbd8636b165f` |

## Registration and legal states

At 1.13.2, `Block.java:1183–1184` registers iron bars as `IronBarsBlock` and glass pane as `GlassPaneBlock`; `GlassPaneBlock` subclasses `IronBarsBlock`. At 1.14.4, `Blocks.java:703–708` registers both keys as `IronBarsBlock`. In both releases, `IronBarsBlock.buildStateDefinition` adds horizontal `NORTH`, `EAST`, `SOUTH`, `WEST` booleans and `WATERLOGGED`: 32 declared states per key. This slice evaluates the same 32-state domain on each endpoint (2 keys, 64 key-state combinations), across four horizontal wall-neighbor directions.

## Wall predicate comparison

**1.13.2.** `WallBlock.shouldConnectTo` (`WallBlock.java:51–55`) accepts a thick middle pole, a middle pole only for a `FenceGateBlock`, or an unexcepted `FaceShape.SOLID`. The inherited `IronBarsBlock.getFaceShape` (`IronBarsBlock.java:116–117`) always returns `MIDDLE_POLE_THIN` for horizontal faces, irrespective of connection bits or waterlogging. Neither key is a fence gate. Thus both keys return false in every horizontal wall direction and state.

**1.14.4.** `WallBlock.m_71853799` (`WallBlock.java:48–52`) tests the generic arm with `!isExceptionForAttachment(block) && BlockState.m_87223014(...)`, plus independent `WALLS` tag and fence-gate arms. `BlockState.m_87223014` (`BlockState.java:326–327`) delegates uncached states to `Block.isFaceSolid`; its cache is populated by that same function (`BlockState.java:416`). `Block.isFaceSolid` checks the full-face projection of the state's collision shape (`Block.java:402–408`). The registered `IronBarsBlock` inherits `PaneBlock.getCollisionShape` (`PaneBlock.java:89`), which selects `this.shapes` created at lines 36–37. Its pane shape has a centered 2/16-wide post and directional 2/16-wide arms. On a queried horizontal outer plane, a present arm occupies at most a 2/16 strip of the 16/16 face; without that arm, it does not reach the plane. Other arms and the center post are inset from that plane. Therefore no legal connection-bit combination fills a complete horizontal collision face, so `isFaceSolid` is false for every state and direction.

Neither `minecraft:iron_bars` nor `minecraft:glass_pane` is in the 1.14.4 `WALLS` resource or a fence gate. The audited exact raw-client payload `data/minecraft/tags/blocks/walls.json` has SHA-256 `f20174f51f66d27681fd9d469a77c86cae82012c0d9125e7d246c426b772391c` (14 values; extraction record SHA `f26f84c7221f667cd84ff4e048d06b0fd3c3aec52779f3fbd0a8f4e2afe64fea`; independent resource audit r2 SHA `6402c6af692b3f198c853d3558b91d4de7c215db793e8847e66e45f08f4d8898`). Thus the target-only alternatives cannot set a wall connection bit for these keys.

## Bounded result and limits

For both exact registry keys, the old wall predicate is false and the target wall predicate is false for all 32 declared states and all four horizontal directions. This is a no-difference source result for the selected wall-neighbor bit only. It does not compare pane placement/neighbor-state production or player collision displacement, and it does not close the full pair. The global crosswalk's r1 TSV was overwritten before its correction audit, so that historical unchanged-row byte-diff limitation remains attached to the crosswalk; this report independently binds and verifies only the two selected rows.

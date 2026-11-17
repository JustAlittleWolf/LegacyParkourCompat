# Independent source audit: glass pane and iron bars wall predicate

## Verdict

**ACCEPT**, bounded to the `minecraft:glass_pane` and `minecraft:iron_bars` wall-neighbor predicate rows in the audited report. The old and target predicates both return false for every declared state and each horizontal direction. This does not establish displacement or close the pair.

Audited report: `build/source-residual-20261116/1.13.2--1.14.4/wall_glass_pane_iron_bars_projection/report.md`  
Report SHA-256: `c481570164d1ad656f9020b9e04113295bc5842addde6278452b3a6114fa0da5`

## Independent checks

- The ready markers bind source manifests `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211` (1.13.2) and `717b468536348557f9dae8cf57edccf80ad16eb65e5bef6c60bdb5ea3c9f75bc` (1.14.4). Both report identities match the ready markers.
- Each of the 12 Java file hashes listed by the report was recomputed from canonical decompiled sources and matched both the report and that version's `ornithe-feather.sources.sha256` entry.
- The cited unexamined crosswalk TSV hash matches `8a79ad3e851470a59976b9120d085ab2ec556d3cd61885205113dbd167712366`; its exact two rows are `minecraft:glass_pane` and `minecraft:iron_bars`, with matching four directional booleans plus `WATERLOGGED` (32 states per key).
- In 1.13.2, `Block.java` registers `iron_bars` as `IronBarsBlock` and `glass_pane` as `GlassPaneBlock`; the latter extends `IronBarsBlock`. `IronBarsBlock.buildStateDefinition` adds the five booleans and its horizontal `getFaceShape` is always `MIDDLE_POLE_THIN`. `WallBlock.shouldConnectTo` only admits a thick pole, a middle pole for a `FenceGateBlock`, or a non-excepted `SOLID` face. Thus these pane faces do not connect.
- In 1.14.4, `Blocks.java` registers both keys as `IronBarsBlock`. `BlockState.m_87223014` uses the cached face-solid bit or delegates to `Block.isFaceSolid`; the cache is populated by that same method. `PaneBlock.getCollisionShape` chooses the state-indexed shape. `IronBarsBlock` constructs its pane at 2/16 thickness; for a face on a pane's outer horizontal plane, the only possible boundary contact is a 2/16-wide arm, and all other arms/post are inset. The projected face is therefore not full for any connection bits, so `isFaceSolid` is false.
- The target `WallBlock` additionally accepts `WALLS` members and qualifying fence gates. Neither key is a fence gate or in the exact `walls.json` payload (SHA-256 `f20174f51f66d27681fd9d469a77c86cae82012c0d9125e7d246c426b772391c`), whose 14 values were independently resource-audited under verdict SHA `6402c6af692b3f198c853d3558b91d4de7c215db793e8847e66e45f08f4d8898`.

The earlier `glass_pane_crosswalk` is used only for class/provider identity; it does not establish this wall-predicate result. The distinct stained-glass-pane predicate packet is outside this audit. The global crosswalk retains its documented r1-overwrite limitation; this ACCEPT applies only to the two selected rows and does not assert crosswalk completeness or pair freeze.

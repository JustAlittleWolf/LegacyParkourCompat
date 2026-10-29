# Independent review: iron bars ID 101 bounded provider closure

Verdict: **ACCEPT**, limited to the report's source claims for legacy ID 101, its default all-false state mapping, and the all-air-neighbor collision geometry case. This does not accept general neighbor conversion, whole traversal, DDA ordering, movement parity, or other pane IDs.

## Reviewed inputs

- Finding: `.native-source/20261028/collision-next/dda-iron-bars-101/FINDING.md`
- Finding SHA-256: `C706322C5A9B90252BB6B2882052C227554C9F569866A26CDC14EDCDD634918C`
- Source ledger: `.native-source/20261028/collision-next/dda-iron-bars-101/source-hashes.json`
- Source ledger SHA-256: `DC20F215A815A7D02A4EA6BEC87641A9335F3C7C04163267148482F6A8A58FE2`

## Verification

The ledger binds the ready markers and source-manifest hashes for 1.8.9 `ornithe-feather` and 26.2 `unobfuscated`. I rehashed all eight listed Java files against both the live canonical files and their `.sources.sha256` entries. All eight matched; the two source-manifest and two ready-marker hashes also matched the ledger (12/12 checks).

The exact 1.8.9 registration is `Block.java:1040`: ID 101 is `new PaneBlock(Material.IRON, true)`, distinct from ID 102 glass pane. `PaneBlock` initializes all four direction properties false; `getMetadataFromState` returns zero. In `addCollisions`, with all four neighboring blocks air, the first and second conditional groups each take their final `else`, adding an east-west full-height strip and a north-south full-height strip. The base `Block.addCollisions` adds each box when it intersects the query, so both are contributed for the report's query covering the unit cell.

For 26.2, `BlockItemIds.IRON_BARS` creates `iron_bars`; `Blocks.IRON_BARS` registers `IronBarsBlock`; and `EntityBlockStateFix` maps `minecraft:iron_bars` to old ID 101. `BlockStateData` entry 1616 maps metadata-zero legacy state to `iron_bars` with all four direction properties false. `IronBarsBlock` constructs a 2/16 post with 16/16 collision height. `CrossCollisionBlock.makeShapes` starts each shape with that post and only adds arms for true direction properties; its all-false collision shape is therefore center-post-only. This verifies the bounded old/current geometry difference.

## Limits

The finding correctly leaves the old neighbor predicate and reconstruction of other adjacent states open. The all-air result is one bounded state/query case; no whole-operation or general movement equivalence is inferred.

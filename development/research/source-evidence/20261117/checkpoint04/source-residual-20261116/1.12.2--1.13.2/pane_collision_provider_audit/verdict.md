# Independent audit: pane collision provider

## Verdict

**ACCEPT for the bounded fixed-connection-bit provider comparison** in `pane_collision_provider/report.md` (SHA-256 `d19fb325f3a20ce89326329f42853fd4318ebfc6819f5861fd41eade4825cadd`). The conclusion is conditional geometry equivalence for identical N/E/S/W bits, not overall player-movement equivalence.

## Identity and registration checks

The 1.12.2 and 1.13.2 ready markers and source-manifest bytes identify Feather Gen2 build 2 with source-manifest SHA-256 `b8a37ccfccd2aac5f40f5e34fec85873dbdfa93a266043c103e592e4a8c949da` and `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211`, respectively. I rehashed all 11 Java files listed in the report against their canonical manifest entries; every value matches.

The old `Block.java` registrations map `iron_bars` and `glass_pane` to `PaneBlock`. The target registrations preserve both keys, constructing `IronBarsBlock` and `GlassPaneBlock`; `GlassPaneBlock` inherits its collision provider from `IronBarsBlock`/`PaneBlock`. The cited provider methods and world collision dispatch are present at the reported locations.

## Provider comparison

The old ordinary `World.getCollisions` route calls `addCollisions(..., false)`. Old `PaneBlock.addCollisions` therefore calls `resolveVirtualProperties` to recompute all four connection bits from adjacent states, then appends the center box and each enabled arm box. The target `PaneBlock.getCollisionShape` indexes the voxel-shape array from the four stored connection bits; it does not reread world, neighbor, position, or collision context. `WATERLOGGED` is registered in the target state but is absent from `getShapeIndex` and the collision provider.

For a fixed connection mask, the old center-plus-enabled-arm box union matches the target center-plus-enabled-arm voxel-shape union. Both use the same horizontal direction IDs (SOUTH=0, WEST=1, NORTH=2, EAST=3). The target `IronBarsBlock` constructor uses full-height pane dimensions, so the collision shape has the same center width and arm extents as the old boxes. Glass pane inherits those same parameters. This covers both keys, all 16 directional masks, and both target waterlogged values.

## Limits retained

The report correctly stops at conditional geometry equivalence. Old collision collection recomputes connection bits live from neighbor FaceShape/exception results, while the target getter consumes stored bits populated by placement/update routes. The audit does not establish that those bits match for all neighbor snapshots, stale or externally inserted states, or migration routes. No whole-movement no-difference, displacement, trajectory, or pair-freeze claim is accepted.

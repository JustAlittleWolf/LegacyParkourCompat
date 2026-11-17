# Independent source audit: pane neighbor producer

Verdict: **ACCEPT**

Reviewed report: `build/source-residual-20261116/1.13.2--1.14.4/pane_neighbor_producer/report.md`, SHA-256 `6c9d762cefb7a4af92954037906d65f6c891c27e2d269e08d6333506bb409202` (recomputed from the file; matches `report.sha256`).

Exact inputs were verified from the local ready markers and source manifests. The 1.13.2 Ornithe Feather Gen2 build.2 manifest `decompiled_minecraft/1.13.2/ornithe-feather.sources.sha256` hashes to `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211`; its ready marker identifies `versionId=1.13.2`, `mapping=ornithe-feather`, and the matching manifest hash. The corresponding 1.14.4 build.2 manifest hashes to `717b468536348557f9dae8cf57edccf80ad16eb65e5bef6c60bdbb5ea3c9f75bc`; its ready marker identifies `versionId=1.14.4`, `mapping=ornithe-feather`, and the matching manifest hash.

I recomputed the SHA-256 of every Java file in the report's source table and matched each digest to its corresponding row in the endpoint source manifest: 1.13.2 `IronBarsBlock`, `Block`, `Blocks`, `BeaconBlock`, `GlowstoneBlock`, and `LeavesBlock`; 1.14.4 `IronBarsBlock`, `Block`, `state/BlockState`, `Blocks`, `BeaconBlock`, and `LeavesBlock`. All twelve hashes match.

The producer claims are supported by the intact endpoint methods. Both `IronBarsBlock.getPlacementState` methods read north, south, west, east neighbor states and set `NORTH`, `SOUTH`, `WEST`, `EAST` in that order, then set `WATERLOGGED`. Each uses the opposite face of that neighbor. Both `updateShape` methods schedule a water tick before branching; for horizontal directions they update the matching directional property using the opposite face, and for nonhorizontal directions delegate to `super`. The classifier receives no fluid-state input. The concrete outcome therefore controls the same stored directional bit in placement and horizontal update.

Classification checks also match source:

- In 1.13.2, `shouldConnectTo` is `(!isExceptionForConnection(block) && faceShape == SOLID) || faceShape == MIDDLE_POLE_THIN`. The old exception set includes leaves, beacon, and glowstone. `Block.getFaceShape` defaults to `SOLID`; `IronBarsBlock.getFaceShape` returns `MIDDLE_POLE_THIN` on horizontal faces.
- In 1.14.4, `shouldConnectTo` is `(!isExceptionForAttachment(block) && faceSolid) || block instanceof IronBarsBlock`. The target attachment exception set includes leaves but not beacon or glowstone. `BlockState.m_87223014` uses cached face solidity or `Block.isFaceSolid`, which rejects leaves-tagged states and otherwise requires a full collision-shape face.
- Ordinary default full-cube blocks that are outside both exception sets connect on both sides. Leaves remain false on both sides. Iron-bars subclasses (including the pane classes covered by the cited crosswalk) remain connected: old horizontal face-shape override and the target class alternative each yield true.
- Beacon is old-excluded and has a solid default face; on the target it is outside the attachment exceptions and inherits the full-block collision shape, so the bit changes false to true. Glowstone is likewise old-excluded and has a solid default face; target registration constructs plain `Block` with ordinary collision, and it is not a target attachment exception, so its bit changes false to true.

Corrections: none to the bounded claims. The report appropriately limits its conclusion: these examples do not census every neighbor or establish an intermediate release boundary, full-pair coverage, implementation acceptance, or runtime parity. The pane-shape consequence is scoped through the report's stated already-accepted provider/query evidence; this verdict accepts the audited producer behavior only.

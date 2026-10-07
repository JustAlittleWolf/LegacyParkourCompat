# F001: Farmland movement collision height changes

- Older version A: 1.9.4, Ornithe Feather `net.ornithemc:feather-gen2:1.9.4+build.2`
- Newer version B: 1.10.2, Ornithe Feather `net.ornithemc:feather-gen2:1.10.2+build.2`
- Mechanic / coverage slice IDs: farmland collision shape; `BLK-FARMLAND-COLLISION`
- Classification: changed behavior
- Confidence: candidate (pair mapped-jar hashes currently differ from the ready artifact manifests; awaiting canonical repair and fresh verification)
- Applicability: historical player behavior
- First changed release: unknown within (1.9.4, 1.10.2]
- Runtime validation: not performed

## Paired evidence

- A artifact: validated 1.9.4 `ornithe-feather` ready record and manifest in the run report. `net/minecraft/block/FarmlandBlock.java`, `FarmlandBlock#getCollisionShape(BlockState, World, BlockPos)`, lines 31-40, SHA-256 `9df445485ba7e1603c20b5dede847fe412558148acefe1eedc9bb3b62b14c25`; returns `FULL_BLOCK_SHAPE`. Its outline `SHAPE` is `Box(0.0, 0.0, 0.0, 1.0, 0.9375, 1.0)`.
- B artifact: validated 1.10.2 `ornithe-feather` ready record and manifest in the run report. `net/minecraft/block/FarmlandBlock.java`, class body/shape methods, lines 20-39, SHA-256 `63d9048ebed65b890c370a1da6e79733904f67d953aff9fd8c0316f97f9d4e7f`; no collision override exists. The same 0.9375-high outline remains.
- A/B base dispatch: `net/minecraft/block/Block.java`, `Block#addCollisions` and `Block#getCollisionShape`, A lines 336-354 SHA-256 `e62ece80c6a7e7121346f65f8fdfd9b148c29441a27de9f568bba9afe1d84fe6`; B lines 342-359 SHA-256 `1971dbc284d511e2ed366f77bc77fd8cd07174baad3e7732e908d3daeb640c01`. The base collision method returns `state.getShape(world, pos)`, and collision insertion moves that shape to block coordinates and intersects it with the query box.
- A/B collision dispatch: `net/minecraft/block/state/StateDefinition.java`, inner state `getCollisionShape` / `addCollisions`, A lines 357-370 SHA-256 `10ba661985c87801e1bb7e941399498e89fb67e1bd9ead2869c00d39ffff6493`; B lines 362-375 SHA-256 `00fea8cdf8a0cabf1af21e7e7ff47f071bd87a16f91697a3b31efce6c78bfda4`. Both delegate to their block implementation.
- A/B movement query: `net/minecraft/world/World.java`, `World#getCollisions`, A lines 899-981 SHA-256 `2fe063e0ec224eed9fc7d01b8f788c32035eed5fee5a9d98ea5e82296236e05a`; B lines 903-984 SHA-256 `888ed0e9de765def87b05c4126ecdf0b10e9dd448b4543dd1cb98211e9951646`. The method obtains block-state collision shapes for candidates. `Entity#move(double,double,double)` calls this query during axis clipping (A lines 441-721, SHA-256 `bcd7fa2206bf8d7271102f6da7fe2771f96dbe22ec79dab9f2101a3e29c17ef0`; B lines 446-726, SHA-256 `05da145effa19a6ef7934cc276e89226373b67c12f4ce89a8ce2183f29039f77`).

## Source-level difference

When a player collision query intersects farmland, A contributes a full one-block-high collision box. B inherits the base collision implementation, which contributes the same 15/16-high shape used for farmland’s outline. The top 1/16 of the farmland block therefore ceases to block player movement in B under the same world and player box preconditions.

## Reachability and dependencies

Player movement reaches `Entity.move` → `World.getCollisions` → `BlockState.addCollisions` → `Block.getCollisionShape`. The block-state dispatch invokes `FarmlandBlock#getCollisionShape` on A and the base `Block#getCollisionShape` on B. This finding depends on the normal registered farmland block state and collision candidate enumeration; it does not depend on farmland moisture, crops, or random ticks.

## Consequence and uncertainty

The decompiled source trees show collision boxes differing by 1/16 block vertically. The pair's currently cached mapped-jar hashes do not match the hashes in their ready artifact manifests. Treat the source comparison as provisional until the source owner repairs artifact immutability and supplies fresh verification. If verified, the change can alter clipping/support outcomes when a player's queried box overlaps that top band. Exact resulting player position, ground flags, and future movement depend on the full collision scenario and are not claimed here. The interval does not establish the first release containing the change.

## Handoff

Provisional comparison: 1.9.4 full-cube farmland collision override versus 1.10.2 inherited outline-height collision shape. Accept only after exact-pair mapped-artifact integrity is repaired and reverified. Keep applicability to player collision with farmland. Implementation and runtime testing are deferred.

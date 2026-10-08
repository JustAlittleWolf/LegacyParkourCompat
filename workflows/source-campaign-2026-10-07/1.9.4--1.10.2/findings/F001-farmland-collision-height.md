# F001: Farmland movement collision height changes

- Older version A: 1.9.4, Ornithe Feather `net.ornithemc:feather-gen2:1.9.4+build.2`
- Newer version B: 1.10.2, Ornithe Feather `net.ornithemc:feather-gen2:1.10.2+build.2`
- Mechanic / coverage slice IDs: farmland collision shape; `BLK-FARMLAND-COLLISION`
- Classification: changed behavior
- Confidence: source-confirmed
- Applicability: historical player behavior
- First changed release: unknown within (1.9.4, 1.10.2]
- Runtime validation: not performed

## Paired evidence

- A artifact: validated 1.9.4 `ornithe-feather` ready record and manifest in the run report. `net/minecraft/block/FarmlandBlock.java`, `FarmlandBlock#getCollisionShape(BlockState, World, BlockPos)`, lines 31-40, SHA-256 `9df445485ba7e1603c20b5dede847fe412558148acefe1eedc9bb3b62b14c25`; returns `FULL_BLOCK_SHAPE`. Its outline `SHAPE` is `Box(0.0, 0.0, 0.0, 1.0, 0.9375, 1.0)`.
- B artifact: validated 1.10.2 `ornithe-feather` ready record and manifest in the run report. `net/minecraft/block/FarmlandBlock.java`, class body/shape methods, lines 20-39, SHA-256 `63d9048ebed65b890c370a1da6e79733904f67d953aff9fd8c0316f97f9d4e7f`; no collision override exists. The same 0.9375-high outline remains.
- A/B base dispatch: `net/minecraft/block/Block.java`, `Block#addCollisions` and `Block#getCollisionShape`, A lines 336-354 SHA-256 `e62ece80c6a7e7121346f65f8fdfd9b148c29441a27de9f568bba9afe1d84fe6`; B lines 342-359 SHA-256 `1971dbc284d511e2ed366f77bc77fd8cd07174baad3e7732e908d3daeb640c01`. The base collision method returns `state.getShape(world, pos)`, and collision insertion moves that shape to block coordinates and intersects it with the query box.
- A/B collision dispatch: `net/minecraft/block/state/StateDefinition.java`, inner state `getCollisionShape` / `addCollisions`, A lines 357-370 SHA-256 `10ba661985c87801e1bb7e941399498e89fb67e1bd9ead2869c00d39ffff6493`; B lines 362-375 SHA-256 `00fea8cdf8a0cabf1af21e7e7ff47f071bd87a16f91697a3b31efce6c78bfda4`. Both delegate to their block implementation.
- A/B movement query: `net/minecraft/world/World.java`, `World#getCollisions`, A lines 899-981 SHA-256 `2fe063e0ec224eed9fc7d01b8f788c32035eed5fee5a9d98ea5e82296236e05a`; B lines 903-985 SHA-256 `888ed0e9de765def87b05c4126ecdf0b10e9dd448b4543dd1cb98211e9951646`. The method obtains block-state collision shapes for candidates. `Entity#move(double,double,double)` calls this query during axis clipping (A lines 441-721, SHA-256 `bcd7fa2206bf8d7271102f6da7fe2771f96dbe22ec79dab9f2101a3e29c17ef0`; B lines 446-726, SHA-256 `05da145effa19a6ef7934cc276e89226373b67c12f4ce89a8ce2183f29039f77`).
- Exact A/B artifact identities: A source-manifest SHA-256 `c7b508fe01634887b65919dcd3a900c311a21d9510a1f1ab248d5c17c528ab19`, original artifact-manifest SHA-256 `9527dca544694daa3b4a7741be1a5b4802d8b6665c8a152a408a37a27a1b4d77`; B source-manifest SHA-256 `91b0f478acb7b6f13463c35b268a30d2806f583402631a56f54ce2eb70d1ec71`, original artifact-manifest SHA-256 `6b402f3e6d6cf2f7b3647806364ff44214c47348fefd6949fad03e2011379116`.
- Revised mapped-artifact evidence: revision `feather-r1-2026-10-07`; A immutable jar `revisions/derived-artifact-snapshots/feather-r1-2026-10-07/1.9.4/ornithe-feather/client-ornithe-feather.jar`, SHA-256 `fbcf50795566e12b8eab0e733b136ed562c4009d707ef4a7a4994936491816a3`, revision.json SHA-256 `df0a26fd4c65292530cdad638e6789cc26fcd47875fe1c3eb2daef6de7e3f915`; B immutable jar `revisions/derived-artifact-snapshots/feather-r1-2026-10-07/1.10.2/ornithe-feather/client-ornithe-feather.jar`, SHA-256 `0c1d71990c9c0d7cc88debf7e67663bd64c8dc7de5872176088a3119527fb28b`, revision.json SHA-256 `79b5688af6f5cf6eb5c006a867de841de0033b42408c8461283cd9c4b9389856`. Both snapshots were independently checked against `artifact.sha256` and `revision.json`; all 1,819/1,845 source entries, original client jars, raw Feather mapping jars/Tiny files, and the full verification log SHA-256 `33b732892a03ffac60026663f7266c20b637330db6d480b4260267861bebd40c` were rehashed. Independent ops audit passed. Revision records state source trees/raw inputs identical and original derived jars unavailable; equivalence to those unavailable original derived jars is unproven.

## Source-level difference

When a player collision query intersects farmland, A contributes a full one-block-high collision box. B inherits the base collision implementation, which contributes the same 15/16-high shape used for farmland’s outline. The top 1/16 of the farmland block therefore ceases to block player movement in B under the same world and player box preconditions.

## Reachability and dependencies

Player movement reaches `Entity.move` → `World.getCollisions` → `BlockState.addCollisions` → `Block.getCollisionShape`. The block-state dispatch invokes `FarmlandBlock#getCollisionShape` on A and the base `Block#getCollisionShape` on B. This finding depends on the normal registered farmland block state and collision candidate enumeration; it does not depend on farmland moisture, crops, or random ticks.

## Consequence and uncertainty

The paired Feather source and revised immutable mapped artifacts show collision boxes differing by 1/16 block vertically. Independent operations verification passed, and the source trees and raw inputs match their original manifests. The original derived jars are unavailable, so their byte identity with the revised artifacts is unproven. When a player's queried box overlaps the top 1/16 band, the different collision shape can change clipping or support; exact player position, ground flags, and later movement depend on the full collision scenario and are not claimed. The difference is established between these paired releases; no release outside this pair is assessed.

## Handoff

Provisional comparison: 1.9.4 full-cube farmland collision override versus 1.10.2 inherited outline-height collision shape. Accept only after exact-pair mapped-artifact integrity is repaired and reverified. Keep applicability to player collision with farmland. Implementation and runtime testing are deferred.

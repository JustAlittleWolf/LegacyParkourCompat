# F020: Bed lower collision footprint changes

- Older version A: 1.13.2
- Newer version B: 1.14.4
- Mechanic / coverage slice IDs: bed collision shape selection by facing and half; S046
- Classification: changed behavior
- Confidence: candidate (the source-confirmed shape difference can change player collision results when the AABB intersects the lower footprint; exact displacement remains conditional on query position and surrounding shapes)
- Applicability: historical player behavior
- First changed release: unknown within (1.13.2,1.14.4]
- Runtime validation: not performed

## Paired evidence

- A source root: `../../../build/movement-campaign-2026-10-07/ready/1.13.2/ornithe-feather/`; `net/minecraft/block/BedBlock.java`::`getShape(BlockState,BlockView,BlockPos)`, lines 38-42 and 209-212, SHA-256 `a274ed3d3dd8e4f4895fe1f3d1444425a9248e9cd41bc6e1f2677af7dacbec18`. Its single shape is `Block.block(0.0, 0.0, 0.0, 16.0, 9.0, 16.0)`. `net/minecraft/block/Block.java`::`getCollisionShape(BlockState,BlockView,BlockPos)`, lines 393-395, delegates to `state.getShape(...)` when `hasCollision`; the `Block.Properties` default is `hasCollision = true` at lines 1901-1902, SHA-256 `735030e8bb5fc7ead2d6dbcb1b262a36a414b4349daf197456337f460b444cd4`. White bed registration uses `Material.WOOL` without `noCollision`, line 901 of the same file.
- B source root: `../../../build/movement-campaign-2026-10-07/ready/1.14.4/ornithe-feather/`; `net/minecraft/block/BedBlock.java`::`getShape(BlockState,BlockView,BlockPos,CollisionContext)`, constants lines 48-56 and method lines 180-192, SHA-256 `aab431c7d85bc654b74215d2b2be5fbe6de81643f5e2be466658d918f272abe1`. The mattress is `Block.block(0.0, 3.0, 0.0, 16.0, 9.0, 16.0)`; four 3-by-3-by-3 corner boxes are combined in facing-specific pairs, selected using `FACING` and `PART`. `net/minecraft/block/Block.java`::`getCollisionShape(BlockState,BlockView,BlockPos,CollisionContext)`, lines 376-378, delegates to `state.getSHape(...)` when `hasCollision`; the `Block.Properties` default is `hasCollision = true` at lines 811-812, SHA-256 `1f8ec3628f08e196c2793e91ca0b94feacea6bc3939e7fb770f0682a6e66f2c5`. White bed registration uses `Material.WOOL` without `noCollision`, lines 212-214 of `net/minecraft/block/Blocks.java`, SHA-256 `9c0323497b31f8a284456e5692898897b92b6f0817d0371fae9a8afb54c638f8`.
- The registered bed families instantiate this same `BedBlock`; both default collision delegates pass its shape to the world collision query. S020 records the paired player block-collision enumerator. No resource-backed data is used by this finding.

## Source-level difference

Under identical bed state and world position, A returns one solid box covering the full 16-by-16 footprint from y=0 to y=9/16. B returns a 16-by-16 mattress from y=3/16 to y=9/16 plus two 3-by-3 corner support boxes from y=0 to y=3/16. `FACING` and `PART` choose which two corners support each bed half; the adjacent head/foot half selects the other pair. The bed registrations and inherited collision-enabled default match. B's collision context is not read by this shape method.

## Reachability and dependencies

Player `Entity.move` collision resolution (S007-S009) -> world block-collision enumeration (S020) -> `BlockState.getCollisionShape` -> inherited `Block.getCollisionShape` -> `BedBlock.getShape`. Any registered bed can reach this path when the player query bounds overlap its state. The state dependency is bed `FACING` and `PART`; no server-only producer is needed to select the geometry once that block state is present. D-COLLISION-SHAPES remains open for the full provider and neighbor inventory.

## Consequence and uncertainty

Source proves that the lower 3/16 of the collision geometry has a continuous footprint in A and only paired corner supports in B. A player AABB intersecting the parts removed from that footprint can therefore receive a different block-shape obstacle set and potentially a different clipped displacement. The exact resulting movement depends on player position, query bounds, nearby shapes and movement branch; no trajectory was simulated. The pair establishes a difference between 1.13.2 and 1.14.4, not the first release that introduced it.

## Handoff

Independent bed-collision delta, applicable to historical direct player movement. Related evidence: S007-S009 player movement and S020 world block-collision enumeration. Independent finding review and full-pair collision-provider closure remain open. No implementation or wiki information was used. First changed release remains unknown within (1.13.2,1.14.4].

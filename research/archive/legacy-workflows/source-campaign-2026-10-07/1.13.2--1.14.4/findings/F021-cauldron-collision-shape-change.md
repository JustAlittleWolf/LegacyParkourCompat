# F021: Cauldron lower collision geometry changes

- Older version A: 1.13.2
- Newer version B: 1.14.4
- Mechanic / coverage slice IDs: cauldron fixed collision-shape construction; S047
- Classification: changed behavior
- Confidence: candidate (the source-confirmed shape difference can change player collision results when the AABB intersects the lower footprint; exact displacement remains conditional on query position and surrounding shapes)
- Applicability: historical player behavior
- First changed release: unknown within (1.13.2,1.14.4]
- Runtime validation: not performed

## Paired evidence

- A source root: `../../../build/movement-campaign-2026-10-07/ready/1.13.2/ornithe-feather/`; `net/minecraft/block/CauldronBlock.java` shape constants and `getShape(BlockState,BlockView,BlockPos)`, lines 32-45, SHA-256 `6948b2e208e1d7a52123fece400aa717b6fa5c35189fff1137490a96c170a3ac`. A subtracts only `INSIDE_SHAPE = Block.block(2.0, 4.0, 2.0, 14.0, 16.0, 14.0)` from `VoxelShapes.block()` with `BooleanOp.ONLY_FIRST`. `net/minecraft/block/shape/BooleanOp.java` defines `ONLY_FIRST = (a, b) -> a && !b`, line 8, SHA-256 `7c8739dc92118e1e4d9cc563c6a9b07780f3c7da4f80f3fd0ccda65dd3e5c470`. `net/minecraft/block/Block.java` defaults `hasCollision` to true and registers `"cauldron"` with no `noCollision`, lines 1216 and 1901-1902, SHA-256 `735030e8bb5fc7ead2d6dbcb1b262a36a414b4349daf197456337f460b444cd4`.
- B source root: `../../../build/movement-campaign-2026-10-07/ready/1.14.4/ornithe-feather/`; `net/minecraft/block/CauldronBlock.java` shape construction and `getShape(BlockState,BlockView,BlockPos,CollisionContext)`, lines 33-52, SHA-256 `686519b540dcb313c5226ebae2f77e902eb1d5b76997ba8fdf8cd37ab772179e`. B subtracts the same interior box plus three boxes spanning y=0 to3/16: two cross strips and one central square. `net/minecraft/block/shape/BooleanOp.java` has the same `ONLY_FIRST` expression and hash as A. `net/minecraft/block/Blocks.java` registers `"cauldron"` without `noCollision`, line 756, SHA-256 `9c0323497b31f8a284456e5692898897b92b6f0817d0371fae9a8afb54c638f8`; `net/minecraft/block/Block.java` defaults `hasCollision` to true at lines 811-812, SHA-256 `1f8ec3628f08e196c2793e91ca0b94feacea6bc3939e7fb770f0682a6e66f2c5`.
- The shape is constant across cauldron `LEVEL` states in both versions. The base collision method delegates to the state shape when collision is enabled. S020 records the paired player block-collision enumerator; no resource-backed data is used by this finding.

## Source-level difference

Both versions compute the collision shape as the full-block shape minus a second shape, using the same `ONLY_FIRST` Boolean operation and the same upper interior box from y=4/16 to16/16. A's second shape contains only that interior box, leaving a solid full-footprint floor from y=0 to4/16. B adds three lower boxes spanning y=0 to3/16: a full-width strip across the middle in each horizontal axis and a central square. Their union removes the lower interior and side-cross regions, leaving four corner L-shaped supports through y=3/16; the 3/16-to4/16 layer remains a continuous plate. The registrations and collision-enabled defaults match.

## Reachability and dependencies

Player `Entity.move` collision resolution (S007-S009) -> world block-collision enumeration (S020) -> `BlockState.getCollisionShape` -> inherited `Block.getCollisionShape` -> `CauldronBlock.getShape`. The shape is independent of `LEVEL`; once a cauldron block is in the queried bounds, it reaches this provider. D-COLLISION-SHAPES remains open for the full provider and neighbor inventory.

## Consequence and uncertainty

Source proves that the lower 3/16 footprint differs: A has a continuous solid base, while B removes the center and cross areas and retains corner supports. A player AABB intersecting those removed regions can receive a different collision shape and potentially a different clipped displacement. The exact result depends on player position, query bounds, nearby shapes and movement branch; no trajectory was simulated. The pair establishes a difference between 1.13.2 and 1.14.4, not the first release that introduced it.

## Handoff

Independent cauldron collision delta, applicable to historical direct player movement. Related evidence: S007-S009 player movement and S020 world block-collision enumeration. Independent finding review and full-pair collision-provider closure remain open. No implementation or wiki information was used. First changed release remains unknown within (1.13.2,1.14.4].

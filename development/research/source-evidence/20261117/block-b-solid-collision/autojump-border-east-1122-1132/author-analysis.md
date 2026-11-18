# 1.12.2–1.13.2 AutoJump world-border applicability

## Exact source identity

Read-only source pair: Minecraft `1.12.2` / `1.13.2`, Ornithe Feather (Feather Gen2 build.2 on each side), Vineflower 1.12.0, Java 25.0.3+9-LTS. The pair's ready markers point to prepared campaign output roots; the present source tree is the reused publication. Ready marker's source-manifest hashes match the actual manifest bytes:

- 1.12.2: source-manifest SHA-256 `b8a37ccfccd2aac5f40f5e34fec85873dbdfa93a266043c103e592e4a8c949da`; client SHA-256 `8ada07da5ee77dad3527bd7278fbd05ee1fc8a597813b216a871a2d7d64cc64f`; mapping artifacts `feather-gen2-1.12.2+build.2-mergedv2.jar` and `.tiny`.
- 1.13.2: source-manifest SHA-256 `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211`; client SHA-256 `3410887ba652f25792c7675bfaf9140e73b60e93cfbf113a803f8a98cb05c0f9`; mapping artifacts `feather-gen2-1.13.2+build.2-mergedv2.jar` and `.tiny`.

Relevant source-file SHA-256 values match their exact source-manifest entries:

- 1.12.2 `LocalClientPlayerEntity.java`: `01a58e94d8c6ff98a8e3794227cdc76a5fcbdbad795c70c9cf28854aff9823cc`
- 1.12.2 `World.java`: `e9fa9b8d6d31ad57a5b876f5f63a5e3c554a23437decf1845a493daf48233594`
- 1.12.2 `WorldBorder.java`: `58904ff4e4905f2c3fff8b2a7f40dd0e0689110497ec49e39eb8c9b1ebdd2257`
- 1.13.2 `LocalClientPlayerEntity.java`: `2583495f3a02b4791aa036e6a8d354d7596c4984761969a0f29b29d8d9bf42bf`
- 1.13.2 `World.java`: `0023de30da608db73c6c6188334d2bb19039d86fe4d6f5e4ad79d732a5d2cd1a`
- 1.13.2 `WorldView.java`: `9577360b1a1bdde9280f0b72593e5e6dde45d9a54719cd7ff933f3ecd31b971f`
- 1.13.2 `EntityView.java`: `2b5b59eef6efa93bb51c1d838c7a1cf64725be96c55c47f76894fde11e13d6de`
- 1.13.2 `WorldBorder.java`: `7593d7a8df153f2a039a5b38cca684e1e15529b80ffde743151419e125590718`

## Applicability result

A synthetic border collision can be the first AutoJump ray qualifier in either release. It is reachable with an ordinary local player and a stationary rectangular border; it is not excluded merely because the player center is inside the border. This establishes applicability of the border provider to AutoJump's probe. It also establishes a timer difference for this exact query: 1.12.2 emits the synthetic stone as a per-cell box (ray-hit cell maxY=65), while 1.13.2 merges every eligible synthetic border cell in the finite query scan into an IntVoxelShape whose vertically merged box reaches the scan ceiling maxY=67. With feet at y=64 and no jump boost, those yield u=1 (timer set) versus u=3 (timer not set).

Bounded source witness (coordinates in blocks): stationary border centered at `(0,0)`, size `20`, hence `maxX=10`; loaded chunk containing `(10,64,0)`; air at `(9,65,0)` / `(9,66,0)` and no other collision provider intersecting the two AutoJump rays; ordinary ground beneath the player's feet. At AutoJump entry, player center is `(9.7,64,0)`, width `.6`, height `1.8`, `onGround=true`, non-sneaking, non-riding, AutoJump enabled, forward input `(x=0,y=1)`, yaw `-90°`, no jump boost, and `ticksToNextAutojump=0`. Pre-move and post-move horizontal positions can both be `x=9.7` (the attempted movement is clipped at the border contact); thus `dx=dz=0`. The callback still runs after `super.move` and the nonzero input fallback points east. With ordinary movement speed `.1`, AutoJump's `p=max(f*7,1/l)` is about `.7`; its ray runs from `x=9.7` to `x=10.4`, at `y=64.51`, with lateral offsets `z=±.3`. The `z=+.3` ray intersects the synthetic full block for `(10,64,0)`. In 1.12.2 that yields `s=65`, `u=s-minY=1.0`, within `(.5,1.2]`, so the timer changes `0 -> 1`. In 1.13.2 the synthetic cell is accumulated in the border `VoxelSet` over y scan cells 63 through 66, and `VoxelShape.getBoundingBoxes()` merges the contiguous run to maxY=67; AutoJump therefore gets `u=3`, over 1.2, and leaves timer `0`. Tick countdown may first convert `1 -> 0` earlier in that tick (`LocalClientPlayerEntity` 1.12.2 line 708; 1.13.2 line 712).

The gates for that witness are exact and nonvacuous:

- Entity's `outsideWorldBorder` flag is false. Both old `World.isWithinBorder` and new `WorldView.collidesWithBorder` use the border expanded by one block for a not-outside entity. At `x=9.7,z=0`, the predicate is true, so the collision query enables border treatment; the flag is not flipped because false does not equal true. If the flag were true here, the inset-by-one predicate would be false and the fake border provider would be disabled.
- The query AABB is wider than the ray: AutoJump grows the endpoint box by `q=width=.6`; around the above path it spans x `[9.1,11.0]`, z `[-.6,.6]`, and y `[64,65.8]`; its 1.13 scan bounds are x `[8,12)`, z `[-2,2)`, y `[63,67)`). In 1.13.2 the scan's strict whole-scan-inside test (worldBorder.minX < i && j < worldBorder.maxX && ...) fails at the east side (i=8, j=12, worldBorder.maxX=10), enabling synthetic border blocks. The synthetic cell (10,64,0) and its y-neighbors 63–66 are interior to the scan in x/z; the corner exclusion does not remove these rows because x/z are interior. They must be in loaded chunks. The contiguous border voxels merge through the scan ceiling.

For this witness, 1.13's finite scan is x `[8,12)`, z `[-2,2)`, y `[63,67)`. The border `VoxelSet` includes full cells at x=10,z=0 for all scanned y=63..66. `VoxelShape.getBoundingBoxes()` merges the contiguous vertical run to maxY=67, so AutoJump sees the qualifying border box with s=67 and u=3; the no-jump cap is 1.2. The older 1.12 list instead provides the individual (10,64,0) stone box with maxY=65, u=1, and sets the timer. This is the actual timer delta in the border-only witness; it is not parity.
- `WorldBorder.contains(BlockPos)` is overlap based: `pos.x+1 > minX && pos.x < maxX` (also z). For this exact max edge, block x=10 is outside (`10 < 10` is false), so the 1.12.2 world substitutes stone at that position and 1.13.2 accumulates a full block in its border voxel set.
- Ground is below the raised ray (`y=64.51`), so it does not compete. The witness excludes other ray-intersecting blocks and entities, making the border the first and sole qualifier.

## Provider order and exclusions

1.12.2 `World.getCollisions(entity, Box)` appends block collision boxes during the x/z/y scan, then appends entity collision shapes (`getEntities(..., shape.expand(.25))`, same-vehicle exclusion, entity shape followed by entity-specific against-shape). Synthetic border stone is inserted at its block's scan position among ordinary blocks.

In 1.13.2, `WorldView.getBlockCollisions` emits actual block shapes from the finite scan and accumulates out-of-border full cells into a `VoxelSet`; its `IntVoxelShape` is concatenated after those block shapes. `World.getCollisions` then concatenates entity collisions last (`EntityView.getEntityCollisions`). AutoJump flattens every shape's bounding boxes before ray testing. Therefore competing qualifying providers can change which box is first: 1.12.2 border boxes follow ordinary scan position; 1.13.2 synthetic border geometry follows all ordinary block shapes and precedes entity shapes. This order issue is separate from the border-only aggregation timer delta above.

The LocalClientPlayerEntity `move` wrapper is the same relevant sequence in both releases: capture old x/z, call `super.move`, then call `autoJump((float)(newX-oldX),(float)(newZ-oldZ))` (1.12.2 lines 846–850; 1.13.2 lines 871–875). The potentially changed base `Entity.move` collision resolution is not evidence for or against the AutoJump provider-order/applicability claim; the witness states its pre/post positions at the AutoJump call boundary and does not attribute the world-border shape construction to Entity.move.

## Exact source locations

- 1.12.2 `LocalClientPlayerEntity.java`: movement wrapper and AutoJump, lines 846–953; countdown line 708.
- 1.13.2 `LocalClientPlayerEntity.java`: movement wrapper and AutoJump, lines 871–972; countdown line 712.
- 1.12.2 `World.java`: collision collection and entity append, lines 964–1037; `isWithinBorder`, 1042–1057.
- 1.12.2 `WorldBorder.java`: block-position containment, lines 24–26.
- 1.13.2 `WorldView.java`: finite block scan / synthetic border voxel set, lines 112–152; collision flag and border gate, lines 170–198. `VoxelSet.java` lines 200–260 and `VoxelShape.java` lines 94–112 show contiguous cells merged into bounding boxes, including the finite scan ceiling.
- 1.13.2 `World.java`: block stream then entity stream, lines 1755–1758.
- 1.13.2 `EntityView.java`: entity collision filtering/shape order, lines 21–39.
- 1.13.2 `WorldBorder.java`: block-position containment, lines 22–24.







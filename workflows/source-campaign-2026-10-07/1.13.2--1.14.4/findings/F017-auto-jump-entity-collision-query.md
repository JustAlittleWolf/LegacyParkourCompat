# F017: Auto-jump entity collision query bounds

- Older version A: 1.13.2
- Newer version B: 1.14.4
- Mechanic / coverage slice IDs: local player post-move auto-jump obstacle scan; S019
- Classification: changed behavior
- Confidence: candidate (query algorithm difference source-confirmed; exact world/entity collision outcomes remain conditional; source dependencies/reviewer open)
- Applicability: historical local-player movement path; first changed release unknown within (1.13.2,1.14.4]
- Runtime validation: not performed

## Paired evidence

- A manifest: `../../../build/movement-campaign-2026-10-07/ready/1.13.2/ornithe-feather/`; `LocalClientPlayerEntity.java`::`move(MoverType,double,double,double)`, lines 871-875, and `autoJump(float,float)`, lines 882-980; SHA-256 `2583495f3a02b4791aa036e6a8d354d7596c4984761969a0f29b29d8d9bf42bf`. `WorldView.java`::`getCollisions(Entity,Box)`, lines 166-167, SHA-256 `9577360b1a1bdde9280f0b72593e5e6dde45d9a54719cd7ff933f3ecd31b971f`; `World.java`::`getCollisions(Entity,VoxelShape,VoxelShape,Set)`, lines 1755-1757, SHA-256 `0023de30da608db73c6c6188334d2bb19039d86fe4d6f5e4ad79d732a5d2cd1a`; `EntityView.java`::`getEntityCollisions(Entity,VoxelShape,Set)`, lines 21-34, SHA-256 `2b5b59eef6efa93bb51c1d838c7a1cf64725be96c55c47f76894fde11e13d6de`.
- A derived-artifact revision: `feather-r1-2026-10-07`; immutable A mapped client SHA-256 `b28c33e023928045c8fd7ed7727a860a6820241e371ddb16108366c6192b718c`. Original A derived-JAR equivalence remains unproven, as recorded in the run manifest.
- B manifest: `../../../build/movement-campaign-2026-10-07/ready/1.14.4/ornithe-feather/`; `LocalClientPlayerEntity.java`::`move(MoverType,Vec3d)`, lines 846-852, and `autoJump(float,float)`, lines 857-959; SHA-256 `708af6a3880fb58b67bf4604a5509b351719a9c2a0c06a8ec261586435bf00ce`. `WorldView.java`::`getCollisions(Entity,Box,Set)` lines 129-130 and `getBlockCollisions(Entity,Box)` lines 133-187, SHA-256 `fcdf8dc98f97781f4639c4b8cda8a238edf834f3963e401e6eeef6cb475c4909`; `WorldAccess.java`::`getEntityCollisions(Entity,Box,Set)`, lines 75-76, SHA-256 `f57639756acdedbd0e5a598f7740a8e17f0dc3b2ba710aa4690d05f4f3bcd746`; `EntityView.java`::`getEntityCollisions(Entity,Box,Set)`, lines 53-64, SHA-256 `3822844ced53882c52f0523a1bd714baa3cd9bb9dab38a5e2c53051c7b1590e3`.
- The B block-collision query builds `CollisionContext.of(entity)` in `WorldView.getBlockCollisions`. The only B block source found that reads collision-context values in its collision-shape path is `ScaffoldingBlock.java`::`getCollisionShape`, lines 118-123, SHA-256 `4b22b178d03266296d715c1b7208f347b879ae24ba55da01a2c1e4983168e64c`; that class is absent from A and is excluded as a modern-only block under project scope.

## Source-level difference

The paired local-player `move` overrides call `autoJump` after `super.move`, using the resulting horizontal displacement. Both auto-jump methods share the same enabled/timer/on-ground/sneak/riding/input gates and the same corridor, obstacle-height, and delayed-jump checks. A calls `world.getCollisions(this, box)`; B calls `world.getCollisions(this, box, Collections.emptySet())`. The empty excluded-entity set matches A's default overload. Both query block and entity collision shapes.

The entity-shape enumeration differs. A returns early only for an empty shape, asks for entities in `shape.bounds().expand(0.25)`, then retains collision shapes that intersect the original bounds. B returns early when the average side length is below `1.0E-7`, asks for entities in `shape.expand(1.0E-7)`, and retains shapes that intersect that epsilon-expanded box. These predicates are not identical: the returned obstacle set can differ at the epsilon boundary or when an entity collision shape extends beyond its entity-query bounds. B also passes an entity collision context to block shapes; the only context-reading collision provider identified here is modern-only scaffolding and is out of scope.

## Reachability and dependencies

`LocalClientPlayerEntity.move` -> superclass collision resolution -> `autoJump(actualDx,actualDz)` -> block/entity obstacle stream -> `ticksToNextAutojump = 1` when the scan finds a rise in the configured height interval -> next local `mobTick` synthesizes jump input. Reachability requires auto-jump enabled, timer expired, player grounded, not sneaking or riding, and nonzero movement input. `D-TICK-CLOSURE` remains open for surrounding call-order branches; `D-COLLISION-SHAPES` remains open for the full block-query/provider and entity-obstacle inventory. No non-player movement or vehicle physics is analyzed.

## Consequence and uncertainty

Source proves distinct entity-obstacle query bounds and intersection predicates in a reachable player auto-jump scan. Whether a common-era world/entity shape occupies the differing boundary, and whether that changes the delayed jump decision, remains conditional and unmeasured. The collision-context effect on scaffolding is excluded because the block did not exist in A. Runtime validation is deferred.

## Handoff

Independent source candidate. Dependencies and blind finding review remain open. No implementation or wiki feedback was used. First changed release unknown within (1.13.2,1.14.4].
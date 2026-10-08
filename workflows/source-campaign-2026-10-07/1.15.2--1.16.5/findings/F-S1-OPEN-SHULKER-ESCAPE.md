# F-S1-OPEN-SHULKER-ESCAPE: open shulker boxes stop triggering player escape

- Older version A: 1.15.2, Mojmap
- Newer version B: 1.16.5, Mojmap
- Mechanic / coverage slice IDs: local-player suffocation escape; S1-ESCAPE
- Classification: changed behavior
- Confidence: source-confirmed
- Applicability: historical player behavior
- First changed release: unknown within (1.15.2, 1.16.5]
- Runtime validation: not performed

## Paired evidence

- A manifest artifact reference: `../../../build/movement-campaign-2026-10-07/ready/1.15.2/mojmap`; `net/minecraft/client/player/LocalPlayer.java`; `LocalPlayer#checkInBlock/blocked`, lines 386-440, SHA-256 `3a9019bd7b860e251c23fd8d0cd70b7f5b38566d34470c4e29b1014ef689ccbd`. It checks each integer y cell in a sampled x/z column using `Player#freeAt`, line 1489, SHA-256 `1ba2724c22163862b8f7fdfdea5a04a66e4db26a119d7e5360ba024724a34793`; `freeAt` returns the negation of `BlockState.isSuffocating`.
- A block predicate: `net/minecraft/world/level/block/ShulkerBoxBlock.java`; `ShulkerBoxBlock#isSuffocating`, lines 62-64, SHA-256 `de5e19cffb75499b509bc361fcfb3dbe0b0fa7ab490ac42adedf89466721b02d`; it unconditionally returns `true`.
- B manifest artifact reference: `../../../build/movement-campaign-2026-10-07/ready/1.16.5/mojmap`; `net/minecraft/client/player/LocalPlayer.java`; `LocalPlayer#moveTowardsClosestSpace/suffocatesAt`, lines 414-447, SHA-256 `6011569e766bb1568609147be9aa14e9c08c51948e3d3a60fd066e848f6a8c2b`. `suffocatesAt` asks `noBlockCollision` for an intersecting collision shape filtered by `BlockState.isSuffocating`.
- B shulker predicate: `net/minecraft/world/level/block/Blocks.java`; `Blocks#shulkerBox`, lines 3230-3236, SHA-256 `3b39d5cc4cd22f146ed3195aa30cbb9fdfca49f63783fabf9924a6ce7795fa12`; the predicate is false when the shulker block entity exists and is open. `net/minecraft/world/level/block/entity/ShulkerBoxBlockEntity.java`; `isClosed()`, line 290, SHA-256 `4aab41f71aacf9e1138a225e1253e202a303276ff21c7b4974b11a76d694c322`.
- Query semantics: B `net/minecraft/world/level/CollisionGetter.java`, `noBlockCollision/getBlockCollisions`, lines 62-67, SHA-256 `b507d6be11e5985a62cfeb249a99dcb5f8edaf346f12cb2487797d9e01763eac`; `CollisionSpliterator#collisionCheck`, lines 61-83, SHA-256 `19ab959c469b8edb5c371ff737a59a61b8a80bf397eff1db280064342094d297`, first applies the suffocation predicate and then requires the block collision shape to intersect the queried AABB. A `net/minecraft/world/level/CollisionGetter.java` block-collision traversal, lines 67-118, SHA-256 `63daec0623c816d53f5dbdde407a27222fa21e62680c1096b63cf9082b1c25fd`, does not use a suffocation predicate; A's `freeAt` uses the block's suffocation result directly.

## Source-level difference

For an open shulker box with its block entity present, A's `ShulkerBoxBlock.isSuffocating` still returns true. When a sampled player column includes that block, `blocked` therefore treats the column as obstructed, regardless of the shulker's open state or the player's overlap with its current shape. B's shulker registration defines suffocation as `blockEntity.isClosed()` (or true when the block entity is absent). An open shulker is filtered out before B checks collision-shape intersection, so that same sample does not count as suffocating.

## Reachability and dependencies

Shulker boxes exist in both endpoints. Opening one gives the common block a present block entity whose animation status is not CLOSED. LocalPlayer calls the A check from four horizontal samples during `aiStep`; B makes the corresponding suffocation-space query. When the open shulker is the obstructing sample and adjacent queried columns are clear, A may write a horizontal escape velocity of magnitude 0.1 while B does not take that escape branch. The conclusion depends only on source predicates, LocalPlayer call sites and block-state reachability; the comparison does not simulate damage or non-player movement.

## Consequence and uncertainty

This is a source-level difference in the local player's collision-escape impulse for open shulker boxes. It does not claim a particular final displacement after later movement processing. Exact first introduction within the endpoint interval and runtime behavior have not been checked.

## Handoff

Independent source delta: A treats an open shulker box as suffocating for player escape; B excludes it through the shulker block entity's `isClosed()` predicate. Related finding IDs: none. Boundary within the endpoint interval remains unknown.

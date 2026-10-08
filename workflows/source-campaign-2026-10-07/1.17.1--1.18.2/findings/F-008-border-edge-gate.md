# F-008: Edge gate treats nearby world border as support in B

- Older version A: 1.17.1
- Newer version B: 1.18.2
- Mechanic / coverage slice IDs: T-EDGE-GATE,T-ENTITY-COLLISION
- Classification: changed behavior
- Confidence: source-confirmed
- Applicability: historical player behavior
- First changed release: unknown within (1.17.1, 1.18.2]
- Runtime validation: not performed

## Paired evidence

A `Player#maybeBackOffFromEdge` calls `Level#noCollision` on candidate AABBs moved horizontally by the requested delta and down by `maxUpStep` (Player.java lines 1017-1067; SHA-256 `724bb298499dabe489dffd5ce7ec81c9773619a8d2c70044911eae4c9e8ff481`). `CollisionSpliterator#worldBorderCheck` gates border admission on `isBoxFullyWithinWorldBorder(worldBorder, source.getBoundingBox())` (lines 103-135; SHA-256 `c4f8158bd6778159946ebf16c22a6c12f5a4bccb40e2f1215f4fc930bb218bd0`): when the current player box is inside the floored/ceiled bounds, the border shape is omitted even if the queried candidate box crosses it.

B `Player#maybeBackOffFromEdge` keeps the same candidate offsets and 0.05 reductions (Player.java lines 1032-1080; SHA-256 `bf639c1962ff90d69e4569b2b18f6fcf57ac46ef80b19686f0fbc1687fca744a`). `CollisionGetter#noCollision` checks blocks and entities, then calls `borderCollision(entity, queriedAABB)` (lines 46-81; SHA-256 `ed62e6b800ec057c1269d3cc4e2fb7c0cc2037370223fac99f41371e000c6b5c`). `WorldBorder#isInsideCloseToBorder` uses the queried box size, entity-to-border distance, and an expanded center-bounds check (lines 76-79; SHA-256 `ade6bcb18ae0b7a2c6e9978295288bfac6fbb759a0f8a2dfb9d3983788d332fb`).

## Source-level difference

With no block or entity collision under an outward candidate, A can report `noCollision=true` because its border check sees only the current in-bounds player box. B can report `noCollision=false` because the candidate AABB intersects the border shape and passes the proximity guard. The edge loop therefore returns different pre-solver horizontal components.

## Reachability and dependencies

The player guard is reachable while grounded, crouching (`Player#isStayingOnGroundSurface` returns `isShiftKeyDown` in both versions, Player.java lines 303-305), not flying, and moving with `MoverType.SELF` or `PLAYER`. A near-border platform with no floor beneath the outward candidate satisfies the source path. Border shape construction is paired (run.md dependency evidence); D-BORDER-MOVE-PATH remains open for the other movement and query paths.

## Consequence and uncertainty

The pre-solver vector from `maybeBackOffFromEdge` differs in the stated setup. The later collision solver can clip that vector against the border; exact final coordinates and tick outcomes are not claimed while D-SHAPE-PROVIDERS and D-BORDER-MOVE-PATH remain open.

## Handoff

Source discovery only; implementation deferred.

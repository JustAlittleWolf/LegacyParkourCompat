# F-10: Player edge support probe geometry changes in 1.21.5

- Older version A: 1.21.4
- Newer version B: 1.21.5
- Mechanic / coverage slice IDs: S4-EDGE, S4-GROUND-SUPPORT
- Classification: changed behavior
- Confidence: source-confirmed
- Applicability: historical player behavior
- First changed release: unknown within (1.21.4, 1.21.5]
- Runtime validation: not performed

## Paired evidence

- A: `ready/1.21.4/mojmap.sources.sha256`; `net/minecraft/world/entity/player/Player.java`; `Player#maybeBackOffFromEdge()` lines 1069-1117 invokes `canFallAtLeast` in per-axis and combined 0.05-step loops. `Player#isAboveGround()`/`canFallAtLeast()` lines 1120-1126 build the support probe AABB with unshrunk horizontal extents and lower Y margin `1.0E-5F` (SHA-256 `c45f41b9784ce50a88a498e84a13edcef0a23e175233d726ed7f90940f19ebc1`).
- B: `ready/1.21.5/mojmap.sources.sha256`; `Player#maybeBackOffFromEdge()` lines 1026-1074 retains the same guards and decrement loops. `Player#isAboveGround()`/`canFallAtLeast()` lines 1077-1085 build the probe with `1.0E-7` inset on each horizontal side and lower Y margin `1.0E-7`; the distance parameter is now `double` (SHA-256 `8fc187f33999db9dfc49251e95e92a17645a50adab16ca0f93f4948209f62036`).

## Source-level difference

When the player is not flying, has non-upward movement of type `SELF` or `PLAYER`, is staying on a ground surface and passes `isAboveGround`, `maybeBackOffFromEdge` repeatedly probes collision support while reducing horizontal movement in `0.05` increments. B's `canFallAtLeast` tests a horizontally inset AABB and a shallower downward extent than A. The probe therefore asks a different `Level.noCollision` question near horizontal boundaries and near the lower support margin. Its distance argument widens from `float` to `double`; existing callers still form the `isAboveGround` difference from float operands, while max-step height is also float.

## Reachability and dependencies

`Entity.move` invokes virtual `maybeBackOffFromEdge` before collision resolution. `Player` supplies this override, so the player route reaches the changed query for `SELF`/`PLAYER` moves satisfying the method guards. Each query calls `Level.noCollision` using the player's current bounding box and world collision shapes; those shape providers and no-collision query dependencies remain open in S4-COLLISION-QUERY/S4-SHAPES/S5-BLOCK-SHAPES.

## Consequence and uncertainty

The source proves that A and B test different support volumes before deciding whether to retain or subtract horizontal movement. It can change the loop outcome for world/player positions where collision intersects the changed boundary regions. The exact world-space cases and resulting displacement were not runtime-tested.

## Handoff

Independent delta: player edge-backoff support query bounds and vertical margin. Related finding IDs: none. Applicability is gated by the `maybeBackOffFromEdge` conditions and a support collision near the changed AABB margins. Exact first release within the pair is unknown.

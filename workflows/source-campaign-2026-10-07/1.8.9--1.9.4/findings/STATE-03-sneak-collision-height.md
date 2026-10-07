# STATE-03: sneaking can resize the player collision box

- Older version A: 1.8.9
- Newer version B: 1.9.4
- Mechanic / coverage slice IDs: player pose/dimensions; `STATE-03`
- Classification: changed behavior
- Confidence: source-confirmed
- Applicability: player is sneaking, not sleeping/fall-flying, and the 1.65-high candidate box has no collisions at pose-update time
- First changed release: unknown within (1.8.9, 1.9.4]
- Runtime validation: not performed

## Paired evidence

- A: source manifest A; `build/movement-campaign-2026-10-07/ready/1.8.9/ornithe-feather/net/minecraft/entity/living/player/PlayerEntity.java`; `PlayerEntity#getEyeHeight()F`, lines 1640-1650; sleep/wake size writes lines 1108 and 1163; SHA-256 `e66cb294fc93118148a444bbafdf4dd57cbf66a23d69b1e8892cefccc690ab88`. The source-wide `PlayerEntity.java` inventory contains no sneaking-conditioned size update.
- B: source manifest B; `build/movement-campaign-2026-10-07/ready/1.9.4/ornithe-feather/net/minecraft/entity/living/player/PlayerEntity.java`; end-of-`tick()V` pose call line 245; `PlayerEntity#updatePlayerPose()V`, lines 285-308; `PlayerEntity#getEyeHeight()F`, lines 1740-1750; SHA-256 `d658a0d95452d12bb7e347bfd802240eeecaf7f938e10dcd43e2640434387f85`.

## Source-level difference

A retains the player's standing width/height (`0.6F`/`1.8F`) when sneaking; its eye height is independently reduced by `0.08F`. B calls `updatePlayerPose()` at the end of each player tick. When sneaking and not in a higher-priority pose branch, it proposes `0.6F`/`1.65F`, constructs a candidate box from the current box's minimum coordinates, and applies `setSize` only if `world.getCollisions(box)` is empty. B also uses the same `0.08F` eye-height reduction for sneaking. This is a collision-box height change; the eye-height result for ordinary sneaking is not a separate delta.

## Reachability and dependencies

Sneak state is read by the tick-end player pose writer. The collision query decides whether the box changes; a successful change updates dimensions used by later movement and collision queries. The support-edge probe in `Entity.move()` is a separate predicate and remains in `COLL-01`; neither result substitutes for the other. Fall-flight and sleeping dimensions are excluded as separate pose cases or non-movement boundaries.

## Consequence and uncertainty

The paired source proves that a successful B pose fit can reduce player collision-box height by `0.15F` while sneaking, while A retains its standing dimensions. It does not prove that a particular future displacement becomes possible. Exact first changed release is unknown inside the endpoint interval.

## Handoff

Independent delta: B adds a tick-end collision-fit resize to a `1.65F` tall player box when sneaking. Runtime validation is deferred.

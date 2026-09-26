# S2-01: Crouch edge restraint applies in a different support window

- Older version A: 1.15.2
- Newer version B: 1.16.5
- Mechanic / coverage slice IDs: Stage 2, player-specific movement gates; `S2-EDGE-RESTRAINT`.
- Classification: changed behavior
- Confidence: source-confirmed
- Applicability: historical player behavior
- First changed release: unknown within (1.15.2, 1.16.5]
- Runtime validation: not performed

## Paired evidence

- A: `Player.maybeBackOffFromEdge(Vec3, MoverType)` lines 1002–1035; guard requires SELF/PLAYER movement, `onGround`, and `isStayingOnGroundSurface()`. `Player.java` SHA-256 `1BA2724C22163862B8F7FDFDEA5A04A66E4DB26A119D7E5360BA024724A34793`.
- B: same method lines 1010–1043; guard excludes flying and requires `isStayingOnGroundSurface()` plus `isAboveGround()`. The latter at lines 1062–1064 admits `onGround` or a fall-distance/support probe. `Player.java` SHA-256 `D2E26589BDB6A20DC914266DB06AA48F50811EFC792D6E63B3008C9199914960`.

## Source-level difference

The shared X-then-Z-then-both backoff loops reduce components in 0.05 steps. A enters them only when on ground. B enters when its `isAboveGround` predicate succeeds, and skips them for ability flight.

## Reachability and dependencies

`Entity.move` calls the player override for SELF/PLAYER movement before collision resolution. With shift held, a player falling less than `maxUpStep` while a downward support probe remains collision-free can have horizontal movement backed off in B while A skips the loop because `onGround` is false. A flying player on ground is restrained in A but excluded in B.

## Consequence and uncertainty

The code changes the horizontal displacement submitted to collision resolution under those states. The direction and extent depend on bounding-box support collisions and the input vector; no trajectory was run.

## Handoff

Independent delta: eligibility window and flight gate for player edge restraint. Introduction release is unknown within the compared interval.

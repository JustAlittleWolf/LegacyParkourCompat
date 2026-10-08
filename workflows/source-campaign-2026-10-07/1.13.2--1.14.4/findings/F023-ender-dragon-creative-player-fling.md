# F023: Ender Dragon wing fling excludes creative players in B

- Older version A: 1.13.2
- Newer version B: 1.14.4
- Mechanic / coverage slice IDs: direct player velocity response to Ender Dragon wing overlap; S069
- Classification: changed behavior candidate
- Confidence: candidate (paired query/filter/writer sources and hashes verified; entity-list outcome and independent review open)
- Applicability: a non-spectator creative player intersecting the server-side dragon wing query bounds while the dragon is not in its damaged-timer window
- First changed release: unknown within (1.13.2, 1.14.4]
- Runtime validation: not performed

## Paired evidence

- A source root: `../../../build/movement-campaign-2026-10-07/ready/1.13.2/ornithe-feather/`; `EnderDragonEntity.java` wing query caller, lines 304-305, and `flingMobs`, lines 402-414, SHA-256 `0c0d8aa04f5b8c2264ff889de517b67d5f084ed6e17515cf696dc4c450e4930e`; `EntityView.java` two-argument `getEntities` default, lines 17-18, SHA-256 `2b5b59eef6efa93bb51c1d838c7a1cf64725be96c55c47f76894fde11e13d6de`; `EntityFilter.java` `NOT_SPECTATOR` and `NOT_SPECTATOR_NOT_CREATIVE`, lines 19-21, SHA-256 `c413c0abc606cd0e6c8951de3906881e5a5a858a9350638faefb68d2ebe5302a`.
- A query application: `World.java` passes the received predicate into each chunk entity query, lines 1761-1776, SHA-256 `0023de30da608db73c6c6188334d2bb19039d86fe4d6f5e4ad79d732a5d2cd1a`.
- B source root: `../../../build/movement-campaign-2026-10-07/ready/1.14.4/ornithe-feather/`; `EnderDragonEntity.java` wing query caller, lines 293-300, and `flingMobs`, lines 397-409, SHA-256 `ec935f9b1d2c2a7b6d3187be7315bc6d95955a2f1d5545af3cea4b5f9536e1ca`; `EntityFilter.java` `NOT_SPECTATOR_NOT_CREATIVE`, lines 17-19, SHA-256 `8423e0f0e2eeca09ac57a87028512c1962a3b45eac016fa864f34671f8c863c1`; `EntityView.java` three-argument query API, lines 22-24 and default query, lines 32-34, SHA-256 `3822844ced53882c52f0523a1bd714ba3cd9bb9dab38a5e2c53051c7b1590e3`.
- B query application: `World.java` passes the predicate into each loaded chunk query, lines 880-893, SHA-256 `498a72cdd4bd035cd731d2c49fce401bef439d6f92f498501577d9cfc59ab487`.
- A artifact note: these source files match the readiness-verified A tree and manifest. The source owner published revised derived artifact `feather-r1-2026-10-07`; the original derived JAR is unavailable and equivalence is unproven. No byte-equivalence claim is made.
- B artifact note: cited source files match the readiness-verified B tree and manifest.

## Source-level difference

A's two-argument wing query resolves through `EntityView.getEntities(exclude,bounds)`, whose default passes `EntityFilter.NOT_SPECTATOR`. That predicate rejects spectator players but allows non-spectator creative players. B passes `EntityFilter.NOT_SPECTATOR_NOT_CREATIVE` explicitly to each wing query. Its player branch requires both `!isSpectator()` and `!isCreative()`, so a creative player is removed before `flingMobs` receives the list.

The paired `flingMobs` writer is the same: for each living entity it computes the x/z offset from the dragon body center, computes `h = f*f + g*g`, and calls `addVelocity(f/h*4.0, 0.2F, g/h*4.0)`. The separate damage condition follows the impulse in both versions and is excluded from this movement finding.

## Reachability and dependencies

The wing query runs server-side when `damagedTimer == 0`. If a non-spectator creative player intersects the expanded right- or left-wing query bounds, A includes that player and applies the direct velocity impulse; B filters the player out. The server velocity writer is consumed by the client entity-velocity packet path bounded in S062. The source does not establish a particular player/dragon trajectory or a runtime overlap. Dragon movement, wing-part movement and damage resolution are excluded; only the direct player velocity response and its reachability filter are in scope.

## Consequence and uncertainty

The source proves a changed query predicate for creative-player reachability and a consequent conditional difference in whether the player receives the wing impulse. It does not prove that a given vanilla world or movement route reaches the overlap. No damage, combat resolution, dragon trajectory or runtime outcome is claimed.

## Handoff

Candidate only. Independent source review and the player/dragon entity-list reachability context remain open. Implementation handoff is not ready. First changed release is unknown within the pair.
# F-15: Water fall-distance accumulation changes sneak edge back-off eligibility

- Older version A: 1.21.4
- Newer version B: 1.21.5
- Mechanic / coverage slice IDs: S3-POST, S4-EDGE, S7-DEPENDENCIES
- Classification: changed behavior
- Confidence: source-confirmed
- Applicability: player movement while holding sneak in water
- First changed release: unknown within (1.21.4, 1.21.5]
- Runtime validation: not performed

## Paired evidence

- A: `ready/1.21.4/mojmap.sources.sha256`; `Entity#checkFallDamage` lines 1237-1252 accumulates downward movement into `fallDistance` on every non-grounded move, including while `isInWater()`; the grounded branch calls `fallOn` when accumulated distance is positive and then resets it. `Entity#move` calls `Player#maybeBackOffFromEdge` before collision resolution at line 647 and `checkFallDamage` after movement/on-ground state updates at line 682. `Player#isStayingOnGroundSurface` returns the sneak-key state at lines 348-349; `Player#maybeBackOffFromEdge` lines 1069-1117 applies the horizontal support back-off only when not flying, requested Y is nonpositive, mover type is SELF or PLAYER, sneak is held, and `isAboveGround(maxUpStep)` is true. `Player#isAboveGround` lines 1120-1122 returns `onGround || fallDistance < maxUpStep && !canFallAtLeast(0, 0, maxUpStep - fallDistance)`. Source hashes: Entity `05f18ef2ec0413fc010230407c812a11553eb5123b68d21b5d7b2c0c175698ad`; Player `c45f41b9784ce50a88a498e84a13edcef0a23e175233d726ed7f90940f19ebc1`.
- B: `ready/1.21.5/mojmap.sources.sha256`; `Entity#checkFallDamage` lines 1274-1292 accumulates downward movement only when `!isInWater()`; its grounded callback/reset remains after that guard. `Entity#move` calls `Player#maybeBackOffFromEdge` before collision resolution at line 655 and `checkFallDamage` after movement/on-ground state updates at line 703. `Player#isStayingOnGroundSurface` returns the sneak-key state at lines 363-364; `Player#maybeBackOffFromEdge` lines 1026-1075 retains the same eligibility gates and back-off loops; `Player#isAboveGround` lines 1077-1079 retains the same state formula. Source hashes: Entity `fc177733e9cc4b2d5cf2562e5529d0f4e0b9c3690f081fbd58aa9119eb173dc4`; Player `8fc187f33999db9dfc49251e95e92a17645a50ad16ca0f93f4948209f62036`.

## Source-level difference

For a player whose downward movement occurs in water, A increases `fallDistance` and B does not. On a later non-grounded move, with abilities-based flight off, sneak held, mover type SELF/PLAYER, requested Y nonpositive, and support within the step-height query, choose A state `fallDistance >= maxUpStep` and B state `fallDistance < maxUpStep`. A's `isAboveGround` is then false; B evaluates the downward no-collision query and can return true when support lies within the queried depth. That changes whether B enters the horizontal edge-back-off loops before collision resolution. The 1.21.5 support query includes the small inset already covered by F-10; the branch difference remains reachable with a support footprint that has margin beyond that inset.

## Reachability and dependencies

`Entity.move` runs the edge-back-off query before applying this move's collision result and before `checkFallDamage` updates the distance for that move. The changed water accumulation therefore feeds a later movement call. The player override of `isStayingOnGroundSurface` makes the gate reachable with sneak input. This finding uses fall distance only as an input to a player movement predicate; it does not cover fall-damage calculation, landing damage, attack criticals, or other combat outcomes.

## Consequence and uncertainty

The source proves a changed player edge-back-off eligibility condition after downward movement in water. When the eligibility branch enters and a proposed horizontal offset lacks support within the allowed query, B reduces that horizontal movement in 0.05 increments while A can skip those checks. The exact position trajectory depends on collision shapes, step height, water state and input; no runtime measurement was performed.

## Handoff

Independent delta: water-gated fall-distance accumulation changes the later player sneak edge-back-off predicate. Related finding IDs: F-10. This finding is a bounded state-writer-to-movement-reader path; full post-travel, collision and water inventories remain open.

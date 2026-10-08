# F-S3-FLUID-JUMP-GATE: flying players skip liquid jump impulses

- Older version A: 1.15.2, Mojmap
- Newer version B: 1.16.5, Mojmap
- Mechanic / coverage slice IDs: LivingEntity jump input; S3-JUMP
- Classification: changed behavior
- Confidence: source-confirmed
- Applicability: historical player behavior
- First changed release: unknown within (1.15.2, 1.16.5]
- Runtime validation: not performed

## Paired evidence

- A manifest artifact reference: `../../../build/movement-campaign-2026-10-07/ready/1.15.2/mojmap`; `net/minecraft/world/entity/LivingEntity.java`; `LivingEntity#aiStep()` jump block, lines 2265-2279; SHA-256 `46d243bb7e51f7b54404aa1d7d6e6b827682d0f5925d02847c4193306c4d5e54`. The outer gate is `if (jumping)` and can call `jumpInLiquid(LAVA)` or `jumpInLiquid(WATER)` under its height/ground conditions.
- B manifest artifact reference: `../../../build/movement-campaign-2026-10-07/ready/1.16.5/mojmap`; same method, lines 2427-2451; SHA-256 `b5d8a1a3c80f85d5d545b5a777e9e2a915dc002a7e31b5f5ad12bf7e285d7a88`. The outer gate is `jumping && isAffectedByFluids()`; its false path resets `noJumpDelay` and performs no liquid jump.
- B player gate: `net/minecraft/world/entity/player/Player.java`; `Player#isAffectedByFluids()`, lines 1004-1007; SHA-256 `d2e26589bdb6a20dc914266db06aa48f50811efc792d6e63b3008c9199914960`; returns `!abilities.flying`.

## Source-level difference

For a flying player who is jumping and otherwise satisfies A's liquid-jump condition, A enters the liquid-jump decision and can invoke its water or lava impulse. B rejects the whole branch before reading fluid height because its Player override returns false while flying. B also reaches the `noJumpDelay = 0` path in that case. This slice is distinct from LocalPlayer's crouch-to-descend input, covered by F-S1-WATER-DESCENT.

## Reachability and dependencies

The jump flag is sampled in the living movement step; A's liquid branch is controlled by water height, lava contact, onGround and noJumpDelay. B measures the matching fluid height and additionally gates through the Player flying ability. The finding only concerns the liquid jump path, not creative flight toggle input or ground jumping outside the liquid conditions.

## Consequence and uncertainty

The source proves that the liquid jump helper is reachable in A and skipped in B under the stated predicates. The exact y-impulse and eventual position depend on the selected liquid and preceding movement state; no runtime result is claimed.

## Handoff

Independent source delta: B excludes flying players from liquid jump processing and clears the jump delay on the skipped path. Related finding IDs: F-S1-WATER-DESCENT. Boundary within the endpoint interval remains unknown.

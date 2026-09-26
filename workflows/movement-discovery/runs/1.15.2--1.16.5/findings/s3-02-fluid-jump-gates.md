# S3-02: Fluid jump input is gated and shallow lava can trigger a ground jump

- Older version A: 1.15.2
- Newer version B: 1.16.5
- Mechanic / coverage slice IDs: Stage 3, living jump integration; `S3-FLUID-JUMP`.
- Classification: changed behavior
- Confidence: source-confirmed
- Applicability: historical player behavior
- First changed release: unknown within (1.15.2, 1.16.5]
- Runtime validation: not performed

## Paired evidence

- A: `LivingEntity.aiStep()` lines 2266–2278 checks `jumping`, routes water through `waterHeight`, and calls `jumpInLiquid(LAVA)` while in lava. Source SHA-256 `46D243BB7E51F7B54404AA1D7D6E6B827682D0F5925D02847C4193306C4D5E54`.
- B: `LivingEntity.aiStep()` lines 2427–2451 requires `jumping && isAffectedByFluids()`, selects lava/water fluid height, and for shallow grounded lava calls `jumpFromGround()` with a 10-tick delay; otherwise it uses the liquid jump path. `Player.isAffectedByFluids()` at lines 1005–1006 returns `!abilities.flying`. Source hashes: `LivingEntity.java` `B5D8A1A3C80F85D5D545B5A777E9E2A915DC002A7E31B5F5AD12BF7E285D7A88`; `Player.java` `D2E26589BDB6A20DC914266DB06AA48F50811EFC792D6E63B3008C9199914960`.

## Source-level difference

B suppresses this liquid-jump branch while the player is ability-flying. In addition, shallow grounded lava at or below the fluid jump threshold uses the ordinary ground jump in B, while A's in-lava branch adds only `0.04F` vertical velocity through `jumpInLiquid`.

## Reachability and dependencies

The local/server player jump state reaches the living AI-step branch. A flying player pressing jump in water or lava reaches A's liquid jump branch but is rejected by B's player override. A nonflying grounded player jumping in shallow lava reaches B's `jumpFromGround` branch.

## Consequence and uncertainty

The branch changes vertical impulse and, in shallow lava, jump-delay state. Exact net displacement depends on surrounding travel, effects, and current state; no trajectory was run.

## Handoff

Two related but independently preconditioned changes: fluid-effectability gate and shallow-lava ground-jump selection. The shallow-lava travel adjustment is recorded separately in `S3-01`.

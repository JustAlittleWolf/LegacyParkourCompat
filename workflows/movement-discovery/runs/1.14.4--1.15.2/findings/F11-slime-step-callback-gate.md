# F11: grounded slime-block damping uses a broader step callback gate in 1.15.2

- Older version A: 1.14.4
- Newer version B: 1.15.2
- Mechanic / coverage slice IDs: stages 4–5; grounded block callbacks and horizontal velocity
- Classification: changed behavior
- Confidence: source-confirmed
- Applicability: player states where the callback gates differ
- First changed release: unknown within (1.14.4, 1.15.2]
- Runtime validation: not performed

## Paired evidence

- A `Entity.move(...)` calls the support block's `stepOn(...)` only inside `makeStepSound() && (!onGround || !isSneaking() || !(this instanceof Player)) && !isPassenger()`, then only when `onGround` (lines 508–518). For a grounded player, this requires `makeStepSound()` true, not sneaking, and not being a passenger. `Player.makeStepSound()` returns `!abilities.flying` (A `Player.java` lines 1646–1648). A `Entity.java` SHA-256: `31C6BE42D165102D3E6DC4295FDFEF6E8971FC3AEA13F938A5B3A33B91D524A7`.
- B `Entity.move(...)` calls `stepOn(...)` under `onGround && !isSteppingCarefully()` (lines 499–501), separately from the later movement-noise and passenger gate. `isSteppingCarefully()` returns the shift-key flag (B `Entity.java` lines 1787–1789), which is the same shared flag read by A's `isSneaking()`. B `Player.isMovementNoisy()` preserves the ordinary grounded step-sound gate by requiring `!abilities.flying && (!onGround || !isDiscrete())`, but this method no longer gates `stepOn`. B `Entity.java` SHA-256: `191B3AD3E7348C9BAC1E703FFF896706D23A751BF15162AACF676F5F97C0A10E`.
- A `SlimeBlock.stepOn(...)` scales horizontal velocity by `0.4 + abs(vY) * 0.2` when `abs(vY) < 0.1 && !isSneaking()` (lines 44–52). B uses the same formula and threshold, with `!isSteppingCarefully()` in the equivalent player shift-state predicate (lines 42–50). A/B `SlimeBlock.java` SHA-256: `C3C88920F4E895B265042557708CB658075D00948ABBE700240F28EB9791A137` / `E45CE854171972D6F46D9B0FFE4AD66217D074E6DE0297DE5551BC0D2405D7C5`.
- The compared collision response and support-block selection paths continue to run before this callback. Their core movement, clipping and support-cell comparison is recorded in [Stage 4 collision correspondence](../run.md#stage-4-collision-correspondence).

## Source-level difference

For a grounded, non-shift player on a slime block, B calls `stepOn` even when the player has flying ability enabled or is a passenger. A suppresses the callback in either state: flying makes `Player.makeStepSound()` false, and passengers fail the explicit passenger condition. Since `SlimeBlock.stepOn` writes horizontal delta movement, B applies the horizontal multiplier `0.4 + abs(vY) * 0.2` in these states while A does not. For ordinary grounded non-flying, non-passenger players, the callback gates and slime damping conditions correspond.

## Reachability and dependencies

Player movement -> `Entity.move` collision resolution -> selected support block -> `Block.stepOn` -> `SlimeBlock.stepOn` -> horizontal delta-movement write. The difference is the B callback gate, not the slime formula. This is a player movement response; unrelated callback effects such as redstone activation and turtle-egg destruction are outside this velocity slice.

## Consequence and uncertainty

The source proves the callback invocation and guarded velocity write. It does not establish an observed trajectory or how often a player reaches the gated flying/passenger states while grounded. Exact first release is unknown within the endpoint interval.

## Handoff

Independent grounded callback-gate difference. It does not close the remaining collision-order, block/fluid consumer, or external-input inventory.

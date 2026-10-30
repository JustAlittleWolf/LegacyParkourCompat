# Independent ordinary movement source audit: 1.9.4 → 1.10.2

## Inputs and scope

Audited the immutable source-only report `194-1102-ordinary31/report.md` (SHA-256 `16bd0e586560d6ded6d76c5b73a8565a4aefcf7263e7a2c53311d02ab5518b58`) and its finding draft `local-auto-jump.md` (SHA-256 `65ca1b9a69e8823f870ef6523e9f3d8f3df12ecf76406ca98d80db8f2c573f88`). Sources are the read-only exact-release `ornithe-feather` trees under `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\1.9.4` and `1.10.2`. Their ready markers bind the exact versions and Feather Gen2 build.2 mappings; source manifests are `c7b508fe01634887b65919dcd3a900c311a21d9510a1f1ab248d5c17c528ab19` and `91b0f478acb7b6f13463c35b268a30d2806f583402631a56f54ce2eb70d1ec71`. Both exact-pair verification manifests report zero source-file differences from independently decompiled trees.

This is a bounded independent audit of the ordinary local-player tick/input/travel/pose/state/velocity/movement-modifier slice and the local-auto-jump claim. It does not close collision providers, `Entity.move`, external callbacks, or the full pair; it establishes no intermediate patch boundary.

## Audit verdict

The local-auto-jump difference is supported. In 1.9.4, `LocalClientPlayerEntity.mobTick` has no auto-jump countdown injection, and the class has no local `move` override or auto-jump detector. In 1.10.2, the local `move` override calls `super.move` first and then passes actual horizontal displacement to the detector (`LocalClientPlayerEntity.java:812–817`); `Input.getMovement` supplies the sideways/forward vector (`Input.java:18–20`).

The detector's key gates and operation order in 1.10.2 are as reported: option cache enabled; no pending countdown; grounded; not sneaking; not riding; at least one movement component nonzero; use actual displacement unless its squared length is at most `0.001F`, in which case construct the direction from input and yaw; reject a direction facing too far backward (`m < -0.15F`); require the two initial block collision shapes above the player to be empty; query swept collision boxes; and queue only for a detected rise strictly greater than `0.5F` and at most the current upper limit (`LocalClientPlayerEntity.java:823–923`). Collision shape/world queries and the inherited collision move remain external dependencies, as the report states.

The one-tick queue chronology is correct: detection runs inside `move`, which is reached later from `super.mobTick`; the current tick has already passed the countdown read at `LocalClientPlayerEntity.java:672–677`. On the next `mobTick`, a positive countdown is decremented and sets `input.jumping = true`. The `bl4` check in the flight toggle (`:713–727`) prevents that injected press from toggling flight. The jump is therefore a synthetic local input on the next local tick, with the downstream jump/travel behavior dependent on ordinary eligibility gates.

## Correction required

Both audited descriptions state that the obstacle rise is at most `1.2F`. That is only the base ceiling. Source initializes `o = 1.2F`, then, if jump boost is active, adds `(amplifier + 1) * 0.75F` (`LocalClientPlayerEntity.java:856–860`); the upper-rise rejection and acceptance test use this adjusted `o` (`:888–895`, `:911–915`). Update the finding and report summary to say the ceiling is `1.2F`, increased by `0.75F` per jump-boost level. See `local-auto-jump-correction.md` for a correction supplement bound to the immutable draft hashes.

The option-cache detail is also narrow: `autoJumpEnabled` is copied from `minecraft.options.autoJump` in `sendMovementToServer`, inside the `isCamera()` branch (`LocalClientPlayerEntity.java:168–229`, assignment at `:228`). The existing phrase “refreshes/synchronizes the option” is acceptable as a summary, but should not imply an unconditional write independent of that enclosing gate.

## Checked no-difference terminals

- `PlayerEntity.jump` has the same superclass call, jump-stat update, sprint-dependent fatigue values, and order in both versions (`1.9.4 PlayerEntity.java:1361–1369`; `1.10.2:1380–1388`). The inherited `LivingEntity.jump` body is likewise identical in the inspected method (`1.9.4 LivingEntity.java:1279–1292`; `1.10.2:1305–1318`).
- Player flight `moveRelative` has matching gates, temporary fly-air-speed assignment, delegated call, `velocityY * 0.6`, air-speed restore, fall-distance reset, flag clear, and movement-stat call order (`1.9.4 PlayerEntity.java:1371–1395`; `1.10.2:1391–1414`). `getSpeed` still reads and casts the movement-speed attribute in both.
- Player pose target dimensions and collision-fit test are unchanged: fall-flying `0.6 × 0.6`, sleeping `0.2 × 0.2`, sneaking `0.6 × 1.65`, otherwise `0.6 × 1.8`; resize only when the candidate box has no world collision (`1.9.4 PlayerEntity.java:285–309`; `1.10.2:296–320`). The local-variable declaration arrangement changed, not the values or gate.
- `LivingEntity.moveRelative` introduces a helper returning `0.8F` for the prior water movement multiplier; the only override in the 1.10.2 tree is `PolarBearEntity`, not a player. The report correctly treats the player's default multiplier as unchanged. Its liquid-travel branch adds `isNoGravity()` gates, but this is the new no-gravity entity feature and is not an ordinary 1.9.4-player behavior difference under the assigned profile comparison.
- `KeyboardInput` is identical per report's compared source hash. The new `Input.getMovement` accessor feeds auto-jump detection; no change to the keyboard input tick itself was found in the cited slice.

No other eligible movement delta was established in these checked terminals. The report's explicit collision-lane dependencies and its no-freeze boundary remain accurate. The upper-ceiling summary correction is required before accepting the finding revision as written.

# F-19: Item slowdown changes the prior-input sprint timer edge

- Older version A: 1.21.4
- Newer version B: 1.21.5
- Mechanic / coverage slice IDs: S1-INPUT-SAMPLE, S1-SPRINT-GATE, S1-SPRINT-TIMER
- Classification: candidate changed behavior
- Confidence: source-confirmed bounded sequence
- Applicability: local keyboard movement on ground; non-passenger; item-use release followed by a forward double-tap sequence
- Runtime validation: not performed
- Review status: pending independent source-only review

## Paired source evidence

- A `KeyboardInput#tick()` writes raw `-1/0/1` impulses (`KeyboardInput.java:13-34`, SHA-256 `c885bcc709e445c3ff91f03bf37acf9320e17fc9daf1bd2ba46ba20487f1d9ed`). In `LocalPlayer#aiStep()`, the prior `input.forwardImpulse` is tested before `input.tick()` at lines 653-662. When item use is active and the player is not a passenger, lines 668-672 multiply both impulses by `0.2F` in place and clear `sprintTriggerTime`; `hasEnoughImpulseToStartSprinting()` uses `forwardImpulse >= 0.8` on land (lines 1068-1071). `LocalPlayer.java` SHA-256 `145686ebdc7f0d12a64070309073665eb8695b7e09911a723e86113a77d04611`; `ClientInput.java` SHA-256 `0f2e4d03749ab7495e386af05aa77a0414b5d1c3ef76256db2b514f42cc6e3c4`.
- B `KeyboardInput#tick()` writes a normalized `Vec2` (`KeyboardInput.java:21-36`, SHA-256 `d5cb0e93df7f66755172d74e028225012ee33c6e4f0d510a1d8e25ba5497c0a3`). `LocalPlayer#aiStep()` snapshots `input.hasForwardImpulse()` before ticking the input at lines 693-703; the B predicate reads `moveVector.y > 1.0E-5F` (`ClientInput.java:17-19`, SHA-256 `597a44339a99f1bce1b081614c7c2984ca03e255b40e9d247675a31b6f810d78`). B clears `sprintTriggerTime` during item use at lines 718-720 but does not attenuate or mutate `moveVector`; its controlled-camera `applyInput()` applies item slowdown to a derived vector in `modifyInput()`, not to the stored input (LocalPlayer.java:608-638). A `LocalPlayer.java` SHA-256 `f1fcfed4a938732361e7ad951f93e9b73b02320ee56f0b219e2b3c7acdbfa2ef`.
- The double-tap branches are A `LocalPlayer#aiStep()` lines 698-708, gated by ground/underwater, no shift, no prior qualifying impulse and `canStartSprinting()`; B lines 722-733, gated by `canStartSprinting()` and no prior forward impulse. A and B both arm `sprintTriggerTime` to 7 when the branch sees a first qualifying press with no active timer; a later qualifying press while the timer is positive calls `setSprinting(true)`. Sprint state changes before inherited `LivingEntity.aiStep()` reaches travel. `LivingEntity.java` hashes: A `e62ce650af0a5ac97e4d0a2ba7cad4ba68a8d098525609f8b5b6976a8ab0ae30`; B `a8aed863d4fdc515c751dd2878a8bb9c13179228cb8dbb50edf1d19cd5404271`.

## Reachable sequence

Use forward-only keyboard input on clear, dry ground; keep the player non-passenger, not shifting/crouching, not sprinting, not fall-flying, without blindness, with food above the sprint threshold, and with sprint key released. Start with `sprintTriggerTime == 0`.

1. While forward remains held, use an item for one tick. Both versions clear the trigger timer. A then stores `forwardImpulse == 0.2F`; B stores `moveVector.y == 1.0F` because item slowdown is applied to a separate movement vector later.
2. Release item use while keeping forward held. Before the new keyboard sample, A's land threshold sees the stored `0.2F` and says there was no qualifying prior impulse; B sees its stored positive unit forward vector. After the sample, both have valid current forward input and all stated sprint gates pass. A arms `sprintTriggerTime` to 7; B does not enter its double-tap branch.
3. Release forward for one tick. A decrements the timer to 6; both current inputs are zero and neither starts sprinting.
4. Press forward again. Both prior-input snapshots now indicate no forward impulse and both current sprint gates pass. A decrements its timer to 5 and starts sprinting because the timer is positive. B has no armed timer, so it arms the timer to 7 without starting sprinting on this tick.

The differing sprint-state transition is reached through vanilla keyboard inputs and source-ordered stored-input values. `LivingEntity#setSprinting()` applies the sprint speed modifier before the later travel call; this candidate claims the state transition only, not a measured trajectory.

## Scope and relation

This is distinct from F-13's airborne-versus-ground double-tap gate: the sequence stays on ground and both versions' terrain gate passes. It is also distinct from F-04's item-use sprint-stop and timer-reset behavior: both versions clear the timer during item use here; A later arms it because its stored forward impulse was attenuated, while B's stored movement vector remains raw. F-03 covers diagonal normalization precision; this witness uses only a forward axis. F-09-r2's rejected multiplication-order candidate is not a dependency for this state-retention path.

Runtime validation was not performed. Exact first changed release within the pair is unknown.

# F-4: Ground jump preserves a stronger upward velocity in 1.21.11

- Older version A: 1.20.6.
- Newer version B: 1.21.11.
- Mechanic / coverage slice IDs: stage 3, slice 3.1 (ground-jump velocity application).
- Classification: changed behavior.
- Confidence: source-confirmed.
- Applicability: player reaches the ground-jump path with positive vertical velocity greater than the computed jump power.
- First changed release: unknown within (1.20.6, 1.21.11].
- Runtime validation: not performed.

## Paired evidence

- A manifest: `../run.md`, artifact A. Source `decompiled_minecraft/1.20.6/mojmap/net/minecraft/world/entity/LivingEntity.java`, `LivingEntity.jumpFromGround()`, lines 2069–2080; SHA-256 `c66ec8dc3b1856e490e5834a46185589030d9fbc411e3e2ce64c73203cd753b2`. It reads `getJumpPower()` and writes `setDeltaMovement($$1.x, $$0, $$1.z)`, replacing the existing vertical component.
- B manifest: `../run.md`, artifact B. Source `decompiled_minecraft/1.21.11/mojmap/net/minecraft/world/entity/LivingEntity.java`, `LivingEntity.jumpFromGround()`, lines 2269–2281; SHA-256 `19ed2d565858c401c69a06750b054a633a20ea864cab3a753b5c767f0bdd60e8`. It writes `setDeltaMovement($$1.x, Math.max($$0, $$1.y), $$1.z)`, preserving the greater of jump power and current vertical velocity.
- Reachability: A `LivingEntity.aiStep()` calls `jumpFromGround()` from the input/fluid-gated jump block at line 2656; B calls it from the corresponding jump block at line 2946. LocalPlayer inherits this living-entity route. The respective LivingEntity source hashes are listed above.

## Source-level difference

When `jumpFromGround()` is reached with a positive current `deltaMovement.y` greater than `getJumpPower()`, A replaces that value with the jump power. B keeps the current larger value through `Math.max`. When current vertical velocity is no greater than jump power, both write jump power. The sprint-jump horizontal impulse expressions are the same in the inspected method; this finding isolates only the vertical write.

## Reachability and dependencies

The local-player input jump path reaches `LivingEntity.aiStep()` and its `jumpFromGround()` call when the ground/fluid jump guards pass. Current vertical velocity may be locally calculated, collision-produced, or synchronized; its upstream producers are outside this slice and remain in stage 4/7 dependency closure. The computed jump power and its attribute, effect and block-factor inputs remain in stage 2/5/6 closure.

## Consequence and uncertainty

The source proves a different vertical velocity assignment under the stated precondition. It predicts a different subsequent vertical trajectory while the B player carries the larger value. Whether a particular play scenario reaches that state, and its measured trajectory, were not runtime-tested. The source does not identify the first release where the max operation appeared.

## Handoff

Independent delta: ground-jump vertical velocity uses `max(jumpPower,currentY)` in B. Related finding IDs: none. Upstream velocity and jump-power dependencies remain open; implementation and testing decisions are deferred.

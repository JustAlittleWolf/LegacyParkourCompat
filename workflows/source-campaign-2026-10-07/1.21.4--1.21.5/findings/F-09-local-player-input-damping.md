# F-09: Controlled local-player input skips the base 0.98 damping in 1.21.5

- Older version A: 1.21.4
- Newer version B: 1.21.5
- Mechanic / coverage slice IDs: S1-INPUT-SCALE, S1-LOCAL-TICK
- Classification: changed behavior
- Confidence: source-confirmed
- Applicability: historical player behavior
- First changed release: unknown within (1.21.4, 1.21.5]
- Runtime validation: not performed

## Paired evidence

- A: `ready/1.21.4/mojmap.sources.sha256`; `net/minecraft/world/entity/LivingEntity.java`; `LivingEntity#aiStep()` lines 2766-2770 multiplies `xxa` and `zza` by `0.98F` after the local player's `serverAiStep()` supplies them and before travel (SHA-256 `e62ce650af0a5ac97e4d0a2ba7cad4ba68a8d098525609f8b5b6976a8ab0ae30`). `LocalPlayer#serverAiStep()` lines 607-617 writes the current input axes (SHA-256 `145686ebdc7f0d12a64070309073665eb8695b7e09911a723e86113a77d04611`).
- B: `ready/1.21.5/mojmap.sources.sha256`; `net/minecraft/world/entity/LivingEntity.java`; `LivingEntity#aiStep()` lines 2712-2717 invokes virtual `applyInput()` before jump and travel; the base `LivingEntity#applyInput()` lines 2806-2809 applies the same `0.98F` multipliers (SHA-256 `a8aed863d4fdc515c751dd2878a8bbc13179228cb8dbb50edf1d19cd5404271`). But `LocalPlayer#applyInput()` lines 608-621, when `isControlledCamera()` is true, writes the modified input axes and does not call `super.applyInput()` (SHA-256 `f1fcfed4a938732361e7ad951f93e9b73b02320ee56f0b219e2b3c7acdbfa2ef`).

## Source-level difference

For the normal controlled-camera local-player path, A attenuates each current input axis by `0.98F` before travel. B dispatches to the `LocalPlayer` override, which sets the axes but bypasses the base method containing those multipliers. The player's item-use, sneaking, float normalization and square-movement modifiers still apply; the missing factor is specifically the shared `0.98F` attenuation. If `isControlledCamera()` is false, the override calls `super.applyInput()`, so this finding does not apply to that branch.

## Reachability and dependencies

`LocalPlayer.aiStep()` reaches `LivingEntity.aiStep()`. B's inherited method invokes `applyInput()` on every tick; `LocalPlayer#applyInput()` chooses its local-input override when the player is the controlled camera. The resulting `xxa`/`zza` values are consumed by player travel through `Entity#getInputVector`. The base factor is written in `LivingEntity#applyInput`; the local override replaces that call on the stated branch.

## Consequence and uncertainty

For nonzero controlled-camera input axes, B passes values to travel without the extra `0.98F` multiplication present in A, after applying its other input transforms. The source establishes this per-tick input difference but not a measured trajectory or downstream persistence. Runtime validation was not performed.

## Handoff

Independent delta: the controlled-camera `LocalPlayer#applyInput()` override bypasses the base movement-input damping. Related finding IDs: F-03. Applicability requires the local player to be the controlled camera and input to reach the travel path. Exact first release within the pair is unknown.

# F-S1-02: 1.21.4 adds local sprint stop and slow-movement start gates

- Older version A: 1.21.3
- Newer version B: 1.21.4
- Mechanic / coverage slice IDs: local player sprint policy; S1-03
- Classification: changed behavior
- Confidence: source-confirmed
- Applicability: historical player behavior
- First changed release: unknown within (1.21.3, 1.21.4]
- Runtime validation: not performed

## Paired evidence

- A manifest: artifact A in `run.md`; `build/movement-campaign-2026-10-07/ready/1.21.3/mojmap/net/minecraft/client/player/LocalPlayer.java`, `LocalPlayer#aiStep()`, lines 638-739, and `canStartSprinting()`, lines 1023-1035, SHA-256 `fbd40f1f47adfa66dda9b15188e5dce82af3e8e8d7c3dd0543e602a354ad3fe0`. A's active sprint stop block checks forward impulse/food, horizontal collision and water/swimming. Its start predicate already checks item use, blindness, passenger vehicle eligibility and fall-flying, but has no slow-movement condition. No `shouldStopSprinting()` helper exists.
- B manifest: artifact B in `run.md`; `LocalPlayer#aiStep()`, lines 643-753, `shouldStopSprinting()`, lines 816-829, and `canStartSprinting()`, lines 1053-1062, SHA-256 `145686ebdc7f0d12a64070309073665eb8695b7e09911a723e86113a77d04611`. B calls `shouldStopSprinting()` after refreshing local crouching and sampling input, before input scaling and sprint-start checks. It stops for fall-flying, blindness, slow movement, a non-camel passenger, or item use unless passenger/underwater. Its start predicate additionally requires `!isMovingSlowly() || isUnderWater()`.
- Slow-movement state: A `LocalPlayer#isCrouching()` / `isMovingSlowly()`, lines 593-598, SHA-256 as A above; B lines 598-603, SHA-256 as B above. `LocalPlayer.aiStep()` refreshes its crouching field from flying/swimming/passenger state, pose-fit checks and shift input before either slow predicate. `Entity#isVisuallyCrawling()` is `isVisuallySwimming() && !isInWater()` in both versions; A `Entity.java` SHA-256 `a93719c302a0381a972af75ea360465e2e3551708dd07c34d4d40b7e5173c2b9`, B SHA-256 `05f18ef2ec0413fc010230407c812a11553eb5123b68d21b5d7b2c0c175698ad`.
- Other predicate sources: player fall-flight entry `Player#tryToStartFallFlying()`, A lines 1525-1533 and B lines 1528-1536; active effects `LivingEntity#hasEffect()` and effect update/removal methods; item-use state `LivingEntity#startUsingItem()` / `stopUsingItem()` plus LocalPlayer use handlers; water and underwater state from `Entity#updateInWaterStateAndDoFluidPushing()` and LocalPlayer's underwater update; passenger and camel state from the local entity's riding relation and vehicle type. A/B `Player.java` hashes `a803203e92aa4729d5f5c9b16085b6a43ce51d9907d309eb96736e9c7c1340de` / `c45f41b9784ce50a88a498e84a13edcef0a23e175233d726ed7f90940f19ebc1`; `LivingEntity.java` hashes `087390495f4fdfd14b9e12230e7aea4fdc50913bb4d1d91119e72892881cfc52` / `e62ce650af0a5ac97e4d0a2ba7cad4ba68a8d098525609f8b5b6976a8ab0ae30`.
- Travel consumer: `LivingEntity#aiStep()` dispatches the local player through its ordinary travel path; see S3-01 in `run.md`. Sprint state then affects travel speed. The bounded comparison does not claim vehicle physics or full travel equivalence.

## Source-level difference

B adds a local-player active-sprint stop check before the existing input scaling/start logic. The added state conditions can turn off sprinting even where A's active-sprint stop block would leave the sprint flag set. B also prevents a slow-moving local player from starting sprinting unless underwater. A already uses blindness, item use, fall-flight and passenger state to reject sprint starts; this finding concerns their new active-stop effect, the new slow-start restriction, and the camel exception for the passenger stop.

## Reachability and dependencies

The player tick refreshes crouching from current local input/pose-fit state, then B evaluates the stop predicate every AI tick. Fall-flight can be entered through the player's jump/glide path; blindness and item-use state are represented on the local living entity; passenger/camel identity and fluid state are available from the current client entity state. Thus the gate can run while the player is already sprinting and a predicate changes. The next travel call consumes the updated sprint flag. The food-state producer is excluded, but its existing start/stop read remains within the bounded comparison.

## Consequence and uncertainty

Source proves that B can clear local sprinting in the added reachable cases and that a slow-moving player cannot start sprinting outside underwater conditions. It does not measure the resulting trajectory or establish a game-runtime outcome. Vehicle movement and attack/damage resolution are outside this finding. The exact release introduction is unknown within the endpoint interval.

## Handoff

This finding is source-confirmed for the local sprint policy. Pair-level freeze and independent source audit remain open.

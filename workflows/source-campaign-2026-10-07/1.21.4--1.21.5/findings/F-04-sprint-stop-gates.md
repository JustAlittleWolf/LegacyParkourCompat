# F-04: Sprinting can persist through item use or slow movement in 1.21.5

- Older version A: 1.21.4
- Newer version B: 1.21.5
- Mechanic / coverage slice IDs: S1-SPRINT-GATE, S1-SPRINT-TIMER, S2-STATE-WRITERS, S6-ATTRIBUTES
- Classification: changed behavior
- Confidence: source-confirmed
- Applicability: historical player behavior
- First changed release: unknown within (1.21.4, 1.21.5]
- Runtime validation: not performed

## Paired evidence

- A: `ready/1.21.4/mojmap.sources.sha256`; `net/minecraft/client/player/LocalPlayer.java`; `LocalPlayer#aiStep()` lines 662-678 calls `setSprinting(false)` through `shouldStopSprinting()` and scales movement input when using an item or moving slowly (SHA-256 `145686ebdc7f0d12a64070309073665eb8695b7e09911a723e86113a77d04611`). `shouldStopSprinting()` lines 816-822 explicitly returns true for `isMovingSlowly()` and for item use when not a passenger and not underwater. `LivingEntity#setSprinting(boolean)` lines 2077-2084 (SHA-256 `e62ce650af0a5ac97e4d0a2ba7cad4ba68a8d098525609f8b5b6976a8ab0ae30`) removes or adds the sprint speed modifier on `Attributes.MOVEMENT_SPEED`.
- B: `ready/1.21.5/mojmap.sources.sha256`; `LocalPlayer.java`; `LocalPlayer#aiStep()` lines 718-744 only clears the double-tap timer for shift, item use, or backward input, then checks `shouldStopRunSprinting()` / `shouldStopSwimSprinting()` (SHA-256 `f1fcfed4a938732361e7ad951f93e9b73b02320ee56f0b219e2b3c7acdbfa2ef`). `canStartSprinting()` lines 1065-1075 prevents a new sprint under item-use/slow-movement conditions, but the B stop predicates at lines 837-852 contain neither item-use nor `isMovingSlowly()`. `LivingEntity#setSprinting(boolean)` lines 2101-2108 (SHA-256 `a8aed863d4fdc515c751dd2878a8bbc13179228cb8dbb50edf1d19cd5404271`) retains the same movement-speed modifier writer.

## Source-level difference

If a player is already sprinting on land and begins using an item while still moving forward, A calls `setSprinting(false)` in the early `shouldStopSprinting` check. B makes `canStartSprinting` false, but its later stop predicate does not stop an already-sprinting player for item use. B still scales the directional input for item use in `modifyInput`; it leaves the sprint flag and sprint speed modifier active. The same start-versus-stop asymmetry applies to slow movement (crouching or visually crawling): A stops the sprint state; B's start gate prevents a new sprint, while its active-sprint stop predicates omit that condition.

The timer also has a separate predicate change: A clears the double-tap timer on shift and qualifying item use, while B additionally clears it on backward key input. This is recorded with the same sprint state-machine slice because the timer drives the same sprint transition.

## Reachability and dependencies

`LocalPlayer.aiStep` reads local key/input, item-use, pose/slow movement, water, collision, vehicle and food predicates and writes sprint state through `setSprinting`. `LivingEntity#setSprinting` applies the transient movement-speed modifier consumed by player travel. Food-level production is excluded; its direct sprint predicate read remains in the gate. The findings concern the local player path.

## Consequence and uncertainty

For the stated land/item-use precondition, source shows A removes the sprint speed modifier while B leaves it active; B's separate item-use directional scale still reduces input. Slow movement has the same state retention difference. The exact resulting displacement depends on travel branch, attribute value, collisions and input. Backward input's added timer reset changes a later double-tap transition when backward input occurs inside the open trigger window; no timing run was performed.

## Handoff

Independent delta: sprint start/stop state and timer predicates. Related finding IDs: F-03. Applicability includes already-sprinting players beginning item use or slow movement on land, plus backward input during the sprint trigger window. Exact first release within the pair is unknown.

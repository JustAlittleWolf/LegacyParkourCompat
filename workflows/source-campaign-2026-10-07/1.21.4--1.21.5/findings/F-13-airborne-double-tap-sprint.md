# F-13: Forward double-tap sprint activation loses its ground gate in 1.21.5

- Older version A: 1.21.4
- Newer version B: 1.21.5
- Mechanic / coverage slice IDs: S1-SPRINT-GATE, S1-SPRINT-TIMER
- Classification: changed behavior
- Confidence: source-confirmed
- Applicability: historical player behavior
- First changed release: unknown within (1.21.4, 1.21.5]
- Runtime validation: not performed

## Paired evidence

- A: `ready/1.21.4/mojmap.sources.sha256`; `net/minecraft/client/player/LocalPlayer.java`; `LocalPlayer#aiStep()` captures the previous impulse before `input.tick()` (lines 653-662), then permits the forward double-tap timer/start branch only when `($$7 || this.isUnderWater()) && $$8 && $$6`, where `$$7` is the vehicle's ground state for a passenger or the player's own `onGround()` and `$$8` excludes shift and a prior qualifying forward impulse (lines 698-708). `canStartSprinting()` and `hasEnoughImpulseToStartSprinting()` are at lines 1053-1072. Source-file SHA-256 `145686ebdc7f0d12a64070309073665eb8695b7e09911a723e86113a77d04611`.
- B: `ready/1.21.5/mojmap.sources.sha256`; `net/minecraft/client/player/LocalPlayer.java`; `LocalPlayer#aiStep()` captures `input.hasForwardImpulse()` before `input.tick()` (lines 693-703), then runs the double-tap timer/start branch under `canStartSprinting()` and `!$$2` without a ground or underwater predicate (lines 722-733). `canStartSprinting()` at lines 1065-1075 continues to require forward input, sufficient food, no item use/blindness, and its passenger/fall-flying/slow-movement/fluid gates. Source-file SHA-256 `f1fcfed4a938732361e7ad951f93e9b73b02320ee56f0b219e2b3c7acdbfa2ef`.
- B input predicate: `net/minecraft/client/player/ClientInput.java`, `hasForwardImpulse()` lines 17-19 returns whether `moveVector.y > 1.0E-5F`; source-file SHA-256 `597a44339a99f1bce1b081614c7c2984ca03e255b40e9d247675a31b6f810d78`. The exact Mojmap source and artifact publication identities are recorded in `run.md`.

## Source-level difference

For a non-passenger local player whose current forward input and sprint eligibility gates pass, B's forward double-tap path can arm and activate its seven-tick sprint trigger while airborne. A requires the player to be on the ground or underwater to enter that double-tap path. The separate held-sprint-key branch is not this finding: A has that branch outside the ground/underwater double-tap guard.

## Reachability and dependencies

`LocalPlayer#aiStep()` samples the prior forward state, ticks local input, evaluates sprint eligibility and writes sprint state before reaching `super.aiStep()`. On the ordinary keyboard path, forward input supplies the positive forward impulse used by both versions' eligibility checks. For the stated non-passenger airborne case, A's ground/underwater condition rejects the double-tap path while B's eligibility predicate contains no airborne restriction. Food, blindness, item-use, fall-flying, movement-slowdown and fluid values are direct eligibility inputs; excluded producer systems remain vanilla. Sprint state is written through `LivingEntity#setSprinting()` and its speed modifier affects later travel; the full attribute producer/default and travel dependencies remain open.

## Consequence and uncertainty

The paired source proves that the local sprint state can transition through a forward double-tap while airborne in B under the stated eligibility gates, while A's corresponding double-tap branch rejects that state. It does not claim that every airborne forward tap sequence succeeds, nor that a measured trajectory differs. Runtime validation was not performed.

## Handoff

Independent delta: B removes the ground-or-underwater guard from the forward double-tap sprint transition; the held-sprint-key route is separate. Related finding IDs: F-04. Applicability requires a non-passenger local player, airborne state, a valid forward tap edge within the timer window, and all sprint eligibility predicates to pass. Exact first release within the pair is unknown.

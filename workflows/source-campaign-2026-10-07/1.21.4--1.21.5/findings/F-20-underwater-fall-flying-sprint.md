# F-20: Underwater fall flying permits sprint in 1.21.5

- Older version A: 1.21.4
- Newer version B: 1.21.5
- Mechanic / coverage slice IDs: S1-SPRINT-GATE, S1-SPRINT-STATE, S3-WATER
- Classification: candidate changed behavior
- Confidence: source-confirmed bounded player sequence
- Applicability: local player gliding into water, then moving forward underwater with sprint-eligible food and no blocking sprint gate
- Runtime validation: not performed
- Review status: pending independent source-only review

## Paired source evidence

- In A, `LocalPlayer#aiStep()` invokes `shouldStopSprinting()` and clears sprint before its sprint-start branches (lines 664-666). The helper returns true whenever `isFallFlying()` (lines 816-822). A `canStartSprinting()` also excludes `isFallFlying()` unconditionally (lines 1053-1061). Thus, after the player enters water while still fall-flying, this tick clears any active sprint and neither the underwater double-tap nor held-sprint branch can start it again.
- In B, `LocalPlayer#aiStep()` first evaluates `canStartSprinting()`, then dispatches the active-sprint stop to either `shouldStopSwimSprinting()` or `shouldStopRunSprinting()` (lines 722-752, 845-852). Neither stop helper checks fall flying. B `canStartSprinting()` permits `isFallFlying()` when `isUnderWater()` (lines 1065-1075). With the sprint key held, forward input, enough food, no blindness, no disallowed passenger, no item use and no slow movement, the sprint starts underwater while fall-flying; both possible stop-helper branches pass when the player is underwater and forward input is present. The sprint-key condition is necessary for this one-tick start path.
- Both versions' `Player#tryToStartFallFlying()` require `!isInWater()` (A lines 1528-1535; B 1507-1514), so the reachable sequence starts the glide in air, then enters water. Both `LivingEntity#canGlide()` methods require airborne, non-passenger state without Levitation and valid glide equipment; neither checks water (A lines 2847-2859; B 2837-2849). The server's `updateFallFlying()` clears the flag only when `canGlide()` fails (A lines 2825-2830; B 2815-2820). Therefore a player with valid glide equipment can retain fall-flying after entering water. `Entity#baseTick()` refreshes fluid contact and eye-fluid state before the next LocalPlayer tick; D31-D32 record the identical order in A and B.
- The same `LivingEntity#travel(Vec3)` fluid-first branch then selects `travelInFluid()` while the player is in water, affected by fluids and unable to stand on the fluid (A lines 2189-2199; B 2213-2222). D27 records the matched water formula: it chooses drag `0.9F` when sprinting and `getWaterSlowDown()` otherwise (default `0.8F`), before applying water travel. F-20 claims the reachable sprint-state difference feeding this existing movement branch, not a distinct fluid formula.
- Exact source SHA-256 values: A `LocalPlayer.java` `145686ebdc7f0d12a64070309073665eb8695b7e09911a723e86113a77d04611`, `Player.java` `c45f41b9784ce50a88a498e84a13edcef0a23e175233d726ed7f90940f19ebc1`, `LivingEntity.java` `e62ce650af0a5ac97e4d0a2ba7cad4ba68a8d098525609f8b5b6976a8ab0ae30`, `Entity.java` `05f18ef2ec0413fc010230407c812a11553eb5123b68d21b5d7b2c0c175698ad`; B `LocalPlayer.java` `f1fcfed4a938732361e7ad951f93e9b73b02320ee56f0b219e2b3c7acdbfa2ef`, `Player.java` `8fc187f33999db9dfc49251e95e92a17645a50adab16ca0f93f4948209f62036`, `LivingEntity.java` `a8aed863d4fdc515c751dd2878a8bbc13179228cb8dbb50edf1d19cd5404271`, `Entity.java` `fc177733e9cc4b2d5cf2562e5529d0f4e0b9c3690f081fbd58aa9119eb173dc4`. Each digest was recomputed from the published versioned Mojmap source root.

## Reachable sequence

Start with an airborne LocalPlayer wearing valid glide equipment in air, not a passenger or affected by Levitation. Start fall-flying through the normal jump/command path while dry. Continue forward into water so fluid contact is refreshed while `canGlide()` remains true. On a later tick, hold sprint and forward underwater with enough food to sprint, and no blindness, item use, disallowed passenger, horizontal collision, or slow-movement modifier.

At the underwater LocalPlayer tick, A's early stop gate clears sprint and its start gate refuses to start sprint while fall-flying. B's start gate accepts fall-flying underwater; with the stated inputs it starts sprint, and the selected run/swim stop helper does not stop it. The inherited travel then uses the same water branch in both releases, but B selects sprint drag `0.9F` while A selects the non-sprint water slow-down. This is a source-predicted movement consequence; no trajectory or runtime result is claimed.

## Scope and relation

This is distinct from F-04's general sprint stop gates: the bounded delta here is the fall-flying predicate, including B's underwater exception to sprint start, after the glide enters water. It is distinct from F-13's airborne double-tap sprint entry, which concerns an airborne double-tap state transition independent of fall-flying/water. It is also distinct from F-19's item-use-induced prior-input timer edge. D27-D32 are dependencies for the unchanged water travel and contact-state route; none supplies this sprint-gate delta.

Runtime validation was not performed. Exact first changed release within the pair is unknown.

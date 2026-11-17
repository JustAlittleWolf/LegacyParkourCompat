# Independent audit: item-component conversion movement slice

## Verdict

**SUPPORTED — bounded no-difference** for active item-use input slowdown and local sprint-start eligibility across Mojmap 1.20.4, 1.20.5, and 1.20.6. The comparison's claims about these gates, the client use-state gate, and ordinary vanilla shield/bow witnesses are supported by the exact local sources. This does not establish full-pair movement coverage.

## Immutable input

Reviewed only the supplied source comparison artifact, bound by SHA-256 `4d21efbbef0f0087cf193ea3e0645bfab0404c7d1593b7b1a2ecfff6bd83040e`.

## Exact source identity

Canonical local `decompiled_minecraft/<version>/mojmap/` sources were checked read-only. Each `mojmap.ready.json` identifies its exact version and Mojmap mapping and its `sourceManifestSha256` matches the local `mojmap.sources.sha256` file:

- 1.20.4: client `9221ab461a491bf9661cd8e773a5e662aaa43d600fa7970b8c12bbfb0431b838`; source manifest `fd3c8668483e5ff208c847474f6cbd3952a909c4aa602ed98b24e3d11a9602a1`.
- 1.20.5: client `49d5949adbb1021a7eb00c1b949f9e39934b10249cbf86d25004774d1b3ff510`; source manifest `162dac6c4f1d539a1c2cea0255b7938a5281c207ab460bdc265358016d732484`.
- 1.20.6: client `02dfd345ac1ad55692d5dbc8486ac7e4fea72cd54ac494a79cd48963048e56b2`; source manifest `56aae10684471d7abb1c366bd5dd431ab976112a87e6cc5c687a68f1eff06311`.

The relevant Java files were present in each source manifest and their local contents matched the manifest records. Mojmap provenance identifies the exact local prepared source sets. No wiki, product implementation/history, remote mapping, generated candidate, tests, or runtime input was used.

## Claim checks and bounds

- `LocalPlayer.tick()` in all three versions calls `input.tick(...)` and tutorial input handling before `if (isUsingItem() && !isPassenger())`; that block multiplies `leftImpulse` and `forwardImpulse` by `0.2F` and clears `sprintTriggerTime`. Locations: 1.20.4 `LocalPlayer.java:666–670`; 1.20.5 and 1.20.6 `:669–673`.
- The same tick path later calls `canStartSprinting()`. Its predicate order and gates match in all three: not already sprinting, enough impulse, enough food, `!isUsingItem()`, no blindness, passenger vehicle allowance, then not fall-flying. Locations: 1.20.4 `LocalPlayer.java:1013–1020`; 1.20.5 and 1.20.6 `:1020–1027`. The passenger exception on input scaling and the separate passenger vehicle predicate are both retained.
- Client use-state caller/state: `LocalPlayer.startUsingItem(hand)` obtains that hand's stack and, only for nonempty stack while not already using, calls `super.startUsingItem`, then sets `startedUsingItem = true` and `usingItemHand = hand`; `LocalPlayer.isUsingItem()` returns that flag. The base `LivingEntity.startUsingItem` applies the same nonempty/not-already-using gate before storing the stack and its duration. Locations: 1.20.4 `LocalPlayer.java:492–504`, `LivingEntity.java:2948–2953`; 1.20.5/1.20.6 `LocalPlayer.java:494–506`, `LivingEntity.java:3027–3032`.
- The local sprint predicate's `vehicleCanSprint` uses `Entity.canSprint()` and local-control status; `Player.canSprint()` returns true in all three releases. It is not an item-use predicate.
- The checked vanilla witnesses are valid: `ShieldItem` returns `UseAnim.BLOCK` and duration `72000`; `BowItem` returns `UseAnim.BOW` and duration `72000`. `ItemStack` forwards animation/duration to its item in every version. `LivingEntity.isBlocking()` checks active/nonempty use, then animation, then the five-tick duration threshold; this is a blocking predicate, not the slowdown gate.
- The 1.20.5 component conversion changes the base `Item` edible animation/duration defaults from legacy food properties to `DataComponents.FOOD` and `eatDurationTicks()`. The checked shield and bow override those methods. In `Items.java`, bow remains registered with durability only; shield gains `BANNER_PATTERNS = EMPTY` along with durability. The slowdown and sprint-start code reads only `isUsingItem()` and passenger/input state; it does not inspect item identity, use animation, use duration, food component, or banner patterns.

This audit establishes downstream movement behavior once local use state is active, plus the named default shield/bow examples. It does not audit every item-specific interaction that can establish active use, modded or otherwise customized item definitions, all component behavior, or later data-driven use-effect definitions. The checked vanilla item registrations are Java registrations in these exact sources; no separate vanilla JSON resource was needed for the claims above.

## Terminal

**COMPLETED** — independent bounded audit of the supplied comparison. Source interval: 1.20.4 / 1.20.5 / 1.20.6; operations: active-use input scaling, local sprint-start eligibility, use-state setter/getter gate, and default shield/bow witnesses with `isBlocking` boundary. No code changes, tests, launches, or source generation.

# Independent audit: `CAN_GLIDE_THROUGH` correction 1

## Verdict

Correction 1 is bound to the stated original closure: its SHA-256 `a91a30510983bb500329b3b593cd52e59ce297061a8e9f8b1bf202a3d3fbbff6` matches `closure.md`. Its pair-audit context hash `24af4f87694b702d8f35b362744cd7eb1434934a8a1d5674180187eae2731a33` also matches. The prior independent audit used for this review is SHA-256 `3aad25c323d730804ffca7786f04e2e12ce583809e997b66624b9eab3d9ff7a2`; correction 1 itself hashes to `fc1c1f3a59737fedcc53dedfe61b6031b4f0d4dc05ce90a193ac614833d90489`.

The correction's core narrowed claim is source-supported: on the non-fluid travel branch, an already fall-flying local player reaches `LivingEntity.travelFallFlying()`. There, a true `onClimbable()` result selects `travelInAir()` and stops fall flight; the fall-flying plus `CAN_GLIDE_THROUGH` predicate returns false before `CLIMBABLE`, avoiding that fallback. The withdrawn `travelInAir()` climb handling and jump-lift effects are not reached in that tag-gated state. No source-level endpoint difference was found.

Two qualifications belong with the bounded claim. First, `LivingEntity.travel()` tests the fluid gate **before** `isFallFlying()`, so the correction's general sentence that a fall-flying entity dispatches to `travelFallFlying()` must be understood as applying when the fluid gate is false. The positive path already names `travelFallFlying()` and is valid within that condition. Second, source provider declarations show all built-in `CAN_GLIDE_THROUGH` entries also occur in `CLIMBABLE` (including the `CAVE_VINES` members). This means the declared vanilla defaults provide a state where the tag-first check can change the predicate result, provided the player is neither a spectator nor using creative ability flight (the `LivingEntity` and `Player` wrapper gates). It does not establish the effective loaded tag values or prove any runtime state follows those defaults.

## Source retrace

For 1.21.11 Mojmap, `LivingEntity.travel(Vec3)` checks `shouldTravelInFluid(level().getFluidState(blockPosition()))`, then `isFallFlying()`, then ordinary air travel (`LivingEntity.java:2309–2316`). The corresponding 26.1.2 unobfuscated body has the same branch order (`LivingEntity.java:2392–2400`). Consequently, the corrected positive path is specifically:

`!shouldTravelInFluid(...) && isFallFlying()` → `travelFallFlying(...)` → `onClimbable()`.

At 1.21.11 `LivingEntity.java:2441–2445` and 26.1.2 `:2528–2532`, `travelFallFlying()` calls `travelInAir()` and then `stopFallFlying()` only when `onClimbable()` is true. The paired predicates at old `LivingEntity.java:1670–1685` and new `:1690–1705` put the fall-flight plus `CAN_GLIDE_THROUGH` return-false gate before `CLIMBABLE` and the usable trapdoor case. Thus a matching state does not execute the fallback block of `travelFallFlying()`. For tag membership to be the distinguishing cause, rather than the other false-return gates, the player must also be non-spectator and `abilities.flying` must be false; the default provider overlap with `CLIMBABLE` supplies the subsequent true result that would otherwise trigger fallback.

The built-in provider's `CLIMBABLE` list contains `VINE`, both twisting-vine blocks, both weeping-vine blocks, and both cave-vine blocks in both endpoints (old `VanillaBlockTagsProvider.java:289–300`; new `:289–299`). The `CAN_GLIDE_THROUGH` chain contains the same set by direct entries plus the `CAVE_VINES` tag (old `:320–324`; new `:319–323`; the cave-vine provider adds both cave-vine blocks in each endpoint). Under those provider declarations, the ordered predicate can distinguish a tagged member from its subsequent climbable-tag result. This is a relation between source provider declarations, not a claim about generated payloads or effective runtime membership.

`LocalPlayer` inherits `Player.onClimbable()` through `AbstractClientPlayer` in both endpoints. The `Player` wrapper preserves the creative-flight false result, otherwise delegates to the superclass (old `Player.java:2043–2044`; new `:2003–2004`). `LocalPlayer.aiStep` reaches the preflight `!onClimbable() && tryToStartFallFlying()` condition and sends the request on success (old `LocalPlayer.java:815–816`; new `:854–855`). Since the tag-specific predicate arm itself requires `isFallFlying()`, this pre-start condition is not a separate tag-triggered path. The inherited living AI travel call is reachable through `LocalPlayer.aiStep` → `Player.aiStep` → `LivingEntity.aiStep`, which calls `travel` (old: `LocalPlayer.java:875`, `Player.java:463`, `LivingEntity.java:2974`; new: `LocalPlayer.java:914`, `Player.java:453`, `LivingEntity.java:3073`).

The `travelInAir()` path is unavailable for the claim condition: when fall flight is active and fluid travel is not selected, `travel()` invokes `travelFallFlying()`. Its only `travelInAir()` call is guarded by `onClimbable() == true`, which the tag condition makes false. Accordingly, ordinary-air `handleOnClimbable()` and the related climb/jump lift checks inside the ordinary-air calculation do not stand as effects of the tag return while this fall-flight path is active.

Fluid travel remains a separate dispatch branch. If `shouldTravelInFluid(...)` succeeds, `travel()` calls `travelInFluid()` before testing fall flight. This review does not make a positive claim about the water-travel collision lift, and the correction's fall-flight branch conclusion should not be generalized across that earlier fluid branch.

## Evidence identity

The endpoint identities and manifest hashes remain those verified in the first audit: 1.21.11 Mojmap source manifest `0c4d83fef84c101d9db88f331acec7960f3c8346228c28995e6cf57bbfcae555`; 26.1.2 unobfuscated source manifest `54ae320660ca911a6d20514c965c33eb414fbedd1ad6727bbaa95fe906aa23c`. I rechecked the release-manifest rows and direct hashes for the provider, `LivingEntity`, `Player`, and `LocalPlayer` files on both sides; they remain identical to the first audit. No changed source hash or source identity is asserted by correction 1. The requested source bindings therefore remain intact.

## Boundary

This audit is limited to the corrected source-level fall-flight branch and its callers. It does not establish generated tag payloads, effective loaded membership, server producer timing, an executed trajectory, broader pair coverage, or runtime parity.

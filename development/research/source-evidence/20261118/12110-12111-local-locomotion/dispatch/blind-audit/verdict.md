# Independent verdict: local locomotion dispatch comparison

## Reviewed inputs

- Author draft: `dispatch/source/comparison.md`, SHA-256 `f4109c1401fb35c82b4e0769b84cc3b1fb04d3f05c0f5a96496454f92f9a558f`.
- Blind freeze: `dispatch/blind-audit/frozen.md`, SHA-256 `5ae9d5f0e93a6ccc3d854b1a7c7e3f790bf4c40f1bb71619ebe5225846118776`.
- Canonical Mojmap sources cited by those artifacts: 1.21.10 and 1.21.11 `LocalPlayer.java`, `Player.java`, `LivingEntity.java`, and `Entity.java` only.

## Verdict

**Accept the source-equivalence claim with the bounded scope below.** For an already-loaded ordinary local player on the logical client, on the direct non-riding, non-fall-flying, nonswimming, nonflying path with no fluid travel dispatch, the inspected tick/`aiStep`/travel dispatch order matches through entry into `LivingEntity.travelInAir` and its first call to `handleRelativeFrictionAndCalculateMovement(input, friction)`. This does not accept equivalence of later dry-air calculations or the complete movement pair.

Ordered dependencies for this accepted path:

1. `LocalPlayer.tick()` enters its superclass path after the loaded condition passes (1.21.10 `LocalPlayer.java:195–200`; 1.21.11 `LocalPlayer.java:211–215`). The inherited `Player.tick()` calls `super.tick()` in both versions (1.21.10 `Player.java:267`; 1.21.11 `Player.java:266`).
2. `LivingEntity.tick()` calls `Entity.tick()`/`baseTick()`, then dynamically calls `this.aiStep()` if not removed (1.21.10 `LivingEntity.java:2542–2544, 2581–2582`; 1.21.11 `LivingEntity.java:2610–2612, 2649–2650`). The virtual dispatch reaches `LocalPlayer.aiStep()`, which calls `super.aiStep()` (1.21.10 `LocalPlayer.java:687, 834`; 1.21.11 `LocalPlayer.java:728, 875`), then `Player.aiStep()` delegates to `LivingEntity.aiStep()` (1.21.10 `Player.java:455–466`; 1.21.11 `Player.java:452–463`).
3. In `LivingEntity.aiStep()`, after jump handling, both versions construct the travel input from `xxa`, `yya`, and `zza`; the controlling-passenger path is bypassed for this witness and the `canSimulateMovement() && isEffectiveAi()` path calls `travel(input)` (1.21.10 `LivingEntity.java:2827–2848`; 1.21.11 `LivingEntity.java:2953–2974`). Both authority predicates return `isLocalInstanceAuthoritative()` (1.21.10 `Entity.java:3420–3425`; 1.21.11 `Entity.java:3438–3443`).
4. `Player.travel()` delegates to `super.travel(input)` with passenger, swimming, and flying gates false (1.21.10 `Player.java:1295–1318`; 1.21.11 `Player.java:1360–1383`). `LivingEntity.travel()` selects `travelInAir(input)` when fluid travel and fall-flying are false (1.21.10 `LivingEntity.java:2275–2283`; 1.21.11 `LivingEntity.java:2309–2317`). Both air branches reach the same first movement-calculation call after block-below selection, on-ground friction selection, and `friction * 0.91F` (1.21.10 `LivingEntity.java:2306–2310`; 1.21.11 `LivingEntity.java:2343–2347`). The 1.21.11 extraction of the fluid condition into `shouldTravelInFluid` does not alter this witness's false fluid result.

## Separate positioning observation

1.21.11 adds `computeSpeed()` at the start of `Entity.baseTick()` before clearing `inBlockState` (`Entity.java:487–488`). The helper stores `position() - lastKnownPosition` and updates `lastKnownPosition` (`Entity.java:540–547`). This is a new positional-delta observation in the tick sequence. It does not change the accepted travel-dispatch order or input in this bounded witness; no broader consequence is accepted here.

## Excluded disposition and limits

The author draft's adjacent client-load gate comparison is **excluded from this review** as instructed. The accepted witness assumes the loaded condition passes; no verdict is given on load timeout or admission behavior.

The author draft's claim that the entire later dry-air arithmetic is equivalent extends beyond the frozen audit boundary and is not accepted here. No runtime trajectory or parity claim is made. Other movement branches and downstream dependencies remain outside this verdict.
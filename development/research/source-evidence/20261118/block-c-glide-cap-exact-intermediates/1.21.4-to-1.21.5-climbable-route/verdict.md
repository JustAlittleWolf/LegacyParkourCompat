# Independent exact-source audit: 1.21.4 → 1.21.5 glide at a climbable

## Verdict

**Accepted for the stated bounded claim, with a source-manifest verification caveat.** The ordered client path and the reported new 1.21.5 diversion are confirmed by the local Mojmap sources. No behavioral correction is needed. This verdict is source evidence only; it does not establish an intermediate patch boundary or runtime trajectory parity.

## Immutable input and source identity

- Input `report.md` SHA-256: `611228b23786fdbea4a9a656eb0ed0a9fbfa3447fc7d79fd1d9e554fa9c5e9c9`
- Exact releases/mapping: `1.21.4` Mojmap → `1.21.5` Mojmap. Each local `mojmap.ready.json` identifies the matching exact `versionId`, `mapping: mojmap`, and `status: ready`; paired `mojmap.provenance.json` identifies the matching exact `versionId` and `namespace: mojmap`.
- Independently recomputed SHA-256 values for the six cited source files match the input report:
  - 1.21.4 `LocalPlayer.java` `145686ebdc7f0d12a64070309073665eb8695b7e09911a723e86113a77d04611`; `LivingEntity.java` `e62ce650af0a5ac97e4d0a2ba7cad4ba68a8d098525609f8b5b6976a8ab0ae30`; `Player.java` `c45f41b9784ce50a88a498e84a13edcef0a23e175233d726ed7f90940f19ebc1`.
  - 1.21.5 `LocalPlayer.java` `f1fcfed4a938732361e7ad951f93e9b73b02320ee56f0b219e2b3c7acdbfa2ef`; `LivingEntity.java` `a8aed863d4fdc515c751dd2878a8bb9c13179228cb8dbb50edf1d19cd5404271`; `Player.java` `8fc187f33999db9dfc49251e95e92a17645a50adab16ca0f93f4948209f62036`.
- Both ready markers repeat the source-manifest SHA-256 values reported in the input (`f90b61197928632e061ea877955a19055c92ae6f357c2daf1bc646172c6f51f0` and `365cc2d22446ceba0e36f46aa1c95ce4cc31514dbd657505680fb660f3adefd9`). However, `mojmap.sources.sha256` for these two versions is absent from the local canonical trees and could not be reopened; the six cited source-file hashes and ready/provenance metadata were independently verified, but the full manifest digest claim itself was not.

## Audited order and behavior

- Startup is excluded by the active-glide premise. Local input has the conditional start request at 1.21.4 `LocalPlayer.java:749–753` and 1.21.5 `:770–774`; `Player.tryToStartFallFlying()` requires `!isFallFlying() && canGlide() && !isInWater()` at 1.21.4 `Player.java:1528–1534` and 1.21.5 `:1507–1513`.
- Before travel, 1.21.4 `LivingEntity.aiStep` zeroes x/y/z independently under strict `abs(component) < 0.003` at `:2708–2724`. In 1.21.5 player x/z are instead jointly zeroed for strict `horizontalDistanceSqr() < 9.0E-6`; y retains the strict per-axis cutoff at `:2685–2709`. This happens before `applyInput()` (`:2712`), `updateFallFlying()` (`:2753–2755`) and travel (`:2763–2767`).
- The input dampening/order changed but the claimed updater placement is correct: 1.21.4 damps xxa/zza and calls `updateFallFlying()` before constructing travel input at `LivingEntity.java:2766–2783`; 1.21.5 calls `applyInput()` at `:2712` (damping body `:2806–2809`), then calls `updateFallFlying()` before travel at `:2753–2767`. `updateFallFlying()` runs `checkSlowFallDistance()` on both releases; its `canGlide()` flag-clearing and wear/event logic are server-gated (`1.21.4 :2825–2844`, `1.21.5 :2815–2834`), so this client path preserves active flag 7 until travel.
- Travel is fluid-first. In 1.21.4, local-instance gating encloses the fluid → fall-flying → air route (`LivingEntity.java:2189–2200`); 1.21.5 uses fluid → fall-flying → air without that enclosing gate (`:2213–2222`). Dry, locally controlled input therefore reaches fall-flying travel on both sides.
- The new 1.21.5 route is exactly inside `travelFallFlying(Vec3)`: `onClimbable()` calls `travelInAir($$0)`, then `stopFallFlying()` (`LivingEntity.java:2301–2305`). This call order means the active flag survives the earlier updater, ordinary-air calculation and its first `move` happen first, and flag 7 is then cleared. The first move is reached inside `travelInAir` via `handleRelativeFrictionAndCalculateMovement` (`:2224–2247`, `:2394–2403`; `move(MoverType.SELF, ...)` at `:2395–2398`). In 1.21.4 `travelFallFlying()` has no climbable check: it applies `updateFallFlyingMovement` and moves (`:2279–2288`).
- `onClimbable()` rejects spectators, samples `blockPosition()` and `getInBlockState()`, accepts `BlockTags.CLIMBABLE` or a usable open ladder trapdoor (`1.21.4 :1575–1590`; 1.21.5 `:1600–1616`). `stopFallFlying()` writes shared flag 7 true then false (`1.21.4 Player.java:1541–1544`; 1.21.5 LivingEntity.java:2317–2320`). These support the bounded predicate and stop-method statements.

## Boundaries retained

The verdict applies only to a dry, locally controlled player already fall-flying with the stated default chest Elytra and with `onClimbable()` true. The report appropriately excludes startup/equipment variants, fluid precedence, exact state/contact geometry, remote/server behavior, runtime outcomes and the unknown intermediate introduction point. No pair-wide coverage or inventory closure is implied.



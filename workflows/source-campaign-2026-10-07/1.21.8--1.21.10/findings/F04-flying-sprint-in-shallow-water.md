# F04: Flying bypasses the shallow-water sprint restriction

- Older version A: `1.21.8`
- Newer version B: `1.21.10`
- Mechanic / coverage slice IDs: S1.3, S3.4
- Classification: changed behavior
- Confidence: source-confirmed
- Applicability: local player with flying ability in water but not underwater; other sprint gates must pass
- First changed release: unknown within (`1.21.8`, `1.21.10`]
- Runtime validation: not performed

## Paired evidence

All paths are relative to the matching `ready/<version>/mojmap` root in the run artifact manifest.

- A: `net/minecraft/client/player/LocalPlayer.java`, `canStartSprinting()` lines 1065–1075; SHA-256 `53F2A71A886B9C71853AFE36F2DF857F80A9BF1CD6EC4CA8604CCD7E238D89EE`. Its final gate requires `!this.isInWater() || this.isUnderWater()`; it also requires the input, food, use, blindness, flight and vehicle conditions shown in the method.
- B: same class, `isSprintingPossible(boolean)` lines 1055–1060 and `canStartSprinting()` lines 1062–1069; SHA-256 `9C1DF00E2F8379B2C19577A3691FEE28071D8925210BE3D3DF928B5352E367E1`. The helper ends with `($$0 || !this.isInShallowWater())`; the caller supplies `this.getAbilities().flying`. `Player.isMobilityRestricted()` lines 1857–1859 resolves that separate gate to blindness (`net/minecraft/world/entity/player/Player.java`, SHA-256 `AF857617B66A5776E63830771360B96F75E21D47D20DB08F164A2DBEEE801D82`).
+- A: `net/minecraft/world/entity/Entity.java`, `isUnderWater()` lines 1394–1396; SHA-256 `C403E6176D27B5BFD6AAA0DEA3735FFA4FCF80DBAE58766661DD84453B7E9704`; it requires the eye-water flag and `isInWater()`.
+- B: same class, `isInShallowWater()` lines 1454–1456; SHA-256 `8361DBB86FE6C975D21F69D008377B6F191669BE751150C842517E9D0346FA18`; it is `isInWater() && !isUnderWater()`.
+- A: `net/minecraft/client/player/LocalPlayer.java`, `aiStep()` lines 722–734 calls `canStartSprinting()` before either double-tap or sprint-key `setSprinting(true)`; hash as cited above. B: same class/member lines 726–736; same hash as above.
+- A: `net/minecraft/world/entity/LivingEntity.java`, `travelInFluid(Vec3)` lines 2301–2330; SHA-256 `609F0197A0B4551AB42279E452C11CDD256135B1D467C2A95950B0E9FD7DDEF8`. The water branch selects `0.9F` when sprinting and `getWaterSlowDown()` otherwise, then applies later efficiency/effect adjustments.
+- B: same class/member, lines 2331–2360; SHA-256 `B8B49D60769203F7BD5AFE4A1BFFCDCDBEC30BE28960324CDC43A2DF85A6EB66`; corresponding sprint-dependent coefficient selection.

## Source-level difference

For an otherwise sprint-eligible local player in shallow water, A's `canStartSprinting()` rejects the state because the player is in water but not underwater. B passes the flying ability to `isSprintingPossible`; when flying is true, its final shallow-water clause is true. The other common requirements, including forward input, no active item use, no blindness, and any vehicle gate, still apply. The two paths can therefore differ in whether the local player enters sprint state under this precondition.

The inherited water-travel path then reads `isSprinting()` to select its water drag coefficient. It uses `0.9F` for sprinting and the water-slowdown value otherwise, before applying later movement-efficiency and Dolphin's Grace adjustments.

## Reachability and dependencies

Local-player forward/sprint input -> `LocalPlayer.aiStep` -> `canStartSprinting` -> A shallow-water rejection or B flying exception -> local sprint flag -> `LivingEntity.travel` water branch -> sprint-dependent delta-movement drag. The source proof is client-player reachable. It depends on vanilla water/eye-water and ability state; no external resource is needed. Food producer logic remains outside scope; only the gate's direct read is relevant.

## Consequence and uncertainty

Source-proven: under the stated preconditions and with other sprint gates satisfied, A rejects the shallow-water sprint transition while B can set sprint state; the water travel branch consumes that state to choose a different coefficient.

Predicted only: player velocity and position can diverge when that branch changes. The exact final coefficient can be affected by water movement efficiency and Dolphin's Grace; no trajectory or runtime reproduction was measured. The first release containing the flying exception is unknown within the pair because intermediate releases were not audited.

## Handoff

Independent delta: B's flying ability bypasses the shallow-water sprint gate, allowing a sprint-state transition rejected by A and exposing the sprint-dependent water-drag path. Applicability requires shallow water, flying ability and all remaining sprint gates. Related findings: F03 concerns the separate double-tap timer. Release boundary unknown within (`1.21.8`, `1.21.10`]. Implementation and runtime decisions are deferred.

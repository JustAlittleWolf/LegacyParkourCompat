# F04: Flying bypasses the shallow-water sprint restriction

1.21.8, mojmap: `1.21.8/mojmap/net/minecraft/world/entity/Entity.java`, isUnderWater 1394-1396.
1.21.8, mojmap: `1.21.8/mojmap/net/minecraft/world/entity/LivingEntity.java`, travelInFluid 2301-2330.
1.21.8, mojmap: `1.21.8/mojmap/net/minecraft/client/player/LocalPlayer.java`, canStartSprinting 1065-1075; aiStep 722-734.
1.21.10, mojmap: `1.21.10/mojmap/net/minecraft/world/entity/player/Player.java`, isMobilityRestricted 1857-1859.

Difference: For an otherwise sprint-eligible local player in shallow water, 1.21.8's `canStartSprinting()` rejects the state because the player is in water but not underwater. 1.21.10 passes the flying ability to `isSprintingPossible`; when flying is true, its final shallow-water clause is true. The other common requirements, including forward input, no active item use, no blindness, and any vehicle gate, still apply. The two paths can therefore differ in whether the local player enters sprint state under this precondition. The inherited water-travel path then reads `isSprinting()` to select its water drag coefficient. It uses `0.9F` for sprinting and the water-slowdown value otherwise, before applying later movement-efficiency and Dolphin's Grace adjustments.

Movement meaning: Local-player forward/sprint input -> `LocalPlayer.aiStep` -> `canStartSprinting` -> 1.21.8 shallow-water rejection or 1.21.10 flying exception -> local sprint flag -> `LivingEntity.travel` water branch -> sprint-dependent delta-movement drag. The source proof is client-player reachable. It depends on vanilla water/eye-water and ability state; no external resource is needed. Food producer logic remains outside scope; only the gate's direct read is relevant. Source-proven: under the stated preconditions and with other sprint gates satisfied, 1.21.8 rejects the shallow-water sprint transition while 1.21.10 can set sprint state; the water travel branch consumes that state to choose a different coefficient. Predicted only: player velocity and position can diverge when that branch changes.

Original evidence: [archived report](../../archive/legacy-workflows/source-campaign-2026-10-07/1.21.8--1.21.10/findings/F04-flying-sprint-in-shallow-water.md).

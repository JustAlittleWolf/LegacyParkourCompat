# F-SPECTATOR-CRAWL-SCALING: spectator crawl transition gets a one-tick input slowdown

1.14.4, mojmap: `1.14.4/mojmap/net/minecraft/client/player/KeyboardInput.java`, `tick`, lines 13–25.
1.15.2, mojmap: `1.15.2/mojmap/net/minecraft/client/player/KeyboardInput.java`, `tick`, lines 13–26.

Difference: Reachable precondition: the local player is the controlled camera, has a dry `Pose.SWIMMING` carried from a tight passage where swimming clearance fits but crouching does not, and the client now reports spectator mode before the next player tick. `Player#tick()` sets spectator state before `super.tick()` but does not recalculate pose until after it, so `LocalPlayer#aiStep()` samples that carried crawl pose. 1.14.4 passes the slow flag as true but also passes `isSpectator() == true`; `KeyboardInput` therefore leaves the horizontal impulses at full scale. 1.15.2's `isMovingSlowly()` is true from the carried crawl pose, and its `KeyboardInput` applies the 0.3 multiplier without a spectator exception. At the end of the same tick 1.15.2 recalculates the pose to standing because `Player#isSwimming()` is false for spectators. This is a one-tick input delta at the transition; no later crawl slowdown is claimed.

Movement meaning: `LocalPlayer#isEffectiveAi()` returns true in both versions.

Original evidence: [archived report](../../archive/legacy-workflows/source-campaign-2026-10-07/1.14.4--1.15.2/findings/F-SPECTATOR-CRAWL-SCALING.md).

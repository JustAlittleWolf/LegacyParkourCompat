# F-SPECTATOR-CRAWL-SCALING: spectator crawl transition gets a one-tick input slowdown

- **1.14.4**, mojmap: `net/minecraft/client/player/LocalPlayer.java`; lines 625-630.
- **1.15.2**, mojmap: `LocalPlayer#aiStep()`; lines 632-636.

## Difference

Reachable precondition: the local player is the controlled camera, has a dry `Pose.SWIMMING` carried from a tight passage where swimming clearance fits but crouching does not, and the client now reports spectator mode before the next player tick. `Player#tick()` sets spectator state before `super.tick()` but does not recalculate pose until after it, so `LocalPlayer#aiStep()` samples that carried crawl pose.

1.14.4 passes the slow flag as true but also passes `isSpectator() == true`; `KeyboardInput` therefore leaves the horizontal impulses at full scale. 1.15.2's `isMovingSlowly()` is true from the carried crawl pose, and its `KeyboardInput` applies the 0.3 multiplier without a spectator exception.

At the end of the same tick 1.15.2 recalculates the pose to standing because `Player#isSwimming()` is false for spectators.

This is a one-tick input delta at the transition; no later crawl slowdown is claimed.

## Movement meaning

`LocalPlayer#isEffectiveAi()` returns true in both versions.

During `LivingEntity.aiStep()`, that reaches `serverAiStep()`; the local-player override copies `input.leftImpulse` and `input.forwardImpulse` to `xxa` and `zza` when it is the controlled camera (1.14.4 lines 600-605, 1.15.2 lines 607-612). `LivingEntity.aiStep()` then passes `(xxa, yya, zza)` to `travel()` (1.14.4 line 2223; 1.15.2 line 2287).

Spectator flying calls the player flying travel path, which delegates to `LivingEntity.travel()` with that vector.

The 0.3 scale therefore changes the client movement input used for this tick; no measured trajectory is claimed.

1.14.4 separate `isVisuallySneaking()` versus `isCrouching()` difference concerns automatic slowdown while sleeping.

The sleeping state also makes `Player#isImmobile()` true; `LivingEntity.aiStep()` zeros `xxa` and `zza` before travel in both releases.

That predicate difference has no player-movement consequence and is excluded from this finding.

The source confirms a single-tick horizontal input scale change under the stated cached-pose and game-mode transition preconditions.

It does not establish a measured position or velocity outcome.

[Archived report](../../archive/legacy-workflows/source-campaign-2026-10-07/1.14.4--1.15.2/findings/F-SPECTATOR-CRAWL-SCALING.md).

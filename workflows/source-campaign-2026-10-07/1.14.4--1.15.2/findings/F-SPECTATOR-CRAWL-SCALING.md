# F-SPECTATOR-CRAWL-SCALING: spectator crawl transition gets a one-tick input slowdown

- Older version A: Minecraft 1.14.4
- Newer version B: Minecraft 1.15.2
- Mechanic / coverage slice IDs: INV-TICK, INV-STATE; S-INPUT-KEYS
- Classification: changed behavior
- Confidence: source-confirmed
- Applicability: historical local-player movement during a game-mode transition
- First changed release: within (1.14.4, 1.15.2]
- Boundary resolution: the endpoint delta is confirmed; the earliest changed release inside the interval has not been established, and no intermediate-release source check is claimed.
- Runtime validation: not performed

## Paired evidence

- A `net/minecraft/client/player/LocalPlayer.java::LocalPlayer#aiStep()`, lines 625-630 computes `isVisuallySneaking() || isVisuallyCrawling()` and calls `input.tick(bl4, this.isSpectator())`; SHA-256 `0795c1223198ce5acf5d2ed9e5db8435bbec4cd52b96f96b1ddf2865baaae85f`.
- B `LocalPlayer#aiStep()`, lines 632-636 calls `input.tick(this.isMovingSlowly())`; `isMovingSlowly()` lines 602-604 combines `isCrouching()` and `isVisuallyCrawling()`; SHA-256 `3a9019bd7b860e251c23fd8d0cd70b7f5b38566d34470c4e29b1014ef689ccbd`.
- A `net/minecraft/client/player/KeyboardInput.java::KeyboardInput#tick(boolean,boolean)`, lines 13-25 multiplies horizontal input by 0.3 only when the spectator argument is false and either sneak is held or the caller marks slow movement; SHA-256 `33daa0833a95e09e728c1b8f509020dd13b70e38aba922e2b1e10ec5c9b7739a`.
- B `KeyboardInput#tick(boolean)`, lines 13-25 multiplies by the same 0.3 whenever the caller marks slow movement, without a spectator guard; SHA-256 `746ea654cf4f46a5f4b94a237c4307652252a44807a488b606dc993e088396f7`.
- A and B `Entity#isVisuallyCrawling()`, A lines 1840-1842/B lines 1823-1825, both test the current pose for swimming and that the player is not in water. Hashes: A `31c6be42d165102d3e6dc4295dfef6e8971fc3aea13f938a5b3a33b91d524a7`; B `191b3ad3e7348c9bac1e703fff896706d23a751bf15162aacf676f5f97c0a10e`.
- A and B `Player#tick()` call `super.tick()` before `updatePlayerPose()` at the end (A lines 209-282; B lines 210-283). Thus a pose carried into the tick remains visible to `LocalPlayer#aiStep()` before pose recalculation. Hashes: A `Player.java` `e9ce5eb18c5581ffe2b610b273ffd85786587a00ba853e5dba0ac96593622b12`; B `Player.java` `1ba2724c22163862b8f7fdfdea5a04a6e4db26a119d7e5360ba024724a34793`.
- Both `Player#updatePlayerPose()` methods choose `Pose.SWIMMING` as the fallback when swimming clearance fits but standing and crouching/sneaking clearance do not (A lines 344-370; B lines 357-383). A and B `Entity#canEnterPose(Pose)` use `level.noCollision` on the pose bounding box (A line 1641; B line 1605). The dimensions are 0.6 x 0.6 for swimming and 0.6 x 1.5 for sneaking/crouching, with 0.6 x 1.8 standing (A `Player.java` lines 101-118; B lines 102-119).
- A and B `Player#isSwimming()` both return false for spectators (A lines 1761-1764; B lines 1840-1843). The crawl predicate is explicitly visual: `Entity#isVisuallySwimming()` compares the current pose to `Pose.SWIMMING`, then `isVisuallyCrawling()` checks that the player is out of water. It therefore remains true for the stale dry crawl pose during the input sample in both versions.

## Source-level difference

Reachable precondition: the local player is the controlled camera, has a dry `Pose.SWIMMING` carried from a tight passage where swimming clearance fits but crouching does not, and the client now reports spectator mode before the next player tick. `Player#tick()` sets spectator state before `super.tick()` but does not recalculate pose until after it, so `LocalPlayer#aiStep()` samples that carried crawl pose.

A passes the slow flag as true but also passes `isSpectator() == true`; `KeyboardInput` therefore leaves the horizontal impulses at full scale. B's `isMovingSlowly()` is true from the carried crawl pose, and its `KeyboardInput` applies the 0.3 multiplier without a spectator exception. At the end of the same tick B recalculates the pose to standing because `Player#isSwimming()` is false for spectators. This is a one-tick input delta at the transition; no later crawl slowdown is claimed.

## Reachability and dependencies

`LocalPlayer#isEffectiveAi()` returns true in both versions. During `LivingEntity.aiStep()`, that reaches `serverAiStep()`; the local-player override copies `input.leftImpulse` and `input.forwardImpulse` to `xxa` and `zza` when it is the controlled camera (A lines 600-605, B lines 607-612). `LivingEntity.aiStep()` then passes `(xxa, yya, zza)` to `travel()` (A line 2223; B line 2287). Spectator flying calls the player flying travel path, which delegates to `LivingEntity.travel()` with that vector. The 0.3 scale therefore changes the client movement input used for this tick; no measured trajectory is claimed.

A separate `isVisuallySneaking()` versus `isCrouching()` difference concerns automatic slowdown while sleeping. The sleeping state also makes `Player#isImmobile()` true; `LivingEntity.aiStep()` zeros `xxa` and `zza` before travel in both releases. That predicate difference has no player-movement consequence and is excluded from this finding.

## Consequence and uncertainty

The source confirms a single-tick horizontal input scale change under the stated cached-pose and game-mode transition preconditions. It does not establish a measured position or velocity outcome. The first release that introduced the missing spectator guard is unknown within the endpoint interval.

## Handoff

Emulate the 1.14.4 spectator bypass only when the selected historical version requires it. This finding is bounded to the transition tick with a dry crawl pose and the local player as camera; broader input, travel, and state inventories remain open in the pair ledger.
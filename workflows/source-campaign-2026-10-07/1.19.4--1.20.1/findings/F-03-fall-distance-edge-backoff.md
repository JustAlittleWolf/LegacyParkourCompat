
# F-03: Slow Falling or Levitation resets fall distance before the sneaking edge-backoff gate

- Older version A: Minecraft 1.19.4
- Newer version B: Minecraft 1.20.1
- Mechanic / coverage slice IDs: sneaking edge-backoff and fall-distance state; S3-02, S3-03, S4-02
- Classification: changed behavior
- Confidence: source-confirmed
- Applicability: historical player behavior
- First changed release: unknown within (1.19.4, 1.20.1]
- Runtime validation: not performed

## Paired evidence

- A: ../../../build/movement-campaign-2026-10-07/ready/1.19.4/mojmap/net/minecraft/world/entity/LivingEntity.java; travel lines 1983-1991 resets fallDistance only inside the downward-velocity + Slow Falling guard; aiStep travel dispatch lines 2540-2553; SHA-256 c8d91af61f87aaa1666de79696d7cd9d4a8212d05f873bdaf28d7bb2926bf165.
- B: ../../../build/movement-campaign-2026-10-07/ready/1.20.1/mojmap/net/minecraft/world/entity/LivingEntity.java; aiStep lines 2562-2567 resets for Slow Falling or Levitation before travel; travel lines 2004-2010; SHA-256 decf8cd70d194098ad51d0c82e5a0087e0687881e0a2dc66456f2ba6ea76676f.
- A player consumer: Player#maybeBackOffFromEdge lines 1040-1093, #isAboveGround lines 1095-1099 and #isStayingOnGroundSurface lines 308-310; SHA-256 5e4436afccb361156f8184e7a5cfd91d5b12dcfe8f5937b4ac23dd3edda737a2.
- B player consumer: Player#maybeBackOffFromEdge lines 1043-1094, #isAboveGround lines 1097-1101 and #isStayingOnGroundSurface lines 309-311; SHA-256 873d82c6f5471d06812929e471d970fd973bfbe21638e4e348a6fcde45637dac.
- Entity.move reaches the virtual maybeBackOffFromEdge call before collision resolution: A lines 568-569; B lines 612-613; Entity hashes in S4-01/S4-02.

## Source-level difference

A resets fall distance during LivingEntity travel only when current Y velocity is non-positive and Slow Falling is present. B resets it in LivingEntity.aiStep before travel whenever Slow Falling or Levitation is active. Thus with Levitation, and with rising movement under Slow Falling, B clears the prior fall-distance value before player movement while A can retain it.

## Reachability and dependencies

For a local player, sneaking input makes Player#isStayingOnGroundSurface true. Entity.move calls the Player override of maybeBackOffFromEdge before clipping for SELF or PLAYER movement. That method requires non-flying state and requested Y <= 0, then checks isAboveGround. If not onGround, isAboveGround compares fallDistance with maxUpStep and performs a no-collision query using a vertical offset derived from fallDistance. Clearing fallDistance can change whether backoff runs and the horizontal 0.05-step probe. Source paths are confirmed; terrain-dependent outcomes remain open under S4/S5.

## Consequence and uncertainty

The changed writer-to-reader path can affect horizontal movement near an edge for the stated guards. This is not a claim that every Levitation or Slow Falling tick changes position; the result depends on prior fall distance and collision geometry. Fall-damage consumers are outside movement scope.

## Handoff

Preserve version-specific reset timing before the player edge-backoff query. F-02 concerns the separate support-position lookup. First changed release inside this interval is unknown.

# F-06: Fall-flying uses ordinary air travel when the player is on a climbable

- Older version A: 1.21.4
- Newer version B: 1.21.5
- Mechanic / coverage slice IDs: S3-GLIDE, S3-CLIMB, S3-DISPATCH
- Classification: changed behavior
- Confidence: source-confirmed
- Applicability: historical player behavior
- First changed release: unknown within (1.21.4, 1.21.5]
- Runtime validation: not performed

## Paired evidence

- A: `ready/1.21.4/mojmap.sources.sha256`; `net/minecraft/world/entity/LivingEntity.java`; `LivingEntity#travel(Vec3)` lines 2189-2199 dispatches to `travelFallFlying()` whenever fall flying is active after the fluid branch. `travelFallFlying()` lines 2279-2288 always applies `updateFallFlyingMovement`, then moves. Source SHA-256: `e62ce650af0a5ac97e4d0a2ba7cad4ba68a8d098525609f8b5b6976a8ab0ae30`.
- B: `ready/1.21.5/mojmap.sources.sha256`; same class; `LivingEntity#travel(Vec3)` lines 2213-2222 preserves the fluid-first then fall-flight dispatch. `travelFallFlying(Vec3)` lines 2301-2315 checks `onClimbable()`, and on true calls `travelInAir(input)` followed by `stopFallFlying()`; otherwise it runs the glide update and move. `stopFallFlying()` lines 2317-2320 toggles shared flag 7 on then off. Source SHA-256: `a8aed863d4fdc515c751dd2878a8bbc13179228cb8dbb50edf1d19cd5404271`.
- A and B `LivingEntity#onClimbable()` have the same predicate: non-spectator and the in-block state is in `BlockTags.CLIMBABLE`, or is a usable trapdoor ladder (A lines 1575-1589; B lines 1600-1615). The method and travel source are in the cited LivingEntity.java hashes.

## Source-level difference

When a non-fluid travel tick enters the fall-flying branch while `onClimbable()` is true, A still applies the glide velocity math and moves with the resulting velocity. B instead runs regular `travelInAir(input)` and calls `stopFallFlying`. For the same input and state, B therefore selects ground/air friction and gravity travel in place of gliding and clears the fall-flying flag through the helper.

## Reachability and dependencies

LocalPlayer jump input can request fall flying through `tryToStartFallFlying`; the resulting living-player state reaches `LivingEntity.aiStep` and `travel(Vec3)`. The branch is reachable when fall flying is active, travel is not taking the water/lava branch, and the in-block state satisfies the unchanged climbable predicate. `travelInAir` consumes movement input, block friction, gravity and applicable effects; these dependencies remain in the open travel slices.

## Consequence and uncertainty

The source proves different travel branch selection and the fall-flying stop call for climbable contact. B's resulting position and velocity depend on ordinary air-travel inputs, friction, gravity and collision. No trajectory was measured. The precise observable meaning of the transient flag toggle is not inferred beyond the method calls.

## Handoff

Independent delta: fall-flying travel on climbable contact. Related finding IDs: F-01. Applicability requires active fall flying, a non-fluid travel dispatch and climbable contact. Exact first release within the pair is unknown.

# Correction supplement: outer swimming-pose fit gate

This supplement corrects and narrows the provisional pose candidates without modifying the immutable source review or finding drafts.

## Audited immutable revisions

- `source-review.md` SHA-256: `f9daef67079078add9ad621c6ef279e8275ae687b00a11184873fc6e00c1d128`.
- `player-sneaking-pose-height.draft.md` SHA-256: `2774b0e7e45d042cc2d2a236df179807325c40bba0fb13082b580b14ecf43d16`.
- `constrained-player-pose-fallback.draft.md` SHA-256: `9e857b97768b5dba8b03851f878d8e0f4c99ad05761e390eeba88345ed90eaf0`.

## Exact 1.14.4 gate and caller

`PlayerEntity.updatePlayerPose()` is called from `PlayerEntity.tick()` at line 281, after the superclass tick. The entire pose-selection and pose-application sequence at `PlayerEntity.java:343–370` is inside the outer `if (this.m_87715359(Pose.SWIMMING))` at line 344. Therefore every 1.14.4 pose update described by either candidate first requires the current-position `Pose.SWIMMING` box to pass the collision query. If it fails, neither the desired sneaking dimensions nor the standing/sneaking/swimming fallback logic runs on that tick.

`m_87715359(Pose)` is `world.hasNoCollisions(this, this.m_16672198(pose))` (`Entity.java:1628–1629`); `m_16672198` constructs the box from the requested pose dimensions at the entity's current position (`Entity.java:2300–2305`). Pose dimensions are applied after a pose data change by `m_84943753()` (`Entity.java:2245–2266`). The collision/query implementation and concrete shape witness remain outside this lane.

The 1.13.2 caller likewise invokes `updatePlayerPose()` at the end of `PlayerEntity.tick()` (`:254`), but its method computes a target size and tests that target directly (`PlayerEntity.java:334–360`); there is no preceding swimming-pose fit gate.

## Effect on provisional candidates

- **Sneaking height 1.65F → 1.5F:** the numeric dimensions difference remains source-confirmed, but 1.14.4 applies its selected pose only on a tick where the swimming-pose box fits. The headroom-band movement claim is conditional on that outer gate and the target pose/query admitting the new pose. Keep the candidate provisional pending collision/query closure.
- **Constrained-pose fallback to 0.6F × 0.6F:** the existing draft already describes the outer gate. The fallback is reachable only after the swimming-pose box passes, then the desired and sneaking pose fits fail in the stated order. Keep this candidate provisional pending collision/query closure and a concrete blocked-player witness.

No earlier report or draft was edited; this supplement binds the correction to those immutable revisions.

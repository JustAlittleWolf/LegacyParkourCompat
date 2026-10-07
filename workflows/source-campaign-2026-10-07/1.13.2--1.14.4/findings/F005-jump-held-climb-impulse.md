# F005: Jump-held climb impulse

- Older version A: 1.13.2
- Newer version B: 1.14.4
- Mechanic / coverage slice IDs: ordinary travel climb response; S005
- Classification: changed behavior
- Confidence: source-confirmed
- Applicability: historical player behavior
- First changed release: unknown within (1.13.2, 1.14.4]
- Runtime validation: not performed

## Paired evidence

- A manifest: `../../../build/movement-campaign-2026-10-07/ready/1.13.2/ornithe-feather/`; `LivingEntity.java`::`moveRelative(float,float,float)`, lines 1575-1578, SHA-256 `bb691358c9a43c9f46e85575bf4d0a4ad671d0eb912502acc3a6a3f625e42f1c`.
- B manifest: `../../../build/movement-campaign-2026-10-07/ready/1.14.4/ornithe-feather/`; `LivingEntity.java`::`moveRelative(Vec3d)`, lines 1846-1850, SHA-256 `2cccf4331ce9e62013eeb8e96163e5e146e1291619eb998ce9867daa87c02b61`.

## Source-level difference

After moving, A sets vertical velocity to 0.2 only if `collidingHorizontally && isClimbing()`. B sets it if `(collidingHorizontally || jumping) && isClimbing()`. Thus holding jump while climbing can trigger the B assignment even without horizontal collision.

## Reachability and dependencies

The ordinary travel branch follows non-water/non-lava and non-fall-flying dispatch. `jumping` and `isClimbing()` are player/entity state inputs; exact climbing block registrations and their historical applicability remain to be inventoried.

## Consequence and uncertainty

Source proves a broader B condition. Expected consequence is an upward velocity assignment when the player is jumping on a climbable surface without horizontal collision; no trajectory is measured. Whether a given block enters `isClimbing()` is an open dependency.

## Handoff

Independent delta; first changed release unknown. Implementation/testing deferred.

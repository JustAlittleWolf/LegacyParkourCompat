# F-02: Piston displacement is capped per world-time axis

- **1.10.2**, ornithe-feather: `LocalClientPlayerEntity.java`; line 75.
- **1.11.2**, ornithe-feather: `LocalClientPlayerEntity.java`; line 77.

## Difference

1.11.2 adds a PISTON-specific cumulative requested-movement limiter keyed by world time and entity. 1.10.2 has no mover-type branch or corresponding accumulator in `Entity.move`.

For a non-noClip entity, 1.11.2 resets the accumulator on a changed world time, then clamps each nonzero axis's accumulated request to `[-0.51,0.51]`, forwards only the difference from the previous accumulator, and returns before ordinary collision resolution when the forwarded magnitude is `<= 1.0E-5F`.

The accumulator is updated before the early return. 1.10.2 forwards each piston caller request to ordinary collision resolution without this limiter.

Because the cap runs before collision handling, this finding is about requested movement input, not a cap on realized position change.

## Movement meaning

The paired moving-piston block entity selects entities intersecting its movement area, skips only `PistonMoveBehavior.IGNORE`, and calls `Entity.move` with the piston direction delta. `PlayerEntity` inherits `Entity#getPistonMoveBehavior() == NORMAL` on both sides; its tick sets `noClip` to the spectator flag.

The bounded precondition is a local non-spectator player present in that entity list and receiving same-axis piston requests during one `World.getTime()` value; 1.11.2 routes each request through the PISTON branch.

The cap itself precedes collision resolution, so the exact collision shape does not determine whether the request is capped once the caller has supplied it.

Source proves a new per-world-time cumulative cap on piston-driven requested movement for the bounded player path.

[Archived report](../../archive/legacy-workflows/source-campaign-2026-10-07/1.10.2--1.11.2/findings/F-02-piston-movement-cap.md).

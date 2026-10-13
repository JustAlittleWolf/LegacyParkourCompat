# F008: Resolved displacement cutoff

- **1.13.2**, ornithe-feather: `Entity.java`; lines 578-599.
- **1.14.4**, ornithe-feather: `Entity.java`; lines 463-468.

## Difference

After sneak-edge preprocessing, 1.14.4 calls the collision solver but sets the shape and derives position only when the resolved vector squared length is greater than `1.0E-7`. 1.13.2 enters axis resolution for any nonzero requested component and later updates position from shape without this squared-length cutoff.

## Movement meaning

All non-noClip Entity.move callers can reach this behavior; piston preprocessing and noClip have separate guards.

The exact vector is produced by LivingEntity travel or external displacement.

Very small resolved movement can update position/shape in 1.13.2 but be skipped in 1.14.4.

The source threshold is proven; practical reachable magnitudes and downstream callback consequences have not been tested.

[Archived report](../../archive/legacy-workflows/source-campaign-2026-10-07/1.13.2--1.14.4/findings/F008-movement-zero-displacement-cutoff.md).

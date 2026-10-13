# F05: Honey Block contact limits descent velocity and clears fall distance

- **1.14.4**, mojmap: [source locations in preserved report](../../archive/legacy-workflows/movement-discovery/runs/1.14.4--1.15.2/findings/F05-honey-slide.md).
- **1.15.2**, mojmap: `HoneyBlock.java`; lines 22–36.

## Difference

When a player is falling beside the Honey Block and satisfies its explicit height, velocity and edge predicates, 1.15.2 replaces vertical velocity with `-0.05`.

For descent faster than `-0.13`, 1.15.2 also scales horizontal velocity by `-0.05 / velocityY`; it resets fall distance. 1.14.4 has no Honey Block or corresponding callback.

## Movement meaning

Local player travel -> `Entity.move` -> bounding-box block-cell scan -> `BlockState.entityInside` -> `HoneyBlock.entityInside` -> slide predicates and delta-velocity writes.

The block shape and registration are present only in 1.15.2.

The method applies to `Entity`; Honey Block's player movement effect is established by the player reaching the shared movement/collision path.

Source proves the guarded velocity and fall-distance writes.

It does not establish an observed trajectory, and the feature is modern-only because 1.14.4 maps cannot contain Honey Block.

[Archived report](../../archive/legacy-workflows/movement-discovery/runs/1.14.4--1.15.2/findings/F05-honey-slide.md).

# F01: Ground friction samples a different block under tall partial surfaces

- **1.14.4**, mojmap: `net/minecraft/world/entity/LivingEntity.java`; lines 1837–1840.
- **1.15.2**, mojmap: `net/minecraft/world/entity/LivingEntity.java`; lines 1886–1889.

## Difference

For a six-layer snow state at block row `y`, its collision top is `y + 10/16`.

For a player standing on it, 1.14.4 constructs the friction lookup at `y - 6/16`, which floors to row `y - 1`. 1.15.2 constructs it at `y + 10/16 - 0.5000001`, which floors to row `y`.

If row `y - 1` is ice, 1.14.4 uses friction `0.98F`; 1.15.2 uses the snow block's default `0.6F`.

Under the grounded precondition, this changes both the friction-influenced input acceleration and the post-move horizontal retention.

The source establishes those formula inputs and operation order; the net position or velocity over multiple ticks depends on prior velocity and input.

## Movement meaning

Local player input -> `LivingEntity.aiStep` -> `travel(Vec3)` -> block lookup -> `getFrictionInfluencedSpeed` and grounded drag -> horizontal velocity.

The concrete state uses the existing snow-layer property, its collision shape and the existing ice friction registration on both sides.

The relevant friction block is locally present in the world state; no server-derived movement attribute is involved.

Source proves a different block row is sampled and gives different friction values for the stated precondition.

The old formula supplies `0.98F`; the new formula supplies `0.6F`.

This changes the per-call acceleration and drag terms.

[Archived report](../../archive/legacy-workflows/movement-discovery/runs/1.14.4--1.15.2/findings/F01-ground-friction-support-cell.md).

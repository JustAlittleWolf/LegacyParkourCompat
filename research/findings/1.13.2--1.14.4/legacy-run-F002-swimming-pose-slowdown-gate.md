# F002: Swimming state suppresses the pose-based sneak slowdown gate

- **1.13.2**, ornithe-feather: `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java`; line 597.
- **1.14.4**, ornithe-feather: [source locations in preserved report](../../archive/legacy-workflows/movement-discovery/runs/1.13.2--1.14.4/findings/F002-swimming-pose-slowdown-gate.md); lines 596–597.

## Difference

At the exact 1.14 base, `m_63723874()` checks `!abilities.flying && canPose(SNEAKING)` before testing the sneak key or whether standing fits.

In 1.14.4 it checks `!abilities.flying && !isSwimming() && canPose(SNEAKING)` before the same expression.

The surrounding `m_99544176()` alternative remains true for the swimming pose outside water, so this added guard specifically suppresses the first gate while the player is swimming; it does not remove the outside-water swimming-pose path.

## Movement meaning

The local player's `mobTick()` evaluates this predicate and passes it to `KeyboardInput.tick(...)`.

The gate is reachable for the local player when the player is swimming and the pose/collision conditions permit the sneaking-pose check.

The later flight-sneak compensation remains in the same player tick.

The source proves the endpoint expression change and confirms the guard is still absent in 1.14.1.

It predicts a changed input slowdown result for swimming players who otherwise satisfy the 1.14 base pose gate.

[Archived report](../../archive/legacy-workflows/movement-discovery/runs/1.13.2--1.14.4/findings/F002-swimming-pose-slowdown-gate.md).

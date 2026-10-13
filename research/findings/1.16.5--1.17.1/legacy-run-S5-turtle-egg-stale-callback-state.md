# Landing callbacks reuse a stale turtle egg state

- **1.16.5**, mojmap: `world/entity/Entity.java`; lines 517–534.
- **1.17.1**, mojmap: `world/entity/Entity.java`; lines 565–585.

## Difference

The resulting block differs: 1.16.5 has air; 1.17.1 retains one egg.

Both versions' `getShape` rules then give the surviving 1.17.1 block a nonempty one-egg collision shape.

## Movement meaning

The player movement path is `Entity.move` → `checkFallDamage` / `fallOn` → `stepOn`. 1.16.5 player is explicitly eligible in `canDestroyEgg`; `stepOn` is skipped when the player is stepping carefully.

The source condition is a server-side landing with positive accumulated fall distance, a two-or-more-egg state, both random checks passing, and the player not stepping carefully.

The source establishes a reachable block-state lifecycle difference and a resulting state-dependent collision-shape difference.

It does not establish an observed trajectory.

Under the project's one-way scope, do not treat the changed egg-count lifecycle as a block-state emulation task; retain the downstream collision dependency for the source audit.

[Archived report](../../archive/legacy-workflows/movement-discovery/runs/1.16.5--1.17.1/findings/S5-turtle-egg-stale-callback-state.md).

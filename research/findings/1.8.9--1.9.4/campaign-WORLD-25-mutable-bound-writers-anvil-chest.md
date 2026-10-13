# WORLD-25: mutable-bound writers and Anvil/Chest collision consumers

- **1.8.9**, ornithe-feather: `AnvilBlock.java`; `ChestBlock.java`.
- **1.9.4**, ornithe-feather: `AnvilBlock.java`; `ChestBlock.java`.

## Difference

- 1.8.9 source hashes: `AnvilBlock.java` ; `ChestBlock.java` ; `Block.java` ; `World.java` ; `Entity.java` .
- 1.9.4 source hashes: `AnvilBlock.java` ; `ChestBlock.java` ; `Block.java` ; `BlockState.java` ; `Entity.java` .

## Movement meaning

- Disposition: the bounded writer/consumer inventory is closed as a conditional source finding: Anvil/Chest effective 1.8.9 geometry is history/order-dependent; 1.9.4 geometry is query-state-dependent.

Retain that as the conditional boundary; do not infer stable per-state equivalence or a trajectory result.

The other six fixed WORLD-23 providers were separately closed only at provider geometry in WORLD-24.

[Archived report](../../archive/legacy-workflows/source-campaign-2026-10-07/1.8.9--1.9.4/findings/WORLD-25-mutable-bound-writers-anvil-chest.md).

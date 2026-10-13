# MOVE-002: Beds reverse a player's downward velocity on landing

- **1.11.2**, ornithe-feather: `1.11.2/ornithe-feather/net/minecraft/block/BedBlock.java`; line 29.
- **1.12.2**, ornithe-feather: `1.12.2/ornithe-feather/net/minecraft/block/BedBlock.java`; lines 141-149.

## Difference

When a player's downward movement is clipped by a bed, 1.11.2 calls the inherited no-op callback.

In 1.12.2, a non-sneaking bed callback reflects negative Y velocity and scales it by `0.66F`; the extra `0.8` scale is guarded by `!(entity instanceof LivingEntity)`, so it does not apply to a player. 1.11.2 sneaking player receives the inherited no-op.

The bed's collision shape remains the same 0.5625-high box in the inspected sources.

## Movement meaning

The player uses `Entity.move` during living travel and collision resolution. 1.11.2 landing that clips downward movement against a bed reaches the block callback.

The change writes player `velocityY`, directly affecting the movement state for the next tick.

The source does not establish a particular measured bounce height or trajectory.

Source-proven: on the stated non-sneaking landing condition, 1.12.2 changes negative player Y velocity into positive velocity at 0.66 magnitude; 1.11.2 does not.

Predicted consequence: the player rebounds from beds in 1.12.2.

Bed block state remains vanilla and this catalog does not alter modern-only block applicability.

[Archived report](../../archive/legacy-workflows/movement-discovery/runs/1.11.2--1.12.2/findings/MOVE-002-bed-bounce.md).

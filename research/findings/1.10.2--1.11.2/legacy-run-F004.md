# F004: Farmland conversion raises intersecting entities to dirt top

- **1.10.2**, ornithe-feather: `1.10.2/ornithe-feather/net/minecraft/block/FarmlandBlock.java`.
- **1.11.2**, ornithe-feather: `1.11.2/ornithe-feather/net/minecraft/block/FarmlandBlock.java`; lines 71–79.

## Difference

On each listed farmland-to-dirt path, 1.11.2 explicitly relocates every entity returned in the replacement block collision box to the new collision top; 1.10.2 only replaces the block state.

## Movement meaning

Player overlaps a farmland block as it converts to dirt -> block callback -> entity position update.

Dirt has a full-block collision top. `onFallenOn` conversion is server-gated; this finding does not assert a local client independently performs authoritative relocation.

1.11.2 sets the overlapping entity's vertical position to the block top, which can alter player position.

[Archived report](../../archive/legacy-workflows/movement-discovery/runs/1.10.2--1.11.2/findings/F004.md).

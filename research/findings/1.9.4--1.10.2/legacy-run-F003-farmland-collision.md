# F003: Farmland collision height reduced to its 15/16 shape

- **1.9.4**, ornithe-feather: `1.9.4/ornithe-feather/net/minecraft/block/FarmlandBlock.java`; lines 32–34.
- **1.10.2**, ornithe-feather: `1.10.2/ornithe-feather/net/minecraft/block/FarmlandBlock.java`; lines 32–34.

## Difference

For farmland in 1.9.4, collision queries use a full `1.0`-high box even though `getShape` is `0.9375` high.

In 1.10.2 the special collision override is removed, so collision queries use the `0.9375`-high shape.

The block and its state existed at both endpoints; this is a collision behavior change, not a newly introduced block.

## Movement meaning

Player movement calls `Entity.move`; the unchanged `World.getCollisions` scan gets each block state's collision shape and passes it to `Entity.move` for axis clipping.

Under the same farmland state and neighboring world data, 1.9.4 supplies a full cube while 1.10.2 supplies a box ending at `y + 0.9375`.

The one-sixteenth vertical difference can change the collision stop/support surface and consequently the player's resolved Y/on-ground state.

Auto-jump scans also query collision boxes, so F001 may interact with this shape where farmland is in its scan path.

World block states are supplied by the client world/server; this finding does not rewrite them.

Source proves the collision-box height differs by `0.0625` blocks.

[Archived report](../../archive/legacy-workflows/movement-discovery/runs/1.9.4--1.10.2/findings/F003-farmland-collision.md).

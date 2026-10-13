# Big Dripleaf contact changes its collision shape

- **1.16.5**, mojmap: [source locations in preserved report](../../archive/legacy-workflows/movement-discovery/runs/1.16.5--1.17.1/findings/S4-big-dripleaf-tilt-collision-shape.md).
- **1.17.1**, mojmap: `world/level/block/BigDripleafBlock.java`.

## Difference

1.16.5 grounded player can trigger the 1.17.1-only contact callback on an unpowered Big Dripleaf.

The scheduled tilt progression lowers and then removes the leaf collision shape, which changes the surface that supports the player.

The generic collision consumer is shared across versions; the provider and block are new in 1.17.1.

## Movement meaning

This is a confirmed movement-affecting mechanic in 1.17.1, but the block is absent from 1.16.5.

Under the one-way compatibility scope, it requires no historical emulation for 1.16.5-era maps.

It is retained to document the 1.17.1-only callback owner's movement consequence and does not close the broader collision or callback inventories.

[Archived report](../../archive/legacy-workflows/movement-discovery/runs/1.16.5--1.17.1/findings/S4-big-dripleaf-tilt-collision-shape.md).

# Bubble Column movement persists after its support is removed in 1.17.1

- **1.16.5**, mojmap: `world/level/block/BubbleColumnBlock.java`.
- **1.17.1**, mojmap: `BubbleColumnBlock.java`.

## Difference

When the support changes to an unsupported block, 1.16.5 removes the Bubble Column state during the neighbor update. 1.17.1 keeps the column state until its scheduled five-tick repair, then converts the unsupported column and its connected upper run to water.

If a player overlaps the column during that interval, 1.17.1 continues applying the bubble-column vertical velocity behavior while 1.16.5 has already stopped doing so.

## Movement meaning

The different state and callback window is source-confirmed and can affect player vertical motion.

No particular trajectory, block-removal mechanism or observed gameplay result is asserted.

[Archived report](../../archive/legacy-workflows/movement-discovery/runs/1.16.5--1.17.1/findings/S3-bubble-column-support-loss-delay.md).

# Honey block callback range inset changes

1.19.2, Mojmap: `net/minecraft/world/entity/Entity.java`, `checkInsideBlocks`, lines 886-895.
1.19.3, Mojmap: `net/minecraft/world/entity/Entity.java`, `checkInsideBlocks`, lines 894-903.

Difference: 1.19.2 converts bounds from the entity box using `+/- 0.001`; 1.19.3 uses `+/- 1.0E-7`. Both then check that the complete inclusive block range is loaded before looping and calling `BlockState.entityInside`. The `1.0E-7` inset remains in 1.19.4 through 1.21.1, with `BlockPos.containing` used from 1.19.4. The 1.21.2 path model is a further caller change described separately in `honey-callback-cadence.md`.

Movement meaning: for a player bounding-box face near a block-coordinate boundary, the smaller inset can include or exclude a boundary block differently, changing whether its per-block callback is reached. This is an applicability difference independent of when callbacks run.
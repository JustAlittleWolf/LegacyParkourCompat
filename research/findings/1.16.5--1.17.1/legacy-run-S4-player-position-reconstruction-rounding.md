# Entity movement reconstructs horizontal position in a different order

- **1.16.5**, mojmap: `world/entity/Entity.java`; lines 487–489.
- **1.17.1**, mojmap: [source locations in preserved report](../../archive/legacy-workflows/movement-discovery/runs/1.16.5--1.17.1/findings/S4-player-position-reconstruction-rounding.md); lines 534–536.

## Difference

For a collision result with nonzero horizontal movement, 1.16.5 first rounds each translated box endpoint and then rounds their midpoint to recover player position. 1.17.1 adds the movement component directly to the stored coordinate, then builds a new box around that coordinate.

For the player width `0.6F`, at X `0.1` with horizontal displacement `0.1`, those source expressions produce 1.16.5 X `0.19999999999999998` and 1.17.1 X `0.2` under IEEE-754 double arithmetic.

The expression order is source-proven; the numeric example illustrates its rounding consequence.

## Movement meaning

The path is the shared player `Entity.move` implementation in open space or after collision clipping. 1.16.5 uses the reconstructed position in `setPosRaw`; 1.17.1 stores the direct sum through `setPos`.

The player pose width used to reconstruct each box is equal in both endpoints, so the difference is the coordinate calculation order rather than a pose-size change.

The source and arithmetic establish a possible one-ULP X/Z position delta for a reachable player move.

They do not establish a persistent trajectory or a block-boundary crossing in a particular scene.

[Archived report](../../archive/legacy-workflows/movement-discovery/runs/1.16.5--1.17.1/findings/S4-player-position-reconstruction-rounding.md).

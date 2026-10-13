# Fluid pushing uses a wider unloaded-chunk guard in 1.17.1

- **1.16.5**, mojmap: `world/entity/Entity.java`.
- **1.17.1**, mojmap: `Entity.java`.

## Difference

For equivalent fluid and block states under the player's actual sampled AABB, 1.16.5 can process fluid flow while all chunks covering that scan range are loaded. 1.17.1 also requires every chunk touched by a one-block horizontal expansion of the player's bounding box to be loaded.

If only that wider margin reaches an unloaded neighbor chunk, 1.17.1 skips the entire fluid-height/pushing calculation for the tick, while 1.16.5 can add the nonzero sampled fluid vector to the player's delta movement.

## Movement meaning

This is a direct source-level difference in player velocity handling for equivalent vanilla fluid states, conditioned on chunk availability near a boundary.

The guard is shared Entity code, not a world-state lifecycle inference.

[Archived report](../../archive/legacy-workflows/movement-discovery/runs/1.16.5--1.17.1/findings/S3-fluid-pushing-unloaded-chunk-guard.md).

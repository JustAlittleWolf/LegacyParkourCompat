# Fluid pushing uses a wider unloaded-chunk guard in B

- Older version A: `1.16.5`
- Newer version B: `1.17.1`
- Mechanic / coverage slice IDs: `S3-TRAVEL-BRANCHES`, `S3-04-TRAVEL-BRANCH-DEPENDENCIES`, `S7-EXTERNAL-STATE`
- Classification: player fluid-velocity update is skipped for a wider unloaded-chunk margin
- Confidence: source-confirmed
- Applicability: a player in flowing water or lava near a chunk border, with the player's sampled AABB chunks loaded but an adjacent chunk inside B's one-block inflated guard unloaded
- First changed release: unknown within (1.16.5, 1.17.1]
- Runtime validation: not performed

## Paired evidence

- A `world/entity/Entity.java`, `updateFluidHeightAndDoFluidPushing(...)`, SHA-256 `F9A9A073FE3105A0AA53D0F21EC72E59084E8D21A14C1CD3BE75703865EE2666`, deflates the entity bounding box by `0.001`, computes its block-coordinate range, and returns early only when `level.hasChunksAt(i, k, m, j, l, n)` is false. Otherwise, for matching fluid states, it samples `FluidState.getFlow(...)` and adds the averaged/scaled flow to delta movement. `isPushedByFluid()` returns true in A.
- B `Entity.java`, SHA-256 `AB28E1FBA924771EC048140DFD293EE5A46A7DFE81F71A1A0B1AECC1927232DE`, first calls `touchingUnloadedChunk()` and returns false when that is true. B's helper inflates the entity bounding box by `1.0` and tests chunk availability over that wider horizontal range. The remaining fluid scan, flow averaging, player non-normalization, scaling and delta-movement addition match A in the reviewed method. `isPushedByFluid()` also returns true in B.
- Both versions' `FlowingFluid.getFlow(...)` use the same horizontal gradient and falling-fluid adjustment; the source hashes are recorded in the run manifest. Both versions' `LevelReader.hasChunksAt(...)` implementations are likewise cited there.

## Source-level difference

For equivalent fluid and block states under the player's actual sampled AABB, A can process fluid flow while all chunks covering that scan range are loaded. B also requires every chunk touched by a one-block horizontal expansion of the player's bounding box to be loaded. If only that wider margin reaches an unloaded neighbor chunk, B skips the entire fluid-height/pushing calculation for the tick, while A can add the nonzero sampled fluid vector to the player's delta movement.

## Consequence and uncertainty

This is a direct source-level difference in player velocity handling for equivalent vanilla fluid states, conditioned on chunk availability near a boundary. The guard is shared Entity code, not a world-state lifecycle inference. No particular server chunk-loading configuration or observed trajectory is asserted; runtime validation was not performed.

## Handoff

Keep the broader S3 water/lava travel and unloaded-chunk external-state audits open. This finding closes only the bounded fluid-pushing guard comparison.
